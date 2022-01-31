package com.appmart.mmcuser.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RadioButton;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.activities.Login;
import com.appmart.mmcuser.services.ConstantDataService;
import com.appmart.mmcuser.sharedPreference.Language_SharedPref;

import java.io.InputStream;
import java.net.URL;


public class ConstantMethods {
    private static Dialog loadingDialog;
    private static Dialog choose_language;
    private static Context context;
    private static androidx.appcompat.app.AlertDialog noInternetDialog;
    private static Dialog dialog,dialog_update;
    private static Button btnDownload;


    private static Context getApplicationConext() {
        context = getApplicationConext();
        return context;
    }
    public static void showUpdateDialog(final Context ctx) {


            dialog_update = new Dialog(ctx);
            dialog_update.setContentView(R.layout.dialog_update_available);
            dialog_update.setCancelable(true);

            btnDownload = dialog_update.findViewById(R.id.btnDownload);
            btnDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.appmart.mmcuser");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    ctx.startActivity(intent);
                    ctx.startService(new Intent(ctx, ConstantDataService.class));

                }
            });

            dialog_update.show();

    }

    public static void loaderDialog(Context context) {
        loadingDialog = new Dialog(context);
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingDialog.setContentView(R.layout.loader_animation);
        loadingDialog.show();
        loadingDialog.setCancelable(false);
        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {

                                          loadingDialog.dismiss();
                                      }
                                  }, 1000 * 15
        );
    }
    public static void hideLoaderDialog(Context context) {
        loadingDialog.dismiss();
    }


    public static void showAlertMessege(Context context, String title, String messege) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(messege)
                    .setTitle(title)
                    .setNegativeButton("Ok..", null)
                    .create()
                    .show();
        } catch (Exception e) {

        }
    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



    public static Drawable LoadImageFromWebOperationsSSSS(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
    public static void selectLanguageDialog(final Context mContext) {
        choose_language = new Dialog(mContext);
        choose_language.requestWindowFeature(Window.FEATURE_NO_TITLE);
        choose_language.setContentView(R.layout.choose_language);
        final RadioButton rdbtnEnglish = choose_language.findViewById(R.id.rdbtnEnglish);
        final RadioButton rdbtnHindi = choose_language.findViewById(R.id.rdbtnHindi);
        Button btnSubmitLanguage = choose_language.findViewById(R.id.btnSubmitLanguage);

        btnSubmitLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selected_language = "";
                if (rdbtnEnglish.isChecked()) {
                    selected_language = "en";
                } else if (rdbtnHindi.isChecked()) {
                    selected_language = "hi";
                }
                Language_SharedPref.getInstance(mContext).savelanguage(selected_language);
                choose_language.dismiss();

                mContext.startActivity(new Intent(mContext, Login.class));

            }
        });
        choose_language.setCancelable(false);
        choose_language.show();
    }

    public static String getSelectedLanguage() {
        String l = "en";
        try {
             l = Language_SharedPref.getInstance(context).getSELECTED_LANGUAGE();

        } catch (Exception e) {

        }
        return l;

    }
}


