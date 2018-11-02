package com.example.admin.solidwaste.activity;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.solidwaste.Interface.RegisterActivityContract;
import com.example.admin.solidwaste.R;
import com.example.admin.solidwaste.adapter.TabAdapter;
import com.example.admin.solidwaste.di.component.DaggerUserRegistrationComponent;
import com.example.admin.solidwaste.di.module.RegisterationPresenterModule;
import com.example.admin.solidwaste.fragment.TabFrag_UserRegisteration;
import com.example.admin.solidwaste.fragment.TabFrag_MerchantRegisteration;
import com.example.admin.solidwaste.presenter.RegistrationPresenter.RegistrationPresenter;
import com.example.admin.solidwaste.sharedprefshelper.SharedPrefModule;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;

public class Registration extends AppCompatActivity implements RegisterActivityContract.RegisterView {


    private static final int REQUESTCODE = 1000;
    TabAdapter adapter;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    RegistrationPresenter registerPresenter;



    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {


            case REQUESTCODE: {
                if (grantResults.length > 0) {


                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {

                    }
                }

            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Creates presenter
        DaggerUserRegistrationComponent.builder()
                .registerationPresenterModule(new RegisterationPresenterModule(this))
                .sharedPrefModule(new SharedPrefModule(this))

                .build()
                .inject(this);
        setupMVP();
        setupViews();
        setupFunctionality();


        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUESTCODE);
        } else {

            buildLocationRequest();
            buildLocationCallBack();
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }

            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

            // fusedLocationProviderClient.removeLocationUpdates(locationCallback);


        }

    }

    private void buildLocationCallBack() {

        locationCallback = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    //   txt_location.setText(String.valueOf(location.getLatitude()) + "/" + String.valueOf(location.getLongitude()));
                    Log.e("TAG", "ddd" + location.getLatitude());

                    Toast.makeText(getApplicationContext(), "" + location.getLatitude(), Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void buildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10);

    }

    private void setupViews() {

        adapter = new TabAdapter(getSupportFragmentManager(), this);
        adapter.addFragment(new TabFrag_UserRegisteration(), "User");
        adapter.addFragment(new TabFrag_MerchantRegisteration(), "Supplier");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupFunctionality() {


        registerPresenter.highLightCurrentTab(0, tabLayout, adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {


                registerPresenter.highLightCurrentTab(position, tabLayout, adapter); // for tab change
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void setupMVP() {
        //registerPresenter = new RegistrationPresenter(this);


    }




    @Override
    protected void onDestroy() {

        super.onDestroy();
        //  registerPresenter.stop();

        //  registerPresenter = null;
    }

    @Override
    protected void onPause() {

        super.onPause();
        //  registerPresenter.stop();

        // registerPresenter = null;
    }

    @Override
    protected void onStart() {

        super.onStart();
        //  registerPresenter.start();

        // registerPresenter = null;

    }

    @Override
    protected void onStop() {

        super.onStop();
        // registerPresenter.stop();

        // registerPresenter = null;

    }

    @Override
    public void displayGreeting(String greeting) {

    }


}
