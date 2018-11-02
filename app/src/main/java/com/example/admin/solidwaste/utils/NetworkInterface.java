package com.example.admin.solidwaste.utils;


import com.example.admin.solidwaste.pojo.FirebaseResponsePojo.FirebaseMessagingResponse;
import com.example.admin.solidwaste.pojo.GeneralResponse.GeneralResponse;
import com.example.admin.solidwaste.pojo.Login.Login_Response;
import com.example.admin.solidwaste.pojo.MapPlacedetailsPojo.PlaceDetailsMapPojo;
import com.example.admin.solidwaste.pojo.MapPojo.Places;
import com.example.admin.solidwaste.pojo.NearByLocationMerchant.NearBymerchantLatLng;
import com.example.admin.solidwaste.pojo.ProductOrderRequestPojo.MyRequest;
import com.example.admin.solidwaste.pojo.ProductOrderRequestPojo.OrderResponse;
import com.example.admin.solidwaste.pojo.ProductRegistrationPojo.Add_Product_Response;
import com.example.admin.solidwaste.pojo.ProductRegistrationPojo.ProductPriceSuggession;
import com.example.admin.solidwaste.pojo.ProductRegistrationPojo.Product_delete_response;
import com.example.admin.solidwaste.pojo.ProductRegistrationPojo.Product_update_response;
import com.example.admin.solidwaste.pojo.RegisteredProduct.GetProductResponse;
import com.example.admin.solidwaste.pojo.Response;
import com.example.admin.solidwaste.pojo.SendOTP;
import com.example.admin.solidwaste.pojo.SlabRatePojo.Products;
import com.example.admin.solidwaste.pojo.UserProductPojo.UserProduct;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface NetworkInterface {

    @Headers("access_token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1MzY5ODk4MzN9.ApRkN7zg-AQgflS28jOzwWF3VonyeTkTdnK1UtsdhvY")
    @GET("v1/solid_otp?")
    Observable<SendOTP> sendOtp_Email(@Query("email") String email);

    @Headers("access_token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1MzY5ODk4MzN9.ApRkN7zg-AQgflS28jOzwWF3VonyeTkTdnK1UtsdhvY")
    @GET("v1/solid_otp?")
    Observable<SendOTP> sendOtp_MobileNo(@Query("mobile") String mobileno);


    @Headers("access_token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1MzY5ODk4MzN9.ApRkN7zg-AQgflS28jOzwWF3VonyeTkTdnK1UtsdhvY")
    @POST("v1/forget_password?")
    Observable<Response> forgotPassword(@Query("userid") String userid, @Query("password") String password);


    @Headers("access_token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1MzY5ODk4MzN9.ApRkN7zg-AQgflS28jOzwWF3VonyeTkTdnK1UtsdhvY")
    @GET("v1/login?")
    Observable<Login_Response> LoginApi(@Query("mobile") String username);


    @Headers("access_token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1MzY5ODk4MzN9.ApRkN7zg-AQgflS28jOzwWF3VonyeTkTdnK1UtsdhvY")
    @POST("v1/solid_waste_firebase_update?")
    Observable<Response> firebaseUpdate(@Query("userid") String userid, @Query("firebaseid") String firebaseId);


    @Headers("access_token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1MzY5ODk4MzN9.ApRkN7zg-AQgflS28jOzwWF3VonyeTkTdnK1UtsdhvY")
    @POST("v1/solid_waste_register?")
    Observable<Response> register(@Query("name") String name, @Query("email") String emailid,
                                  @Query("password") String password, @Query("city") String city,
                                  @Query("address") String address, @Query("firebaseid") String firebaseid,
                                  @Query("latlng") String latlng, @Query("name_of_shop") String name_of_shop,
                                  @Query("gstno") String gstno, @Query("upino") String upino,
                                  @Query("panno") String panno, @Query("mobile") String mobile
            , @Query("typeofuser") String typeofUser);


    @Headers("access_token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1MzY5ODk4MzN9.ApRkN7zg-AQgflS28jOzwWF3VonyeTkTdnK1UtsdhvY")
    @GET("v1/login?")
    Observable<GeneralResponse> checkUserorNot(@Query("mobile") String mobileno);


//
//    @Headers("access_token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1MzY5ODk4MzN9.ApRkN7zg-AQgflS28jOzwWF3VonyeTkTdnK1UtsdhvY")
//    @GET("v1/login?")
//    Call<CheckMobileNoResponse> checkUserorNotCheckMobileNoResponse(@Query("mobile") String mobileno);
//


    @Headers("access_token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1MzY5ODk4MzN9.ApRkN7zg-AQgflS28jOzwWF3VonyeTkTdnK1UtsdhvY")
    @GET("v1/solid_otpkey?")
    Observable<Response> registerSendOtp_Email(@Query("email") String email);

    @Headers("access_token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1MzY5ODk4MzN9.ApRkN7zg-AQgflS28jOzwWF3VonyeTkTdnK1UtsdhvY")
    @GET("v1/solid_otpkey?")
    Observable<Response> registerSendOtp_Mobile(@Query("mobile") String mobileno);


    @Headers("access_token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1MzY5ODk4MzN9.ApRkN7zg-AQgflS28jOzwWF3VonyeTkTdnK1UtsdhvY")
    @POST("v1/forget_password?")
    Observable<Response> changePassword(@Query("userid") String userid, @Query("password") String password);


    @Headers("access_token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1MzY5ODk4MzN9.ApRkN7zg-AQgflS28jOzwWF3VonyeTkTdnK1UtsdhvY")
    @POST("v1/solid_update_profile?")
    Observable<Response> updateProfile(@Query("userid") String userid, @Query("name")
            String name, @Query("email") String emailid, @Query("mobile") String mobile, @Query("upino") String upino
            , @Query("panno") String panno, @Query("gstno") String gstno, @Query("address") String address, @Query("latlng") String latlng, @Query("name_of_shop") String name_of_shop);


    @Headers({
            "Content-Type:application/json",
            "project_id:569624951379",
            "Authorization:key=AAAAhKBK-lM:APA91bFtJHUKsyE9D6sHEiq5BDCv4EvyJsTVNP9lHlkHU4iC5I7trwWRY9-ccABhNd4LB8w8ea091TLkK1TP4-MRXVY88c140KjmDwdM_JB7OlgEin3aYmRIHaXF9PcopGTdSaXMlNn9",
            "Accept:application/json"
    })
    @POST("https://fcm.googleapis.com/fcm/send")
    Observable<FirebaseMessagingResponse> firebaseMessagingSendMessageUser(@Body RequestBody body);

    @GET
    Call<Places> getNearByPlaces(@Url String url);

    @GET
    Call<PlaceDetailsMapPojo> getDetailedPlace(@Url String url);


    @GET("maps/api/directions/json")
    Call<String> getDirections(@Query("origin") String origin, @Query("destination") String destination);

    @Headers("access_token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1MzY5ODk4MzN9.ApRkN7zg-AQgflS28jOzwWF3VonyeTkTdnK1UtsdhvY")
    @POST("v1/solid_waste_address_update?")
    Observable<Response> getLocationUpdate(@Query("userid") String userid, @Query("address") String address, @Query("latlng") String latlng);


    @Headers("access_token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1MzY5ODk4MzN9.ApRkN7zg-AQgflS28jOzwWF3VonyeTkTdnK1UtsdhvY")
    @GET("v1/TypeofUser_Merchant?")
    Observable<NearBymerchantLatLng> getAllMerchantLocation();


    //integrated
    @POST("v1/Add_products")
    @Headers("access_token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1MzY5OTMyNjN9.Lpot7r_O2WjKfTGwUlm7PRwqgal3DW0ctsCgoBhzy9A")
    Observable<Add_Product_Response> addProductWitoutImage(@Query("productname") String productName, @Query("product_cost") String product_cost, @Query("userid") String userid, @Query("productdescription") String product_description, @Query("productunit") String productunit, @Query("type") String type, @Query("color") String color, @Query("grade") String grade);


    @POST("v1/solid_product_update")
    @Headers("access_token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1MzY5OTMyNjN9.Lpot7r_O2WjKfTGwUlm7PRwqgal3DW0ctsCgoBhzy9A")
    Observable<Product_update_response> updateProductwithoutImage(@Query("productname") String productName,
                                                                  @Query("product_cost") String product_cost,
                                                                  @Query("userid") String userid,
                                                                  @Query("productunit")String productunit,
                                                                  @Query("product_id") String productid,
                                                                  @Query("productdescription") String productDescription,
                                                                  @Query("type") String type,
                                                                  @Query("color") String color,
                                                                  @Query("grade") String grade
    );



    //real api
    @Multipart
    @POST("v1/Add_products")
    @Headers("access_token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1MzY5OTMyNjN9.Lpot7r_O2WjKfTGwUlm7PRwqgal3DW0ctsCgoBhzy9A")
    Observable<Add_Product_Response> addProduct(@Part MultipartBody.Part image, @Query("productname") String productName, @Query("product_cost") String product_cost, @Query("userid") String userid, @Query("productdescription") String product_description, @Query("productunit") String productunit, @Query("type") String type, @Query("color") String color, @Query("grade") String grade);



    @Multipart
    @POST("v1/solid_product_update")
    @Headers("access_token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1MzY5OTMyNjN9.Lpot7r_O2WjKfTGwUlm7PRwqgal3DW0ctsCgoBhzy9A")
    Observable<Product_update_response> updateProduct(@Part MultipartBody.Part image,@Query("productname") String productName,
                                                      @Query("product_cost") String product_cost,
                                                      @Query("userid") String userid,
                                                      @Query("productunit")String productunit,
                                                      @Query("product_id") String productid,
                                                      @Query("productdescription") String productDescription,
                                                      @Query("type") String type,
                                                      @Query("color") String color,
                                                      @Query("grade") String grade
    );


    @GET("v1/get_top_product_range")
    @Headers("access_token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1MzY5OTMyNjN9.Lpot7r_O2WjKfTGwUlm7PRwqgal3DW0ctsCgoBhzy9A")
    Observable<ProductPriceSuggession> getProductPriceSuggestion(@Query("product_cost") String product_cost, @Query("type") String type);

    @POST("v1/Delete_products")
    @Headers("access_token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1MzY5OTMyNjN9.Lpot7r_O2WjKfTGwUlm7PRwqgal3DW0ctsCgoBhzy9A")
    Observable<Product_delete_response> deleteProducts(@Query("userid") String userId, @Query("product_id") String productId);



    @GET("v1/getall_products")
    @Headers("access_token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1MzY5OTMyNjN9.Lpot7r_O2WjKfTGwUlm7PRwqgal3DW0ctsCgoBhzy9A")
    Observable<GetProductResponse> getProduct(@Query("userid") String userid);



    @Headers("access_token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1MzcyNDY4NjV9.YrdvK-CJIp9Tvw0QcSpwvwqzJklWu3UNhrqqcGiv9k8")
    @GET("v1/SlabDetails")
    Observable<Products> getSlabDetsils(@Query("userid") String userid);



    @GET("v1/getMerchart")
    @Headers("access_token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1MzY5OTMyNjN9.Lpot7r_O2WjKfTGwUlm7PRwqgal3DW0ctsCgoBhzy9A")
    Observable<UserProduct> getUserProduct();


    @POST("v1/Add_orders")
    @Headers("access_token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1MzY5OTMyNjN9.Lpot7r_O2WjKfTGwUlm7PRwqgal3DW0ctsCgoBhzy9A")
    Observable<Add_Product_Response> placeOrder(@Query("productid") String productid, @Query("productname") String productname,
                                                @Query("productcost") String productcost, @Query("nameofuser") String nameofuser,
                                                @Query("address") String address, @Query("price") String price, @Query("email") String email,
                                                @Query("mobile") String mobile, @Query("firebaseid") String firebaseid, @Query("quantity") String quantity,
                                                @Query("unit") String unit, @Query("orderstatus") String orderstatus, @Query("ordercashtype") String ordercashtype,
                                                @Query("orderapproval") String orderapproval, @Query("pickupdate") String pickupdate, @Query("userid") String userid, @Query("merchantid") String merchantid);

    @GET("v1/order_type")
    @Headers("access_token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1MzY5OTMyNjN9.Lpot7r_O2WjKfTGwUlm7PRwqgal3DW0ctsCgoBhzy9A")
    Observable<MyRequest> myOrders_merchant(@Query("merchantid") String userid);

    @GET("v1/order_type")
    @Headers("access_token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1MzY5OTMyNjN9.Lpot7r_O2WjKfTGwUlm7PRwqgal3DW0ctsCgoBhzy9A")
    Observable<MyRequest> myOrders_user(@Query("userid") String userid);





    @POST("v1/solid_orders_pickupdate")
    @Headers("access_token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1MzY5OTMyNjN9.Lpot7r_O2WjKfTGwUlm7PRwqgal3DW0ctsCgoBhzy9A")
    Observable<OrderResponse> updateOrder(@Query("orderstatus") String orderstatus, @Query("orderid") String orderid, @Query("pickupdate") String pickupdate, @Query("orderdeliverydate") String orderdeliverydate);


}
