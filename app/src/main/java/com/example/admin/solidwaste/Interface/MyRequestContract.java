package com.example.admin.solidwaste.Interface;



import com.example.admin.solidwaste.pojo.ProductOrderRequestPojo.MyRequestResponseResponse;

import java.util.ArrayList;
import java.util.List;

public interface MyRequestContract {

    interface view{

        void loadData(ArrayList<MyRequestResponseResponse> myRequestResponseResponse);
        void showError(String message);

        void showDialog();
        void hideDialog();

        void showResult(String msg);


    }

    interface model{

    }

    interface presenter{
        void loadDataByOrderStatus(String userid, String utype, String reqType);
        void loadMyData(String userid, String utype);

    }


}
