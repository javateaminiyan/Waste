package com.example.admin.solidwaste.presenter.AdminPresenter.ProductRegPresenter;

import android.util.Log;

import com.example.admin.solidwaste.Interface.BasePresenter;
import com.example.admin.solidwaste.Interface.NewProductContract;

import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.pojo.ProductRegistrationPojo.Add_Product_Response;
import com.example.admin.solidwaste.pojo.ProductRegistrationPojo.Product;
import com.example.admin.solidwaste.pojo.ProductRegistrationPojo.ProductPriceSuggession;
import com.example.admin.solidwaste.pojo.ProductRegistrationPojo.ProductPriceSuggessionResponseResponse;
import com.example.admin.solidwaste.pojo.ProductRegistrationPojo.Product_update_response;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

public class NewProduct extends BasePresenter<NewProductContract.view> implements NewProductContract.presenter {

    NewProductContract.view view;

    ArrayList<String> serviceList = new ArrayList<>();
    ArrayList<String> typeList = new ArrayList<>();
    ArrayList<String> gradeList = new ArrayList<>();
    ArrayList<String> unitList = new ArrayList<>();

     @Inject
    Retrofit retrofit;
      @Inject
    NetworkClient networkClient;

    @Inject
    public NewProduct(NewProductContract.view view) {
        super(view);
        this.view = view;
    }




    @Override
    public void onSubmit(File file, String userid, String name, String type, String color, String grade, String approximateval, String unit, String suggession, String addimage, String description) {
        Product p = new Product(name, type, color, grade, approximateval, unit, suggession, addimage, description);
        int val = p.validateFields();


        switch (val) {
            case 1:
                view.failure("Choose  Product Name");
                break;

            case 2:
                view.failure("Choose Type");
                break;
            case 3:
                view.failure("Enter Color");
                break;

            case 4:
                view.failure("Choose Grade");
                break;
            case 5:
                view.failure("Enter Approximate order value");
                break;
            case 6:
                view.failure("Choose Unit");
                break;
            case 0:
//                view.success("validated success");
                addobservable(file, name, approximateval, userid, description, unit, type, color, grade).subscribeWith(addProduct());

                break;
            default:
//                view.success("done");
//                addobservable(name,approximateval,userid,description,unit,type,color,grade).subscribeWith(addProduct());

        }

    }

    @Override
    public ArrayList<String> ServiceNameList() {
        serviceList.add("NDLKC Waste paper");
        serviceList.add("Old Kraft paper");
        serviceList.add("Old newspaper");
        serviceList.add("Corrugated Box Scrap");
        serviceList.add("Industrial Scrap");
        serviceList.add("Electronic Scrap");
        serviceList.add("Others");
        return serviceList;
    }

    @Override
    public ArrayList<String> TypeList() {

        typeList.add("LCC");
        typeList.add("OCC");
        typeList.add("Silicone");
        typeList.add("Corrugated");
        typeList.add("Duplex");
        typeList.add("Newspaper");
        typeList.add("Other");
        return typeList;
    }

    @Override
    public ArrayList<String> GradeList() {
        gradeList.add("Reuse");
        gradeList.add("Waste");
        gradeList.add("Other");
        return gradeList;
    }

    @Override
    public ArrayList<String> UnitList() {
        unitList.add("Kilogram");

        unitList.add("Gram");
        unitList.add("Sheet");
        unitList.add("Ton");
        unitList.add("Unit");
        unitList.add("Other");
        return unitList;
    }

    @Override
    public void updateProduct(File file, String productName, String productcost, String userid, String productunit, String productid, String productdescrption, String type, String color, String grade) {


        update_responseObservable(file, productName, productcost, userid, productunit, productid, productdescrption, type, color, grade).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(update_response());

    }


    public Observable<Product_update_response> update_responseObservable(File file, String productName, String productcost, String userid, String productunit, String productid, String productdescrption, String type, String color, String grade) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.build();
        MultipartBody.Part body = null;


        if (file != null) {
            RequestBody reqFile = RequestBody.create(MediaType.parse("*/*"), file);
            body = MultipartBody.Part.createFormData("imageupload", file.getName(), reqFile);
            return networkClient.getApiInterface(retrofit).updateProduct(body, productName, productcost, userid, productunit, productid, productdescrption, type, color, grade);

        } else {

            return networkClient.getApiInterface(retrofit).updateProductwithoutImage(productName, productcost, userid, productunit, productid, productdescrption, type, color, grade);


        }

    }


    public DisposableObserver<Product_update_response> update_response() {
        return new DisposableObserver<Product_update_response>() {
            @Override
            public void onNext(Product_update_response product_update_response) {

                Log.e("messsssss", product_update_response.getMessage());
//
//                if(product_update_response.getMessage().equalsIgnoreCase("Products Update Succesfully")){
//
//                    Log.e("messsssss",product_update_response.getMessage());
//                }
                view.success(product_update_response.getMessage());

            }

            @Override
            public void onError(Throwable e) {

                Log.e("messsssss", e.toString());

                view.failure(e.toString());

            }

            @Override
            public void onComplete() {

            }
        };
    }


    @Override
    public void priceSuggession(String price, String typee) {
        getProductPrice(price, typee).subscribeWith(getProductCost());

    }


    public Observable<ProductPriceSuggession> getProductPrice(String product_cost, String type) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.build();
        return networkClient.getApiInterface(retrofit).getProductPriceSuggestion(product_cost, type).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());


    }


    public Observable<Add_Product_Response> addobservable(File file, String productname, String product_cost, String userid, String product_description, String product_unit, String type, String color, String grade) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.build();

        MultipartBody.Part body = null;
        if (file != null) {
            RequestBody reqFile = RequestBody.create(MediaType.parse("*/*"), file);
            body = MultipartBody.Part.createFormData("imageupload", file.getName(), reqFile);
            return networkClient.getApiInterface(retrofit).addProduct(body, productname, product_cost, userid, product_description, product_unit, type, color, grade).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        } else {

            return networkClient.getApiInterface(retrofit).addProductWitoutImage(productname, product_cost, userid, product_description, product_unit, type, color, grade).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());


        }

    }


    public DisposableObserver<Add_Product_Response> addProduct() {
        return new DisposableObserver<Add_Product_Response>() {
            @Override
            public void onNext(Add_Product_Response post) {

                Log.e("presenter", "" + post.getMessage());
                view.success(post.getMessage());
            }

            @Override
            public void onError(Throwable e) {
                Log.e("presenter", "err" + e.toString());
                view.failure(e.toString());
            }

            @Override
            public void onComplete() {
                Log.e("presenter", "completed");

            }
        };
    }


    public DisposableObserver<ProductPriceSuggession> getProductCost() {

        view.ShowProgress();

        return new DisposableObserver<ProductPriceSuggession>() {
            @Override
            public void onNext(ProductPriceSuggession post) {


                Log.e("presenter", "" + post.getResponse().getStatusMessage());


                if (post.getResponse().getStatusMessage().equalsIgnoreCase("Success")) {
                    if (post.getResponse().getResponse().length > 0) {
                        for (ProductPriceSuggessionResponseResponse res : post.getResponse().getResponse()) {
                            String name = res.getName_of_shop();
                            int price = res.getProduct_cost();

                            Log.e("pricccc", "" + name);
                            Log.e("pricccc", "" + price);

                            view.pricesuggession(String.valueOf(price), name);

                        }
                    } else {
                        view.pricesuggession("", "");

                    }
                } else {
                    view.pricesuggession("", "");

                }
                view.HideProgress();

            }

            @Override
            public void onError(Throwable e) {
                Log.e("presenter", "err" + e.toString());
//                view.failure(e.toString());
                view.HideProgress();
            }

            @Override
            public void onComplete() {
                Log.e("presenter", "completed");
                view.HideProgress();
            }
        };
    }


}
