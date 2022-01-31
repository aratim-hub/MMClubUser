package com.appmart.mmcuser.fragments;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.activities.Home;
import com.appmart.mmcuser.activities.RegisterActivity;
import com.appmart.mmcuser.sharedPreference.General_SharedPref;
import com.appmart.mmcuser.sharedPreference.Host_Details_SharedPref;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;
import com.appmart.mmcuser.utils.ConstantMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.REGISTER_USER;
import static com.appmart.mmcuser.utils.ServerAddress.UPDATE_WHATSAPP_NUMBER;

public class FragProfile extends Fragment {
    String status;
    private TextView txtName, txtEmail, txtMobile, txtWhatsApp, txtCityPincode, txtHostName, txtJoiningDate, txtCurrentStatus;
    private ProgressBar pb3;
    Button btnChangeWANumber;
    private boolean success;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_profile, container, false);
        ((Home) getActivity()).setActionBarTitle(getString(R.string.profile));
        setHasOptionsMenu(true);
        txtName = view.findViewById(R.id.txtName);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtMobile = view.findViewById(R.id.txtMobile);
        txtWhatsApp = view.findViewById(R.id.txtWhatsApp);
        txtCityPincode = view.findViewById(R.id.txtCityPincode);
        txtHostName = view.findViewById(R.id.txtHostName);
        txtJoiningDate = view.findViewById(R.id.txtJoiningDate);
        txtCurrentStatus = view.findViewById(R.id.txtCurrentStatus);
        btnChangeWANumber = view.findViewById(R.id.btnChangeWANumber);
        pb3 = view.findViewById(R.id.pb3);

        txtName.setText(Profile_SharedPref.getInstance(getContext()).getFName() + " " + Profile_SharedPref.getInstance(getContext()).getLName());
        txtEmail.setText(Profile_SharedPref.getInstance(getContext()).getEmailId());
        txtMobile.setText(Profile_SharedPref.getInstance(getContext()).getMobile());
        txtWhatsApp.setText(Profile_SharedPref.getInstance(getContext()).getWhatsAppNumber());
        txtJoiningDate.setText(Profile_SharedPref.getInstance(getContext()).getJoiningDate());
        txtCityPincode.setText(Profile_SharedPref.getInstance(getContext()).getCity() + ", " + Profile_SharedPref.getInstance(getContext()).getPincode());
        txtHostName.setText(Host_Details_SharedPref.getInstance(getContext()).getHOST_NAME());


        pb3.setProgress(5);
        getStatus();
        
        
        btnChangeWANumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.change_whatsappnumber_dialog);
                dialog.setCancelable(true);

                final EditText edtWhatsAppNumber = dialog.findViewById(R.id.edtWhatsAppNumber);
                Button btnSave = dialog.findViewById(R.id.btnSave);
                Button btnCancel = dialog.findViewById(R.id.btnCancel);

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateWANumber(edtWhatsAppNumber.getText().toString());
dialog.dismiss();
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        return view;
    }

    private void updateWANumber(final String waNumber) {

        ConstantMethods.loaderDialog(getContext());
        final String userid = Profile_SharedPref.getInstance(getContext()).getUserId();
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();

                RequestBody requestBody = new FormBody.Builder()
                        .add("user_id",userid)
                        .add("whatsappnumber",waNumber)
                        .build();

                Request request = new Request.Builder()
                        .url(UPDATE_WHATSAPP_NUMBER)
                        .post(requestBody)
                        .build();

                try {
                    Response response = client.newCall(request).execute();

                    JSONObject object = new JSONObject(response.body().string());
                    success = object.getBoolean("success");

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                ConstantMethods.hideLoaderDialog(getContext());
                if (success) {
                    Toast.makeText(getContext(), "WhatsApp Number Changed Successfully...!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), "Please try Again later", Toast.LENGTH_SHORT).show();
                }
            }
        };

        task.execute();

    }

    private void getStatus() {
        String S = General_SharedPref.getInstance(getContext()).getJoingStatus();
        int percentage = (100 * Integer.parseInt(S)) / 12;
        txtCurrentStatus.setText(percentage + "% Profile Completed");
        pb3.setProgress(Integer.parseInt(S));

    }

}
