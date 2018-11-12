package com.example.admin.solidwaste.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.solidwaste.Interface.IAdminDashboardContract;
import com.example.admin.solidwaste.map.MapDragger;
import com.example.admin.solidwaste.map.NearByLocationOfShop;
import com.example.admin.solidwaste.R;
import com.example.admin.solidwaste.di.component.DaggerAdminApiComponent;
import com.example.admin.solidwaste.di.module.AdminDashboardModule;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.map.ViewDirections;
import com.example.admin.solidwaste.pojo.AdminPojo.AdminDashboardPojoResponseResponse;
import com.example.admin.solidwaste.presenter.AdminPresenter.AdminDash_Presenter;
import com.example.admin.solidwaste.sharedprefshelper.SharedPrefModule;
import com.example.admin.solidwaste.utils.CommonHelper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_address;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_city;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_datetime;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_email;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_firebaseid;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_gstno;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_latlng;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_mobileno;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_name;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_name_of_shop;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_panno;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_password;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_status;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_typeofuser;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_upino;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_userid;

public class AdminDashboard extends AppCompatActivity implements IAdminDashboardContract.view {


    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @BindView(R.id.drawer)
    DrawerLayout drawerLayout;


    @BindView(R.id.im_processing)
    ImageView im_processing;

    @BindView(R.id.im_completed)
    ImageView im_completed;

    @BindView(R.id.im_pending)
    ImageView im_pending;

    @BindView(R.id.im_collected)
    ImageView im_collected;

    @BindView(R.id.navigation_view)
    NavigationView navigationView;

    @BindView(R.id.badge_notification_collected)
    TextView badge_notification_collected;

    @BindView(R.id.badge_notification_processing)
    TextView badge_notification_processing;

    @BindView(R.id.badge_notification_pending)
    TextView badge_notification_pending;

    @BindView(R.id.badge_notification_completed)
    TextView badge_notification_completed;

    @Inject
    SharedPreferences sharedPreferences;


    @Inject
    Retrofit retrofit;
    @Inject
    NetworkClient networkClient;
    @Inject
    AdminDash_Presenter adminDash_presenter;

    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Admin DashBoard");


        DaggerAdminApiComponent.builder()
                .adminDashboardModule(new AdminDashboardModule(this))
                .sharedPrefModule(new SharedPrefModule(this))
                .networkClient(new NetworkClient(CommonHelper.BASE_URL))

                .build()
                .inject(this);

        pd = new ProgressDialog(this,R.style.MyAlertDialogTheme);
        initNavigationDrawer();
        initMenu();

        if(CommonHelper.isNetworkAvailable(getApplicationContext())){
            //getcount here
            adminDash_presenter.loadStatusCount(sharedPreferences.getString(CommonHelper.sharedpref_userid, ""));
        }


    }



    private void initMenu() {
        im_processing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MyRequestActivity.class);
                i.putExtra("reqType", "Process");
                startActivity(i);
            }
        });

        im_completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MyRequestActivity.class);
                i.putExtra("reqType", "Approved");
                startActivity(i);
            }
        });

        im_pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MyRequestActivity.class);
                i.putExtra("reqType", "pending");
                startActivity(i);

            }
        });
//
        im_collected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), MyRequestActivity.class);
                i.putExtra("reqType", "Collected");
                startActivity(i);

            }
        });
    }

    public void initNavigationDrawer() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();


                switch (id) {
                    case R.id.home:
                        //    Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(AdminDashboard.this,NearByLocationOfShop.class));


                        drawerLayout.closeDrawers();
                        break;
                    case R.id.profile:
                        startActivity(new Intent(AdminDashboard.this, Profile.class));

                        // Toast.makeText(getApplicationContext(), "profile", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.request:
                        Intent i = new Intent(getApplicationContext(), MyRequestActivity.class);
                        i.putExtra("reqType", "merchant");
                        startActivity(i);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.registration:
                        startActivity(new Intent(getApplicationContext(), NewProductActivity.class));
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.slabdetails:
                        startActivity(new Intent(getApplicationContext(), SlabRateActivity.class));
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.registereddetails:
                        startActivity(new Intent(getApplicationContext(), ViewProductActivity.class));
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.addmap:
                        startActivity(new Intent(AdminDashboard.this, MapDragger.class));
                        drawerLayout.closeDrawers();
                        break;


                    case R.id.contactus:
                        startActivity(new Intent(AdminDashboard.this, ContactUs.class));
                        drawerLayout.closeDrawers();
                        break;


                    case R.id.logout:


                        SharedPreferences.Editor editor = sharedPreferences.edit();


                        editor.putString(sharedpref_userid, null);
                        editor.putString(sharedpref_name, null);
                        editor.putString(sharedpref_mobileno, null);


                        editor.putString(sharedpref_email, null);
                        editor.putString(sharedpref_password, null);

                        editor.putString(sharedpref_city, null);
                        editor.putString(sharedpref_address, null);

                        //new Firebase Id
                        editor.putString(sharedpref_typeofuser, null);
                        editor.putString(sharedpref_firebaseid, null);
                        editor.putString(sharedpref_latlng, null);
                        editor.putString(sharedpref_name_of_shop, null);
                        editor.putString(sharedpref_gstno, null);
                        editor.putString(sharedpref_upino, null);
                        editor.putString(sharedpref_panno, null);
                        editor.putString(sharedpref_status, null);
                        editor.putString(sharedpref_datetime, null);


                        editor.apply();
                        startActivity(new Intent(AdminDashboard.this, Login.class));
                        finish();

                        break;
                }
                return true;
            }
        });
        View header = navigationView.getHeaderView(0);
        TextView tv_email = (TextView) header.findViewById(R.id.tv_email);

        String name = sharedPreferences.getString(sharedpref_name, null);

        if (name != null)
            tv_email.setText(name);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


    }

    public void showDialog(){
        pd.show();
    }

    @Override
    public void dismissDialog() {
        pd.dismiss();
    }



    @Override
    public void displayDashboardCoubt(AdminDashboardPojoResponseResponse[] adminDashboardPojoResponseResponses) {
        if(adminDashboardPojoResponseResponses[0]!=null){

            if(adminDashboardPojoResponseResponses[0].getProcessed()!=null)
            {
                if(!adminDashboardPojoResponseResponses[0].getProcessed().equalsIgnoreCase("0")){
                    badge_notification_processing.setText(adminDashboardPojoResponseResponses[0].getProcessed());
                    badge_notification_processing.setVisibility(View.VISIBLE);
                }else{
                    badge_notification_processing.setVisibility(View.GONE);
                }

            }else{
                badge_notification_processing.setVisibility(View.GONE);
            }


            if(adminDashboardPojoResponseResponses[0].getPending()!=null){
                if(!adminDashboardPojoResponseResponses[0].getPending().equalsIgnoreCase("0")){
                    badge_notification_pending.setText(adminDashboardPojoResponseResponses[1].getPending());
                    badge_notification_pending.setVisibility(View.VISIBLE);
                }else{
                    badge_notification_pending.setVisibility(View.GONE);
                }

            }else{
                badge_notification_pending.setVisibility(View.GONE);
            }
            if(adminDashboardPojoResponseResponses[0].getCollected()!=null){
                if(!adminDashboardPojoResponseResponses[0].getCollected().equalsIgnoreCase("0")){
                    badge_notification_collected.setText(adminDashboardPojoResponseResponses[0].getCollected());
                    badge_notification_collected.setVisibility(View.VISIBLE);
                }else{
                    badge_notification_collected.setVisibility(View.GONE);
                }


            }else{
                badge_notification_collected.setVisibility(View.GONE);
            }
            if(adminDashboardPojoResponseResponses[0].getApproved()!=null){
                if(!adminDashboardPojoResponseResponses[0].getApproved().equalsIgnoreCase("0")){
                    badge_notification_completed.setText(adminDashboardPojoResponseResponses[0].getApproved());
                    badge_notification_completed.setVisibility(View.VISIBLE);
                }else{
                    badge_notification_completed.setVisibility(View.GONE);
                }
            }else{
                badge_notification_completed.setVisibility(View.GONE);
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.notification_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.notifi_menu:

                Intent i = new Intent(getApplicationContext(), NotificationActivity.class);
                i.putExtra("type", "merchant");
                i.putExtra("userid", sharedPreferences.getString(CommonHelper.sharedpref_userid, ""));
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
