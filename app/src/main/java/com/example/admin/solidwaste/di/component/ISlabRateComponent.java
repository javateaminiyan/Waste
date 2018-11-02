package com.example.admin.solidwaste.di.component;

import com.example.admin.solidwaste.activity.SlabRateActivity;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.di.module.SlabRatePresenterModule;
import com.example.admin.solidwaste.sharedprefshelper.SharedPrefModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Murugan on 10-10-2018.
 */

@Singleton
@Component(modules = {SlabRatePresenterModule.class, NetworkClient.class, SharedPrefModule.class})
public interface ISlabRateComponent {

    void inject(SlabRateActivity slabRateActivity);

}
