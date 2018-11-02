package com.example.admin.solidwaste.Interface;

public interface ProfileActivityContract {


    interface IProfileData {


    }

    interface IProfileView {
        void onError(String message);

        void onSuccess(String message);
    }

    interface IProfilePresenter {
        void onCheckMobileNo(String mobileno);

        void onCheckEmailId(String emailid);
        void onPasswordChange(String userid,String password);


        void updateProfile(String userid,String name,String email,String mobile,String upino,String panno,String gstno,String address,String latlng,String nameofshop);

        void callSendOtp(String mobileoremail);

        void otpValidate(String otp);

        void callSendOtpEmail(String email);

        void otpValidateEmail(String otp);

    }


}
