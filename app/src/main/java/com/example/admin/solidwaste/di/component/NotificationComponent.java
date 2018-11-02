package com.example.admin.solidwaste.di.component;

import com.example.admin.solidwaste.activity.NotificationActivity;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.di.module.NotificationPresenterModule;
import com.example.admin.solidwaste.sharedprefshelper.SharedPrefModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Murugan on 12-10-2018.
 */

@Singleton
@Component(modules = {NotificationPresenterModule.class, NetworkClient.class,SharedPrefModule.class})
public interface NotificationComponent {
    void inject(NotificationActivity notificationActivity);
}

