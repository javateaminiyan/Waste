package com.example.admin.solidwaste.di.module;


import com.example.admin.solidwaste.utils.NetworkInterface;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkClient {

    private static String base_Url;


    public NetworkClient(String urlPath) {
        this.base_Url = urlPath;
    }


    @Inject
    public NetworkClient() {

    }


    @Provides
    @Singleton
    public  NetworkInterface getApiInterface(Retrofit retroFit) {
        return retroFit.create(NetworkInterface.class);
    }




    @Provides
    @Singleton
    public static NetworkInterface getApiInterfaceAll() {
        OkHttpClient okHttpClient = getOkHttpCleint(getHttpLoggingInterceptor());

        Retrofit retrofit=new  Retrofit.Builder()
              .baseUrl(base_Url)
              .addConverterFactory(GsonConverterFactory.create())
              .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

              .client(okHttpClient)
              .build();
        return retrofit.create(NetworkInterface.class);
    }



    @Provides
    @Singleton
    public static Retrofit getRetrofit() {
        OkHttpClient okHttpClient = getOkHttpCleint(getHttpLoggingInterceptor());

        return new Retrofit.Builder()
                .baseUrl(base_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

                .client(okHttpClient)
                .build();
    }


    @Provides
    public static OkHttpClient getOkHttpCleint(HttpLoggingInterceptor httpLoggingInterceptor) {

//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    @Provides
    public static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }


//
//    public static Retrofit getInstance() {
//
//
//        if (okHttpClient == null)
//          client= RetrofitClient();
//
//        if (retrofitInstance == null)
//
//            retrofitInstance = new Retrofit.Builder()
//                    .baseUrl("https://jsonplaceholder.typicode.com/")
//                    .client(client.build())
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                  .build();
//
//
//        return retrofitInstance;
//    }
//
//    private static OkHttpClient.Builder RetrofitClient() {
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
//                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
//                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
//                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);
//
//        httpClient.addInterceptor(logging);  // <-- this is the important line!
//        return httpClient;
//    }
}
