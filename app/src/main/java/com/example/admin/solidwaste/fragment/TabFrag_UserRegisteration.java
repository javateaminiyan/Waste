package com.example.admin.solidwaste.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.admin.solidwaste.Interface.IFragUserRegisterContract;
import com.example.admin.solidwaste.R;
import com.example.admin.solidwaste.activity.Login;
import com.example.admin.solidwaste.di.component.DaggerFrag_UserApiComponent;
import com.example.admin.solidwaste.di.module.Frag_UserModule;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.presenter.RegistrationPresenter.Frag_UserPresenter;
import com.example.admin.solidwaste.sharedprefshelper.SharedPrefModule;
import com.example.admin.solidwaste.utils.CommonHelper;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Retrofit;

import static com.example.admin.solidwaste.utils.CommonHelper.isNetworkAvailable;
import static com.example.admin.solidwaste.utils.CommonHelper.isValid;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_firebaseid;


public class TabFrag_UserRegisteration extends Fragment implements IFragUserRegisterContract.view {

    private static final String TAG = TabFrag_UserRegisteration.class.getName();
    private String validateOnSuccessResonspeCall;
    @BindView(R.id.name)
    MaterialEditText username;

    AlertDialog alertDialogOtpValidate;
    @BindView(R.id.mobileno)
    MaterialEditText mMobileno;
    @Inject
    SharedPreferences sharedPreferences;


    MaterialEditText menterOtp;
    @BindView(R.id.email)
    MaterialEditText mEmail;


    android.app.AlertDialog waitingDialog;
    @BindView(R.id.address)
    MaterialEditText mAddress;


    @BindView(R.id.upiid)
    MaterialEditText mUpiid;


    @BindView(R.id.password)
    MaterialEditText mPassword;


    @BindView(R.id.confirmpassword)
    MaterialEditText mConfirmPassword;

    @Inject
    Frag_UserPresenter userPresenter;

    @Inject
    Retrofit retrofit;
    @Inject
    NetworkClient networkClient;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_user_registration, container, false);

        ButterKnife.bind(this, view);
        // menterOtp.setVisibility(View.GONE);
        DaggerFrag_UserApiComponent.builder()
                .frag_UserModule(new Frag_UserModule(this))
                .sharedPrefModule(new SharedPrefModule(getContext()))
                .networkClient(new NetworkClient(CommonHelper.BASE_URL))
                .build()
                .inject(this);
        mAddress.setEnabled(false);
        mUpiid.setEnabled(false);
        mPassword.setEnabled(false);
        mConfirmPassword.setEnabled(false);


        mMobileno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (!TextUtils.isEmpty(mMobileno.getText().toString()) && isValid(s.toString())) {


                    if (isNetworkAvailable(Objects.requireNonNull(getContext()))) {

                        userPresenter.onCheckMobileNo(mMobileno.getText().toString());

                    } else onError("Internet Not Available");


                    Log.e(TAG, "Tab=====valid");

                } else
                    Log.e(TAG, "Tab=====mobile not valid");


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {

                    if (s.toString().endsWith(".com")) {

                        if (isNetworkAvailable(Objects.requireNonNull(getContext()))) {

                            userPresenter.onCheckEmailId(s.toString());
                        } else onError("Internet Not Available");


                        Log.e(TAG, "call email" + s.toString());
                    }

                } else Log.e(TAG, "call email");


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return view;


    }

    @OnClick(R.id.login_btn)
    public void onSubmit() {

        Log.e(TAG, "submit");

        if (isNetworkAvailable(Objects.requireNonNull(getContext()))) {

            userPresenter.onValidate(username.getText().toString(), mMobileno.getText().toString(), mEmail.getText().toString(), mAddress.getText().toString(), mUpiid.getText().toString(), mPassword.getText().toString(), mConfirmPassword.getText().toString());

        } else onError("Internet Not Available");


    }

    public void loadProgressbar() {
        waitingDialog = new SpotsDialog(getActivity());
        waitingDialog.show();

        waitingDialog.setMessage("Please Waiting...");
    }

    public void dismiss() {
        waitingDialog.dismiss();
    }

    @Override
    public void onError(String message) {

        if (message.equals("Already Login"))

            Toasty.info(Objects.requireNonNull(getContext()), "Your are Already Login", Toast.LENGTH_SHORT, true).show();

        else if (message.equalsIgnoreCase("Network Exception") || message.equalsIgnoreCase("Io Exception"))
            dismiss();
        else

            Toasty.info(Objects.requireNonNull(getContext()), message, Toast.LENGTH_SHORT, true).show();

    }

    @Override
    public void onSuccess(String message) {


        if (message.equalsIgnoreCase("validation_success")) {

            loadProgressbar();
            userPresenter.registerUser(username.getText().toString(), mEmail.getText().toString(), mPassword.getText().toString(), "city", mAddress.getText().toString(), sharedPreferences.getString(sharedpref_firebaseid, null), "latlng", "nameofshop", "gstno", mUpiid.getText().toString(), "panno", mMobileno.getText().toString(), "user", sharedPreferences, networkClient, retrofit);
            Log.e(TAG, message);

        } else if (message.equalsIgnoreCase("SuccessRegistered")) {
            dismiss();
            startActivity(new Intent(getContext(), Login.class));

        } else if (message.equalsIgnoreCase("SendOTP")) {

            Log.e(TAG, "SendOTPCall");

            userPresenter.callSendOtp(mMobileno.getText().toString());

            otpValidateDialog();


        } else if (message.equalsIgnoreCase("OTPSuccess")) {
            validateOnSuccessResonspeCall = "OTPSuccess";

        } else if (message.equalsIgnoreCase("mobileotpmatched")) {

            alertDialogOtpValidate.dismiss();
            mEmail.requestFocus();


        } else if (message.equalsIgnoreCase("SendOTPEmail")) {
            userPresenter.callSendOtpEmail(mEmail.getText().toString());
            otpValidateDialog();


        } else if (message.equalsIgnoreCase("OTPSuccessEmail")) {
            validateOnSuccessResonspeCall = "OTPSuccessEmail";


        } else if (message.equalsIgnoreCase("emailotpmatched")) {

            alertDialogOtpValidate.dismiss();

            mAddress.setEnabled(true);
            mUpiid.setEnabled(true);
            mPassword.setEnabled(true);
            mConfirmPassword.setEnabled(true);

            mAddress.requestFocus();

        } else


            Toasty.success(Objects.requireNonNull(getContext()), message, Toast.LENGTH_SHORT, true).show();

    }


    private void otpValidateDialog() {

        @SuppressLint("InflateParams") View otplayout = getLayoutInflater().inflate(R.layout.otpvalidatelayout, null);

        menterOtp = (MaterialEditText) otplayout.findViewById(R.id.enterotp);
        Button cancel = (Button) otplayout.findViewById(R.id.cancel);
        menterOtp.setText("");


        menterOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() == 6) {

                    if (validateOnSuccessResonspeCall.equalsIgnoreCase("OTPSuccessEmail")) {
                        userPresenter.otpValidateEmail(menterOtp.getText().toString());


                    } else if (validateOnSuccessResonspeCall.equalsIgnoreCase("OTPSuccess")) {
                        userPresenter.otpValidate(menterOtp.getText().toString());

                    }
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        AlertDialog.Builder otpValidateDialog = new AlertDialog.Builder(Objects.requireNonNull(getActivity()), R.style.MyAlertDialogTheme);

        otpValidateDialog.setTitle("OTP VERIFICATION");
        otpValidateDialog.setCancelable(false);
        otpValidateDialog.setView(otplayout);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMobileno.setText("");
                mEmail.setText("");
                alertDialogOtpValidate.dismiss();
            }
        });
        alertDialogOtpValidate = otpValidateDialog.create();
        alertDialogOtpValidate.show();
    }

//    @Override
//    public void onDestroy() {
//
//        userPresenter.stop();
//        super.onDestroy();
//    }
//
//
//    @Override
//    public void onPause() {
//        userPresenter.stop();
//
//        super.onPause();
//        userPresenter = null;
//
//    }
//
//    @Override
//    public void onStart() {
//
//        userPresenter.start();
//        super.onStart();
//        userPresenter = null;
//
//    }
//
//    @Override
//    public void onStop() {
//
//        userPresenter.stop();
//        super.onStop();
//        userPresenter = null;
//
//    }

}