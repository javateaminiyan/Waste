package com.example.admin.solidwaste.presenter.ProductOrderRequest;

import android.util.Log;

import com.example.admin.solidwaste.Interface.BasePresenter;
import com.example.admin.solidwaste.Interface.MyRequestContract;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.pojo.ProductOrderRequestPojo.MyRequest;
import com.example.admin.solidwaste.pojo.ProductOrderRequestPojo.MyRequestResponseResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class MyRequestPresenter extends BasePresenter<MyRequestContract.view> implements MyRequestContract.presenter{

    MyRequestContract.view view;


    @Inject
    Retrofit retrofit;
    @Inject
    NetworkClient networkClient;

    @Inject
    MyRequestPresenter(MyRequestContract.view view){
        super(view);
        this.view = view;
    }

    @Override
    public void loadDataByOrderStatus(String userid, String utype, String reqType) {
        getMyRequest(userid,utype,reqType).subscribeWith(getRequestValues());
    }

    @Override
    public void loadMyData(String userid,String type) {
            getMyRequest(userid,type,null).subscribeWith(getRequestValues());

    }


    private Observable<MyRequest> getMyRequest(String userid, String type, String reqType) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.build();
        Log.e("reqtype",reqType+"");
        if(type.equalsIgnoreCase("user")){
            return networkClient.getApiInterface(retrofit).myOrders_user(userid).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        }else{
            if(reqType == null ){
                return networkClient.getApiInterface(retrofit).myOrders_merchant(userid).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
            else
            {
                return networkClient.getApiInterface(retrofit).orderstatus(reqType,userid).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

            }

        }




    }


    private DisposableObserver<MyRequest> getRequestValues() {

        view.showDialog();
        return new DisposableObserver<MyRequest>() {
            @Override
            public void onNext(MyRequest post) {


               // Log.e("ewsssssss",""+post.getResponse().getResponse());

//                if(post.getResponse().getResponse().length==0) {
//
//                    view.showError("No Data Found");
//
//                }else{
                if (post.getResponse().getStatusCode() ==200){
                    ArrayList<MyRequestResponseResponse> mList = new ArrayList<>();
                    if(post.getResponse().getResponse().length>0){
                        for (MyRequestResponseResponse mylist : post.getResponse().getResponse()) {

                            Log.e("dhf",mylist.getUserid()+" "+mylist.getproductimage());

                            mList.add(mylist);

                        }


                        view.loadData(mList);
                    }else{
                        view.showResult(post.getResponse().getStatusMessage());
                    }

                }else{
                    view.showResult(post.getResponse().getStatusMessage());
                }


//                }

                view.hideDialog();
            }

            @Override
            public void onError(Throwable e) {
                Log.e("presenter", "err" + e.toString());
//                view.failure(e.toString());
                view.hideDialog();
            }

            @Override
            public void onComplete() {
                Log.e("presenter", "completed");
                view.hideDialog();

            }
        };
    }
}
