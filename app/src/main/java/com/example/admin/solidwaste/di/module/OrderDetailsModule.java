package com.example.admin.solidwaste.di.module;

import com.example.admin.solidwaste.Interface.OrderUpdateContract;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Murugan on 11-10-2018.
 */


@Singleton
@Module
public class OrderDetailsModule {

    private final OrderUpdateContract.view view;

    public OrderDetailsModule(OrderUpdateContract.view view) {
        this.view = view;
    }

    @Singleton
    @Provides
    OrderUpdateContract.view provideOrderDetails(){
        return view;
    }
}
