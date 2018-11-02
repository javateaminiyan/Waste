package com.example.admin.solidwaste.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.admin.solidwaste.Interface.CheckInternetCallback;
import com.example.admin.solidwaste.Interface.LoginActivityContract;
import com.example.admin.solidwaste.R;
import com.example.admin.solidwaste.di.component.DaggerSplashScreenComponent;
import com.example.admin.solidwaste.di.component.SplashScreenComponent;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.di.module.SplashScreenModule;
import com.example.admin.solidwaste.presenter.Login.LoginPresenter;
import com.example.admin.solidwaste.service.InternetCheck;
import com.example.admin.solidwaste.sharedprefshelper.SharedPrefModule;
import com.example.admin.solidwaste.utils.CommonHelper;

import javax.inject.Inject;

import es.dmoral.toasty.Toasty;
import retrofit2.Retrofit;

import static com.example.admin.solidwaste.utils.CommonHelper.isNetworkAvailable;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_mobileno;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_password;


public class SplashScreen extends AppCompatActivity implements LoginActivityContract.ILoginView {

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    Retrofit retrofit;
    @Inject
    NetworkClient networkClient;
    @Inject
    LoginPresenter loginPresenter;
    public String TAG = SplashScreen.class.getName();
    // Splash screen timer
    private static final int SPLASH_TIME_OUT = 600;
    boolean value;


    LinearLayout rootlayout;


    final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 100;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        rootlayout = findViewById(R.id.rootlayout);

        InternetCheck.bindListener(new CheckInternetCallback() {

            @Override
            public void statusCode(int code) {
                Toasty.success(getApplicationContext(), "code status internet" + code, Toast.LENGTH_SHORT, true).show();


                Log.e(TAG, "code status internet" + code);

                if (code != 0) {

                    if (isNetworkAvailable(getApplicationContext())) {

                        splash_Call();
                    } else {

                        Snackbar.make(rootlayout, "Internet Not Connected", Snackbar.LENGTH_SHORT).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreen.this);
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

                } else {

                    Snackbar.make(rootlayout, "Internet Not Connected", Snackbar.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreen.this);
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


        checkRunTimePermission();


        DaggerSplashScreenComponent.builder()
                .splashScreenModule(new SplashScreenModule(this))
                .sharedPrefModule(new SharedPrefModule(this))
                .networkClient(new NetworkClient(CommonHelper.BASE_URL))
                .build()
                .inject(this);


        if (isNetworkAvailable(getApplicationContext())) {

            String mobileno = sharedPreferences.getString(sharedpref_mobileno, "");

            String password = sharedPreferences.getString(sharedpref_password, "");
            if (!mobileno.isEmpty() && !password.isEmpty()) {
                if (isNetworkAvailable(getApplicationContext())) {
                    Log.e(TAG, "onClick" + sharedPreferences.getString(sharedpref_mobileno, null) + "======" + sharedPreferences.getString(sharedpref_password, null));


                    loginPresenter.onLogin(sharedPreferences.getString(sharedpref_mobileno, null), sharedPreferences.getString(sharedpref_password, null), getApplicationContext());

                }
            } else splash_Call();

        } else {


            Snackbar.make(rootlayout, "Internet Not Connected", Snackbar.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreen.this);
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


    public void splash_Call() {


        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {

                Intent dashboardintent = new Intent(getApplicationContext(), Login.class);
                startActivity(dashboardintent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }


    private void checkRunTimePermission() {
        String[] permissionArrays = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.SEND_SMS,
                Manifest.permission.READ_SMS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION

        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissionArrays, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean openActivityOnce = true;
        boolean openDialogOnce = true;
        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE) {
            for (int i = 0; i < grantResults.length; i++) {
                String permission = permissions[i];

                // isPermitted = grantResults[i] == PackageManager.PERMISSION_GRANTED;

                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    // user rejected the permission
                    boolean showRationale = shouldShowRequestPermissionRationale(permission);
                    if (!showRationale) {
                        splash_Call();
                        //execute when 'never Ask Again' tick and permission dialog not show
                    } else {
                        if (openDialogOnce) {
                            checkRunTimePermission();
                        }
                    }
                }
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();
        InternetCheck.unbindListener();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            InternetCheck.unbindListener();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onLoginResult(String message) {
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {

                if (message.equals("MerchantDashBoard")) {
                    startActivity(new Intent(SplashScreen.this, AdminDashboard.class));
                    finish();
                    Log.e(TAG, "Admin");
                } else if (message.equals("UserDashBoard")) {
                    startActivity(new Intent(SplashScreen.this, UserDashboard.class));
                    finish();
                    Log.e(TAG, "Admin");
                } else
                    Toasty.success(getApplicationContext(), message, Toast.LENGTH_SHORT, true).show();
            }
        }, SPLASH_TIME_OUT);


    }

    @Override
    public void onLoginError(String message) {
        // Toasty.info(getApplicationContext(), message, Toast.LENGTH_SHORT, true).show();

    }
}


