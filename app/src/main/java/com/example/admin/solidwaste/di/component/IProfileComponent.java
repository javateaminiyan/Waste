package com.example.admin.solidwaste.di.component;


import com.example.admin.solidwaste.activity.Profile;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.di.module.ProfilePresenterModule;
import com.example.admin.solidwaste.sharedprefshelper.SharedPrefModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ProfilePresenterModule.class,SharedPrefModule.class, NetworkClient.class})
public interface IProfileComponent {

    void inject(Profile profile);
}
