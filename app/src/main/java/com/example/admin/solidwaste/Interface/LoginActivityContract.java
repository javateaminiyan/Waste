package com.example.admin.solidwaste.Interface;

import android.content.Context;

public interface LoginActivityContract {


     interface ILoginUser {


        String EmailorMobileno();
        String Password();
        int isvalidate();


    }

     interface ILoginView {
        void onLoginResult(String message);

        void onLoginError(String message);
    }
     interface ILoginPresenter {

        void onLogin(String Username, String Password,Context context);


        void LoginUser(String Username, String Password,Context context);


        void UpdateFirebaseId(String Userid,String FirebaseId);
    }

}
