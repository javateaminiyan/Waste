package com.example.admin.solidwaste.di.component;

import com.example.admin.solidwaste.activity.SplashScreen;
import com.example.admin.solidwaste.di.module.LoginPresenterModule;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.di.module.SplashScreenModule;
import com.example.admin.solidwaste.fcmmessagesendcontroller.FirebaseMessagingMessageSendController;
import com.example.admin.solidwaste.sharedprefshelper.SharedPrefModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {SplashScreenModule.class, SharedPrefModule.class, NetworkClient.class, FirebaseMessagingMessageSendController.class})
public interface SplashScreenComponent {

    void inject(SplashScreen splashScreen);
}