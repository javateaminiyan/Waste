package com.example.admin.solidwaste.presenter.AdminPresenter.OrderUpdatePresenter;

import android.util.Log;

import com.example.admin.solidwaste.Interface.BasePresenter;
import com.example.admin.solidwaste.Interface.OrderUpdateContract;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.pojo.ProductOrderRequestPojo.OrderResponse;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by Murugan on 04-10-2018.
 */

public class OrderUpdate extends BasePresenter<OrderUpdateContract.view> implements OrderUpdateContract.presenter {


    OrderUpdateContract.view view;

    @Inject
    Retrofit retrofit;
    @Inject
    NetworkClient networkClient;

    @Inject
    public OrderUpdate(OrderUpdateContract.view view) {
        super(view);
        this.view = view;
    }

    @Override
    public void updateOrder(String orderStatus, String orderid, String deliverDate) {
        getMyRequest(orderStatus, orderid, deliverDate, deliverDate).subscribeWith(getRequestValues());

    }


    public Observable<OrderResponse> getMyRequest(String orderstatus, String orderid, String pickupdate, String deliverydate) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.build();

        Log.e("errrr","e"+orderid );
        Log.e("errrr","e"+orderstatus );
        Log.e("errrr","e"+pickupdate );

        return networkClient.getApiInterface(retrofit).updateOrder(orderstatus, orderid, pickupdate, deliverydate).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    public DisposableObserver<OrderResponse> getRequestValues() {
        return new DisposableObserver<OrderResponse>() {
            @Override
            public void onNext(OrderResponse post) {

                String response = post.getMessage();

                view.successMsg(response);


            }

            @Override
            public void onError(Throwable e) {
                Log.e("presenter", "err" + e.toString());
//                view.failure(e.toString());
                view.failureMsg(e.toString());

            }

            @Override
            public void onComplete() {
                Log.e("presenter", "completed");

            }
        };
    }

}
