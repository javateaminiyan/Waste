package com.example.admin.solidwaste.presenter.AdminPresenter.RegProductPresenter;

import android.util.Log;

import com.example.admin.solidwaste.Interface.BasePresenter;
import com.example.admin.solidwaste.Interface.GetProductContract;

import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.pojo.ProductRegistrationPojo.Product_delete_response;
import com.example.admin.solidwaste.pojo.ProductRegistrationPojo.Product_delete_responseResponse;
import com.example.admin.solidwaste.pojo.RegisteredProduct.GetProductResponse;
import com.example.admin.solidwaste.pojo.RegisteredProduct.GetProductResponseResponseResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class GetProduct extends BasePresenter<GetProductContract.view> implements GetProductContract.presenter {


    GetProductContract.view view;


    List<GetProductResponseResponseResponse> productList = new ArrayList<>();

    @Inject
    Retrofit retrofit;
    @Inject
    NetworkClient networkClient;


    @Inject
    public GetProduct(GetProductContract.view view) {
        super(view);
        this.view = view;
    }


    @Override
    public void getProduct(String userid) {
        getProductObservable(userid).subscribeWith(getAvailProduct());

    }

    @Override
    public void deleteProduct(String userid,int product_id) {
        deleteProducts(userid, product_id).subscribeWith(deleteAvailProduct());
    }


    public Observable<Product_delete_response> deleteProducts(String userId, int productId) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.build();
        return networkClient.getApiInterface(retrofit).deleteProducts(userId, String.valueOf(productId)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }

    public DisposableObserver<Product_delete_response> deleteAvailProduct() {
        return new DisposableObserver<Product_delete_response>() {
            @Override
            public void onNext(Product_delete_response response) {
                Log.e("product delete ", " " + response.toString());

                Product_delete_response  repos = response;
                Product_delete_responseResponse final_response  = repos.getResponse();
                final_response.getStatusMessage();
                view.deleteSuccess(final_response.getStatusMessage());
            }

            @Override
            public void onError(Throwable e) {
                Log.e("product delete ", " " + e.toString());
                view.deleteFailure(e.getMessage());
            }

            @Override
            public void onComplete() {

                Log.e("product delete ", "completed ");
            }
        };
    }


    public Observable<GetProductResponse> getProductObservable(String userid) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.build();

        return networkClient.getApiInterface(retrofit).getProduct(userid).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    public DisposableObserver<GetProductResponse> getAvailProduct() {
        return new DisposableObserver<GetProductResponse>() {
            @Override
            public void onNext(GetProductResponse post) {


                productList.clear();
                Log.e("presenter", "" + post.getResponse().getStatusMessage());



                if( !post.getResponse().getStatusMessage().equalsIgnoreCase("No data Found")) {

                    for (GetProductResponseResponseResponse rs : post.getResponse().getResponse()) {

                        Log.e("presenter", "" + rs.getProductdescription());
                        productList.add(rs);
                    }
                    view.loadData(productList);
                }


            }

            @Override
            public void onError(Throwable e) {
                Log.e("presenter", "err" + e.toString());
//                view.failure(e.toString());
            }

            @Override
            public void onComplete() {
                Log.e("presenter", "completed");

            }
        };
    }


}
