package com.example.admin.solidwaste.di.component;


import com.example.admin.solidwaste.fcmmessagesendcontroller.FirebaseMessagingMessageSendController;
import com.example.admin.solidwaste.activity.Login;
import com.example.admin.solidwaste.di.module.LoginPresenterModule;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.sharedprefshelper.SharedPrefModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {LoginPresenterModule.class,SharedPrefModule.class, NetworkClient.class,FirebaseMessagingMessageSendController.class})
public interface LoginUserComponent {

    void inject(Login login);
}