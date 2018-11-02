package com.example.admin.solidwaste.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.solidwaste.Interface.CheckInternetCallback;
import com.example.admin.solidwaste.Interface.ForgotContract;
import com.example.admin.solidwaste.Interface.SmsListener;
import com.example.admin.solidwaste.R;
import com.example.admin.solidwaste.di.component.DaggerForgotApiComponent;
import com.example.admin.solidwaste.di.module.ForgotPresenterModule;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.presenter.ForgotPresenter.ForgotPresenter;
import com.example.admin.solidwaste.service.InternetCheck;
import com.example.admin.solidwaste.service.SMSReceiver;
import com.example.admin.solidwaste.utils.CommonHelper;
import com.rengwuxian.materialedittext.MaterialEditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Retrofit;

import static com.example.admin.solidwaste.utils.CommonHelper.isNetworkAvailable;

public class ForgotPassword extends AppCompatActivity implements ForgotContract.View {
    private String TAG = ForgotPassword.class.getName();

    @BindView(R.id.imgforgot)
    ImageView img_forgot;
    @BindView(R.id.contenttitleforgot)
    TextView forgottitle;


    @BindView(R.id.emailormobileno)
    MaterialEditText memailormobileno;

    @BindView(R.id.enterotp)
    MaterialEditText menter_Otp;

    @BindView(R.id.password)
    MaterialEditText mPassword;
    @BindView(R.id.confirmpassword)
    MaterialEditText mConfirmPassword;

    @BindView(R.id.send_otp)
    Button sendotp_Btn;

    String message = null;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    ForgotPresenter presenter;
    @Inject
    Retrofit retrofit;
    @Inject
    NetworkClient networkClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Forgot Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        DaggerForgotApiComponent.builder()
                .forgotPresenterModule(new ForgotPresenterModule(this))
                .networkClient(new NetworkClient(CommonHelper.BASE_URL))

                .build()
                .inject(this);


        menter_Otp.setVisibility(View.GONE);
        mConfirmPassword.setVisibility(View.GONE);
        mPassword.setVisibility(View.GONE);
        SMSReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                Log.d("Text", messageText);
                Toast.makeText(ForgotPassword.this, "Message: " + messageText, Toast.LENGTH_LONG).show();
                menter_Otp.setText(message);
            }
        });


        InternetCheck.bindListener(new CheckInternetCallback() {

            @Override
            public void statusCode(int code) {
                Toasty.success(getApplicationContext(), "code status internet" + code, Toast.LENGTH_SHORT, true).show();


                Log.e(TAG, "code status internet" + code);
            }
        });

    }


    @SuppressLint("SetTextI18n")
    @OnClick(R.id.send_otp)
    public void sendOTPClick() {


        if (isNetworkAvailable(getApplicationContext())) {


            if (sendotp_Btn.getText().toString().equals("Send OTP")) {


                String response = presenter.sendEmailorMobileno(memailormobileno.getText().toString());

                if (response != null) {
                    sendotp_Btn.setText(response);
                    memailormobileno.setVisibility(View.GONE);
                    menter_Otp.setVisibility(View.VISIBLE);
                }


            } else if (sendotp_Btn.getText().toString().equals("Submit")) {


                String response_otp = presenter.otpValidate(menter_Otp.getText().toString());

                if (response_otp.equals("valid")) {
                    forgottitle.setText("Please Enter Password & Confirm Password To Reset");
                    img_forgot.setImageResource(R.drawable.ic_key);
                    menter_Otp.setVisibility(View.GONE);
                    mConfirmPassword.setVisibility(View.VISIBLE);
                    mPassword.setVisibility(View.VISIBLE);
                    sendotp_Btn.setText("Change Password");
                }

            } else if (sendotp_Btn.getText().toString().equals("Change Password")) {


                presenter.validatePassword(mPassword.getText().toString(), mConfirmPassword.getText().toString());

            }

        } else onError("Internet Not Available");


    }

    @Override
    public void onError(String message) {
        Toasty.info(getApplicationContext(), message, Toast.LENGTH_SHORT, true).show();

    }

    @Override
    public void onSuccess(String message) {
        Toasty.success(getApplicationContext(), message, Toast.LENGTH_SHORT, true).show();

    }

    @Override
    public void handleResponse(String message) {


        if (message.equals("completed")) {
            startActivity(new Intent(this, Login.class));
            finish();
        }
        Toasty.info(getApplicationContext(), message, Toast.LENGTH_SHORT, true).show();

    }

    @Override
    public void handleError(String error) {
        Toasty.info(getApplicationContext(), error, Toast.LENGTH_SHORT, true).show();

    }


    @Override
    protected void onDestroy() {
        //   presenter.stop();

        super.onDestroy();
        SMSReceiver.unbindListener();
        InternetCheck.unbindListener();
        //  presenter = null;
    }

    @Override
    protected void onPause() {
        //   presenter.stop();

        super.onPause();
        // LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        //presenter = null;
    }

    @Override
    protected void onStart() {
        //  presenter.start();

        super.onStart();
        // presenter = null;

    }

    @Override
    protected void onStop() {

        super.onStop();
        //  presenter.stop();

        //  presenter = null;

    }


//    private BroadcastReceiver receiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (intent.getAction().equalsIgnoreCase("otp")) {
//                message = intent.getStringExtra("message");
//                Log.e("messag= =>", message + " <====");
//
//                menter_Otp.setText(message);
//            }
//        }
//    };


//    @Override
//    public void onResume() {
//        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
//        super.onResume();
//    }


}
