package com.example.admin.solidwaste.sharedprefshelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_address;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_city;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_email;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_firebaseid;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_gstno;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_latlng;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_mobileno;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_name;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_name_of_shop;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_panno;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_password;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_typeofuser;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_upino;

@Module
public class SharedPrefModule {
    private Context context;


    @Inject
    public SharedPrefModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    Context provideContext() {
        return context;
    }

    @Singleton
    @Provides
    public SharedPreferences provideSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

//    @Singleton
//    @Provides
//    public void registerPersonalInfo(String name, String mobileno, String email, String password, String city, String address, String typeofuser, String firebaseid, String latlng, String nameofshop, String gstno, String upiid, String panno) {
//
//        SharedPreferences.Editor editor = provideSharedPreferences(context).edit();
//
//
//        editor.putString(sharedpref_name, name);
//        editor.putString(sharedpref_mobileno, mobileno);
//
//
//        editor.putString(sharedpref_email, email);
//        editor.putString(sharedpref_password, password);
//
//        editor.putString(sharedpref_city, city);
//        editor.putString(sharedpref_address, address);
//
//        //new Firebase Id
//        editor.putString(sharedpref_typeofuser, typeofuser);
//        editor.putString(sharedpref_firebaseid, firebaseid);
//        editor.putString(sharedpref_latlng, latlng);
//        editor.putString(sharedpref_name_of_shop, nameofshop);
//        editor.putString(sharedpref_gstno, gstno);
//        editor.putString(sharedpref_upino, upiid);
//        editor.putString(sharedpref_panno, panno);
//        editor.apply();
//
//
//    }


}