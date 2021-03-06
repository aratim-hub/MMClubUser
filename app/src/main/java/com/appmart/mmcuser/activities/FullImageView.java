package com.appmart.mmcuser.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.appmart.mmcuser.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.ActivityResult;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;

import static com.google.android.play.core.install.model.ActivityResult.RESULT_IN_APP_UPDATE_FAILED;
import static com.google.android.play.core.install.model.AppUpdateType.FLEXIBLE;

public class FullImageView extends AppCompatActivity {
    private AppUpdateManager appUpdateManager;
    private static final int MY_REQUEST_CODE = 17326;
    Button btnup, btnDown, btnLeft, btnRight,bShare;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image_view);

        btnup = findViewById(R.id.bUp);
        btnDown = findViewById(R.id.bbDown);
        btnLeft = findViewById(R.id.bLeft);
        btnRight = findViewById(R.id.bRight);
        bShare = findViewById(R.id.bShare);

       bShare.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               checkUpdate();
           }
       });

    }


    private void checkUpdate(){
        appUpdateManager = AppUpdateManagerFactory.create(this);
        appUpdateManager.registerListener(listener);

        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                Log.d("appUpdateInfo :", "packageName :"+appUpdateInfo.packageName()+ ", "+ "availableVersionCode :"+ appUpdateInfo.availableVersionCode() +", "+"updateAvailability :"+ appUpdateInfo.updateAvailability() +", "+ "installStatus :" + appUpdateInfo.installStatus() );

                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && appUpdateInfo.isUpdateTypeAllowed(FLEXIBLE)){
                    requestUpdate(appUpdateInfo);
                    Log.d("UpdateAvailable","update is there ");
                    Toast.makeText(FullImageView.this, "UpdateAvailable  update is there", Toast.LENGTH_SHORT).show();
                }
                else if (appUpdateInfo.updateAvailability() == Integer.parseInt("3")){
                    Log.d("Update","3");
                    notifyUser();
                    Toast.makeText(FullImageView.this, "Notify user ", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(FullImageView.this, "No Update Available", Toast.LENGTH_SHORT).show();
                    Log.d("NoUpdateAvailable","update is not there ");
                }
            }
        });
    }
    private void requestUpdate(AppUpdateInfo appUpdateInfo){
        try {
            appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.FLEXIBLE,FullImageView.this,MY_REQUEST_CODE);
            resume();
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_REQUEST_CODE){
            switch (resultCode){
                case Activity.RESULT_OK:
                    if(resultCode != RESULT_OK){
                        Toast.makeText(this,"RESULT_OK" +resultCode, Toast.LENGTH_LONG).show();
                        Log.d("RESULT_OK  :",""+resultCode);
                    }
                    break;
                case Activity.RESULT_CANCELED:

                    if (resultCode != RESULT_CANCELED){
                        Toast.makeText(this,"RESULT_CANCELED" +resultCode, Toast.LENGTH_LONG).show();
                        Log.d("RESULT_CANCELED  :",""+resultCode);
                    }
                    break;
                case RESULT_IN_APP_UPDATE_FAILED:

                    if (resultCode != RESULT_IN_APP_UPDATE_FAILED){

                        Toast.makeText(this,"RESULT_IN_APP_UPDATE_FAILED" +resultCode, Toast.LENGTH_LONG).show();
                        Log.d("RESULT_IN_APP_FAILED:",""+resultCode);
                    }
            }
        }
    }

    InstallStateUpdatedListener listener = new InstallStateUpdatedListener() {
        @Override
        public void onStateUpdate(InstallState installState) {
            if (installState.installStatus() == InstallStatus.DOWNLOADED){
                Log.d("InstallDownloded","InstallStatus sucsses");
                notifyUser();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            appUpdateManager.unregisterListener((InstallStateUpdatedListener) this);

        } catch (Exception e) {

        }
    }

    private void notifyUser() {

        Snackbar snackbar =
                Snackbar.make(findViewById(R.id.message),
                        "An update has just been downloaded.",
                        Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("RESTART", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUpdateManager.completeUpdate();
            }
        });
        snackbar.setActionTextColor(
                getResources().getColor(R.color.colorBlack));
        snackbar.show();
    }

    private void resume(){
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED){
                    notifyUser();

                }

            }
        });
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
