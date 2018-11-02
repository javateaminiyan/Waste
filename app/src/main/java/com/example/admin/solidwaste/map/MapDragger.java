package com.example.admin.solidwaste.map;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.solidwaste.pojo.Response;
import com.example.admin.solidwaste.R;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.sharedprefshelper.SharedPrefModule;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdate;
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

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.example.admin.solidwaste.utils.CommonHelper.isNetworkAvailable;
import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_userid;

public class MapDragger extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    private String TAG = ViewDirections.class.getName();
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback;
    LocationRequest mLocationRequest;
    private CompositeDisposable disposables = new CompositeDisposable();
    private double EARTH_RADIUS = 0.003919625722779;


    Marker mCurrentMarker;
    Location mLastLocation;
    private GoogleMap.OnCameraIdleListener onCameraIdleListener;
    @BindView(R.id.dragg_result)
    TextView resutText;
    //SupportMapFragment mapFragment;
    GoogleMap map;
    LatLng center;
    @BindView(R.id.cardView)
    CardView cardView;
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    @BindView(R.id.updateshoplocation)
    ImageView updateshoplocation;
    FragmentActivity view;
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT < 22)
            setStatusBarTranslucent(false);
        else
            setStatusBarTranslucent(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_dragger);
        ButterKnife.bind(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        resutText.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        resutText.setSingleLine(true);
        resutText.setMarqueeRepeatLimit(-1);
        resutText.setSelected(true);

        updateshoplocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder1 = new AlertDialog.Builder(MapDragger.this);
                builder1.setMessage("Are you want to store this location as your shop");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                android.app.AlertDialog waitingDialog = new SpotsDialog(MapDragger.this);
                                waitingDialog.show();

                                waitingDialog.setMessage("Please Waiting...");


                                if (isNetworkAvailable(MapDragger.this)) {
                                    SharedPrefModule sharedPrefModule = new SharedPrefModule(getApplicationContext());
                                    SharedPreferences sharedPreferences = sharedPrefModule.provideSharedPreferences(getApplicationContext());

                                    if (sharedPreferences.getString(sharedpref_userid, null) != null) {
                                        disposables.add(NetworkClient.getApiInterfaceAll().getLocationUpdate(sharedPreferences.getString(sharedpref_userid, null), resutText.getText().toString().toLowerCase().trim(), latLng.latitude + "," + latLng.longitude)
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribeOn(Schedulers.io())
                                                .subscribe(new Consumer<Response>() {
                                                    @Override
                                                    public void accept(Response response) throws Exception {
                                                        waitingDialog.dismiss();

                                                        Toasty.success(MapDragger.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                                                        dialog.cancel();

                                                    }
                                                }, new Consumer<Throwable>() {
                                                    @Override
                                                    public void accept(Throwable throwable) throws Exception {
                                                        waitingDialog.dismiss();
                                                        dialog.cancel();
                                                        Toasty.success(MapDragger.this, "Failure Throwable Exception" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }));


                                    } else
                                        Toasty.success(MapDragger.this, "User Id Not Available", Toast.LENGTH_SHORT).show();


                                }


                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                //builder1.show();
                AlertDialog alertDialog = builder1.create();
                alertDialog.show();
            }
        });
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(MapDragger.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    printToast("Google Play Service Repair");
                } catch (GooglePlayServicesNotAvailableException e) {
                    printToast("Google Play Service Not Available");
                }
            }
        });
        buildLocationRequest();
        buildLocationCallback();


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, locationCallback, Looper.myLooper());

        configureCameraIdle();

    }


    private void configureCameraIdle() {


        onCameraIdleListener = new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {

                latLng = mMap.getCameraPosition().target;
                Geocoder geocoder = new Geocoder(MapDragger.this, Locale.ENGLISH);

                try {
                    List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    if (addressList != null && addressList.size() > 0) {


                        String locality = addressList.get(0).getAddressLine(0);
                        String country = addressList.get(0).getCountryName();
                        Address fetchedAddress = addressList.get(0);
                        StringBuilder strAddress = new StringBuilder();

                        for (int i = 0; i < fetchedAddress.getMaxAddressLineIndex(); i++) {
                            strAddress.append(fetchedAddress.getAddressLine(i)).append(" ");
                        }

                        // resutText.setText(strAddress.toString());

                        if (!locality.isEmpty() && !country.isEmpty())
                            resutText.setText(locality + "  " + country);

                        if (mCurrentMarker != null) {

                            mCurrentMarker.remove();
                        }
                        MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude))
                                .title("Place Your Shop Here")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//
                        mCurrentMarker = mMap.addMarker(markerOptions);
                        mCurrentMarker.showInfoWindow();

                    } else {
                        resutText.setText("Searching Current Address");
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                    printToast("Could not get address..!");
                }

            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                if (!place.getAddress().toString().contains(place.getName())) {
                    resutText.setText(place.getName() + ", " + place.getAddress());
                } else {
                    resutText.setText(place.getAddress());
                }

                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 16);
                mMap.animateCamera(cameraUpdate);


            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                printToast("Error in retrieving place info");

            }
        }
    }

    private void printToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnCameraIdleListener(onCameraIdleListener);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {


                mLastLocation = location;

                Log.e("mlastLocation", "" + mLastLocation);
                if (mLastLocation != null) {
                    MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()))
                            .title("Place Your Shop Here")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

                    mCurrentMarker = mMap.addMarker(markerOptions);
                    mCurrentMarker.hideInfoWindow();
                    mMap.setOnMarkerClickListener(onMarkerClickedListener);

//                Circle circle = mMap.addCircle(new CircleOptions()
//                        .center(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()))
//                        .radius(1000)
//                        .strokeColor(Color.RED)
//                        .fillColor(Color.BLUE));

                    CircleOptions circleOptions = new CircleOptions()
                            .center(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()))
                            .radius(EARTH_RADIUS)
                            // .fillColor(getResources().getColor(R.color.blue))
                            .strokeColor(Color.RED)
                            .strokeWidth(2);

                    mMap.addCircle(circleOptions);

                    //Circle zoom level

                    //   int zoomlevel = getZoomLevel(circle);


                    //working normal camera level
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())));

                    mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                        if (ContextCompat.checkSelfPermission(MapDragger.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


                            mMap.setMyLocationEnabled(true);

                        }
                    } else {
                        mMap.setMyLocationEnabled(true);
                    }


                }
            }
        });

    }

    public int getZoomLevel(Circle circle) {
        int zoomLevel = 12;
        if (circle != null) {
            double radius = circle.getRadius();
            double scale = radius / 500;
            zoomLevel = (int) (16 - Math.log(scale) / Math.log(2));
        }
        return zoomLevel;
    }

    private GoogleMap.OnMarkerClickListener onMarkerClickedListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            if (marker.isInfoWindowShown()) {
                marker.hideInfoWindow();
            } else {
                marker.showInfoWindow();
            }
            return true;
        }
    };


    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
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

                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())));

                mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));

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
