package com.example.admin.solidwaste.Interface;


import android.content.Context;


import com.example.admin.solidwaste.RoomDB.NotificationDatabase;
import com.example.admin.solidwaste.pojo.NotificationBean;

import java.util.List;

public interface NotificationInterface {
    interface view{
        public void getDatas(List<NotificationBean> listvalues);
    }
    interface presenter{
        public void test();
       public void loadDatas(NotificationDatabase database, String userId);
       public void loadDatasByMerchant(NotificationDatabase database, String userId);
    }
}
