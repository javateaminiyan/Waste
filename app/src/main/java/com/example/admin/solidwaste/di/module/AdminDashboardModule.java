package com.example.admin.solidwaste.di.module;


import com.example.admin.solidwaste.Interface.IAdminDashboardContract;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AdminDashboardModule {


    private final IAdminDashboardContract.view view;


    public AdminDashboardModule(IAdminDashboardContract.view view) {
        this.view = view;

    }

    @Singleton
    @Provides
    IAdminDashboardContract.view provideSayHelloContractView() {
        return view;
    }

}
