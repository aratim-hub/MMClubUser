package com.appmart.mmcuser.fragments;

import android.graphics.Bitmap;
import android.graphics.Color;
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

import static com.appmart.mmcuser.utils.ServerAddress.GENERATE_NEXMONEY_COUPONS;

public class AffidaviteLetterCopy extends Fragment {
    private WebView WebViewLetterContent;
    private ProgressBar progressBar;
    private String success = "false";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.affidavit_letter_copy, container, false);
        ((Home) getActivity()).setActionBarTitle("Affidavit Letter");
        WebViewLetterContent = view.findViewById(R.id.WebViewLetterContent);


        progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);

        WebViewLetterContent.setWebViewClient(new myWebClient());
        WebViewLetterContent.getSettings().setJavaScriptEnabled(true);// enable javascript
        WebViewLetterContent.loadUrl("https://manageapp.mmclub.in/user/affidavit_letter.html");



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