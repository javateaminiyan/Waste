package com.example.admin.solidwaste.map;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.solidwaste.R;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.pojo.NearByLocationMerchant.NearBymerchantLatLng;
import com.example.admin.solidwaste.pojo.NearByLocationMerchant.Response;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NearByLocationOfShop extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String TAG = NearByLocationOfShop.class.getName();
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback;
    LocationRequest mLocationRequest;
    private double EARTH_RADIUS = 25000;


    float[] distance = new float[2];


    Marker mCurrentMarker;
    Location mLastLocation;
    private CompositeDisposable disposables = new CompositeDisposable();
    Circle circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_by_location_of_shop);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ButterKnife.bind(this);
        buildLocationRequest();
        buildLocationCallback();


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, locationCallback, Looper.myLooper());
        nearbyAllMerchant();
    }

    private void nearbyAllMerchant() {

        disposables.add(NetworkClient.getApiInterfaceAll().getAllMerchantLocation()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<NearBymerchantLatLng>() {
                    @Override
                    public void accept(NearBymerchantLatLng nearBymerchantLatLng) throws Exception {

                        Log.e(TAG, "nearBymerchantLatLng.getResponse().getResponse()" + nearBymerchantLatLng.getResponse().getResponse().size());
                        int size = 0;
                        for (Response response : nearBymerchantLatLng.getResponse().getResponse()) {
                            if (response.getLatlng().contains(",")) {

                                Log.e(TAG, "latlng response" + response.getLatlng());

                                String Latitude = response.getLatlng().split(",")[0];
                                String Longitude = response.getLatlng().split(",")[1];
                                Log.e(TAG, Latitude + "latlng" + Longitude + "sizefff" + "  " + size++);


                                Location.distanceBetween(Double.parseDouble(Latitude), Double.parseDouble(Longitude),
                                        circle.getCenter().latitude, circle.getCenter().longitude, distance);


                                if (distance[0] > circle.getRadius()) {
                                    MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(Double.parseDouble(Latitude), Double.parseDouble(Longitude)))
                                            .title(response.getNameOfShop())
                                            .snippet("Contact" + response.getMobile())
                                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

                                    mCurrentMarker = mMap.addMarker(markerOptions);
                                    mCurrentMarker.showInfoWindow();
                                  //  Toast.makeText(getBaseContext(), "Outside", Toast.LENGTH_LONG).show();
                                } else {
                                  //  Toast.makeText(getBaseContext(), "Inside", Toast.LENGTH_LONG).show();
                                    MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(Double.parseDouble(Latitude), Double.parseDouble(Longitude)))
                                            .title(response.getNameOfShop())
                                            .snippet("Contact" + response.getMobile())

                                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                                    mCurrentMarker = mMap.addMarker(markerOptions);
                                    mCurrentMarker.showInfoWindow();

                                }
                            }
                        }


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {



                mLastLocation = location;

//                MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()))
//                        .title("Current Location")
//                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//
//                mCurrentMarker = mMap.addMarker(markerOptions);
                mMap.setOnMarkerClickListener(onMarkerClickedListener);


                CircleOptions circleOptions = new CircleOptions()
                        .center(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()))
                        .radius(EARTH_RADIUS)
                        // .fillColor(getResources().getColor(R.color.blue))
                        .strokeColor(Color.RED)
                        .strokeWidth(2);

                circle = mMap.addCircle(circleOptions);


                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())));

                mMap.animateCamera(CameraUpdateFactory.zoomTo(getZoomLevel(circle)));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                    if (ContextCompat.checkSelfPermission(NearByLocationOfShop.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


                        mMap.setMyLocationEnabled(true);

                    }
                } else {
                    mMap.setMyLocationEnabled(true);
                }


            }
        });


    }

    public int getZoomLevel(Circle circle) {
        int zoomLevel = 0;
        if (circle != null) {
            double radius = circle.getRadius();
            double scale = radius / 500;
            zoomLevel = (int) (16 - Math.log(scale) / Math.log(2));
        }
        return zoomLevel;
    }

    @Override
    protected void onStop() {

        fusedLocationProviderClient.removeLocationUpdates(locationCallback);

        super.onStop();
    }

    private void buildLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setSmallestDisplacement(10f);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

    }

    private GoogleMap.OnMarkerClickListener onMarkerClickedListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {

            Log.e(TAG, marker.getTitle());
            Log.e(TAG, "" + marker.getPosition());
            Log.e(TAG, "" + marker.getSnippet());

            if (marker.isInfoWindowShown()) {
                marker.hideInfoWindow();
            } else {
                marker.showInfoWindow();
            }

            if (!marker.getTitle().isEmpty()) {
                Intent send_viewDirection = new Intent(NearByLocationOfShop.this, ViewDirections.class);
                send_viewDirection.putExtra("markertitle", marker.getTitle());

                String removefirst = marker.getPosition().toString().replace("lat/lng: (", "").trim();
                String removesecond = removefirst.replace(")", "").trim();
                send_viewDirection.putExtra("latitude", removesecond.split(",")[0]);
                send_viewDirection.putExtra("longitude", removesecond.split(",")[1]);
                Log.e(TAG, "===============" + removesecond.split(",")[0] + "longitude" + removesecond.split(",")[1]);


                send_viewDirection.putExtra("snippet", marker.getSnippet());
                startActivity(send_viewDirection);

            }

            return true;
        }
    };

    private void buildLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                mLastLocation = locationResult.getLastLocation();


//                MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()))
//                        .title("Your Position")
//                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//                mCurrentMarker = mMap.addMarker(markerOptions);


                mMap.setOnMarkerClickListener(onMarkerClickedListener);


                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())));

                mMap.animateCamera(CameraUpdateFactory.zoomTo(getZoomLevel(circle)));

                //  mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));

            }
        };

    }

    @Override
    protected void onDestroy() {
        disposables.clear();
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        super.onDestroy();
    }
}
