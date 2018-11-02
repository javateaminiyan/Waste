package com.example.admin.solidwaste.presenter.UserModules;


import android.util.Log;

import com.example.admin.solidwaste.Interface.BasePresenter;
import com.example.admin.solidwaste.Interface.ProfileActivityContract;
import com.example.admin.solidwaste.pojo.GeneralResponse.GeneralResponse;
import com.example.admin.solidwaste.pojo.Response;
import com.example.admin.solidwaste.di.module.NetworkClient;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Retrofit;

public class ProfilePresenter extends BasePresenter<ProfileActivityContract.IProfileView> implements ProfileActivityContract.IProfilePresenter {
    private String passKey = "";

    @Inject
     NetworkClient networkClient;

    @Inject
     Retrofit retrofit;

    private String TAG = ProfilePresenter.class.getName();
    private ProfileActivityContract.IProfileView view;

    @Inject
    ProfilePresenter(ProfileActivityContract.IProfileView view) {
        super(view);
        this.view = view;
    }


    @Override
    public void onCheckMobileNo(String mobileno) {

        Log.e(TAG, "mobile numberrrrrrr" + mobileno + "dddd" + retrofit);


        addDisposable(networkClient.getApiInterface(retrofit).checkUserorNot(mobileno)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<GeneralResponse>() {
                    @Override
                    public void accept(GeneralResponse generalResponse) {
                        Long message = Long.parseLong(String.valueOf(generalResponse.getmResponse().getStatusCode()));
                        Log.e(TAG + "response ", "" + message);


                        if (message == 400) {


                            passKey = "";
                            view.onSuccess("SendOTP");
                        } else view.onError("Already Login");


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG + "throwable ", "" + throwable);
                        if (throwable instanceof HttpException) {
                            view.onError( "Network Exception "+throwable.toString());

                        }
                        if (throwable instanceof IOException) {
                            view.onError( "Io Exception "+throwable.toString());

                        }
                    }
                })
        );


    }


    @Override
    public void onCheckEmailId(String emailid) {
        Log.e(TAG, "email" + emailid);
        addDisposable(networkClient.getApiInterface(retrofit).checkUserorNot(emailid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<GeneralResponse>() {
                    @Override
                    public void accept(GeneralResponse response) {
                        if (Long.parseLong(String.valueOf(response.getmResponse().getStatusCode())) == 400) {


                            Log.e(TAG, "" + Long.parseLong(String.valueOf(response.getmResponse().getStatusCode())));

                            view.onSuccess("SendOTPEmail");


                        } else

                            view.onError("Not Send otp to mail Already Mail Id Exist");


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e(TAG, throwable.toString());
                        if (throwable instanceof HttpException) {
                            view.onError( "Network Exception "+throwable.toString());

                        }
                        if (throwable instanceof IOException) {
                            view.onError( "Io Exception "+throwable.toString());

                        }

                    }
                }));

    }

    @Override
    public void onPasswordChange(String userid, String password) {

        Log.e(TAG, "SendOTPCall" + password);

        addDisposable(networkClient.getApiInterface(retrofit).changePassword(userid, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Response>() {
                    @Override
                    public void accept(Response sendOTP) {

                        if (Integer.parseInt(String.valueOf(sendOTP.getStatusCode())) == 200) {


                            view.onSuccess("PasswordChangedSuccess");


                        } else

                            view.onError("Password Change Failure");


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e(TAG, throwable.toString());
                        if (throwable instanceof HttpException) {
                            view.onError( "Network Exception "+throwable.toString());

                        }
                        if (throwable instanceof IOException) {
                            view.onError( "Io Exception "+throwable.toString());

                        }
                    }
                }));
    }

    @Override
    public void updateProfile(String userid, String name, String email, String mobile, String upino, String panno,
                              String gstno, String address, String latlng, String nameofshop) {


        Log.e(TAG, "updateProfile");

        addDisposable(networkClient.getApiInterface(retrofit).updateProfile(userid, name, email, mobile, upino, panno,
                gstno, address, latlng, nameofshop)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Response>() {
                    @Override
                    public void accept(Response sendOTP) {

                        if (Integer.parseInt(String.valueOf(sendOTP.getStatusCode())) == 200) {


                            view.onSuccess("UpdateSuccess");


                        } else

                            view.onError("Update  Failure");


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e(TAG, throwable.toString());
                        if (throwable instanceof HttpException) {
                            view.onError( "Network Exception "+throwable.toString());

                        }
                        if (throwable instanceof IOException) {
                            view.onError( "Io Exception "+throwable.toString());

                        }
                    }
                }));


    }


    @Override
    public void callSendOtp(String mobileoremail) {

        Log.e(TAG, "SendOTPCall" + mobileoremail);

        addDisposable(networkClient.getApiInterface(retrofit).registerSendOtp_Mobile(mobileoremail)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Response>() {
                    @Override
                    public void accept(Response sendOTP) {

                        if (Integer.parseInt(String.valueOf(sendOTP.getStatusCode())) == 200) {


                            passKey = String.valueOf(sendOTP.getStatusMessage());
                            Log.e(TAG, "passkey============" + sendOTP.getStatusMessage());


                            view.onSuccess("OTPSuccess");


                        } else

                            view.onError("" + sendOTP.getStatusCode());


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e(TAG, throwable.toString());
                        if (throwable instanceof HttpException) {
                            view.onError( "Network Exception "+throwable.toString());

                        }
                        if (throwable instanceof IOException) {
                            view.onError( "Io Exception "+throwable.toString());

                        }
                    }
                }));

    }

    @Override
    public void otpValidate(String otp) {


        if (otp.length() == 6) {


            if (passKey != null) {
                if (otp.equalsIgnoreCase(passKey)) {

                    passKey = "";
                    view.onSuccess("mobileotpmatched");
                } else view.onError("Pin wrong");
            } else view.onError("OTP Not Matched ");


        } else view.onError("Enter Otp Pin");

    }

    @Override
    public void callSendOtpEmail(String email) {


        addDisposable(networkClient.getApiInterface(retrofit).registerSendOtp_Email(email)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Response>() {
                    @Override
                    public void accept(Response sendOTP) {

                        if (Integer.parseInt(sendOTP.getStatusCode()) == 200) {


                            Log.e(TAG, "" + sendOTP.getStatusCode());
                            passKey = String.valueOf(sendOTP.getStatusMessage());
                            Log.e(TAG, "passkey============" + sendOTP.getStatusMessage());


                            view.onSuccess("OTPSuccessEmail");


                        } else

                            view.onError(sendOTP.getStatusMessage());


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e(TAG, throwable.toString());
                        if (throwable instanceof HttpException) {
                            view.onError( "Network Exception "+throwable.toString());

                        }
                        if (throwable instanceof IOException) {
                            view.onError( "Io Exception "+throwable.toString());

                        }
                    }
                }));

    }

    @Override
    public void otpValidateEmail(String otp) {


        if (otp.length() == 6) {


            if (passKey != null) {
                if (otp.equalsIgnoreCase(passKey)) {

                    passKey = "";
                    view.onSuccess("emailotpmatched");
                } else view.onError("Pin wrong");
            } else view.onError("OTP Not Matched ");


        } else view.onError("Enter Otp Pin");


    }

}