package com.example.admin.solidwaste.RoomDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import com.example.admin.solidwaste.pojo.NotificationBean;

import java.util.List;

/**
 * Created by Murugan on 12-10-2018.
 */
@Dao
public interface NotificationDao {
    @Query("select * from NotificationBean")
    List<NotificationBean> getAllNotification();

    @Query("select * from NotificationBean where userid=:userid order by uid desc")
    List<NotificationBean> getNotitificationById(String userid);

    @Query("select * from NotificationBean where merchantId=:merchantId order by uid desc")
    List<NotificationBean> getNotitificationByMerchantId(String merchantId);


    @Insert
    void insert(NotificationBean notificationBeans);

    @Query("update   NotificationBean set isViewed=:isViewed where uid=:uid")
    void UpdateDate(boolean isViewed, int uid);

}
