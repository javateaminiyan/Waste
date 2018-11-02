package com.example.admin.solidwaste.di.component;

import com.example.admin.solidwaste.di.module.Frag_MerchantModule;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.fragment.TabFrag_MerchantRegisteration;
import com.example.admin.solidwaste.sharedprefshelper.SharedPrefModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {Frag_MerchantModule.class,SharedPrefModule.class,NetworkClient.class})

public interface Frag_MerchantApiComponent {

    void inject(TabFrag_MerchantRegisteration frag_merchantRegisteration);
}