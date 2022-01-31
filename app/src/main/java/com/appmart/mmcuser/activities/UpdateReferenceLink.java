package com.appmart.mmcuser.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appmart.mmcuser.GetMyReferalLinks;
import com.appmart.mmcuser.R;
import com.appmart.mmcuser.UpdateStatus;
import com.appmart.mmcuser.services.GetLinkRequestDetails;
import com.appmart.mmcuser.sharedPreference.General_SharedPref;
import com.appmart.mmcuser.sharedPreference.Host_Details_SharedPref;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;
import com.appmart.mmcuser.utils.ConstantMethods;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.UPDATE_REFERAL_LINK;

public class UpdateReferenceLink extends AppCompatActivity {
    TextView actionBarTitle;
    EditText edtReferalLink;
    Button btnUploadPaymentReceipt, btnSubmitPaymentReceipt;
    ImageView imgPaymentReceipt;
    String COMPANY_NAME;
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

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        getSupportActionBar().setElevation(0);
        View viewCustomActionBar = getSupportActionBar().getCustomView();
        actionBarTitle = viewCustomActionBar.findViewById(R.id.actionbarTitle);
        actionBarTitle.setText("Update Link/ Sponsor Id-" + COMPANY_NAME);

        //Change Status bar colour
        Window window = UpdateReferenceLink.this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(UpdateReferenceLink.this, R.color.colorPrimaryDark));
        }


        edtReferalLink = findViewById(R.id.edtReferalLink);
        btnUploadPaymentReceipt = findViewById(R.id.btnUploadPaymentReceipt);
        btnSubmitPaymentReceipt = findViewById(R.id.btnSubmitPaymentReceipt);
        imgPaymentReceipt = findViewById(R.id.imgPaymentReceipt);

        btnUploadPaymentReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtReferalLink.getText().toString().equals("")) {
                    Toast.makeText(UpdateReferenceLink.this, "Enter or paste Link/Sponsor Id first", Toast.LENGTH_LONG).show();
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
        if (COMPANY_NAME.equals("NexMoney")) {
            edtReferalLink.setHint("NexMoney SELF Sponsor Link   ");
            btnUploadPaymentReceipt.setText("Select NexMoney Profile Pic");
            imgPaymentReceipt.setImageResource(R.mipmap.nexmoney_proof);

        } else if (COMPANY_NAME.equals("RenatusNova")) {
            edtReferalLink.setHint("Renatus Nova SELF Sponsor ID");
            imgPaymentReceipt.setImageResource(R.mipmap.renatus_proof);

        }else if (COMPANY_NAME.equals("OkLifeCare")) {
            edtReferalLink.setHint("OkLifeCare SELF Sponsor ID & Payment Proof");
            imgPaymentReceipt.setImageResource(R.mipmap.oklifecare_proof);

        }else if (COMPANY_NAME.equals("HHI")) {
            edtReferalLink.setHint("HHI SELF Sponsor ID & Payment Proof");
            imgPaymentReceipt.setImageResource(R.mipmap.hhi_proof);

        }
    }

    private void uploadToServer() {
        ConstantMethods.loaderDialog(UpdateReferenceLink.this);
        final String user_id = Profile_SharedPref.getInstance(UpdateReferenceLink.this).getUserId();
        final String host_id = Host_Details_SharedPref.getInstance(UpdateReferenceLink.this).getHOST_ID();
        final String referal_link = edtReferalLink.getText().toString();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        final String datee = formatter.format(date);
        try {
            IMAGE = getStringImage(bitmap);
        } catch (Exception E) {
            Toast.makeText(UpdateReferenceLink.this, "Upload proof First", Toast.LENGTH_SHORT).show();
            IMAGE = "";
            return;
        }

        final String finalPhotoUrl = COMPANY_NAME + user_id;

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(120, TimeUnit.SECONDS)
                        .writeTimeout(120, TimeUnit.SECONDS)
                        .readTimeout(120, TimeUnit.SECONDS)
                        .build();

                RequestBody requestBody = new FormBody.Builder()

                        .add("user_id", user_id)
                        .add("host_id", host_id)
                        .add("company", COMPANY_NAME)
                        .add("ref_link", referal_link)
                        .add("datee", datee)
                        .add("name", finalPhotoUrl)
                        .add("image", IMAGE)
                        .build();

                Request request = new Request.Builder()
                        .url(UPDATE_REFERAL_LINK)
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
                ConstantMethods.hideLoaderDialog(UpdateReferenceLink.this);
                startService(new Intent(UpdateReferenceLink.this, GetLinkRequestDetails.class));
                new GetMyReferalLinks(UpdateReferenceLink.this);
                try {
                    if (success) {
                        if (messege.equalsIgnoreCase("Upload Success")) {
                            if (COMPANY_NAME.equals("HHI")) {
                                //This condition will exucute one time only to update steps progress
                                UpdateStatus updateStatus = new UpdateStatus(UpdateReferenceLink.this, "8");
                                General_SharedPref.getInstance(UpdateReferenceLink.this).saveJoiningStatus("8");
                                dialogJoinNexMoney();
                            } else if (COMPANY_NAME.equals("OkLifeCare")) {
                                UpdateStatus updateStatus = new UpdateStatus(UpdateReferenceLink.this, "9");
                                General_SharedPref.getInstance(UpdateReferenceLink.this).saveJoiningStatus("9");
//                                dialogTrainingVideo();
                                dialogWaitForSponsorPaymentApproval();
                            } else {
                                showDialog("Congratulations, Your payment Proof uploaded successfully");
                            }

                        } else if (messege.equalsIgnoreCase("Already Updated")) {
                            showDialog(getString(R.string.upload_response_failure));
                            if (COMPANY_NAME.equals("HHI")) {
                                UpdateStatus updateStatus = new UpdateStatus(UpdateReferenceLink.this, "8");
                                General_SharedPref.getInstance(UpdateReferenceLink.this).saveJoiningStatus("8");
                                dialogJoinNexMoney();

                            }
                            if (COMPANY_NAME.equals("OkLifeCare")) {
                                UpdateStatus updateStatus = new UpdateStatus(UpdateReferenceLink.this, "9");
                                General_SharedPref.getInstance(UpdateReferenceLink.this).saveJoiningStatus("9");
//                                dialogTrainingVideo();
                                dialogWaitForSponsorPaymentApproval();


                            }
                        }
                    }
                } catch (Exception e) {

                }

            }
        };

        task.execute();
    }

    private void dialogWaitForSponsorPaymentApproval() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateReferenceLink.this);
        // Set a title for alert dialog
        builder.setTitle(getString(R.string.imp_msg));
        // Show a message on alert dialog
//        builder.setMessage(getString(R.string.step_join_nexmoney));
        builder.setMessage("Your  Sponsor link & Pofile pic Uploaded Successfully \nPlease Wait for Payment Receipt Approval by Your Sponsor");
        // Set the positive button
//        builder.setPositiveButton(getString(R.string.join_nexmoney_now), null);
        builder.setPositiveButton("Okay", null);
        // Set the negative button
//        builder.setNegativeButton(getString(R.string.upload_link), null);
//        builder.setNegativeButton(getString(R.string.upload_link), null);

        // Create the alert dialog
        final AlertDialog dialog = builder.create();
        // Finally, display the alert dialog
        dialog.show();
        // Get the alert dialog buttons reference
        Button Yes = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

        // Change the alert dialog buttons text and background color
        Yes.setTextColor(Color.parseColor("#b90d86"));
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dialog.dismiss();
                    //This condition will be true if user logged in first Time, it results in play introducton video
                    finish();

                } catch (Exception e) {

                }
            }
        });

    }

    private void dialogJoinNexMoney() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateReferenceLink.this);
        // Set a title for alert dialog
        builder.setTitle("Uploaded Successfully");
        // Show a message on alert dialog
//        builder.setMessage(getString(R.string.step_join_nexmoney));
        builder.setMessage(getString(R.string.step_join_nexmoney));
        // Set the positive button
//        builder.setPositiveButton(getString(R.string.join_nexmoney_now), null);
        builder.setPositiveButton("Okay", null);
        // Set the negative button
//        builder.setNegativeButton(getString(R.string.upload_link), null);
//        builder.setNegativeButton(getString(R.string.upload_link), null);

        // Create the alert dialog
        final AlertDialog dialog = builder.create();
        // Finally, display the alert dialog
        dialog.show();
        // Get the alert dialog buttons reference
        Button Yes = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

        // Change the alert dialog buttons text and background color
        Yes.setTextColor(Color.parseColor("#b90d86"));
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dialog.dismiss();
                    //This condition will be true if user logged in first Time, it results in play introducton video
finish();
                    UpdateReferenceLink.this.sendBroadcast(new Intent("RELOAD_GET_STATUS_METHOD"));

                } catch (Exception e) {

                }
            }
        });

    }

    private void dialogTrainingVideo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateReferenceLink.this);
        // Set a title for alert dialog
        builder.setTitle(getString(R.string.imp_msg));
        // Show a message on alert dialog
        builder.setMessage(getString(R.string.step_join_training));
        // Set the positive button
        builder.setPositiveButton(getString(R.string.join_training_now), null);
        // Set the negative button
        builder.setNegativeButton(getString(R.string.later), null);
        // Create the alert dialog
        final AlertDialog dialog = builder.create();
        // Finally, display the alert dialog
        dialog.show();
        // Get the alert dialog buttons reference
        Button Yes = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button No = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        // Change the alert dialog buttons text and background color
        Yes.setTextColor(Color.parseColor("#b90d86"));
        No.setTextColor(Color.parseColor("#b90d86"));
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //This condition will be true if user logged in first Time, it results in play introducton video

                    Intent i = new Intent(UpdateReferenceLink.this, TrainningVideo.class);
                    startActivity(i);
                    dialog.dismiss();
                    finish();
                } catch (Exception e) {

                }
            }
        });
        No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                finish();
            }
        });
    }

    private void showDialog(String msg) {
        {
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateReferenceLink.this);
                // Set a title for alert dialog
                builder.setTitle(getString(R.string.mmessege));
                // Show a message on alert dialog
                builder.setMessage(msg);
                // Set the positive button
                builder.setPositiveButton("ok, got it", null);
                // Set the negative button

                // Create the alert dialog
                final AlertDialog dialog = builder.create();
                // Finally, display the alert dialog
                dialog.show();
                // Get the alert dialog buttons reference
                Button Yes = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                // Change the alert dialog buttons text and background color
                Yes.setTextColor(Color.parseColor("#b90d86"));
                Yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
//         dialog.setCancelable(false);
            }
        }
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

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == UpdateReferenceLink.this.RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                imgPaymentReceipt.setVisibility(View.VISIBLE);
                bitmap = MediaStore.Images.Media.getBitmap(UpdateReferenceLink.this.getContentResolver(), filePath);
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
            ConstantMethods.showAlertMessege(UpdateReferenceLink.this, "Out of memory", "Please select another image");
        }
        return encodedImage;

    }

}
