package com.appmart.mmcuser.fcm;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.appmart.mmcuser.activities.CustomerCareActivity;
import com.appmart.mmcuser.activities.Home;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private static int count = 0;

@Override
public void onNewToken(String s) {
    super.onNewToken(s);
    Log.d(TAG, "Refreshed token: " + s);
    storeToken(s);

}
    private void storeToken(String token) {
        //saving the token on shared preferences
        SharedPrefManager.getInstance(getApplicationContext()).saveDeviceToken(token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());


                JSONObject data = json.getJSONObject("data");

                //parsing json data
                String title = data.getString("title");
                String message = data.getString("message");
                String imageUrl = data.getString("image");
                String click_action = data.getString("click_action");


                sendPushNotification(title, message, imageUrl,click_action);

            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }else {
            String title = remoteMessage.getNotification().getTitle();
            String message = remoteMessage.getNotification().getBody();
            String imageUrl = remoteMessage.getNotification().getIcon();
            String click_action = remoteMessage.getNotification().getClickAction();

            sendPushNotification(title, message, imageUrl, click_action);
        }


    }

    //this method will display the notification
    //We are passing the JSONObject that is received from
    //firebase cloud messaging



    private void sendPushNotification(String title, String message, String imageUrl, String click_action) {
        //optionally we can display the json into log

        try {
            //getting the json data

            //parsing json data

            //creating MyNotificationManager object
            MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());

            //creating an intent for the notification
            // Intent intent = new Intent(getApplicationContext(), Long.class);

            Intent intent = new Intent(getApplicationContext(), Home.class);
//            Intent intent = new Intent();
//            intent.setAction(click_action);
//            Intent intent = new Intent(getApplicationContext(), CustomerCareActivity.class);
//            intent.putExtra("nott", click_action);
            //if there is no image
            if (imageUrl == null) {
                //displaying small notification0
                mNotificationManager.showSmallNotification(title, message, intent);
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }
}