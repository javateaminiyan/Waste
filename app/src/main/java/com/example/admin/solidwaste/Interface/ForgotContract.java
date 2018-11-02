package com.example.admin.solidwaste.Interface;

public interface ForgotContract {


    interface Presenter {

        String sendEmailorMobileno(String mobilenooremail);

        String otpValidate(String otp);

        String validatePassword(String password, String confirmpassword);

        String mobilenosendtoverify(String mobileno);
        String emailsendtoverify(String email);
        void forgotApiCall(String userid,String Password);
    }


    interface View {

        void onError(String message);

        void onSuccess(String message);

        void handleResponse(String  sendOTP);


        void handleError(String  error);


    }
}
