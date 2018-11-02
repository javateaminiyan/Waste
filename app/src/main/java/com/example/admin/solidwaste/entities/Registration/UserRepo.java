package com.example.admin.solidwaste.models.Login.Registration;

import android.text.TextUtils;
import android.util.Patterns;

import com.example.admin.solidwaste.Interface.IFragUserRegisterContract;

import static com.example.admin.solidwaste.utils.CommonHelper.isValid;

public class UserRepo implements IFragUserRegisterContract.user_repo {

    String name;
    String mobileno;
    String email;
    String address;
    String upiid;
    String password;
    String confirmpassword;

    @Override
    public String name() {
        return name;
    }


    @Override
    public String mobileno() {
        return mobileno;
    }

    @Override
    public String email() {
        return email;
    }

    @Override
    public String address() {
        return address;
    }

    @Override
    public String upiid() {
        return upiid;
    }


    @Override
    public String password() {
        return password;
    }

    @Override
    public String confirmpassword() {
        return confirmpassword;
    }


    public UserRepo(String name, String mobileno, String email, String address, String upiid, String password, String confirmpassword) {
        this.name = name;
        this.mobileno = mobileno;
        this.email = email;
        this.address = address;
        this.upiid = upiid;
        this.password = password;
        this.confirmpassword = confirmpassword;
    }

    @Override
    public int isvalidate() {
        //boolean


        if (TextUtils.isEmpty(name()))
            return 0;

        else if (TextUtils.isEmpty(mobileno())
                && !isValid(mobileno()))
            return 2;

        else if (TextUtils.isEmpty(email())
                && !Patterns.EMAIL_ADDRESS.matcher(email()).matches())
            return 1;


        else if (TextUtils.isEmpty(address()))
            return 4;


        else if (TextUtils.isEmpty(upiid())
                && upiid().length() <= 12)
            return 3;

        else if (password().length() <= 5)
            return 5;

        else if (confirmpassword().length() <= 5)
            return 6;


        else if (!password().equals(confirmpassword()))

            return 7;


        else
            return 8;
    }


}
