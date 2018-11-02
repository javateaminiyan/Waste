package com.example.admin.solidwaste.di.module;

import com.example.admin.solidwaste.Interface.RegisterActivityContract;

import dagger.Module;
import dagger.Provides;

@Module
public class RegisterationPresenterModule {

    private final RegisterActivityContract.RegisterView view;


    public RegisterationPresenterModule(RegisterActivityContract.RegisterView view) {
        this.view = view;

    }

    @Provides
    RegisterActivityContract.RegisterView provideSayHelloContractView() {
        return view;
    }

}