package com.appmart.mmcuser;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import androidx.core.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class InstallAPK extends AsyncTask<String, Void, Void> {

    ProgressDialog progressDialog;
    int status = 0;

    private Context context;

    public void setContext(Context context, ProgressDialog progress) {
        this.context = context;
        this.progressDialog = progress;
    }

    public void onPreExecute() {
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(String... arg0) {
        try {
            URL url = new URL(arg0[0]);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();

            File sdcard = Environment.getExternalStorageDirectory();

            File myDir = new File(sdcard, "Android/data/com.appmart.mmc/temp");


            File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                    + "/Android/data/"
                    + context.getPackageName()
                    + "/temp");

            myDir.mkdirs();
            File outputFile = new File(mediaStorageDir, "temp.apk");
            if (outputFile.exists()) {
                outputFile.delete();
            }
            FileOutputStream fos = new FileOutputStream(outputFile);

            InputStream is = c.getInputStream();

            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
            }
            fos.flush();
            fos.close();
            is.close();

            File filepath = new File(mediaStorageDir, "temp.apk");

//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//            Uri apkURI = FileProvider.getUriForFile(context,
//                    BuildConfig.APPLICATION_ID + ".provider",
//                    filepath);
//            intent.setDataAndType(apkURI, "application/vnd.android.package-archive");


//            FileProvider.getUriForFile(MainActivity.this,
//                    BuildConfig.APPLICATION_ID + ".provider",
//                    createImageFile());

//            intent.setDataAndType(Uri.fromFile(filepath), "application/vnd.android.package-archive");
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // without this flag android returned a intent error!
//            context.startActivity(intent);





            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                filepath.setReadable(true, false);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.fromFile(filepath), "application/vnd.android.package-archive");
                context.getApplicationContext().startActivity(intent);
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", filepath);

                intent.setDataAndType(uri, "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        } catch (FileNotFoundException fnfe) {
            status = 1;
            Log.e("File", "FileNotFoundException! " + fnfe);
        } catch (Exception e) {
            Log.e("UpdateAPP", "Exception " + e);
        }
        return null;
    }

    public void onPostExecute(Void unused) {
        progressDialog.dismiss();
        if (status == 1)
            Toast.makeText(context, "Game Not Available", Toast.LENGTH_LONG).show();
    }
}
