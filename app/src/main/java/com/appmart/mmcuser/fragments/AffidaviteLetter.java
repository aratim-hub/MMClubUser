package com.appmart.mmcuser.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.UpdateStatus;
import com.appmart.mmcuser.activities.Home;
import com.appmart.mmcuser.activities.UpdateReferenceLink;
import com.appmart.mmcuser.activities.YoutubePlayVideoIntroduction;
import com.appmart.mmcuser.sharedPreference.General_SharedPref;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;
import com.appmart.mmcuser.utils.ConstantMethods;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.appmart.mmcuser.GetWALink.WHATSAPP_LINK;
import static com.appmart.mmcuser.utils.ServerAddress.GENERATE_NEXMONEY_COUPONS;

public class AffidaviteLetter extends Fragment {
    private WebView WebViewLetterContent;
    private Button btnAcceptLetter;
    private ProgressBar progressBar;
    private String success = "false";
    CheckBox chkConfirmAccept;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.affidavit_letter, container, false);
        ((Home) getActivity()).setActionBarTitle("Affidavit Letter");
        WebViewLetterContent = view.findViewById(R.id.WebViewLetterContent);
        chkConfirmAccept = view.findViewById(R.id.chkConfirmAccept);


        progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);

        WebViewLetterContent.setWebViewClient(new myWebClient());
        WebViewLetterContent.getSettings().setJavaScriptEnabled(true);// enable javascript
        WebViewLetterContent.loadUrl("https://manageapp.mmclub.in/user/affidavit_letter.html");

        btnAcceptLetter = view.findViewById(R.id.btnAcceptLetter);

        btnAcceptLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkConfirmAccept.isChecked()) {

                    UpdateStatus updateStatus = new UpdateStatus(getContext(), "6");
                    General_SharedPref.getInstance(getContext()).saveJoiningStatus("6");

                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction trans = manager.beginTransaction();
                    trans.commit();
                    manager.popBackStack();
                    getContext().sendBroadcast(new Intent("RELOAD_GET_STATUS_METHOD"));

                }else{
                    Toast.makeText(getContext(), "Please tick on Checkbox first ", Toast.LENGTH_LONG).show();
                }

            }
        });

        return view;
    }

    @Override
    public void onDetach() {
        ((Home) getActivity()).setActionBarTitle("Home");
        super.onDetach();
    }

    public class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            progressBar.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            progressBar.setVisibility(View.GONE);
        }
    }
}