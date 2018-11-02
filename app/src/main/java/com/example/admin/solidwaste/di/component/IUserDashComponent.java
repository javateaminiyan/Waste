package com.example.admin.solidwaste.di.component;


import com.example.admin.solidwaste.activity.UserDashboard;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.di.module.UserDashboardModule;
import com.example.admin.solidwaste.sharedprefshelper.SharedPrefModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {UserDashboardModule.class,SharedPrefModule.class, NetworkClient.class})
public interface IUserDashComponent {

    void inject(UserDashboard userDashboard);
}