package com.appmart.mmcuser.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.sharedPreference.General_SharedPref;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;
import com.appmart.mmcuser.utils.ConstantMethods;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.LOGIN_USER;
import static com.appmart.mmcuser.utils.ServerAddress.WITHDRAWAL_REQUEST_URL;


public class WalletBalance extends Fragment {
    private TextView txtWalletBalance;
    private String wallet_balance, withdraw_amount, mobile_number, withdraw_on;
    private TextInputEditText edtWithdrawAmount, edtMobileNumber;
    private Button btnWithdraw, btnWithdrawHistory, btnWalletHistory;
    private RadioGroup rdgrpWithdrawalOn;
    private Fragment menu_Frag;

    private boolean success;
    private String id, f_name, l_name, email, mobile, whatsapp_no, p_code, city, sponsor_id, status, is_business_start, created_at, multisuccess_intro_video,multisuccess_path_video;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wallet_balance, container, false);

        mobile_number = Profile_SharedPref.getInstance(getContext()).getMobile();
        wallet_balance = Profile_SharedPref.getInstance(getContext()).getWALLETE_BALANCE();

        txtWalletBalance = view.findViewById(R.id.txtWalletBalance);
        txtWalletBalance.setText("₹ " + wallet_balance + " /-");

        edtWithdrawAmount = view.findViewById(R.id.edtWithdrawAmount);
        edtMobileNumber = view.findViewById(R.id.edtMobileNumber);
        btnWithdraw = view.findViewById(R.id.btnWithdraw);
        rdgrpWithdrawalOn = view.findViewById(R.id.rdgrpWithdrawalOn);
        btnWithdrawHistory = view.findViewById(R.id.btnWithdrawHistory);
        btnWalletHistory = view.findViewById(R.id.btnWalletHistory);


        edtMobileNumber.setText(mobile_number);

        withdraw_on = "Paytm";

        btnWalletHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu_Frag = new WalletHistoryList();
                loadFragment();

            }
        });
        btnWithdrawHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu_Frag = new WithdrawHistoryList();
                loadFragment();

            }
        });
        getProfileDetails();
        rdgrpWithdrawalOn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.Paytm:
                        withdraw_on = "Paytm";
                        break;

                    case R.id.GooglePay:
                        withdraw_on = "GooglePay";
                        break;

                    case R.id.PhonePe:
                        withdraw_on = "PhonePe";
                        break;


                }
            }
        });

        btnWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                withdraw_amount = edtWithdrawAmount.getText().toString();
                if (withdraw_amount.length() != 0) {
                    if (499 < Integer.parseInt(withdraw_amount) && Integer.parseInt(withdraw_amount) <= Integer.parseInt(wallet_balance)) {
                        withdrwalRequest();
                    } else if (Integer.parseInt(withdraw_amount) >= Integer.parseInt(wallet_balance)) {
                        ConstantMethods.showAlertMessege(getContext(), "Warning", "Withdraw Amount should be less than Wallet Balance");
                    } else if (Integer.parseInt(withdraw_amount) < 500) {
                        ConstantMethods.showAlertMessege(getContext(), "Warning", "Withdraw Amount should be greater than 500");

                    }
                } else {
                    ConstantMethods.showAlertMessege(getContext(), "Warning", "Withdrawl Amount Should not be empty");

                }
            }
        });

        return view;
    }

    private void loadFragment() {
        if (menu_Frag != null) {
            FragmentManager FM = getFragmentManager();
            FM.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.main_Container, menu_Frag)
                    .addToBackStack(String.valueOf(FM))
                    .commit();
        }
    }

    private void getProfileDetails() {

        final String mobile_number = Profile_SharedPref.getInstance(getContext()).getMobile();

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();

                RequestBody requestBody = new FormBody.Builder()
                        .add("mobile", mobile_number)
                        .build();

                Request request = new Request.Builder()
                        .url(LOGIN_USER)
                        .post(requestBody)
                        .build();

                try {
                    Response response = client.newCall(request).execute();

                    JSONObject object = new JSONObject(response.body().string());
                    success = object.getBoolean("success");
                    id = object.getString("id");
                    f_name = object.getString("f_name");
                    l_name = object.getString("l_name");
                    mobile = object.getString("mobile");
                    email = object.getString("email");
                    whatsapp_no = object.getString("whatsapp_no");
                    city = object.getString("city");
                    p_code = object.getString("p_code");
                    sponsor_id = object.getString("sponsor_id");
                    status = object.getString("status");
                    created_at = object.getString("created_at");
                    is_business_start = object.getString("is_business_start");
                    wallet_balance = object.getString("wallet_balance");
                    multisuccess_intro_video = object.getString("multisuccess_intro_video");
                    multisuccess_path_video = object.getString("multisuccess_path_video");

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (success) {
                    txtWalletBalance.setText("₹ " + wallet_balance + " /-");

                    Profile_SharedPref.getInstance(getContext()).saveProfile(id, f_name, l_name, mobile, email, whatsapp_no, city, p_code, sponsor_id, created_at, multisuccess_intro_video, is_business_start, wallet_balance,multisuccess_path_video);
                    General_SharedPref.getInstance(getContext()).saveJoiningStatus(status);


                } else {
                }
            }
        };

        task.execute();

    }

    private void withdrwalRequest() {
        ConstantMethods.loaderDialog(getContext());
        final Long time_in_millis = System.currentTimeMillis();
        final String user_id = Profile_SharedPref.getInstance(getContext()).getUserId();

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();

                RequestBody requestBody = new FormBody.Builder()
                        .add("user_id", user_id)
                        .add("mobile_number", edtMobileNumber.getText().toString())
                        .add("withdrawal_amount", edtWithdrawAmount.getText().toString())
                        .add("withdraw_on", withdraw_on)
                        .add("time_in_millis", String.valueOf(time_in_millis))
                        .build();

                Request request = new Request.Builder()
                        .url(WITHDRAWAL_REQUEST_URL)
                        .post(requestBody)
                        .build();

                try {
                    okhttp3.Response response = client.newCall(request).execute();

                    JSONObject object = new JSONObject(response.body().string());

                    success = object.getBoolean("success");
                } catch (JSONException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                ConstantMethods.hideLoaderDialog(getContext());
                getProfileDetails();
                if (success) {
                    // Service for getting Wallet Balance
//                    getActivity().startService(new Intent(getContext(), WalletBalanceService.class));
                    ConstantMethods.showAlertMessege(getContext(), "Success", "Withdrawal request made successfully. Wait for Admin confirmation");
                } else {
                    ConstantMethods.showAlertMessege(getContext(), "Error", "Try again after some time");

                }
            }
        };
        task.execute();
    }
}
