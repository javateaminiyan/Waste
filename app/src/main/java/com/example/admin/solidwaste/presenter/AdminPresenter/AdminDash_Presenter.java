package com.example.admin.solidwaste.presenter.AdminPresenter;

import android.util.Log;

import com.example.admin.solidwaste.Interface.BasePresenter;
import com.example.admin.solidwaste.Interface.IAdminDashboardContract;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.pojo.AdminPojo.AdminDashboardPojo;
import com.example.admin.solidwaste.pojo.ProductOrderRequestPojo.MyRequest;
import com.example.admin.solidwaste.pojo.ProductOrderRequestPojo.MyRequestResponseResponse;


import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class AdminDash_Presenter extends BasePresenter<IAdminDashboardContract.view> implements IAdminDashboardContract.presenter {

    private  IAdminDashboardContract.view view;

    @Inject
    Retrofit retrofit;

    @Inject
    NetworkClient networkClient;

    @Inject
    public AdminDash_Presenter(IAdminDashboardContract.view view) {
        super(view);
        this.view = view;
    }

    @Override
    public void loadStatusCount(String merchantId) {
        getStatusCount(merchantId).subscribeWith(getDashboardValues());
    }

    private Observable<AdminDashboardPojo> getStatusCount(String merchantId) {
        return networkClient.getApiInterface(retrofit).statusCountForAdminDashboard(merchantId).subscribeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread());
    }
    public DisposableObserver<AdminDashboardPojo> getDashboardValues() {

        view.showDialog();
        return new DisposableObserver<AdminDashboardPojo>() {
            @Override
            public void onNext(AdminDashboardPojo post) {

                if(post.getResponse().getStatusCode() == 200){
                    Log.e("values=>",post.getResponse().getResponse()[0]+"");
                    view.displayDashboardCoubt(post.getResponse().getResponse());
                }
                view.dismissDialog();
            }

            @Override
            public void onError(Throwable e) {
                Log.e("presenter", "err" + e.toString());
//                view.failure(e.toString());
                view.dismissDialog();
            }

            @Override
            public void onComplete() {
                Log.e("presenter", "completed");
                view.dismissDialog();

            }
        };
    }
}
