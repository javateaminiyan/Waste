package com.example.admin.solidwaste.di.component;


import com.example.admin.solidwaste.di.module.Frag_UserModule;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.fragment.TabFrag_UserRegisteration;
import com.example.admin.solidwaste.sharedprefshelper.SharedPrefModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {Frag_UserModule.class,SharedPrefModule.class,NetworkClient.class})
public interface Frag_UserApiComponent {

    void inject(TabFrag_UserRegisteration frag_userRegisteration);
}