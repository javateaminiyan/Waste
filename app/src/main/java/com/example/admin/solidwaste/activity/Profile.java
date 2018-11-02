package com.example.admin.solidwaste.activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.solidwaste.Interface.ProfileActivityContract;
import com.example.admin.solidwaste.R;
import com.example.admin.solidwaste.di.component.DaggerIProfileComponent;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.di.module.ProfilePresenterModule;
import com.example.admin.solidwaste.presenter.UserModules.ProfilePresenter;
import com.example.admin.solidwaste.sharedprefshelper.SharedPrefModule;
import com.example.admin.solidwaste.utils.CommonHelper;
import com.rengwuxian.materialedittext.MaterialEditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Retrofit;

import static com.example.admin.solidwaste.utils.CommonHelper.isNetworkAvailable;
import static com.example.admin.solidwaste.utils.CommonHelper.isValid;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_address;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_email;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_gstno;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_mobileno;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_name;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_name_of_shop;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_panno;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_password;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_upino;

public class Profile extends AppCompatActivity implements ProfileActivityContract.IProfileView {
    MenuItem item_edit, item_update;


    @BindView(R.id.txt_address)
    TextView mtxt_Address;
    @BindView(R.id.txt_email)
    TextView mtxt_Email;
    @BindView(R.id.txt_gstno)
    TextView mtxt_Gstno;
    @BindView(R.id.txt_mobileno)
    TextView mtxt_Mobileno;
    @BindView(R.id.txt_shopname)
    TextView mtxt_Shopname;
    @BindView(R.id.txt_panno)
    TextView mtxt_Panno;
    @BindView(R.id.txt_upiid)
    TextView mtxt_Upiid;


    @BindView(R.id.emailid)
    TextView mEmailid;

    @BindView(R.id.username)
    TextView mUsername;


    @BindView(R.id.label_upiid)
    TextView mlabel_upiid;

    @BindView(R.id.layout_editshowdetails)
    LinearLayout meditshowdetails;

    @BindView(R.id.layout_showdetails)
    LinearLayout mshowdetails;


    @BindView(R.id.label_shopname)
    TextView mlabel_shopname;


    @BindView(R.id.label_panno)
    TextView mlabel_panno;


    @BindView(R.id.label_mobileno)
    TextView mlabel_mobileno;


    @BindView(R.id.label_emailid)
    TextView mlabel_emailid;


    @BindView(R.id.label_address)
    TextView mlabel_address;


    @BindView(R.id.label_gstno)
    TextView getMlabel_gstno;

    MaterialEditText menterOtp;

    @BindView(R.id.edtmobileno)
    MaterialEditText edtMobileno;

    AlertDialog alertDialogOtpValidate;
    @BindView(R.id.edtEmail)
    MaterialEditText edtEmail;
    @BindView(R.id.edtAddress)
    MaterialEditText edtAddress;
    @BindView(R.id.edtUpi)
    MaterialEditText edtUpi;
    @BindView(R.id.edtpanno)
    MaterialEditText edtPanno;
    @BindView(R.id.edtpassword)
    MaterialEditText edtPassword;
    @BindView(R.id.edtgstno)
    MaterialEditText edtgstno;

    @BindView(R.id.edtnameofshop)
    MaterialEditText edtnameofshop;

    private String validateOnSuccessResonspeCall;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Inject
    SharedPreferences sharedPreferences;

    private String TAG = Profile.class.getName();
    @Inject
    Retrofit retrofit;
    @Inject
    NetworkClient networkClient;
    @Inject
    ProfilePresenter profilePresenter;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        DaggerIProfileComponent.builder()
                .profilePresenterModule(new ProfilePresenterModule(this))
                .sharedPrefModule(new SharedPrefModule(this))
                .networkClient(new NetworkClient(CommonHelper.BASE_URL))

                .build()
                .inject(this);
        meditshowdetails.setVisibility(View.GONE);
        // menterOtp.setVisibility(View.GONE);


        userid = sharedPreferences.getString(CommonHelper.sharedpref_userid, null);
        if (!sharedPreferences.getString(sharedpref_mobileno, null).isEmpty()) {

            mtxt_Mobileno.setText(sharedPreferences.getString(sharedpref_mobileno, null));
            edtMobileno.setText(sharedPreferences.getString(sharedpref_mobileno, null));
        } else {
            mtxt_Mobileno.setVisibility(View.GONE);
            mlabel_mobileno.setVisibility(View.GONE);
            edtMobileno.setVisibility(View.GONE);
        }

        if (!sharedPreferences.getString(sharedpref_address, null).isEmpty()) {
            mtxt_Address.setText(sharedPreferences.getString(sharedpref_address, null));
            edtAddress.setText(sharedPreferences.getString(sharedpref_address, null));


        } else {
            mtxt_Address.setVisibility(View.GONE);
            mlabel_address.setVisibility(View.GONE);

            edtAddress.setVisibility(View.GONE);

        }
        if (!sharedPreferences.getString(sharedpref_email, null).isEmpty()) {
            mEmailid.setText(sharedPreferences.getString(sharedpref_email, null));
            mtxt_Email.setText(sharedPreferences.getString(sharedpref_email, null));
            edtEmail.setText(sharedPreferences.getString(sharedpref_email, null));

        } else {
            mtxt_Email.setVisibility(View.GONE);
            mlabel_emailid.setVisibility(View.GONE);
            mEmailid.setVisibility(View.GONE);
            edtEmail.setVisibility(View.GONE);


        }
        if (!sharedPreferences.getString(sharedpref_name_of_shop, null).isEmpty() && !sharedPreferences.getString(sharedpref_name_of_shop, null).equalsIgnoreCase("nameofshop")) {
            mtxt_Shopname.setText(sharedPreferences.getString(sharedpref_name_of_shop, null));
            edtnameofshop.setText(sharedPreferences.getString(sharedpref_name_of_shop, null));


        } else {
            mtxt_Shopname.setVisibility(View.GONE);
            edtnameofshop.setVisibility(View.GONE);
            edtnameofshop.setText("");
            mlabel_shopname.setVisibility(View.GONE);
        }


        if (!sharedPreferences.getString(sharedpref_upino, null).isEmpty()) {
            mtxt_Upiid.setText(sharedPreferences.getString(sharedpref_upino, null));
            edtUpi.setText(sharedPreferences.getString(sharedpref_upino, null));


        } else {
            mtxt_Upiid.setVisibility(View.GONE);
            mlabel_upiid.setVisibility(View.GONE);
            edtUpi.setVisibility(View.GONE);
        }


        if (!sharedPreferences.getString(sharedpref_panno, null).isEmpty() && !sharedPreferences.getString(sharedpref_panno, null).equalsIgnoreCase("panno")) {
            mtxt_Panno.setText(sharedPreferences.getString(sharedpref_panno, null));

            edtPanno.setText(sharedPreferences.getString(sharedpref_panno, null));


        } else {
            mlabel_panno.setVisibility(View.GONE);
            mtxt_Panno.setVisibility(View.GONE);
            edtPanno.setVisibility(View.GONE);

        }
        if (!sharedPreferences.getString(sharedpref_gstno, null).isEmpty() && !sharedPreferences.getString(sharedpref_gstno, null).equalsIgnoreCase("gstno")) {
            edtgstno.setText(sharedPreferences.getString(sharedpref_gstno, null));

            mtxt_Gstno.setText(sharedPreferences.getString(sharedpref_gstno, null));
        } else {
            mtxt_Gstno.setVisibility(View.GONE);
            getMlabel_gstno.setVisibility(View.GONE);
            edtgstno.setVisibility(View.GONE);

        }
        if (!sharedPreferences.getString(sharedpref_password, null).isEmpty())
            edtPassword.setText(sharedPreferences.getString(sharedpref_password, null));
        else {
            edtPassword.setVisibility(View.GONE);
        }
        if (!sharedPreferences.getString(sharedpref_name, null).isEmpty())
            mUsername.setText(sharedPreferences.getString(sharedpref_name, null));
        else {
            mUsername.setVisibility(View.GONE);

        }


        edtMobileno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (!TextUtils.isEmpty(edtMobileno.getText().toString()) && isValid(s.toString())) {


                    if (isNetworkAvailable(getApplicationContext())) {

                        profilePresenter.onCheckMobileNo(edtMobileno.getText().toString());

                    } else onError("Internet Not Available");


                    Log.e(TAG, "Tab=====valid");

                } else
                    Log.e(TAG, "Tab=====mobile not valid");


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (!TextUtils.isEmpty(edtPassword.getText().toString()) && edtPassword.getText().toString().length() == 8) {


                    if (isNetworkAvailable(getApplicationContext())) {

                        if (userid != null)
                            profilePresenter.onPasswordChange(userid, edtPassword.getText().toString());
                        else onError("User ID Not Found");


                    } else onError("Internet Not Available");


                    Log.e(TAG, "Tab=====valid");

                } else
                    Log.e(TAG, "Tab=====mobile not valid");


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {

                    if (s.toString().endsWith(".com")) {

                        if (isNetworkAvailable(getApplicationContext())) {

                            profilePresenter.onCheckEmailId(s.toString());
                        } else onError("Internet Not Available");


                        Log.e(TAG, "call email" + s.toString());
                    }

                } else Log.e(TAG, "call email");


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


//        menterOtp.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {

//
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        item_update = menu.findItem(R.id.update);

        item_edit = menu.findItem(R.id.edit);
        item_update.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:

                item_edit.setVisible(false);
                item_update.setVisible(true);
                meditshowdetails.setVisibility(View.VISIBLE);
                mshowdetails.setVisibility(View.GONE);
                return (true);
            case R.id.update:


                profilePresenter.updateProfile(userid, mUsername.getText().toString(), edtEmail.getText().toString(), edtMobileno.getText().toString(), edtUpi.getText().toString(), edtPanno.getText().toString(), edtgstno.getText().toString(), edtAddress.getText().toString(), "50,60", edtnameofshop.getText().toString());


                return (true);
            case android.R.id.home:


                onBackPressed();
                return true;

        }
        return (super.onOptionsItemSelected(item));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onError(String message) {
        if (message.equals("Already Login"))

            Toasty.info(getApplicationContext(), "Please Give Different Already Exist", Toast.LENGTH_SHORT, true).show();


        else

            Toasty.info(getApplicationContext(), message, Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void onSuccess(String message) {


        if (message.equalsIgnoreCase("UpdateSuccess")) {

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(sharedpref_mobileno, edtMobileno.getText().toString());
            editor.putString(sharedpref_email, edtEmail.getText().toString());
            editor.putString(sharedpref_password, edtPassword.getText().toString());
            editor.putString(sharedpref_address, edtAddress.getText().toString());
            editor.putString(sharedpref_name_of_shop, edtnameofshop.getText().toString());
            editor.putString(sharedpref_gstno, edtgstno.getText().toString());
            editor.putString(sharedpref_upino, edtUpi.getText().toString());
            editor.putString(sharedpref_panno, edtPanno.getText().toString());
            mtxt_Panno.setText(edtPanno.getText().toString());
            mtxt_Upiid.setText(edtUpi.getText().toString());
            mtxt_Shopname.setText(edtnameofshop.getText().toString());
            mtxt_Email.setText(edtEmail.getText().toString());
            mtxt_Address.setText(edtAddress.getText().toString());
            mtxt_Mobileno.setText(edtMobileno.getText().toString());
            mtxt_Gstno.setText(edtgstno.getText().toString());
            Toasty.success(getApplicationContext(), "Update Success", Toast.LENGTH_SHORT, true).show();

            editor.apply();
            meditshowdetails.setVisibility(View.GONE);
            mshowdetails.setVisibility(View.VISIBLE);
            item_edit.setVisible(true);
            item_update.setVisible(false);


        } else if (message.equalsIgnoreCase("PasswordChangedSuccess")) {

            item_edit.setVisible(true);
            item_update.setVisible(false);
            meditshowdetails.setVisibility(View.GONE);
            mshowdetails.setVisibility(View.VISIBLE);


        } else if (message.equalsIgnoreCase("SendOTP")) {

            Log.e(TAG, "SendOTPCall");
            profilePresenter.callSendOtp(edtMobileno.getText().toString());

            otpValidateDialog();

            //  menterOtp.setText("");
            //  menterOtp.setVisibility(View.VISIBLE);


        } else if (message.equalsIgnoreCase("OTPSuccess")) {
            validateOnSuccessResonspeCall = "OTPSuccess";
            //   userPresenter.otpValidate(menterOtp.getText().toString());

        } else if (message.equalsIgnoreCase("mobileotpmatched")) {

            alertDialogOtpValidate.dismiss();

            //  menterOtp.setVisibility(View.GONE);
            edtEmail.requestFocus();


        } else if (message.equalsIgnoreCase("SendOTPEmail")) {
            profilePresenter.callSendOtpEmail(edtEmail.getText().toString());
            // menterOtp.setText("");
            otpValidateDialog();
            // menterOtp.setVisibility(View.VISIBLE);


        } else if (message.equalsIgnoreCase("OTPSuccessEmail")) {
            validateOnSuccessResonspeCall = "OTPSuccessEmail";


        } else if (message.equalsIgnoreCase("emailotpmatched")) {
            alertDialogOtpValidate.dismiss();


            //  menterOtp.setVisibility(View.GONE);

            edtAddress.requestFocus();

        } else


            Toasty.success(getApplicationContext(), message, Toast.LENGTH_SHORT, true).show();


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
                        profilePresenter.otpValidateEmail(menterOtp.getText().toString());


                    } else if (validateOnSuccessResonspeCall.equalsIgnoreCase("OTPSuccess")) {
                        profilePresenter.otpValidate(menterOtp.getText().toString());


                    }
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        AlertDialog.Builder otpValidateDialog = new AlertDialog.Builder(Profile.this, R.style.MyAlertDialogTheme);

        otpValidateDialog.setTitle("OTP VERIFICATION");
        otpValidateDialog.setCancelable(false);

        otpValidateDialog.setView(otplayout);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialogOtpValidate.dismiss();
            }
        });
        alertDialogOtpValidate = otpValidateDialog.create();
        alertDialogOtpValidate.show();
    }

}
