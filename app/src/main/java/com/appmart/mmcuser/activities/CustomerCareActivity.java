package com.appmart.mmcuser.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.appmart.mmcuser.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;

public class CustomerCareActivity extends AppCompatActivity {
    TextView txtNumberRenatus1;
    private int PERMISSION_REQUEST_CODE = 123;
    Button btnFullingTest;

    private AppUpdateManager mAppUpdateManager;
    private static final int RC_APP_UPDATE = 11;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_care);
        txtNumberRenatus1 = findViewById(R.id.txtNumberRenatus1);
        btnFullingTest = findViewById(R.id.btnFullingTest);
        btnFullingTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustomerCareActivity.this, FullImageView.class);
                startActivity(i);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAppUpdateManager = AppUpdateManagerFactory.create(this);

        mAppUpdateManager.registerListener(installStateUpdatedListener);

        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {

                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE /*AppUpdateType.IMMEDIATE*/)) {

                    try {
                        mAppUpdateManager.startUpdateFlowForResult(
                                appUpdateInfo, AppUpdateType.FLEXIBLE /*AppUpdateType.IMMEDIATE*/, CustomerCareActivity.this, RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();

                    }

                } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    //CHECK THIS if AppUpdateType.FLEXIBLE, otherwise you can skip

                    CustomerCareActivity.this.popupSnackbarForCompleteUpdate();
                } else {

                    Log.e("TAG:", "checkForAppUpdateAvailability: something else");
                    Toast.makeText(CustomerCareActivity.this, "checkForAppUpdateAvailability: something else", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    InstallStateUpdatedListener installStateUpdatedListener = new
            InstallStateUpdatedListener() {
                @Override
                public void onStateUpdate(InstallState state) {
                    if (state.installStatus() == InstallStatus.DOWNLOADED) {
                        //CHECK THIS if AppUpdateType.FLEXIBLE, otherwise you can skip
                        popupSnackbarForCompleteUpdate();
                    } else if (state.installStatus() == InstallStatus.INSTALLED) {
                        if (mAppUpdateManager != null) {
                            mAppUpdateManager.unregisterListener(installStateUpdatedListener);
                        }

                    } else {
                        Log.i("TAG:", "InstallStateUpdatedListener: state: " + state.installStatus());
                    }
                    Toast.makeText(CustomerCareActivity.this, "Install state update listener", Toast.LENGTH_SHORT).show();

                }
            };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_APP_UPDATE) {
            if (resultCode != RESULT_OK) {
                Log.e("TAG", "onActivityResult: app download failed");
            }
        }
    }

    private void popupSnackbarForCompleteUpdate() {
        Toast.makeText(CustomerCareActivity.this, "in pop up snackBar function", Toast.LENGTH_SHORT).show();

        Snackbar snackbar =
                Snackbar.make(
                        findViewById(R.id.main_Container),
                        "New app is ready!",
                        Snackbar.LENGTH_INDEFINITE);

        snackbar.setAction("Install", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAppUpdateManager != null) {
                    mAppUpdateManager.completeUpdate();
                }
            }
        });


        snackbar.setActionTextColor(getResources().getColor(R.color.black));
        snackbar.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAppUpdateManager != null) {
            mAppUpdateManager.unregisterListener(installStateUpdatedListener);
        }

    }
}