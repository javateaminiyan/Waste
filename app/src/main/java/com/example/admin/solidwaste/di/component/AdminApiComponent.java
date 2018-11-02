package com.example.admin.solidwaste.di.component;


import com.example.admin.solidwaste.activity.AdminDashboard;
import com.example.admin.solidwaste.di.module.AdminDashboardModule;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.sharedprefshelper.SharedPrefModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AdminDashboardModule.class,SharedPrefModule.class,NetworkClient.class})

public interface AdminApiComponent {

    void inject(AdminDashboard adminDashboard);
}