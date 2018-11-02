package com.example.admin.solidwaste.fcmmessagesendcontroller;

import android.util.Log;

import com.example.admin.solidwaste.Interface.IFirebaseMessagingCallbackResponse;
import com.example.admin.solidwaste.pojo.FirebaseResponsePojo.FirebaseMessagingResponse;
import com.example.admin.solidwaste.di.module.NetworkClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import dagger.Module;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

@Module
public class FirebaseMessagingMessageSendController {

    private IFirebaseMessagingCallbackResponse iFirebaseMessagingCallbackResponse;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private Date date = new Date();

    private Retrofit retrofit;

    private NetworkClient networkClient;

    @Inject
    public FirebaseMessagingMessageSendController(IFirebaseMessagingCallbackResponse iFirebaseMessagingCallbackResponse, NetworkClient networkClient, Retrofit retrofit) {

        this.iFirebaseMessagingCallbackResponse = iFirebaseMessagingCallbackResponse;
        this.networkClient = networkClient;
        this.retrofit = retrofit;
    }

    private CompositeDisposable disposables = new CompositeDisposable();

    public void stop() {
        disposables.clear();
    }


    private void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }


    public void SendMessageUserFirebase() throws JSONException {

        JSONObject main = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", "ddd");
        jsonObject.put("body", "ddd");
        jsonObject.put("image", "https://cdn3.iconfinder.com/data/icons/communication-mass-media-news/512/browser_address-512.png");
        jsonObject.put("message", "ddd");
        jsonObject.put("nameofuser", "ddd");
        jsonObject.put("firebaseid", "ddd");
        jsonObject.put("mobileno", "ddd");
        jsonObject.put("upiid", "ddd");
        jsonObject.put("productid", "ddd");
        jsonObject.put("productname", "ddd");
        jsonObject.put("productcost", "ddd");
        jsonObject.put("quantity", "ddd");
        jsonObject.put("merchantid", "ddd");
        jsonObject.put("datetime", dateFormat.format(date));
        jsonObject.put("userid", "ddd");
        jsonObject.put("merchantoruser", "user");

        main.put("data", jsonObject);
        main.put("priority", "high");

        main.put("to", "drwYGhlFPjg:APA91bEfPFYA3L5Eajv3NDnSZuZWmujoEnT0udJrg8zG7ESLRfMNXo6z3If_Z16bgALXlIf5BbhABzScr7FJzrR0NCVkRYh6LkQLS5NFG7ShsEYUXgTiq8cWsu57GCeHYQw4jG-G11Vk");

        final String requestBody = main.toString();
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(requestBody)).toString());


        addDisposable(networkClient.getApiInterface(retrofit).firebaseMessagingSendMessageUser(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<FirebaseMessagingResponse>() {
                    @Override
                    public void accept(FirebaseMessagingResponse firebaseMessagingResponse) throws Exception {


                        if (firebaseMessagingResponse.getSuccess() == 1)
                            iFirebaseMessagingCallbackResponse.onMessageSuccess("Notification Send Successfully");
                        else
                            iFirebaseMessagingCallbackResponse.onMessageError("Notification Firebase Id Not Registered");


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        iFirebaseMessagingCallbackResponse.onMessageError("Throwable" + throwable.getMessage());
                        Log.e("firebaseee", throwable.getMessage());
                    }
                }));


    }


//    public void SendMessageMerchantFirebase() throws JSONException {
//
//        JSONObject main = new JSONObject();
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("title", "Approval For user Request");
//        jsonObject.put("body", "Product Order Status From Merchant");
//        jsonObject.put("image", "https://cdn3.iconfinder.com/data/icons/shopping-auxiliary-icons-set-2/512/Truck_Free-512.png");
//        jsonObject.put("message", "Order Request From User");
//        jsonObject.put("nameofmerchant", "Iniyan");
//        jsonObject.put("merchant_firebaseid", "xxxx");
//        jsonObject.put("OrderStatus", "98945916520");
//        jsonObject.put("productid", "ddd");
//        jsonObject.put("productname", "ddd");
//        jsonObject.put("productcost", "ddd");
//        jsonObject.put("quantity", "120");
//        jsonObject.put("merchantid", "ddd");
//        jsonObject.put("datetime", dateFormat.format(date));
//        jsonObject.put("userid", "100");
//        jsonObject.put("merchantoruser", "merchant");
//
//        main.put("data", jsonObject);
//        main.put("priority", "high");
//
//        main.put("to", "drwYGhlFPjg:APA91bEfPFYA3L5Eajv3NDnSZuZWmujoEnT0udJrg8zG7ESLRfMNXo6z3If_Z16bgALXlIf5BbhABzScr7FJzrR0NCVkRYh6LkQLS5NFG7ShsEYUXgTiq8cWsu57GCeHYQw4jG-G11Vk");
//        final String requestBody = main.toString();
//        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(requestBody)).toString());
//
//
//        addDisposable(networkClient.getApiInterface(retrofit).firebaseMessagingSendMessageUser(body)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Consumer<FirebaseMessagingResponse>() {
//                    @Override
//                    public void accept(FirebaseMessagingResponse firebaseMessagingResponse) throws Exception {
//
//
//                        if (firebaseMessagingResponse.getSuccess() == 1)
//                            iFirebaseMessagingCallbackResponse.onMessageSuccess("Notification Send Successfully");
//                        else
//                            iFirebaseMessagingCallbackResponse.onMessageError("Notification Firebase Id Not Registered");
//
//
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        iFirebaseMessagingCallbackResponse.onMessageError("Throwable" + throwable.getMessage());
//                        Log.e("firebaseee", throwable.getMessage());
//                    }
//                }));
//
//
//    }

    public void SendMessageUserFirebase(String nameofuser, String orderstatus, String productname, String orderid,String quantity, String merchantid,
                                        String merchant_firebaseid, String user_firebaseid,
                                        String upiid, String productid, String productcost, String mobileno,
                                        String userid,String address,String orderapproval,String pickupdate,
                                        String ordercashtype,String price,String email,String unit,String img) throws JSONException {

        Log.e("user to merchant ===>",""+dateFormat.format(date));
        JSONObject main = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", "Product Order");
        jsonObject.put("body", "Product Order");
        jsonObject.put("image", img);
        jsonObject.put("message", "you received order from this customer " + nameofuser + " ,product name " + productname + " , Qantity " + quantity + "");
        jsonObject.put("nameofuser", nameofuser);
        jsonObject.put("Merchantfirebaseid", merchant_firebaseid);
        jsonObject.put("Userfirebaseid", user_firebaseid);
        jsonObject.put("mobileno", mobileno);
        jsonObject.put("upiid", upiid);
        jsonObject.put("productid", productid);
        jsonObject.put("productname", productname);
        jsonObject.put("productcost", productcost);
        jsonObject.put("quantity", quantity);
        jsonObject.put("merchantid", merchantid);
        jsonObject.put("datetime", dateFormat.format(date));
        jsonObject.put("userid", userid);
        jsonObject.put("merchantoruser", "user");
        jsonObject.put("orderid", orderid);
        jsonObject.put("address", address);
        jsonObject.put("OrderStatus", orderstatus);
        jsonObject.put("orderapproval", orderapproval);
        jsonObject.put("pickupdate", pickupdate);
        jsonObject.put("ordercashtype", ordercashtype);
        jsonObject.put("price", price);
        jsonObject.put("email", email);
        jsonObject.put("unit", unit);

        main.put("data", jsonObject);
        main.put("priority", "high");

        main.put("to", merchant_firebaseid);

        final String requestBody = main.toString();
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(requestBody)).toString());


        addDisposable(networkClient.getApiInterface(retrofit).firebaseMessagingSendMessageUser(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<FirebaseMessagingResponse>() {
                    @Override
                    public void accept(FirebaseMessagingResponse firebaseMessagingResponse) throws Exception {


                        if (firebaseMessagingResponse.getSuccess() == 1)
                            iFirebaseMessagingCallbackResponse.onMessageSuccess("Notification Send Successfully");
                        else
                            iFirebaseMessagingCallbackResponse.onMessageError("Notification Firebase Id Not Registered");


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        iFirebaseMessagingCallbackResponse.onMessageError("Throwable" + throwable.getMessage());
                        Log.e("firebaseee", throwable.getMessage());
                    }
                }));


    }

    public void SendMessageMerchantFirebase(String merchantname, String orderstatus, String orderid,
                                            String merchantuserid, String userfirebaseid, String merchantfirebaseid,
                                            String productname, String productid, String productcost, String userid,
                                            String quantity,String address,String orderapproval,String pickupdate,
                                            String ordercashtype,String price,String email,String unit) throws JSONException {
        Log.e("dfvbjhbch","hiiii"+orderid);
        Log.e("merchant to user===>",""+dateFormat.format(date));
        JSONObject main = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", orderstatus+" For user Request");
        jsonObject.put("body", "Product Order Status From Merchant");
        jsonObject.put("image", "https://cdn3.iconfinder.com/data/icons/shopping-auxiliary-icons-set-2/512/Truck_Free-512.png");
        jsonObject.put("message", "Your Order updated by " + merchantname + " , order status is " + orderstatus + " for this " + productname + " product ");
        jsonObject.put("nameofmerchant", merchantname);
        jsonObject.put("merchant_firebaseid", merchantfirebaseid);
        jsonObject.put("OrderStatus", orderstatus);
        jsonObject.put("productid", productid);
        jsonObject.put("productname", productname);
        jsonObject.put("productcost", productcost);
        jsonObject.put("quantity", quantity);
        jsonObject.put("merchantid", merchantuserid);
        jsonObject.put("datetime", dateFormat.format(date));
        jsonObject.put("userid", userid);
        jsonObject.put("merchantoruser", "merchant");
        jsonObject.put("orderid", orderid);
        jsonObject.put("address", address);
        jsonObject.put("orderapproval", orderapproval);
        jsonObject.put("pickupdate", pickupdate);
        jsonObject.put("ordercashtype", ordercashtype);
        jsonObject.put("price", price);
        jsonObject.put("email", email);
        jsonObject.put("unit", unit);

        main.put("data", jsonObject);
        main.put("priority", "high");

        main.put("to", userfirebaseid);
        final String requestBody = main.toString();
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(requestBody)).toString());


        addDisposable(networkClient.getApiInterface(retrofit).firebaseMessagingSendMessageUser(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<FirebaseMessagingResponse>() {
                    @Override
                    public void accept(FirebaseMessagingResponse firebaseMessagingResponse) throws Exception {


                        if (firebaseMessagingResponse.getSuccess() == 1)
                            iFirebaseMessagingCallbackResponse.onMessageSuccess("Notification Send Successfully");
                        else
                            iFirebaseMessagingCallbackResponse.onMessageError("Notification Firebase Id Not Registered");


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        iFirebaseMessagingCallbackResponse.onMessageError("Throwable" + throwable.getMessage());
                        Log.e("firebaseee", throwable.getMessage());
                    }
                }));


    }


}
