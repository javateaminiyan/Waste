package com.example.admin.solidwaste.di.component;

import com.example.admin.solidwaste.activity.ViewProductActivity;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.di.module.RegisteredProductPresenterModule;
import com.example.admin.solidwaste.sharedprefshelper.SharedPrefModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Murugan on 10-10-2018.
 */

@Singleton
@Component(modules = {RegisteredProductPresenterModule.class, NetworkClient.class, SharedPrefModule.class})
public interface IRegisteredProductComponent {
   void inject(ViewProductActivity viewProductActivity);

}
