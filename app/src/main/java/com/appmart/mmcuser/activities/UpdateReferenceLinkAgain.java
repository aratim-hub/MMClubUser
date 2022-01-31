package com.appmart.mmcuser.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.sharedPreference.Host_Details_SharedPref;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;
import com.appmart.mmcuser.utils.ConstantMethods;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.UPDATE_REFERAL_LINK_AGAIN;

public class UpdateReferenceLinkAgain extends AppCompatActivity {
    TextView actionBarTitle;
    EditText edtReferalLink;
    Button btnUploadPaymentReceipt, btnSubmitPaymentReceipt;
    ImageView imgPaymentReceipt;
    String COMPANY_NAME,LINK_ID;
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private String IMAGE = "";
    private boolean success = false;
    String messege = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_reference_link);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        COMPANY_NAME = getIntent().getStringExtra("CompanyName");
        LINK_ID = getIntent().getStringExtra("link_id");

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        getSupportActionBar().setElevation(0);
        View viewCustomActionBar = getSupportActionBar().getCustomView();
        actionBarTitle = viewCustomActionBar.findViewById(R.id.actionbarTitle);
        actionBarTitle.setText("Update Link-" + COMPANY_NAME);

        //Change Status bar colour
//        Window window = UpdateReferenceLinkAgain.this.getWindow();
//        window.setStatusBarColor(ContextCompat.getColor(UpdateReferenceLinkAgain.this, R.color.colorPrimaryDark));


        edtReferalLink = findViewById(R.id.edtReferalLink);
        btnUploadPaymentReceipt = findViewById(R.id.btnUploadPaymentReceipt);
        btnSubmitPaymentReceipt = findViewById(R.id.btnSubmitPaymentReceipt);
        imgPaymentReceipt = findViewById(R.id.imgPaymentReceipt);

        btnUploadPaymentReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtReferalLink.getText().toString().equals("")) {
                    Toast.makeText(UpdateReferenceLinkAgain.this, "Enter or paste Link first", Toast.LENGTH_SHORT).show();
                    return;
                }
                showFileChooser();
            }
        });
        btnSubmitPaymentReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadToServer();
            }
        });
    }

    private void uploadToServer() {
        ConstantMethods.loaderDialog(UpdateReferenceLinkAgain.this);
        final String user_id = Profile_SharedPref.getInstance(UpdateReferenceLinkAgain.this).getUserId();
        final String host_id = Host_Details_SharedPref.getInstance(UpdateReferenceLinkAgain.this).getHOST_ID();
        final String referal_link = edtReferalLink.getText().toString();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        final String datee = formatter.format(date);
        try {
           IMAGE = getStringImage(bitmap);
        } catch (Exception E) {
            Toast.makeText(UpdateReferenceLinkAgain.this, "Upload proof First", Toast.LENGTH_SHORT).show();
            IMAGE = "";
            return;
        }

        final String finalPhotoUrl = COMPANY_NAME + user_id;

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();

                RequestBody requestBody = new FormBody.Builder()

                        .add("user_id", user_id)
                        .add("link_id", LINK_ID)
                        .add("company", COMPANY_NAME)
                        .add("ref_link", referal_link)
                        .add("datee", datee)
                        .add("name", finalPhotoUrl)
                        .add("image", IMAGE)
                        .build();

                Request request = new Request.Builder()
                        .url(UPDATE_REFERAL_LINK_AGAIN)
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONObject object = new JSONObject(response.body().string());
                    success = object.getBoolean("success");
                    messege = object.getString("messege");


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                ConstantMethods.hideLoaderDialog(UpdateReferenceLinkAgain.this);
                try {
                    if (success) {
                        Toast.makeText(UpdateReferenceLinkAgain.this, "Upload Success", Toast.LENGTH_SHORT).show();
finish();
                        if (messege.equalsIgnoreCase("Upload Success")) {

                        }
                    }
                } catch (Exception e) {

                }
            }
        };

        task.execute();
    }


    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == UpdateReferenceLinkAgain.this.RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                imgPaymentReceipt.setVisibility(View.VISIBLE);
                bitmap = MediaStore.Images.Media.getBitmap(UpdateReferenceLinkAgain.this.getContentResolver(), filePath);
                imgPaymentReceipt.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private String getStringImage(Bitmap bmp) {
        String encodedImage = null;

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        } catch (OutOfMemoryError error) {
            ConstantMethods.showAlertMessege(UpdateReferenceLinkAgain.this,"Out of memory", "Please select another image");
        }
        return encodedImage;

    }

}
