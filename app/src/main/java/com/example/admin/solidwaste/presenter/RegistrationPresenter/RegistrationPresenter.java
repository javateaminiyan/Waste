package com.example.admin.solidwaste.presenter.RegistrationPresenter;

import android.support.design.widget.TabLayout;

import com.example.admin.solidwaste.adapter.TabAdapter;
import com.example.admin.solidwaste.Interface.RegisterActivityContract;
import com.example.admin.solidwaste.activity.Registration;
import com.example.admin.solidwaste.Interface.BasePresenter;

import javax.inject.Inject;

public class RegistrationPresenter extends BasePresenter<RegisterActivityContract.RegisterView> implements RegisterActivityContract.RegisterPresenter {


    private Registration registration;
    private RegisterActivityContract.RegisterView view;




    @Inject
    public RegistrationPresenter(RegisterActivityContract.RegisterView view) {
        super(view);

        this.view = view;


    }


    @Override
    public void highLightCurrentTab(int position, TabLayout tabLayout, TabAdapter adapter) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            assert tab != null;
            tab.setCustomView(null);
            tab.setCustomView(adapter.getTabView(i));
        }
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        assert tab != null;
        tab.setCustomView(null);
        tab.setCustomView(adapter.getSelectedTabView(position));

    }
}
