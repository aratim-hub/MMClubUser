package com.appmart.mmcuser.activities;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;
import com.appmart.mmcuser.utils.ConstantMethods;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.ByteArrayOutputStream;


public class FullImageShare extends AppCompatActivity {
    private int PERMISSION_REQUEST_CODE = 123;

    ImageView view1;
    String text = "More info call or WhatsApp";
    Drawable drawable;
    String imageTitle;
    private Bitmap bitmap1, bitmap2;
    private String MOBILE_NUMBER;
    TextView txtMsgContent;
    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) throws Exception{
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_full_image);

        view1 = findViewById(R.id.view1);
        txtMsgContent = findViewById(R.id.txtMsgContent);
        String imageUrl = getIntent().getStringExtra("imageUrl");
        imageTitle = getIntent().getStringExtra("imageTitle");
        MOBILE_NUMBER = Profile_SharedPref.getInstance(FullImageShare.this).getWhatsAppNumber();
        txtMsgContent.setText(imageTitle);

//        Glide.with(FullImageShare.this)
//                .load(imageUrl)
//                .fitCenter()
//                .into(view1);

        ConstantMethods.loaderDialog(FullImageShare.this);

        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Toast.makeText(FullImageShare.this, "Ready to share", Toast.LENGTH_SHORT).show();
                                                   DrawTargetImage(resource);

                        ConstantMethods.hideLoaderDialog(FullImageShare.this);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    private void DrawTargetImage(Bitmap resource)  {

        try {
//            drawable = LoadImageFromWebOperationsSSSS(imageUrl);

            // bitmap1 is image received from server, This is original image
            bitmap1 = resource;
//            bitmap1 = ((BitmapDrawable) drawable).getBitmap();


            //bitmap2 is additinal image to be add at bottom of original image
            bitmap2 = Bitmap.createBitmap(bitmap1.getWidth(), 150, Bitmap.Config.ARGB_8888);
            //bitmap3 logo added on bitmap2 at bottom of original image

            Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.mmc_logo_cropped);

            Bitmap scaledBitmap = scaleDown(bitmap3, 150, true);

            Canvas c = new Canvas(bitmap2);
            c.drawColor(Color.BLACK);

            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(getResources().getColor(android.R.color.white)); // Text Color
            paint.setTextSize(40); //Text Size

            c.drawBitmap(scaledBitmap, 10, 20, paint);
            c.drawText(text, 170, 50, paint);
            c.drawText(MOBILE_NUMBER, 170, 100, paint);

            view1.setImageBitmap(mergeBitmap(bitmap1, bitmap2));

        } catch (Exception E) {
            Toast.makeText(this, "Internet connection is too slow,Please check your connection and try again", Toast.LENGTH_SHORT).show();
            ConstantMethods.showAlertMessege(FullImageShare.this, "Warning", "Internet connection is too slow,Please check your connection and try again");
            finish();
        }
    }

    public Bitmap mergeBitmap(Bitmap bitmap1, Bitmap bitmap2) {
        Bitmap mergedBitmap = null;

        int w, h = 0;

        h = bitmap1.getHeight() + bitmap2.getHeight();

        if (bitmap1.getWidth() > bitmap2.getWidth()) {
            w = bitmap1.getWidth();
        } else {
            w = bitmap2.getWidth();
        }
        try {
            mergedBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

        } catch (Exception e) {
            Toast.makeText(this, "Please try again Later", Toast.LENGTH_SHORT).show();
            finish();
        }

        Canvas canvas = new Canvas(mergedBitmap);

        canvas.drawBitmap(bitmap1, 0f, 10f, null);
        canvas.drawBitmap(bitmap2, 0f, bitmap1.getHeight(), null);


        return mergedBitmap;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            shareImage();
            return true;
        } else if (id == R.id.action_copy) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("copied", imageTitle);
            clipboard.setPrimaryClip(clip);
            clipboard.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
                        @Override
                        public void onPrimaryClipChanged() {
                            Toast.makeText(FullImageShare.this, "copied to clipboard", Toast.LENGTH_SHORT).show();                        }
                    });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void shareImage() {
        Bitmap bmp = mergeBitmap(bitmap1, bitmap2);
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), bmp, "Title", null);
            Uri imageUri = Uri.parse(path);

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("image/*");
            waIntent.putExtra(Intent.EXTRA_STREAM,imageUri);
            waIntent.putExtra(Intent.EXTRA_TEXT, imageTitle);
            this.startActivity(Intent.createChooser(waIntent, "Share with"));
        } catch (Exception e) {
            Log.e("Error on sharing", e + " ");
            Toast.makeText(this, "Kindly allow all permissions", Toast.LENGTH_SHORT).show();
            getAllPermission();
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getAllPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED


        ) {

            requestPermissions(new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    },
                    PERMISSION_REQUEST_CODE);
        } else {

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length == 11 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED
                    && grantResults[3] == PackageManager.PERMISSION_GRANTED) ;

        } else {
            Toast.makeText(this, "You must give permissions to use this app. App is exiting.", Toast.LENGTH_SHORT).show();

        }
    }
}