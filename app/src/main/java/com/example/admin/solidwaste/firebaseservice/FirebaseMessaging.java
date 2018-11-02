package com.example.admin.solidwaste.firebaseservice;


import android.app.PendingIntent;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import android.app.Notification;
import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;

import com.example.admin.solidwaste.R;
import com.example.admin.solidwaste.RoomDB.NotificationDatabase;
import com.example.admin.solidwaste.activity.Login;
import com.example.admin.solidwaste.activity.NotificationActivity;
import com.example.admin.solidwaste.pojo.NotificationBean;
import com.example.admin.solidwaste.utils.CommonHelper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FirebaseMessaging extends FirebaseMessagingService {
    private String TAG = "FirebaseMessaging";
    Bitmap bitmap;
    String neededId;
    private static final int REQUEST_CODE = 1;
    private static final int NOTIFICATION_ID = 6578;

    public FirebaseMessaging() {
        super();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        Log.d(TAG, "From: " + remoteMessage.getFrom());


        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e("cvkjncj", "gbjfg 1");
            Log.e(TAG, "Message data payload: " + remoteMessage.getData());


            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                //  scheduleJob();
            } else {
                // Handle message within 10 seconds
                //  handleNow();
            }

        } else {
            Log.e("cvkjncj", "gbjfg 0");
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());


        }


        //TODO https://fcm.googleapis.com/fcm/send to receive parameter to get approperiate  values

        //message will contain the Push Message


        String title = remoteMessage.getData().get("title");

        Log.e("vbhjbv", title + "");
        String messagebody = remoteMessage.getData().get("body");
        String imageUri = remoteMessage.getData().get("image");
        bitmap = getBitmapfromUrl(imageUri);
        String nameofuser = remoteMessage.getData().get("nameofuser");
        String message = remoteMessage.getData().get("message");
        String user_firebaseid = remoteMessage.getData().get("Userfirebaseid");

        String merchant_firebaseid = remoteMessage.getData().get("Merchantfirebaseid");
        String mobileno = remoteMessage.getData().get("mobileno");
        String upiid = remoteMessage.getData().get("upiid");
        String productid = remoteMessage.getData().get("productid");
        String productname = remoteMessage.getData().get("productname");
        String productcost = remoteMessage.getData().get("productcost");
        String quantity = remoteMessage.getData().get("quantity");
        String datetime = remoteMessage.getData().get("datetime");
        String userid = remoteMessage.getData().get("userid");
        String nameofmerchant = remoteMessage.getData().get("nameofmerchant");
        String OrderStatus = remoteMessage.getData().get("OrderStatus");
        String TypeofUser = remoteMessage.getData().get("merchantoruser");
        String orderid = remoteMessage.getData().get("orderid");
        String merchantid = remoteMessage.getData().get("merchantid");
        String address = remoteMessage.getData().get("address");
        String orderapproval = remoteMessage.getData().get("orderapproval");
        String pickupdate = remoteMessage.getData().get("pickupdate");
        String ordercashtype = remoteMessage.getData().get("ordercashtype");
        String unit = remoteMessage.getData().get("unit");
        String price = remoteMessage.getData().get("price");
        String email = remoteMessage.getData().get("email");

        NotificationDatabase database = Room.databaseBuilder(getApplicationContext(), NotificationDatabase.class, CommonHelper.ROOM_DBNAME).fallbackToDestructiveMigration().build();

        Log.e("insert into room,", TypeofUser);
        //TODO After receiving we need to call Notification class to start process

       /* int imagid, String msg, String time, boolean isviewd, String userid, String productCost,
                String address, String quantity, String productid, String orderapproval,
                String orderstatus, String orderid, String mobile, String pickupdate, String ordercashtype,
                String userid1, String datetime, String unit,
                String firebaseid, String price, String productname, String nameofuser, String email*/


        //Send notification from merchant to user
        Log.e("userid==>", userid + " fdhfg");
        Log.e("merchantid==>", merchantid + " fdhfg");
        if (TypeofUser.toLowerCase().equalsIgnoreCase("merchant")) {


            neededId = "user";
            database.notificationDao().insert(new NotificationBean(1, message, datetime, false, userid, productcost, address, quantity, productid, orderapproval, OrderStatus, orderid, mobileno,
                    pickupdate, ordercashtype
                    , datetime, unit, user_firebaseid, price, productname, nameofuser, email, merchantid, imageUri));


            sendNotificationuser(neededId, title, messagebody, bitmap, message, nameofuser, user_firebaseid, mobileno, upiid, productid, productname, productcost, quantity, datetime, userid, imageUri);


//        Log.e(TAG, "title " + title + " " + "message " + message + " " + "imageUri " + imageUri + " " + "nextActivity " + nextActivity);


        }
        //Send notification from user to merchant
        else if (TypeofUser.toLowerCase().equalsIgnoreCase("user")) {
            neededId = "merchant";
            sendNotificationmerchant(neededId, title, messagebody, bitmap, message, nameofmerchant, merchant_firebaseid, OrderStatus, productid, productname, productcost, quantity, datetime, merchantid, imageUri);

//      Log.e(TAG, "title " + title + " " + "message " + message + " " + "imageUri " + imageUri + " " + "nextActivity " + nextActivity);


            database.notificationDao().insert(new NotificationBean(2, message, datetime, false, userid, productcost, address, quantity, productid, orderapproval, OrderStatus, orderid, mobileno, pickupdate, ordercashtype
                    , datetime, unit, user_firebaseid, price, productname, nameofuser, email, merchantid, imageUri));

        }


    }


    //TODO All features Applied upto Oreo Supported to send message to users


    private void sendNotificationuser(String type, String title, String messagebody, Bitmap bitmap, String message,
                                      String nameofuser, String firebaseid, String mobileno,
                                      String upiid, String productid, String productname,
                                      String productcost, String quantity, String datetime,
                                      String userid, String img) {


        Intent intent = new Intent(this, NotificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        intent.putExtra("messagebody", messagebody);
        intent.putExtra("message", message);
        intent.putExtra("nameofuser", nameofuser);
        intent.putExtra("firebaseid", firebaseid);
        intent.putExtra("mobileno", mobileno);
        intent.putExtra("upiid", upiid);
        intent.putExtra("productid", productid);
        intent.putExtra("productname", productname);
        intent.putExtra("productcost", productcost);
        intent.putExtra("quantity", quantity);
        intent.putExtra("datetime", datetime);
        intent.putExtra("userid", userid);
        intent.putExtra("imageUri", img);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setLargeIcon(bitmap)/*Notification icon image*/
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentText(message)
//                .setStyle(new NotificationCompat.BigPictureStyle()
//                        .bigPicture(bitmap))/*Notification with Image*/
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID /* ID of notification */, notificationBuilder.build());
        }


    }


    private void sendNotificationmerchant(String type, String title, String messagebody, Bitmap bitmap, String message, String nameofmerchant, String merchant_firebaseid, String orderStatus, String productid, String productname, String productcost, String quantity, String datetime, String userid, String imageUri) {

        Intent intent = new Intent(this, NotificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        intent.putExtra("messagebody", messagebody);
        intent.putExtra("message", message);
        intent.putExtra("nameofuser", nameofmerchant);
        intent.putExtra("merchant_firebaseid", merchant_firebaseid);
        intent.putExtra("orderStatus", orderStatus);
        intent.putExtra("productid", productid);
        intent.putExtra("productname", productname);
        intent.putExtra("productcost", productcost);
        intent.putExtra("quantity", quantity);
        intent.putExtra("datetime", datetime);
        intent.putExtra("userid", userid);
        intent.putExtra("imageUri", imageUri);


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setLargeIcon(bitmap)/*Notification icon image*/
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentText(message)
//                .setStyle(new NotificationCompat.BigPictureStyle()
//                        .bigPicture(bitmap))/*Notification with Image*/
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID /* ID of notification */, notificationBuilder.build());
        }


    }


    //TODO Simple Notification Title and Message Send by Notification getNotification Method
    private void showNotifications(String title, String msg) {
        Intent i = new Intent(this, Login.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE,
                i, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentText(msg)
                .setContentTitle(title)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .build();

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, notification);
    }


    //TODO Converting Img url from Firebase Image converting into bitmap and send notification to user
    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }

}
