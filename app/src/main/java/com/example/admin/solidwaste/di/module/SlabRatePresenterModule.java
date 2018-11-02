package com.example.admin.solidwaste.di.module;

import com.example.admin.solidwaste.Interface.SlabRateContract;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Murugan on 10-10-2018.
 */

@Singleton
@Module
public class SlabRatePresenterModule {

    private final SlabRateContract.view view;

    public SlabRatePresenterModule(SlabRateContract.view view) {
        this.view = view;
    }

    @Singleton
    @Provides
    SlabRateContract.view provideSlabRate(){
        return view;
    }
}
