package com.example.admin.solidwaste.di.module;

import com.example.admin.solidwaste.Interface.NotificationInterface;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Murugan on 12-10-2018.
 */

@Singleton
@Module
public class NotificationPresenterModule {
   private final NotificationInterface.view view;

    public NotificationPresenterModule(NotificationInterface.view view) {
        this.view = view;
    }


    @Singleton
    @Provides
    NotificationInterface.view provideNotificationProvide(){
        return view;
    }
}
