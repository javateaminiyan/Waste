package com.example.admin.solidwaste.presenter;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

import com.example.admin.solidwaste.Interface.BasePresenter;
import com.example.admin.solidwaste.Interface.NotificationInterface;
import com.example.admin.solidwaste.RoomDB.NotificationDatabase;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.pojo.NotificationBean;
import com.example.admin.solidwaste.utils.CommonHelper;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class NotificationPresenter extends BasePresenter<NotificationInterface.view> implements  NotificationInterface.presenter{

    NotificationInterface.view view;

    @Inject
    Retrofit retrofit;
    @Inject
    NetworkClient networkClient;

    @Inject
    public NotificationPresenter(NotificationInterface.view view) {
        super(view);
        this.view = view;
    }

    @Override
    public void test() {
        Log.e("fgn","hiiii");
    }

    @Override
    public void loadDatas(NotificationDatabase database,String userId) {
        Log.e("vbjhc","fhvbdfuj");
//        List<NotificationBean> notifications = new ArrayList<>();

//        List<NotificationBean> notifications=   database.notificationDao().getAllNotification();
        List<NotificationBean> notifications=database.notificationDao().getNotitificationById(userId);
        Log.e("vbjhc",notifications.size()+"sxv");
       for(int i=0;i<notifications.size();i++){
           Log.e("vbjh==>",notifications.get(i).getNameofuser()+" sdds "+notifications.get(i).getUserid());
       }
//        notifications.add(new NotificationBean(R.drawable.ic_hexagon,"The review you made for Bad Liar helped the community and you earned 3 points","4 minutes ago",false));
//        notifications.add(new NotificationBean(R.drawable.ic_letter_s,"The lyrics you published for something just like this have been translated more than 5 languages.","5 minutes ago",true));
//        notifications.add(new NotificationBean(R.drawable.ic_stars_couple_of_different_sizes,"The lyrics you submitted for something just like this are now published and you earned 4 points","9 minutes ago",true));
//        notifications.add(new NotificationBean(R.drawable.ic_stars_couple_of_different_sizes,"You ranked up and now you are a Master!","4 minutes ago",false));
//        notifications.add(new NotificationBean(R.drawable.ic_letter_s,"The lyrics you published for something just like this have been translated more than 5 languages.","2 minutes ago",true));
        view.getDatas(notifications);
    }

    @Override
    public void loadDatasByMerchant(NotificationDatabase database, String userId) {
        Log.e("vbjhc","fhvbdfuj");
//        List<NotificationBean> notifications = new ArrayList<>();

//        List<NotificationBean> notifications=   database.notificationDao().getAllNotification();
        List<NotificationBean> notifications=database.notificationDao().getNotitificationByMerchantId(userId);
        Log.e("vbjhc",notifications.size()+"sxv");
        for(int i=0;i<notifications.size();i++){
            Log.e("vbjh==>",notifications.get(i).getNameofuser()+" sdds "+notifications.get(i).getUserid());
        }
//        notifications.add(new NotificationBean(R.drawable.ic_hexagon,"The review you made for Bad Liar helped the community and you earned 3 points","4 minutes ago",false));
//        notifications.add(new NotificationBean(R.drawable.ic_letter_s,"The lyrics you published for something just like this have been translated more than 5 languages.","5 minutes ago",true));
//        notifications.add(new NotificationBean(R.drawable.ic_stars_couple_of_different_sizes,"The lyrics you submitted for something just like this are now published and you earned 4 points","9 minutes ago",true));
//        notifications.add(new NotificationBean(R.drawable.ic_stars_couple_of_different_sizes,"You ranked up and now you are a Master!","4 minutes ago",false));
//        notifications.add(new NotificationBean(R.drawable.ic_letter_s,"The lyrics you published for something just like this have been translated more than 5 languages.","2 minutes ago",true));
        view.getDatas(notifications);
    }
}
