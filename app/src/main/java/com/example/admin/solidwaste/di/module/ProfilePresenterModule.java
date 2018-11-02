package com.example.admin.solidwaste.di.module;

import com.example.admin.solidwaste.Interface.LoginActivityContract;
import com.example.admin.solidwaste.Interface.ProfileActivityContract;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Singleton
@Module
public class ProfilePresenterModule {


    private final ProfileActivityContract.IProfileView view;


    public ProfilePresenterModule(ProfileActivityContract.IProfileView view) {
        this.view = view;

    }


    @Singleton
    @Provides
    ProfileActivityContract.IProfileView provideSayHelloContractView() {
        return view;
    }


}
