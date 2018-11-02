package com.example.admin.solidwaste.presenter.Login;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.admin.solidwaste.Interface.BasePresenter;
import com.example.admin.solidwaste.Interface.LoginActivityContract;
import com.example.admin.solidwaste.pojo.Login.Login_Response;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.entities.LoginUser;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Retrofit;

import static com.example.admin.solidwaste.utils.CommonHelper.isNetworkAvailable;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_address;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_city;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_datetime;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_email;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_firebaseid;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_gstno;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_latlng;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_mobileno;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_name;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_name_of_shop;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_panno;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_password;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_status;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_typeofuser;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_upino;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_userid;

public class LoginPresenter extends BasePresenter<LoginActivityContract.ILoginView> implements LoginActivityContract.ILoginPresenter {
    @Inject
    SharedPreferences sharedPreferences;
    @Inject

    NetworkClient networkClient;

    @Inject
    Retrofit retrofit;

    private LoginActivityContract.ILoginView loginView;
    private Login_Response loginResponseList;
    public String TAG = LoginPresenter.class.getName();

    @Inject
    LoginPresenter(LoginActivityContract.ILoginView loginView) {
        super(loginView);

        this.loginView = loginView;
    }


    @Override
    public void onLogin(String Username, String Password, Context context) {

        LoginUser user = new LoginUser(Username, Password);


        int isLoginSuccess = user.isvalidate();


        switch (isLoginSuccess) {

            case 0: {
                loginView.onLoginError("Enter Username");

                break;
            }
            case 1: {

                loginView.onLoginError("Enter Email/Mobile Number Pattern Not Matched");
                break;
            }

            case 3: {
                loginView.onLoginError("Enter Password");
                break;
            }

            case 4: {
                loginView.onLoginError("Password is to short");
                break;
            }
            default:
                LoginUser(Username, Password, context);


        }


    }


    @Override
    public void LoginUser(String Username, String Password, Context context) {

        if (isNetworkAvailable(context)) {
            Log.e(TAG, "onClick");


            Log.e(TAG, "LoginUser");
            addDisposable(networkClient.getApiInterface(retrofit).LoginApi(Username)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Consumer<Login_Response>() {
                        @Override
                        public void accept(Login_Response response) throws Exception {

                            Log.e(TAG, "res" + response.getResponse().StatusCode);

                            if (response.getResponse().getStatusCode() == 200) {
                                Log.e(TAG, "LoginUser === onSuccess");

                                Log.e(TAG, "res password" + response.getResponse().Response.get(0).password);

                                if (Password.equals(response.getResponse().Response.get(0).password)) {
                                    Log.e(TAG, "res  success password" + response.getResponse().Response.get(0).status);


                                    if (response.getResponse().Response.get(0).status.equals("active")) {


                                        loginResponseList = response;
                                      //  loginView.onLoginResult("Success" + loginResponseList.getResponse().getResponse().get(0).firebaseid);
                                        Log.e(TAG, "res  success firebase" + response.getResponse().Response.get(0).firebaseid);


                                        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

//
//                                        if (!response.getResponse().Response.get(0).firebaseid.equalsIgnoreCase(refreshedToken)) {
//
//                                        } else updateSharedPref_Users();

                                        Log.e("firebaseid==>", refreshedToken + " gvn");
                                        Log.e("userid==>", String.valueOf(response.getResponse().Response.get(0).userid + " gvn"));

                                        UpdateFirebaseId(String.valueOf(response.getResponse().Response.get(0).userid), refreshedToken);

                                    } else
                                        loginView.onLoginError("Your are blocked by Admin");


                                } else
                                    loginView.onLoginError("Password Not Matched");


                            } else if (response.getResponse().getStatusCode() == 400) {
                                loginView.onLoginError("Mobile No / Email Not Registered");
                            }


                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.e(TAG, "LoginUser === throwable " + throwable);

                            Log.e(TAG, "post " + throwable);

                            if (throwable instanceof HttpException) {
                                loginView.onLoginError("Network Exception " + throwable.toString());

                            }
                            if (throwable instanceof IOException) {
                                loginView.onLoginError("Io Exception " + throwable.toString());

                            }

                        }
                    }));

        } else loginView.onLoginError("Internet Not Available");
    }

    @Override
    public void UpdateFirebaseId(String Userid, String FirebaseId) {


        addDisposable(networkClient.getApiInterface(retrofit).firebaseUpdate(Userid, FirebaseId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }


    private void handleResponse(com.example.admin.solidwaste.pojo.Response response) throws IOException {
        Log.e(TAG, "handleResponse");


        if (Integer.parseInt(response.getStatusCode()) == 200) {

            updateSharedPref_Users();


        } else loginView.onLoginError("Update Firebase Failed");


    }


    private void handleError(Throwable throwable) {
        Log.e(TAG, "res" + throwable);
        if (throwable instanceof HttpException) {
            loginView.onLoginError("Network Exception " + throwable.toString());

        }
        if (throwable instanceof IOException) {
            loginView.onLoginError("Io Exception " + throwable.toString());

        }

    }


    private void updateSharedPref_Users() {
        Log.e(TAG, "inside handlerespomse" + loginResponseList.getResponse().Response.get(0).name);


        SharedPreferences.Editor editor = sharedPreferences.edit();


        editor.putString(sharedpref_userid, "" + loginResponseList.getResponse().getResponse().get(0).userid);
        editor.putString(sharedpref_name, loginResponseList.getResponse().getResponse().get(0).name);
        editor.putString(sharedpref_mobileno, loginResponseList.getResponse().getResponse().get(0).mobile);editor.putString(sharedpref_email, loginResponseList.getResponse().getResponse().get(0).email);
        editor.putString(sharedpref_password, loginResponseList.getResponse().getResponse().get(0).password);
        editor.putString(sharedpref_city, loginResponseList.getResponse().getResponse().get(0).city);
        editor.putString(sharedpref_address, loginResponseList.getResponse().getResponse().get(0).address);

        //new Firebase Id
        editor.putString(sharedpref_firebaseid, sharedPreferences.getString(sharedpref_firebaseid, null));
        editor.putString(sharedpref_typeofuser, loginResponseList.getResponse().getResponse().get(0).typeofuser);
        editor.putString(sharedpref_latlng, loginResponseList.getResponse().getResponse().get(0).latlng);
        editor.putString(sharedpref_name_of_shop, loginResponseList.getResponse().getResponse().get(0).name_of_shop);
        editor.putString(sharedpref_gstno, loginResponseList.getResponse().getResponse().get(0).gstno);
        editor.putString(sharedpref_upino, loginResponseList.getResponse().getResponse().get(0).upino);
        editor.putString(sharedpref_panno, loginResponseList.getResponse().getResponse().get(0).panno);
        editor.putString(sharedpref_status, loginResponseList.getResponse().getResponse().get(0).status);
        editor.putString(sharedpref_datetime, loginResponseList.getResponse().getResponse().get(0).datetime);


        editor.apply();


        if (loginResponseList.getResponse().getResponse().get(0).typeofuser.equalsIgnoreCase("Merchant"))
            loginView.onLoginResult("MerchantDashBoard");

        else
            loginView.onLoginResult("UserDashBoard");
    }


}
