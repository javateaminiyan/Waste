package com.example.admin.solidwaste.activity;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.admin.solidwaste.Interface.NotificationInterface;
import com.example.admin.solidwaste.R;
import com.example.admin.solidwaste.RoomDB.NotificationDatabase;
import com.example.admin.solidwaste.adapter.NotificationAdapter;
import com.example.admin.solidwaste.di.component.DaggerNotificationComponent;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.di.module.NotificationPresenterModule;
import com.example.admin.solidwaste.pojo.NotificationBean;
import com.example.admin.solidwaste.presenter.NotificationPresenter;
import com.example.admin.solidwaste.sharedprefshelper.SharedPrefModule;
import com.example.admin.solidwaste.utils.CommonHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_typeofuser;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_userid;

public class NotificationActivity extends AppCompatActivity implements NotificationInterface.view {

    @BindView(R.id.rv_notification_list)
    RecyclerView rv_notification_list;

    NotificationPresenter notificationPresenter;

    List<NotificationBean> notificationList = new ArrayList<>();

    @Inject
    SharedPreferences sharedPreferences;
    //
    @Inject
    NotificationPresenter getNotificationPresenter;

    String pref_userid;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    String mImageUrl;
    String mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Notification");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent i = getIntent();
        String userid = i.getStringExtra("userid");
        String type = i.getStringExtra("type");
        Log.e("fbhhd=>",type+"");
        if(i.getStringExtra("imageUri")!=null){
            mImageUrl = i.getStringExtra("imageUri");
        }

        if (getIntent().getExtras() != null) {

            for (String key : getIntent().getExtras().keySet()) {
                String value = getIntent().getExtras().getString(key);

                if (key.equals("AnotherActivity") && value.equals("True")) {
                    Intent intent = new Intent(this, Login.class);
                    intent.putExtra("value", value);
                    startActivity(intent);
                    finish();
                }

            }
        }


        DaggerNotificationComponent.builder()
                .notificationPresenterModule(new NotificationPresenterModule(this))
                .sharedPrefModule(new SharedPrefModule(this))
                .networkClient(new NetworkClient(CommonHelper.BASE_URL))
                .build()
                .inject(this);
        NotificationDatabase database = Room.databaseBuilder(NotificationActivity.this, NotificationDatabase.class, CommonHelper.ROOM_DBNAME).build();


        initRecyclerView();


        new Thread(new Runnable() {
            @Override
            public void run() {
               /* NotificationDatabase database = Room.databaseBuilder(getApplicationContext(), NotificationDatabase.class, CommonHelper.ROOM_DBNAME).build();

                notificationList = database.notificationDao().getNotitificationById(mUserId);
                Log.e("TAG", "merchantid"+userid);*/

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                NotificationDatabase database1 = Room.databaseBuilder(getApplicationContext(), NotificationDatabase.class, CommonHelper.ROOM_DBNAME).build();
                if(type.equalsIgnoreCase("user"))
                getNotificationPresenter.loadDatas(database1,userid);
                else if(type.equalsIgnoreCase("merchant"))
                getNotificationPresenter.loadDatasByMerchant(database1,userid);

                Log.e("TAG", "merchantid"+userid);

            }
        }).start();



        Log.e("rommmdatsaaa", "" + notificationList);
//        setListAdapter(notificationList);

//        notificationPresenter = new NotificationPresenter(NotificationActivity.this);
//        notificationPresenter.loadDatas(NotificationActivity.this);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                onBackPressed();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void setListAdapter(List<NotificationBean> listAdapter) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                NotificationAdapter notificationAdapter = new NotificationAdapter(listAdapter, NotificationActivity.this);
//                rv_notification_list.setAdapter(notificationAdapter);

            }
        });


    }

    public void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NotificationActivity.this);
        rv_notification_list.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void getDatas(List<NotificationBean> listvalues) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                notificationList = listvalues;
                NotificationAdapter notificationAdapter = new NotificationAdapter(notificationList, NotificationActivity.this, sharedPreferences.getString(sharedpref_typeofuser, null));
                rv_notification_list.setAdapter(notificationAdapter);

            }
        });

    }

}