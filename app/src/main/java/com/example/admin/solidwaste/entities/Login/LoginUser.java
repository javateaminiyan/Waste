package com.example.admin.solidwaste.entities;

import android.text.TextUtils;
import android.util.Patterns;

import com.example.admin.solidwaste.Interface.LoginActivityContract;

import static com.example.admin.solidwaste.utils.CommonHelper.isValid;

public class LoginUser implements LoginActivityContract.ILoginUser {


    private String email, password;


    public LoginUser(String email, String password) {
        this.email = email;
        this.password = password;
    }


    @Override
    public String EmailorMobileno() {
        return email;
    }

    @Override
    public String Password() {
        return password;
    }

    @Override
    public int isvalidate() {
        //boolean


        if (TextUtils.isEmpty(EmailorMobileno()))
            return 0;

        else if (TextUtils.isEmpty(Password()))
            return 3;
        else if (Password().length() <= 5)
            return 4;
        else if (!Patterns.EMAIL_ADDRESS.matcher(EmailorMobileno()).matches() && !isValid(EmailorMobileno()))
            return 1;


        else
            return 5;
    }
}
