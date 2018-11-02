package com.example.admin.solidwaste.utils;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

import com.example.admin.solidwaste.RoomDB.NotificationDatabase;
import com.example.admin.solidwaste.pojo.MapPojo.Result;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.example.admin.solidwaste.di.module.NetworkClient.getHttpLoggingInterceptor;
import static com.example.admin.solidwaste.di.module.NetworkClient.getOkHttpCleint;

public class CommonHelper {

    public static String BASE_URL = "http://paypre.info/";
    public static String GOOGLE_API_URL = "https://maps.googleapis.com/";

    public static Result currentResult ;
    public static String  ROOM_DBNAME="notificationdb";


    public static boolean isValid(String s) {

        Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }


    public static boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static String firebaseid = "common_firebase";
    public static final String sharedpref_userid = "sharedpref_userid";
    public static final String sharedpref_name = "sharedpref_name";
    public static final String sharedpref_mobileno = "sharedpref_mobileno";
    public static final String sharedpref_email = "sharedpref_email";
    public static final String sharedpref_password = "sharedpref_password";
    public static final String sharedpref_city = "sharedpref_city";
    public static final String sharedpref_address = "sharedpref_address";
    public static final String sharedpref_typeofuser = "sharedpref_typeofuserr";
    public static final String sharedpref_firebaseid = "sharedpref_firebaseid";
    public static final String sharedpref_latlng = "sharedpref_latlng";
    public static final String sharedpref_name_of_shop = "sharedpref_name_of_shop";
    public static final String sharedpref_gstno = "sharedpref_gstno";
    public static final String sharedpref_upino = "sharedpref_upino";
    public static final String sharedpref_panno = "sharedpref_panno";
    public static final String sharedpref_status = "sharedpref_status";
    public static final String sharedpref_datetime = "sharedpref_datetime";

    public static  Migration MIGRATION_1_2 =null;




    public static Retrofit getRetrofitInstance() {
        OkHttpClient okHttpClient = getOkHttpCleint(getHttpLoggingInterceptor());

        return new Retrofit.Builder()
                .baseUrl(GOOGLE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)

                .build();
    }



    public static Retrofit getRetrofitScalarInstance() {
        OkHttpClient okHttpClient = getOkHttpCleint(getHttpLoggingInterceptor());

        return new Retrofit.Builder()
                .baseUrl(GOOGLE_API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(okHttpClient)

                .build();
    }

    /**
     * Get API Service
     *
     * @return API Service
     */
    public static com.example.admin.solidwaste.utils.NetworkInterface getApiService() {
        return getRetrofitInstance().create(com.example.admin.solidwaste.utils.NetworkInterface.class);
    }
    public static com.example.admin.solidwaste.utils.NetworkInterface getApiServiceScalar() {
        return getRetrofitScalarInstance().create(com.example.admin.solidwaste.utils.NetworkInterface.class);
    }

}
