package com.example.admin.solidwaste.presenter.AdminPresenter;

import com.example.admin.solidwaste.Interface.BasePresenter;
import com.example.admin.solidwaste.Interface.IAdminDashboardContract;

import javax.inject.Inject;

public class AdminDash_Presenter extends BasePresenter<IAdminDashboardContract.view> implements IAdminDashboardContract.presenter {

    private  IAdminDashboardContract.view view;


    @Inject
    public AdminDash_Presenter(IAdminDashboardContract.view view) {
        super(view);
        this.view = view;
    }
}
