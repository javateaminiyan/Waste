package com.example.admin.solidwaste.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.solidwaste.R;
import com.example.admin.solidwaste.pojo.DirectionApiPojo.DirectionRoutePojo;
import com.example.admin.solidwaste.pojo.DirectionApiPojo.Step;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.admin.solidwaste.utils.CommonHelper.getApiServiceScalar;

public class ViewDirections extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String TAG = ViewDirections.class.getName();
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback;
    LocationRequest mLocationRequest;

    Marker mCurrentMarker;
    Location mLastLocation;
    Polyline polyline;
    DirectionRoutePojo myRoutes;
    @BindView(R.id.btn_text_direction)
    Button btn_text_direction;
    private String dest_latitude, destLongitude;
    private String destination_snippet, destination_markertitle;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_directions);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        ButterKnife.bind(this);

        if (getIntent().getExtras().getString("markertitle", null)!=null) {


            Log.e(TAG, "Lat" + getIntent().getExtras().getString("latitude", null));
            Log.e(TAG, "===============" + getIntent().getExtras().getString("latitude", null) + "=========" + getIntent().getExtras().getString("longitude", null));

            dest_latitude = getIntent().getExtras().getString("latitude", null);
            destLongitude = getIntent().getExtras().getString("longitude", null);

            destination_snippet = getIntent().getExtras().getString("snippet", null);
            destination_markertitle = getIntent().getExtras().getString("markertitle", null);
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        buildLocationRequest();
        buildLocationCallback();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }


        fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, locationCallback, Looper.myLooper());
        btn_text_direction.setVisibility(View.GONE);
        btn_text_direction.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(ViewDirections.this);
                LayoutInflater inflater = LayoutInflater.from(ViewDirections.this);
                View step_view = inflater.inflate(R.layout.step_layout, null);
                HtmlTextView txt_routes = (HtmlTextView) step_view.findViewById(R.id.txt_routes);
                TextView text_alldetails = (TextView) step_view.findViewById(R.id.text_alldetails);
                builder.setTitle("Route");
                if (myRoutes != null && myRoutes.getRoutes().get(0).getLegs().get(0).getSteps().size() > 0) {

                    Log.e(TAG, "my routes====" + myRoutes.getRoutes().get(0).getLegs().get(0).getSteps().get(0).getHtmlInstructions());
                    for (Step step : myRoutes.getRoutes().get(0).getLegs().get(0).getSteps()) {
                        txt_routes.setHtml(myRoutes.getRoutes().get(0).getLegs().get(0).getSteps().get(0).getHtmlInstructions(), new HtmlHttpImageGetter(txt_routes));

                        if (myRoutes.getRoutes().get(0).getLegs().get(0).getSteps().size() > 0) {
                            builder.setTitle(myRoutes.getRoutes().get(0).getLegs().get(0).getSteps().get(0).getmManeuver());
                        }

                        text_alldetails.setText(myRoutes.getRoutes().get(0).getLegs().get(0).getDistance().getText());


//                        text_alldetails.setText("Distance text" + myRoutes.getRoutes().get(0).getLegs().get(0).getDistance().getText() + "\n"
//                                + "duration Text " + myRoutes.getRoutes().get(0).getLegs().get(0).getDuration().getText());

                    }
                    // text_alldetails

                }
                builder.setView(step_view);
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();

            }
        });
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
//
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())));
//
//                mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
//
//
//                //create marker for destination Location
//
//                LatLng destinationLatLng = new LatLng(
//                        CommonHelper.currentResult.getGeometry().getLocation().getLat(),
//                        CommonHelper.currentResult.getGeometry().getLocation().getLng());
//
//
//                mMap.addMarker(new MarkerOptions()
//                        .position(destinationLatLng)
//                        .title(CommonHelper.currentResult.getName())
//                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
//                drawPath(mLastLocation, destinationLatLng);


                MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()))
                        .title("Your Position")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                mCurrentMarker = mMap.addMarker(markerOptions);

                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())));

                mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));


                //create marker for destination Location
//
//                LatLng destinationLatLng = new LatLng(
//                        CommonHelper.currentResult.getGeometry().getLocation().getLat(),
//                        CommonHelper.currentResult.getGeometry().getLocation().getLng());
//                LatLng destinationLatLng = new LatLng(
//                        12.7486768,
//                        77.80555729999999);
                LatLng destinationLatLng = new LatLng(
                        Double.parseDouble(dest_latitude),
                        Double.parseDouble(destLongitude));


                Log.e(TAG, "======destinationLatLng=========" + destinationLatLng);

                mMap.addMarker(new MarkerOptions()
                        .position(destinationLatLng)
                        .title(destination_markertitle)
                        .snippet(destination_snippet)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                drawPath(mLastLocation, destinationLatLng);
            }
        };

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
                MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()))
                        .title("Your Position")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                mCurrentMarker = mMap.addMarker(markerOptions);

                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())));

                mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                    if (ContextCompat.checkSelfPermission(ViewDirections.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


                        mMap.setMyLocationEnabled(true);

                    }
                } else {
                    mMap.setMyLocationEnabled(true);
                }


                //create marker for destination Location
//
//                LatLng destinationLatLng = new LatLng(
//                        CommonHelper.currentResult.getGeometry().getLocation().getLat(),
//                        CommonHelper.currentResult.getGeometry().getLocation().getLng());

//                Log.e(TAG, "get Location get Lat" + CommonHelper.currentResult.getGeometry().getLocation().getLat());
//                Log.e(TAG, "get Location get Lng" + CommonHelper.currentResult.getGeometry().getLocation().getLng());


//                LatLng destinationLatLng = new LatLng(
//                        12.7486768,
//                        77.80555729999999);
                LatLng destinationLatLng = new LatLng(
                        Double.parseDouble(dest_latitude),
                        Double.parseDouble(destLongitude));


                Log.e(TAG, "======destinationLatLng=========" + destinationLatLng);


                mMap.addMarker(new MarkerOptions()
                        .position(destinationLatLng)

                        .title(destination_markertitle)
                        .snippet(destination_snippet)

                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                drawPath(mLastLocation, destinationLatLng);
//                CalculationByDistance(new LatLng(
//                        12.7463692, 77.8119697), new LatLng(
//                        12.7486768, 77.80555729999999));
                CalculationByDistance(new LatLng(
                        mLastLocation.getLatitude(), mLastLocation.getLongitude()), new LatLng(
                        Double.parseDouble(dest_latitude), Double.parseDouble(destLongitude)));
            }
        });

    }

    private void drawPath(Location mLastLocation, LatLng location) {

        if (polyline != null) {
            polyline.remove();
        }
        String origin = String.valueOf(mLastLocation.getLatitude()) + "," + String.valueOf(mLastLocation.getLongitude());
         //String destination = String.valueOf(location.getLat()) + "," + String.valueOf(location.getLng());
        String destination = String.valueOf("12.7486768,77.80555729999999");
        Log.e(TAG, "origin===" + origin);
        Log.e(TAG, "destination===" + destination);


        getApiServiceScalar().getDirections(origin, destination).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {

                Log.e(TAG, "response===" + response.body());


                assert response.body() != null;
                myRoutes = new Gson().fromJson(response.body(), new TypeToken<DirectionRoutePojo>() {
                }.getType());
//                Log.e(TAG, "myRoutes===" +myRoutes.getRoutes().get(0).getLegs().get(0).getSteps().get(0).getHtmlInstructions());


                new ParserTask().execute(response.body());


                if (myRoutes != null) {
                    btn_text_direction.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Log.e(TAG + "response throwable", t.getMessage());

            }
        });

    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

         android.app.AlertDialog waitingDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
            super.onPostExecute(lists);
            ArrayList<LatLng> points = new ArrayList<LatLng>();
            PolylineOptions lineOptions = new PolylineOptions();
            // lineOptions.width(2);
            lineOptions.width(5f);
            lineOptions.color(Color.RED);
            lineOptions.geodesic(true);
            MarkerOptions markerOptions = new MarkerOptions();
            // Traversing through all the routes
            for (int i = 0; i < lists.size(); i++) {
                // Fetching i-th route
                List<HashMap<String, String>> path = lists.get(i);
                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                }
                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);

            }
            // Drawing polyline in the Google Map for the i-th route
            if (points.size() != 0) {
                mMap.addPolyline(lineOptions);
                polyline = mMap.addPolyline(lineOptions);

            }
//
//            waitingDialog = new SpotsDialog(ViewDirections.this);
//            waitingDialog.show();
//
//            waitingDialog.setMessage("Please Waiting...");
//            waitingDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(List<List<HashMap<String, String>>> lists) {
            super.onCancelled(lists);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {


            JSONObject jsonObject;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jsonObject = new JSONObject(strings[0]);
                DirectionsJsonParser parser = new DirectionsJsonParser();
                routes = parser.parse(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return routes;
        }
    }

    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
