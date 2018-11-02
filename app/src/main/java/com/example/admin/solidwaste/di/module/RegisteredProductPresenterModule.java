package com.example.admin.solidwaste.di.module;

import com.example.admin.solidwaste.Interface.GetProductContract;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Murugan on 10-10-2018.
 */

@Singleton
@Module
public class RegisteredProductPresenterModule {
    private final GetProductContract.view view;


    public RegisteredProductPresenterModule(GetProductContract.view view) {
        this.view = view;
    }

    @Singleton
    @Provides
    GetProductContract.view ProviceGetProductView(){
        return view;
    }


}
