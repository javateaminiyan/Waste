package com.example.admin.solidwaste.presenter.ForgotPresenter;

import android.util.Log;
import android.util.Patterns;

import com.example.admin.solidwaste.Interface.BasePresenter;
import com.example.admin.solidwaste.Interface.ForgotContract;
import com.example.admin.solidwaste.pojo.Response;
import com.example.admin.solidwaste.pojo.SendOTP;
import com.example.admin.solidwaste.pojo.SendOTPResponse;
import com.example.admin.solidwaste.di.module.NetworkClient;

import java.io.IOException;
import java.util.Arrays;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Retrofit;

import static android.support.constraint.Constraints.TAG;
import static com.example.admin.solidwaste.utils.CommonHelper.isValid;

public class ForgotPresenter extends BasePresenter<ForgotContract.View> implements ForgotContract.Presenter {
    private  String userid = null;
    private  String passKey = null;

    private ForgotContract.View views;

  @Inject
    NetworkClient networkClient;

    @Inject
      Retrofit retrofit;

    @Inject
    ForgotPresenter(ForgotContract.View view) {
        super(view);
        this.views = view;
    }




    @Override
    public String sendEmailorMobileno(String mobilenooremail) {


        if (Patterns.EMAIL_ADDRESS.matcher(mobilenooremail).matches())

        {
            views.onSuccess(" Valid Email");

            emailsendtoverify(mobilenooremail);


            return "Submit";

        } else if (isValid(mobilenooremail))

        {
            views.onSuccess("Valid Phone No");

            mobilenosendtoverify(mobilenooremail);

            return "Submit";

        } else {

            views.onError("Not Valid Email/Mobile Number");
        }


        return null;


    }

    @Override
    public String otpValidate(String otp) {


        if (otp.length() == 6) {


            if (passKey != null) {
                passKey.equals(otp);
                return "valid";

            } else
                views.onError("Otp Not Matched :)");


            return "error";
        } else if (otp.isEmpty()) {
            views.onError("Enter Otp Here :)");
            return "error";

        }
        return null;
    }

    @Override
    public String validatePassword(String password, String confirmpassword) {

        if (password.equals(confirmpassword)) {
            views.onSuccess("Success");
            if (userid != null)
                forgotApiCall(userid, confirmpassword);
            return "SuccessApi";

        } else if (password.isEmpty() && confirmpassword.isEmpty()) {
            views.onError("Password and confirm Password is Empty");

        } else {
            views.onError("Please Enter Correct Password");

        }


        return null;
    }


    @Override
    public String mobilenosendtoverify(final String mobileno) {


        addDisposable(networkClient.getApiInterface(retrofit).sendOtp_MobileNo(mobileno)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));

        return null;
    }

    @Override
    public String emailsendtoverify(String email) {


        addDisposable(networkClient.getApiInterface(retrofit).sendOtp_Email(email)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));


        return null;
    }

    @Override
    public void forgotApiCall(String userid, String Password) {


        addDisposable(networkClient.getApiInterface(retrofit).forgotPassword(userid, Password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleForgotResponse, this::handleForgotError));


    }

    private void handleForgotResponse(Response response) {


        if (response.getStatusCode().equals("200")) {
            views.handleResponse("completed");

        } else
            views.handleError("Not Updated Password");


    }

    private void handleForgotError(Throwable error) {

        Log.e(TAG, "" + error);
        if (error instanceof HttpException) {
            views.handleError( "Network Exception "+error.toString());

        }
        if (error instanceof IOException) {
            views.handleError( "Io Exception "+error.toString());

        }
        Log.e(TAG, "" + error);
    }


    private String handleResponse(SendOTP sendOTP) {

        if (sendOTP.getStatusCode() == 200) {
            Log.e(TAG, "post " + Arrays.toString(sendOTP.getResponse()));


            SendOTPResponse[] response = sendOTP.getResponse();

            Log.e(TAG, "userid============" + response[0].getUserid());
            userid = String.valueOf(response[0].getUserid());

            passKey = String.valueOf(sendOTP.getPassKey());
            Log.e(TAG, "passkey============" + sendOTP.getPassKey());


        }

        return null;
    }

    private void handleError(Throwable error) {

       Log.e(TAG, "" + error);
        if (error instanceof HttpException) {
            views.handleError( "Network Exception "+error.toString());

        }
        if (error instanceof IOException) {
            views.handleError( "Io Exception "+error.toString());

        }

    }


}
