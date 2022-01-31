package com.appmart.mmcuser.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.activities.Home;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;
import com.appmart.mmcuser.utils.ConstantMethods;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.appmart.mmcuser.utils.ServerAddress.UPDATE_PAYMENT_APPROVAL;
import static com.appmart.mmcuser.utils.ServerAddress.USER_BASE_URL;

public class ViewPaymentProof extends Fragment {
    ImageView imgimgPaymentProof;
    Button btnApprove;
    String link_id, payment_proof;
    String hideButton = "false";
    private boolean success=false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_payment_proof, container, false);
        ((Home) getActivity()).setActionBarTitle(getString(R.string.view_payment_proof));
        imgimgPaymentProof = view.findViewById(R.id.imgPaymentProof);
        btnApprove = view.findViewById(R.id.btnApprove);

         link_id= getArguments().getString("link_id");
        payment_proof = getArguments().getString("payment_proof");
        hideButton = getArguments().getString("hideButton");

        ConstantMethods.loaderDialog(getContext());
        Glide.with(this)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)

                .load(USER_BASE_URL + payment_proof)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        ConstantMethods.hideLoaderDialog(getContext());
                        imgimgPaymentProof.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateToServer();
            }
        });
        try {
            if (hideButton.equals("true")) {
                btnApprove.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e) {

        }
        Toast.makeText(getContext(), "Please wait, It will take some time to load Payment Proof", Toast.LENGTH_LONG).show();
        return view;
    }

    private void updateToServer() {

        ConstantMethods.loaderDialog(getContext());
        final String user_id = Profile_SharedPref.getInstance(getContext()).getUserId();
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();

                RequestBody requestBody = new FormBody.Builder()
                        .add("link_id", link_id)
                        .add("user_id", user_id)
                        .build();

                Request request = new Request.Builder()
                        .url(UPDATE_PAYMENT_APPROVAL)
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONObject object= new JSONObject(response.body().string());
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
                    Toast.makeText(getContext(), "Payment Receipt Approved Successfully", Toast.LENGTH_SHORT).show();

                }
            }
        };

        task.execute();

    }
}
