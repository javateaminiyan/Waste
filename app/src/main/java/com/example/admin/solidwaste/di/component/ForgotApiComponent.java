package com.example.admin.solidwaste.di.component;


import com.example.admin.solidwaste.activity.ForgotPassword;

import com.example.admin.solidwaste.di.module.ForgotPresenterModule;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.sharedprefshelper.SharedPrefModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ForgotPresenterModule.class, NetworkClient.class, SharedPrefModule.class})
public interface ForgotApiComponent {

  void inject(ForgotPassword  forgotPassword);
}