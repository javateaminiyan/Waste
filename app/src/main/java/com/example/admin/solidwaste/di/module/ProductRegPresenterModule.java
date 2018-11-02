package com.example.admin.solidwaste.di.module;

import com.example.admin.solidwaste.Interface.NewProductContract;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Murugan on 10-10-2018.
 */

@Singleton
@Module
public class ProductRegPresenterModule {
    private final NewProductContract.view view;


    public ProductRegPresenterModule(NewProductContract.view pro_reg_view) {
        this.view = pro_reg_view;
    }

    @Singleton
    @Provides
    NewProductContract.view  provideProductView(){
        return view;
    }
}
