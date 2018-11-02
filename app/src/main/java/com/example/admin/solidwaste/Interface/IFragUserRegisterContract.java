package com.example.admin.solidwaste.Interface;

import android.content.SharedPreferences;

import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.sharedprefshelper.SharedPrefModule;

import retrofit2.Retrofit;

public interface IFragUserRegisterContract {

    public  interface merchantpresenter{



        void onValidate(String name,  String shopname, String mobileno,  String email, String address,String upiid,String panno,String gstno,String password,String confirmpassword);


        void registerMerchant(String name, String email, String password, String city , String address, String firebaseid, String latlng, String nameofshop, String gstno, String upiid, String panno, String mobileno, String typeofuser, SharedPreferences sharedPreferences, NetworkClient networkClient, Retrofit retrofit);

        void onCheckMobileNo(String mobileno);

        void onCheckEmailId(String emailid);


        void callSendOtp(String mobileoremail);

        void otpValidate(String otp);

        void callSendOtpEmail(String email);

        void otpValidateEmail(String otp);
    }



      interface userpresenter{



        void onValidate(String name,  String mobileno,  String email, String address,String upiid,String password,String confirmpassword);

   void registerUser(String name, String email, String password, String city , String address, String firebaseid, String latlng, String nameofshop, String gstno, String upiid, String panno, String mobileno, String typeofuser, SharedPreferences sharedPreferences, NetworkClient networkClient, Retrofit retrofit);


   void onCheckMobileNo(String mobileno);

          void onCheckEmailId(String emailid);


          void callSendOtp(String mobileoremail);

          void otpValidate(String otp);

          void callSendOtpEmail(String email);

          void otpValidateEmail(String otp);

      }

    interface  view{
        void onError(String message);

        void onSuccess(String message);
    }



    interface  register_repo{

        String name();
        String shopname();
        String mobileno();
        String email();
        String address();
        String upiid();
        String panno();
        String gstno();
        String password();
        String confirmpassword();
        int isvalidate();
    }


    interface  user_repo{

        String name();
        String mobileno();
        String email();
        String address();
        String upiid();
        String password();
        String confirmpassword();
        int isvalidate();

    }
}
