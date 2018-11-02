package com.example.admin.solidwaste.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.example.admin.solidwaste.Interface.SmsListener;

public class SMSReceiver extends BroadcastReceiver {

    private static SmsListener mListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        // get the bundle object
        Bundle bundle = intent.getExtras();
        SmsMessage[] smsMessages = null;
        String sms_Str = "";
        if (bundle != null) {
            //Get the sms message
            // Get the SMS message
            Object[] pdus = (Object[]) bundle.get("pdus");
            assert pdus != null;


            for (int i = 0; i < pdus.length; i++) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);

                String sender = smsMessage.getDisplayOriginatingAddress();
                //You must check here if the sender is your provider and not another one with same text.

                String messageBody = smsMessage.getMessageBody();

                //Pass on the text to our listener.
                mListener.messageReceived(messageBody);
            }


            smsMessages = new SmsMessage[pdus.length];

            for (int i = 0; i < smsMessages.length; i++) {
                smsMessages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                Log.e("Vb", smsMessages[i].getDisplayMessageBody());
                sms_Str = smsMessages[i].getDisplayMessageBody().substring(12, 19);
                //Check here sender is yours
                Intent smsIntent = new Intent("otp");
                smsIntent.putExtra("message", sms_Str);

                LocalBroadcastManager.getInstance(context).sendBroadcast(smsIntent);
            }

        }
    }


    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }


    public static void unbindListener() {
        mListener = null;
    }


}