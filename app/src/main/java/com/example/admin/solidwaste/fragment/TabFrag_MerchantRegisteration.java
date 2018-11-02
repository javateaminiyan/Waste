package com.example.admin.solidwaste.fragment;

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
import com.example.admin.solidwaste.di.component.DaggerFrag_MerchantApiComponent;
import com.example.admin.solidwaste.di.module.Frag_MerchantModule;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.presenter.RegistrationPresenter.Frag_MerchantPresenter;
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


public class TabFrag_MerchantRegisteration extends Fragment implements IFragUserRegisterContract.view {


    private static final String TAG = TabFrag_MerchantRegisteration.class.getName();
    @BindView(R.id.name)
    MaterialEditText username;

    private String validateOnSuccessResonspeCall;
    @BindView(R.id.shopname)
    MaterialEditText mShopname;


    android.app.AlertDialog waitingDialog;
    @BindView(R.id.mobileno)
    MaterialEditText mMobileno;


    @BindView(R.id.email)
    MaterialEditText mEmail;


    @BindView(R.id.address)
    MaterialEditText mAddress;


    @BindView(R.id.upiid)
    MaterialEditText mUpiid;

//
//    @BindView(R.id.enterotp)
//    MaterialEditText menterOtp;

    @BindView(R.id.gstno)
    MaterialEditText mGstno;


    @BindView(R.id.panno)
    MaterialEditText mPanno;

    AlertDialog alertDialogOtpValidate;
    @Inject
    SharedPreferences sharedPreferences;
    @BindView(R.id.password)
    MaterialEditText mPassword;

    MaterialEditText menterOtp;

    @BindView(R.id.confirmpassword)
    MaterialEditText mConfirmPassword;


    @Inject
    Frag_MerchantPresenter merchantPresenter;


    @Inject
    Retrofit retrofit;
    @Inject
    NetworkClient networkClient;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.frag_merchant_registration, container, false);

        ButterKnife.bind(this, view);


        DaggerFrag_MerchantApiComponent.builder()
                .frag_MerchantModule(new Frag_MerchantModule(this))
                .sharedPrefModule(new SharedPrefModule(getContext()))
                .networkClient(new NetworkClient(CommonHelper.BASE_URL))

                .build()
                .inject(this);

        mAddress.setEnabled(false);
        mUpiid.setEnabled(false);
        mPassword.setEnabled(false);
        mConfirmPassword.setEnabled(false);
        mPanno.setEnabled(false);
        mGstno.setEnabled(false);


        mMobileno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (!TextUtils.isEmpty(mMobileno.getText().toString()) && isValid(s.toString())) {
                    if (isNetworkAvailable(getContext())) {


                        merchantPresenter.onCheckMobileNo(mMobileno.getText().toString());

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

                        if (isNetworkAvailable(getContext())) {


                            merchantPresenter.onCheckEmailId(s.toString());

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

            merchantPresenter.onValidate(username.getText().toString(), mShopname.getText().toString(), mMobileno.getText().toString(), mEmail.getText().toString(), mAddress.getText().toString(), mUpiid.getText().toString(), mPanno.getText().toString(), mGstno.getText().toString(), mPassword.getText().toString(), mConfirmPassword.getText().toString());

        } else onError("Internet Not Available");


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

    public void loadProgressbar() {
        waitingDialog = new SpotsDialog(getActivity());
        waitingDialog.show();

        waitingDialog.setMessage("Please Waiting...");
    }

    public void dismiss() {
        waitingDialog.dismiss();
    }

    @Override
    public void onSuccess(String message) {
        if (message.equalsIgnoreCase("validation_success")) {

            loadProgressbar();

            merchantPresenter.registerMerchant(username.getText().toString(), mEmail.getText().toString(), mPassword.getText().toString(), "city", mAddress.getText().toString(), sharedPreferences.getString(sharedpref_firebaseid, null), "latlng", mShopname.getText().toString(), mGstno.getText().toString(), mUpiid.getText().toString(), mPanno.getText().toString(), mMobileno.getText().toString(), "merchant", sharedPreferences, networkClient, retrofit);
            Log.e(TAG, message);

        } else if (message.equalsIgnoreCase("SuccessRegistered")) {

            dismiss();
            startActivity(new Intent(getContext(), Login.class));

        } else if (message.equalsIgnoreCase("SendOTP")) {

            Log.e(TAG, "SendOTPCall");

            merchantPresenter.callSendOtp(mMobileno.getText().toString());
            otpValidateDialog();


        } else if (message.equalsIgnoreCase("OTPSuccess")) {
            validateOnSuccessResonspeCall = "OTPSuccess";
            //   userPresenter.otpValidate(menterOtp.getText().toString());

        } else if (message.equalsIgnoreCase("mobileotpmatched")) {

            alertDialogOtpValidate.dismiss();

            mEmail.requestFocus();


        } else if (message.equalsIgnoreCase("SendOTPEmail")) {


            merchantPresenter.callSendOtpEmail(mEmail.getText().toString());
            otpValidateDialog();


        } else if (message.equalsIgnoreCase("OTPSuccessEmail")) {
            validateOnSuccessResonspeCall = "OTPSuccessEmail";


        } else if (message.equalsIgnoreCase("emailotpmatched")) {
            alertDialogOtpValidate.dismiss();

            mAddress.setEnabled(true);
            mUpiid.setEnabled(true);
            mPassword.setEnabled(true);
            mConfirmPassword.setEnabled(true);
            mPanno.setEnabled(true);
            mGstno.setEnabled(true);
            mAddress.requestFocus();

        } else


            Toasty.success(getContext(), message, Toast.LENGTH_SHORT, true).show();


    }

    private void otpValidateDialog() {

        View otplayout = getLayoutInflater().inflate(R.layout.otpvalidatelayout, null);

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
                        merchantPresenter.otpValidateEmail(menterOtp.getText().toString());


                    } else if (validateOnSuccessResonspeCall.equalsIgnoreCase("OTPSuccess")) {
                        merchantPresenter.otpValidate(menterOtp.getText().toString());

                    }
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        AlertDialog.Builder otpValidateDialog = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogTheme);

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
//        merchantPresenter.stop();
//        super.onDestroy();
//        merchantPresenter = null;
//    }
//
//
//    @Override
//    public void onPause() {
//
//        merchantPresenter.stop();
//        super.onPause();
//        merchantPresenter = null;
//    }
//
//    @Override
//    public void onStart() {
//        merchantPresenter.start();
//
//        super.onStart();
//        merchantPresenter = null;
//
//    }
//
//    @Override
//    public void onStop() {
//        merchantPresenter.stop();
//
//        super.onStop();
//        merchantPresenter = null;
//
//    }
}