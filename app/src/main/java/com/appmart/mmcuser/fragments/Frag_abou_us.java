package com.appmart.mmcuser.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.activities.Home;

public class Frag_abou_us extends Fragment {
    private WebView mywebview;
    private ProgressBar progressBar;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_about_us, container, false);
        ((Home) getActivity()).setActionBarTitle(getString(R.string.about_us));

        mywebview = view.findViewById(R.id.webView);

        progressBar = (ProgressBar)view. findViewById(R.id.progressBar1);

        mywebview.setWebViewClient(new myWebClient());
        mywebview.getSettings().setJavaScriptEnabled(true);// enable javascript
        mywebview.loadUrl("https://manageadmin.mmclub.in/about_us.php");

        return view;

    }

    public class myWebClient extends WebViewClient
    {
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
