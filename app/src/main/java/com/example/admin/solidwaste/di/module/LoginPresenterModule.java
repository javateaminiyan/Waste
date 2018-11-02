package com.example.admin.solidwaste.di.module;

import com.example.admin.solidwaste.Interface.LoginActivityContract;
import com.example.admin.solidwaste.sharedprefshelper.SharedPrefModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class LoginPresenterModule {


    private final LoginActivityContract.ILoginView view;


    public LoginPresenterModule(LoginActivityContract.ILoginView view) {
        this.view = view;

    }
@Singleton
    @Provides
    LoginActivityContract.ILoginView provideSayHelloContractView() {
        return view;
    }

}
