package com.example.admin.solidwaste.di.module;

import com.example.admin.solidwaste.Interface.MyRequestContract;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Murugan on 11-10-2018.
 */


@Singleton
@Module
public class MyRequestPresenterModule {


    private final MyRequestContract.view view;


    public MyRequestPresenterModule(MyRequestContract.view view) {
        this.view = view;

    }
    @Singleton
    @Provides
    MyRequestContract.view  provideMyRequestView() {
        return view;
    }

}
