package com.example.admin.solidwaste.RoomDB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.admin.solidwaste.pojo.NotificationBean;


/**
 * Created by Murugan on 12-10-2018.
 */

@Database(entities = {NotificationBean.class},version = 2,exportSchema = false)
public abstract class NotificationDatabase extends RoomDatabase {

public abstract NotificationDao notificationDao();

}
