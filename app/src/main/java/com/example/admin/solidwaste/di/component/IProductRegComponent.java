package com.example.admin.solidwaste.di.component;

import com.example.admin.solidwaste.activity.NewProductActivity;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.di.module.ProductRegPresenterModule;
import com.example.admin.solidwaste.sharedprefshelper.SharedPrefModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Murugan on 10-10-2018.
 */

@Singleton
@Component(modules = {ProductRegPresenterModule.class,NetworkClient.class, SharedPrefModule.class})
 public interface IProductRegComponent {
    void inject(NewProductActivity newProduct);
}
