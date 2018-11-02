package com.example.admin.solidwaste.di.module;


import com.example.admin.solidwaste.Interface.IUserDashBoardContract;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UserDashboardModule {


    private final IUserDashBoardContract.view view;


    public UserDashboardModule(IUserDashBoardContract.view view) {
        this.view = view;

    }

    @Singleton
    @Provides
    IUserDashBoardContract.view provideSayHelloContractView() {
        return view;
    }

}
