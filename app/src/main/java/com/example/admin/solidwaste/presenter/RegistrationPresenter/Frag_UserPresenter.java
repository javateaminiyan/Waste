package com.example.admin.solidwaste.presenter.RegistrationPresenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.admin.solidwaste.Interface.BasePresenter;
import com.example.admin.solidwaste.Interface.IFragUserRegisterContract;
import com.example.admin.solidwaste.pojo.GeneralResponse.GeneralResponse;
import com.example.admin.solidwaste.pojo.Response;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.models.Login.Registration.UserRepo;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Retrofit;

import static android.content.ContentValues.TAG;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_mobileno;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_password;

public class Frag_UserPresenter extends BasePresenter<IFragUserRegisterContract.view> implements IFragUserRegisterContract.userpresenter {

    private   IFragUserRegisterContract.view view;
    private String passKey = null;
    @Inject
     NetworkClient networkClient;
    @Inject
      Retrofit retrofit;


    @Inject
     Frag_UserPresenter(IFragUserRegisterContract.view view) {
        super(view);
        this.view = view;
    }

    @Override
    public void onValidate(String name, String mobileno, String email, String address, String upiid, String password, String confirmpassword) {


        UserRepo user = new UserRepo(name, mobileno, email, address, upiid, password, confirmpassword);


        int isLoginSuccess = user.isvalidate();


        switch (isLoginSuccess) {

            case 0: {
                view.onError("Enter Name");

                break;
            }
            case 1: {

                view.onError("Enter EmailPattern Not Matched");
                break;
            }

            case 2: {
                view.onError("Enter Mobile Number  ");
                break;
            }

            case 3: {
                view.onError("Enter UPI ID");
                break;
            }

            case 4: {
                view.onError("Enter Address");
                break;
            }
            case 5: {
                view.onError("Password is to short");
                break;
            }
            case 6: {
                view.onError("Confirm Password is to short");
                break;
            }
            case 7: {
                view.onError("Please Give correct Password");
                break;
            }
            default: {
                view.onSuccess("validation_success");


            }


        }


    }

    @Override
    public void registerUser(String name, String email, String password, String city, String address, String firebaseid, String latlng, String nameofshop, String gstno, String upiid, String panno, String mobileno, String typeofuser, SharedPreferences sharedPreferences, NetworkClient networkClient, Retrofit retrofit) {
        Log.e(TAG, "registerUser");

        addDisposable(networkClient.getApiInterface(retrofit).register(name, email, password, city, address, firebaseid, latlng, nameofshop, gstno, upiid, panno, mobileno, typeofuser)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Response>() {
                    @Override
                    public void accept(Response response) {

                        Log.e(TAG, "registerUser======" + response.getStatusCode());

                        if (Integer.parseInt(response.getStatusCode()) == 200) {


                            SharedPreferences.Editor editor = sharedPreferences.edit();


                            editor.putString(sharedpref_mobileno, mobileno);
                            editor.putString(sharedpref_password, password);




                            editor.apply();


                            view.onSuccess("SuccessRegistered");


                        } else
                            view.onError(response.getStatusMessage());


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        view.onError(throwable.toString());
                        Log.e(TAG, "registerUser======" + throwable);
                        if (throwable instanceof HttpException) {
                            view.onError( "Network Exception"+throwable.toString());

                        }
                        if (throwable instanceof IOException) {
                            view.onError( "Io Exception"+throwable.toString());

                        }
                    }
                }));


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

                            view.onError("Not Send otp to mail Your are Already Login");


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
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
                        //   view.onError(throwable.toString());
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
