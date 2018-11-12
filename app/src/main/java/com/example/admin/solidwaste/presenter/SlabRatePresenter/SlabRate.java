package com.example.admin.solidwaste.presenter.SlabRatePresenter;

import android.util.Log;

import com.example.admin.solidwaste.Interface.BasePresenter;
import com.example.admin.solidwaste.Interface.SlabRateContract;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.pojo.SlabRatePojo.Products;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class SlabRate extends BasePresenter<SlabRateContract.view> implements SlabRateContract.presenter {

    SlabRateContract.view view;
    CompositeDisposable mCompositeDisposable=new CompositeDisposable();


    @Inject
    Retrofit retrofit;
    @Inject
    NetworkClient networkClient;



    @Inject
    public SlabRate(SlabRateContract.view view) {
        super(view);
        this.view = view;
    }




    @Override
    public void getSlabRate(String userid) {
        subscribefunction(userid);
    }


    public void subscribefunction(String userid){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.build();
        mCompositeDisposable.add( networkClient.getApiInterface(retrofit).getSlabDetsils(userid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }
    private void handleResponse(Products androidList) {
        if(androidList.getStatusCode() == 200)
        view.ViewSlabRate(androidList);
        else
        view.showResult(androidList.getStatusMessage());

    }
    private void handleError(Throwable error) {
        Log.e("error=>",error+"");
    }

}
