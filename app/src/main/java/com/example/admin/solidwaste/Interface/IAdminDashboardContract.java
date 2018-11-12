package com.example.admin.solidwaste.Interface;

import com.example.admin.solidwaste.pojo.AdminPojo.AdminDashboardPojoResponseResponse;

import java.util.List;

public interface IAdminDashboardContract  {


    interface  view{
        void showDialog();
        void dismissDialog();
        void displayDashboardCoubt(AdminDashboardPojoResponseResponse[] adminDashboardPojoResponseResponses);
    }
    interface presenter{
        void loadStatusCount(String merchantId);
    }
}
