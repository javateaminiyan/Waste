package com.example.admin.solidwaste.presenter.UserModules;

import android.util.Log;

import com.example.admin.solidwaste.Interface.BasePresenter;
import com.example.admin.solidwaste.Interface.IUserDashBoardContract;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.pojo.ProductRegistrationPojo.Add_Product_Response;
import com.example.admin.solidwaste.pojo.UserProductPojo.UserProduct;
import com.example.admin.solidwaste.pojo.UserProductPojo.UserProductResponseResponse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class UserDash_Presenter extends BasePresenter<IUserDashBoardContract.view> implements IUserDashBoardContract.presenter {

    IUserDashBoardContract.view view;
    List<UserProductResponseResponse> productList = new ArrayList<>();

    ArrayList<String> unitList = new ArrayList<>();



    @Inject
    Retrofit retrofit;
    @Inject
    NetworkClient networkClient;

   @Inject
   public UserDash_Presenter(IUserDashBoardContract.view view) {
        super(view);
        this.view = view;
    }
    @Override
    public void getUserProduct() {
        getProductObservable().subscribeWith(getAvailProduct());

    }

    @Override
    public void paceOrder(String productid,String orderid,String productname, String productcost,String nameofuser,String address,String price,String email,String mobile,String firebaseid
            ,String quantity,String unit,String orderstatus,String ordercashtype,String orderapproval,String pickupdate,String userid,String merchantId) {
        placeOrder(productid,orderid,productname,productcost,nameofuser,address,price,email,mobile,firebaseid,quantity,unit,orderstatus,ordercashtype,orderapproval,pickupdate,userid,merchantId).subscribeWith(placeOrderProduct());

    }

    public Observable<UserProduct> getProductObservable() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.build();

        return networkClient.getApiInterface(retrofit).getUserProduct().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    public DisposableObserver<UserProduct> getAvailProduct() {


        return new DisposableObserver<UserProduct>() {
            @Override
            public void onNext(UserProduct post) {

                productList.clear();
                Log.e("presenter", "" + post.getResponse().getStatusMessage());
                Log.e("presenter", "" + post.getResponse().getResponse());

                if(post.getResponse().getStatusCode()==200){
                    if(post.getResponse().getResponse().length>0) {

                        for (UserProductResponseResponse rs : post.getResponse().getResponse()) {

                            Log.e("presenter", "" + rs.getProductdescription());
                            productList.add(rs);
                        }
                        view.loadUserData(productList);
                    }
//                }else{
//
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



    public Observable<Add_Product_Response> placeOrder(String productid, String orderid, String productname, String productcost, String nameofuser, String address, String price, String email, String mobile, String firebaseid
            , String quantity, String unit, String orderstatus, String ordercashtype, String orderapproval, String pickupdate, String userid, String merchantId) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.build();

        Log.e("vbhj",firebaseid+" oii");

        return networkClient.getApiInterface(retrofit)
                .placeOrder(productid,productname,productcost,nameofuser,address,price,email,mobile,firebaseid,quantity,unit,orderstatus,ordercashtype,orderapproval,pickupdate,userid,merchantId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public DisposableObserver<Add_Product_Response> placeOrderProduct() {
        return new DisposableObserver<Add_Product_Response>() {
            @Override
            public void onNext(Add_Product_Response post) {

                Log.e("presenter", "" + post.getMessage());
                Log.e("presenter", "" + post.getStatusCode());

                view.showMessage( post.getMessage(),post);

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

    @Override
    public ArrayList<String> UnitList() {
        unitList.add("kilogram");

        unitList.add("gram");
        unitList.add("sheet");
        unitList.add("ton");
        unitList.add("unit");
        unitList.add("other");
        return unitList;
    }

    @Override
    public void filterProductByPriceRange(List<UserProductResponseResponse> userProductResponseResults, int min, int max) {
        List<UserProductResponseResponse> templist = new ArrayList<>();
        for(UserProductResponseResponse userProductResponseResponse:userProductResponseResults){
            Log.e("min=>"+min,"Max=>"+max);
            if(userProductResponseResponse.getProduct_cost()>=min &&userProductResponseResponse.getProduct_cost()<=max){
                templist.add(userProductResponseResponse);
                Log.e("price==>",userProductResponseResponse.getProduct_cost()+"");
            }
        }
        view.loadUserDataByPriceRange(templist);
    }

    @Override
    public List<String> getProductNamesWithoutDuplicate(List<UserProductResponseResponse> userProductResponseResults) {
        ArrayList<String> actualList = new ArrayList<>();
        for(int i=0;i<userProductResponseResults.size();i++){
            Log.e("ghxdghc",userProductResponseResults.get(i).getProductname());
            actualList.add(userProductResponseResults.get(i).getProductname());
        }
        ArrayList<String> tempProductList = new ArrayList<>();
        // add elements to al, including duplicates
        Set<String> hs = new HashSet<>();
        hs.addAll(actualList);
        tempProductList.addAll(hs);
        Log.e("size",tempProductList.size()+"");
        /*if(userProductResponseResults.size()>0){
            for(int i=0;i<userProductResponseResults.size();i++){
                if(tempProductList.size()==0)
                tempProductList.add(userProductResponseResults.get(i));

                if(tempProductList.size()>0){
                    for(int j=0;j<tempProductList.size();j++){
                        Log.e("tempsize>0"+tempProductList.get(j).getProductname(),userProductResponseResults.get(i).getProductname()+" y");
                        if(!tempProductList.get(j).getProductname().equals(userProductResponseResults.get(i).getProductname())){
                            tempProductList.add(userProductResponseResults.get(i));
                        }
                    }
                }else{

                }

            }
        }
*/

        return tempProductList;
    }

    @Override
    public List<String> getLocationNamesWithoutDuplicate(List<UserProductResponseResponse> userProductResponseResults) {
        ArrayList<String> actualList = new ArrayList<>();
        for(int i=0;i<userProductResponseResults.size();i++){
            Log.e("ghxdghc",userProductResponseResults.get(i).getCity());
            actualList.add(userProductResponseResults.get(i).getCity());
        }
        ArrayList<String> tempProductList = new ArrayList<>();
        // add elements to al, including duplicates
        Set<String> hs = new HashSet<>();
        hs.addAll(actualList);
        tempProductList.addAll(hs);
        Log.e("size",tempProductList.size()+"");
        /*if(userProductResponseResults.size()>0){
            for(int i=0;i<userProductResponseResults.size();i++){
                if(tempProductList.size()==0)
                tempProductList.add(userProductResponseResults.get(i));

                if(tempProductList.size()>0){
                    for(int j=0;j<tempProductList.size();j++){
                        Log.e("tempsize>0"+tempProductList.get(j).getProductname(),userProductResponseResults.get(i).getProductname()+" y");
                        if(!tempProductList.get(j).getProductname().equals(userProductResponseResults.get(i).getProductname())){
                            tempProductList.add(userProductResponseResults.get(i));
                        }
                    }
                }else{

                }

            }
        }
*/

        return tempProductList;
    }
    @Override
    public void filterProductByProductName(List<UserProductResponseResponse> userProductResponseResults, List<String> productNameList) {
        List<UserProductResponseResponse> templist = new ArrayList<>();
        for(int i=0;i<userProductResponseResults.size();i++){
            for(int j=0;j<productNameList.size();j++){
                if(productNameList.get(j).equalsIgnoreCase(userProductResponseResults.get(i).getProductname())){
                    templist.add(userProductResponseResults.get(i));
                }
            }
        }
        view.loadUserDataProduct(templist);
    }
    @Override
    public void filterProductByLocationName(List<UserProductResponseResponse> userProductResponseResults, List<String> productNameList) {
        List<UserProductResponseResponse> templist = new ArrayList<>();
        for(int i=0;i<userProductResponseResults.size();i++){
            for(int j=0;j<productNameList.size();j++){
                if(productNameList.get(j).equalsIgnoreCase(userProductResponseResults.get(i).getCity())){
                    templist.add(userProductResponseResults.get(i));
                }
            }
        }
        view.loadUserDataLocation(templist);
    }


    @Override
    public void filterByRange() {

    }

}
