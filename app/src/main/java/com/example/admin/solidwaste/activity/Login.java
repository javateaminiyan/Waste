package com.example.admin.solidwaste.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.solidwaste.Interface.CheckInternetCallback;
import com.example.admin.solidwaste.Interface.IFirebaseMessagingCallbackResponse;
import com.example.admin.solidwaste.Interface.LoginActivityContract;
import com.example.admin.solidwaste.R;
import com.example.admin.solidwaste.di.component.DaggerLoginUserComponent;
import com.example.admin.solidwaste.di.module.LoginPresenterModule;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.fcmmessagesendcontroller.FirebaseMessagingMessageSendController;
import com.example.admin.solidwaste.presenter.Login.LoginPresenter;

import com.example.admin.solidwaste.service.InternetCheck;
import com.example.admin.solidwaste.sharedprefshelper.SharedPrefModule;
import com.example.admin.solidwaste.utils.CommonHelper;
import com.google.firebase.iid.FirebaseInstanceId;


import org.json.JSONException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Retrofit;

import static com.example.admin.solidwaste.utils.CommonHelper.isNetworkAvailable;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_firebaseid;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_mobileno;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_password;

public class Login extends AppCompatActivity implements LoginActivityContract.ILoginView {
    private String TAG = Login.class.getName();

    @BindView(R.id.password)
    EditText mPassword;

    @BindView(R.id.username)
    EditText mUsername;
    //
    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    Retrofit retrofit;
    @Inject
    NetworkClient networkClient;
    @Inject
    LoginPresenter loginPresenter;


    android.app.AlertDialog waitingDialog;

    FirebaseMessagingMessageSendController firebaseMessagingMessageSendController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //ToDo Refreshed Token
        final String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "FCM token: " + refreshedToken);


        DaggerLoginUserComponent.builder()
                .loginPresenterModule(new LoginPresenterModule(this))
                .sharedPrefModule(new SharedPrefModule(this))
                .networkClient(new NetworkClient(CommonHelper.BASE_URL))
                .build()
                .inject(this);

        InternetCheck.bindListener(new CheckInternetCallback() {

            @Override
            public void statusCode(int code) {
                Toasty.success(getApplicationContext(), "code status internet" + code, Toast.LENGTH_SHORT, true).show();


                Log.e(TAG, "code status internet" + code);

                if (code != 0) {


                } else {

                     AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                    builder.setTitle("Oops");
                    builder.setMessage("Internet Not Connected");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    dialog.dismiss();


                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });

        int REQUEST_CODE_READ_SMS = 100;
        ActivityCompat.requestPermissions(Login.this, new String[]{android.Manifest.permission.RECEIVE_SMS}, REQUEST_CODE_READ_SMS);



        SharedPreferences.Editor sharedpref = sharedPreferences.edit();
        sharedpref.putString(sharedpref_firebaseid, refreshedToken);
        sharedpref.apply();

        if (sharedPreferences.getString(sharedpref_mobileno, null) != null && sharedPreferences.getString(sharedpref_password, null) != null) {
            mUsername.setText(sharedPreferences.getString(sharedpref_mobileno, null));
            mPassword.setText(sharedPreferences.getString(sharedpref_password, null));
        }


    }

    @OnClick(R.id.signup_btn)
    public void signUp() {


        startActivity(new Intent(Login.this, Registration.class));

    }

    public void loadProgressbar() {
        waitingDialog = new SpotsDialog(Login.this);
        waitingDialog.show();

        waitingDialog.setMessage("Please Waiting...");
    }

    public void dismiss() {
        waitingDialog.dismiss();
    }

    @OnClick(R.id.login_btn)
    public void loginFun() {

        if (isNetworkAvailable(getApplicationContext())) {
            Log.e(TAG, "onClick");
            loadProgressbar();


            loginPresenter.onLogin(mUsername.getText().toString(), mPassword.getText().toString(), getApplicationContext());
            hideSoftKeyboard();
        } else onLoginError("Internet Not Available");
    }

    @OnClick(R.id.forgotpassword)
    public void forgotpassword() {


        startActivity(new Intent(Login.this, ForgotPassword.class));

    }


    @Override
    public void onLoginResult(String message) {
        hideSoftKeyboard();
        if (message.equals("MerchantDashBoard")) {
            startActivity(new Intent(Login.this, AdminDashboard.class));
            finish();


            Log.e(TAG+"==>", "Admin");

        } else if (message.equals("UserDashBoard")) {
            startActivity(new Intent(Login.this, UserDashboard.class));
            finish();
            Log.e(TAG+"==>", "User");

        } else
            Toasty.success(getApplicationContext(), message, Toast.LENGTH_SHORT, true).show();

        dismiss();
    }

    @Override
    public void onLoginError(String message) {
        Toasty.info(getApplicationContext(), message, Toast.LENGTH_SHORT, true).show();
        dismiss();
        hideSoftKeyboard();
        //   Toasty.info(getApplicationContext(), "Internet Not Connected", Toast.LENGTH_SHORT, true).show();

    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
        // loginPresenter.stop();

        // loginPresenter = null;
        InternetCheck.unbindListener();

    }


    @Override
    protected void onPause() {

        super.onPause();
        //loginPresenter.stop();
        InternetCheck.unbindListener();
        // loginPresenter = null;
    }

    @Override
    protected void onStart() {

        super.onStart();
        //  loginPresenter.start();

        //  loginPresenter = null;

    }

    @Override
    protected void onStop() {


        super.onStop();
        //  loginPresenter.stop();

        // loginPresenter = null;

    }


//
//    private void hideSoftKeyboard() {
//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//
//        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (inputMethodManager != null) {
//            inputMethodManager.toggleSoftInput(InputMethodManager.RESULT_HIDDEN, 0);
//        }
//
//
//    }



    @Override
    protected void onResume() {
        super.onResume();

    }


    protected void hideSoftKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
