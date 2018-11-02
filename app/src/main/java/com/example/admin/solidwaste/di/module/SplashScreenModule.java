package com.example.admin.solidwaste.di.module;

import com.example.admin.solidwaste.Interface.LoginActivityContract;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
@Module
public class SplashScreenModule {


    private final LoginActivityContract.ILoginView view;


    public SplashScreenModule(LoginActivityContract.ILoginView view) {
        this.view = view;

    }
    @Singleton
    @Provides
    LoginActivityContract.ILoginView provideSayHelloContractView() {
        return view;
    }

}
