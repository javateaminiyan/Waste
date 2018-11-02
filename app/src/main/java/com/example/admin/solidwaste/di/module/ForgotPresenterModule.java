package com.example.admin.solidwaste.di.module;

import com.example.admin.solidwaste.Interface.ForgotContract;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
@Singleton
@Module
public class ForgotPresenterModule {


    private final ForgotContract.View views;

     public ForgotPresenterModule(ForgotContract.View view) {
        this.views = view;
    }
@Singleton
    @Provides
   public  ForgotContract.View provideSayHelloContractView() {
        return views;
    }

}




