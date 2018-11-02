package com.example.admin.solidwaste.di.component;

import com.example.admin.solidwaste.di.module.RegisterationPresenterModule;
import com.example.admin.solidwaste.activity.Registration;
import com.example.admin.solidwaste.sharedprefshelper.SharedPrefModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {RegisterationPresenterModule.class,SharedPrefModule.class})

public interface UserRegistrationComponent {

    void inject(Registration registration);
}