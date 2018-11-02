package com.example.admin.solidwaste.di.module;


import com.example.admin.solidwaste.Interface.IFragUserRegisterContract;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
@Singleton
@Module
public class Frag_UserModule {


    private final IFragUserRegisterContract.view views;


    public Frag_UserModule(IFragUserRegisterContract.view view) {
        this.views = view;
    }
@Singleton
    @Provides
    public IFragUserRegisterContract.view provideSayHelloContractView() {
        return views;
    }

}


