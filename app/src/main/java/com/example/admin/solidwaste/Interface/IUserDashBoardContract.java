package com.example.admin.solidwaste.Interface;


import com.example.admin.solidwaste.pojo.ProductRegistrationPojo.Add_Product_Response;
import com.example.admin.solidwaste.pojo.UserProductPojo.UserProductResponseResponse;

import java.util.ArrayList;
import java.util.List;

public interface IUserDashBoardContract {


    interface view {
        void loadUserData(List<UserProductResponseResponse> userProductResponseResults);

        void loadUserDataProduct(List<UserProductResponseResponse> userProductResponseResults);

        void loadUserDataLocation(List<UserProductResponseResponse> userProductResponseResults);

        void loadUserDataByPriceRange(List<UserProductResponseResponse> userProductResponseResults);

        void showMessage(String message, Add_Product_Response responseObj);


    }


    interface presenter {
        void getUserProduct();

        void paceOrder(String productid, String orderid, String productname, String productcost, String nameofuser, String address, String price, String email, String mobile, String firebaseid
                , String quantity, String unit, String orderstatus, String ordercashtype, String orderapproval, String pickupdate, String userid, String merchantId);

        ArrayList<String> UnitList();

        void filterProductByPriceRange(List<UserProductResponseResponse> userProductResponseResults, int min, int max);

        List<String> getProductNamesWithoutDuplicate(List<UserProductResponseResponse> userProductResponseResults);

        List<String> getLocationNamesWithoutDuplicate(List<UserProductResponseResponse> userProductResponseResults);

        void filterProductByProductName(List<UserProductResponseResponse> userProductResponseResults, List<String> productNameList);

        void filterProductByLocationName(List<UserProductResponseResponse> userProductResponseResults, List<String> productNameList);

        void filterByRange();
    }


}
