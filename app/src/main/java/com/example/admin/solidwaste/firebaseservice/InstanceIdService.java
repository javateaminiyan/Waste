package com.example.admin.solidwaste.firebaseservice;


import android.util.Log;

import com.example.admin.solidwaste.utils.CommonHelper;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class InstanceIdService extends FirebaseInstanceIdService {
    private String  TAG="InstanceIdService";



    public InstanceIdService() {
        super();
    }
    @Override
    public void onTokenRefresh() {

        // Get updated InstanceID token.

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        CommonHelper.firebaseid=refreshedToken;
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        // sendRegistrationToServer(refreshedToken);
    }

}
