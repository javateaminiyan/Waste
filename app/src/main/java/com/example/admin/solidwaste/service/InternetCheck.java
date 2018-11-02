package com.example.admin.solidwaste.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.admin.solidwaste.Interface.CheckInternetCallback;

public class InternetCheck extends BroadcastReceiver {


    private static CheckInternetCallback mListener;

    private static final int TYPE_WIFI = 1;
    private static final int TYPE_MOBILE = 2;
    private static final int TYPE_NOT_CONNECTED = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        getNetworkStatus(context);
    }


    public void getNetworkStatus(Context context) {
        try {


            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;
            if (connectivityManager != null) {
                networkInfo = connectivityManager.getActiveNetworkInfo();
                if (null != networkInfo) {
                    if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
                        mListener.statusCode(TYPE_WIFI);
                    else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
                        mListener.statusCode(TYPE_MOBILE);
                    else mListener.statusCode(TYPE_NOT_CONNECTED);
                }else mListener.statusCode(TYPE_NOT_CONNECTED);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void bindListener(CheckInternetCallback listener) {
        mListener = listener;
    }


    public static void unbindListener() {
        mListener = null;

    }


//    public static String getConnectivityStatusString(Context context) {
//        String status = null;
//
//        int conn = getNetworkStatus(context);
//
//        if (conn == TYPE_WIFI) {
//            status = "Wifi enabled";
//        } else if (conn == TYPE_MOBILE) {
//            status = "Mobile data enabled";
//        } else if (conn == TYPE_NOT_CONNECTED) {
//            status = "Not connected to Internet";
//        }
//        return status;
//    }

}
