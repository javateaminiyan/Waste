package com.example.admin.solidwaste.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.example.admin.solidwaste.Interface.IFirebaseMessagingCallbackResponse;
import com.example.admin.solidwaste.Interface.IShowDialogContract;
import com.example.admin.solidwaste.Interface.IUserDashBoardContract;

import com.example.admin.solidwaste.R;
import com.example.admin.solidwaste.adapter.UserProduct.Filter.CityAdapter;
import com.example.admin.solidwaste.adapter.UserProduct.Filter.StreetAdapter;
import com.example.admin.solidwaste.adapter.UserProduct.UserProductAdapter;
import com.example.admin.solidwaste.di.component.DaggerIUserDashComponent;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.di.module.UserDashboardModule;
import com.example.admin.solidwaste.fcmmessagesendcontroller.FirebaseMessagingMessageSendController;
import com.example.admin.solidwaste.map.NearByLocationOfShop;
import com.example.admin.solidwaste.pojo.ProductRegistrationPojo.Add_Product_Response;
import com.example.admin.solidwaste.pojo.UserProductPojo.UserProductResponseResponse;
import com.example.admin.solidwaste.presenter.UserModules.UserDash_Presenter;
import com.example.admin.solidwaste.sharedprefshelper.SharedPrefModule;
import com.example.admin.solidwaste.utils.CommonHelper;
import com.google.firebase.iid.FirebaseInstanceId;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import retrofit2.Retrofit;

import static com.example.admin.solidwaste.utils.CommonHelper.isNetworkAvailable;
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

public class UserDashboard extends AppCompatActivity implements IUserDashBoardContract.view, IShowDialogContract, IFirebaseMessagingCallbackResponse {


    @BindView(R.id.drawer)
    DrawerLayout drawerLayout;


    @BindView(R.id.navigation_view)
    NavigationView navigationView;


    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @BindView(R.id.img_close)
    ImageView bootSheetClose;

    @BindView(R.id.bottom_sheet)
    LinearLayout layoutBottomSheet;

    BottomSheetBehavior sheetBehavior;

    @BindView(R.id.price_range)
    RangeSeekBar price_range;

    @BindView(R.id.user_recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.search)
    EditText search;


    @BindView(R.id.min_price)
    TextView min_price;
    int temp_min_price, temp_max_price;
    @BindView(R.id.max_price)
    TextView max_price;


    @BindView(R.id.filter_options)
    LinearLayout filter_options;

    @BindView(R.id.price_range_lay)
    LinearLayout price_range_lay;
    @BindView(R.id.tv_apply)
    TextView tv_apply;

    @BindView(R.id.tv_product_name)
    TextView tv_product_name;

    @BindView(R.id.tv_location)
    TextView tv_location;

    EditText etQuantity;
    String merchantid, merchantfirebaseid, productName;
    int productid, productcost;
    CrystalRangeSeekbar rangeSeekbar;

    UserProductAdapter userProductAdapter;

    SpinnerDialog sd_unit;
    StreetAdapter streetAdapter;
    CityAdapter cityAdapter;

    String userAddress;
    String userUnit;
    int totprice;

    String selectedMerchantFirebaseId;

    public static final float screen_width = 0;

    List<UserProductResponseResponse> tempUserProductResponseResponses;
    List<UserProductResponseResponse> tempUserProductResponseResponses1 = new ArrayList<>();
    List<String> tempUserSelectedProductResponseResponses;
    List<String> tempUserSelectedLocationResponseResponses;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    Retrofit retrofit;
    @Inject
    NetworkClient networkClient;
    @Inject
    UserDash_Presenter userDash_presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("User");


        DaggerIUserDashComponent.builder()
                .userDashboardModule(new UserDashboardModule(this))
                .sharedPrefModule(new SharedPrefModule(this))
                .networkClient(new NetworkClient(CommonHelper.BASE_URL))
                .build()
                .inject(this);


        initNavigationDrawer();

        // get seekbar from view
        rangeSeekbar = (CrystalRangeSeekbar) findViewById(R.id.rangeSeekbar3);


        setUpView();

        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        sheetBehavior.setHideable(true);
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
//                        bootSheetClose.setText("Close Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
//                        btnBottomSheet.setText("Expand Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });


        bootSheetClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetBehavior.setHideable(true);
                sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

//                sheetBehavior.setSkipCollapsed(true);
            }
        });


        tv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLocationValuesInAlert(userDash_presenter.getLocationNamesWithoutDuplicate(tempUserProductResponseResponses));
            }
        });
        tv_product_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ik=>", tempUserProductResponseResponses.size() + "");
                Log.e("i=>", userDash_presenter.getProductNamesWithoutDuplicate(tempUserProductResponseResponses).size() + "");
                showStreetValuesInAlert(userDash_presenter.getProductNamesWithoutDuplicate(tempUserProductResponseResponses));
            }
        });
        tv_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetBehavior.setHideable(true);
                sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                userDash_presenter.filterProductByPriceRange(tempUserProductResponseResponses, temp_min_price, temp_max_price);
            }
        });
// set listener
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                Log.e("min bbjhxvb", minValue + "");
                Log.e("max bbjhxvb", maxValue + "");
                min_price.setText("₹" + String.valueOf(minValue));
                temp_min_price = minValue.intValue();
                temp_max_price = maxValue.intValue();
                max_price.setText("₹" + String.valueOf(maxValue));
            }
        });

        price_range.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                Log.e("min price=>", bar.getSelectedMinValue() + "");
                Log.e("max price=>", bar.getSelectedMaxValue() + "");
                temp_min_price = (int) bar.getSelectedMinValue();
                max_price.setText("Maximum price: " + bar.getSelectedMaxValue() + "");

            }
        });

        sd_unit = new SpinnerDialog(UserDashboard.this, userDash_presenter.UnitList(), "Search Unit", "Close");
        hideSoftKeyboard(search);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bankSearch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
//                userProductAdapter.getFilter().filter(s);
                hideSoftKeyboard(search);
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
                        //  Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.profile:
                        // Toast.makeText(getApplicationContext(), "profile", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UserDashboard.this, Profile.class));
                        break;
                    case R.id.request:
                        // Toast.makeText(getApplicationContext(), "request", Toast.LENGTH_SHORT).show();
//                        drawerLayout.closeDrawers();
                        Intent i = new Intent(UserDashboard.this, MyRequestActivity.class);
                        i.putExtra("type", "user");
                        startActivity(i);
                        drawerLayout.closeDrawers();
                        break;


                    case R.id.showlocation:
                        startActivity(new Intent(UserDashboard.this, NearByLocationOfShop.class));

                        drawerLayout.closeDrawers();
                        break;

                    case R.id.contactus:
                        startActivity(new Intent(UserDashboard.this, ContactUs.class));

                        drawerLayout.closeDrawers();
                        break;

                    case R.id.logout:

                        SharedPreferences.Editor editor = sharedPreferences.edit();


                        editor.putString(sharedpref_userid, null);
                        editor.putString(sharedpref_name, null);
                        editor.putString(sharedpref_mobileno, "");


                        editor.putString(sharedpref_email, "");
                        editor.putString(sharedpref_password, "");

                        editor.putString(sharedpref_city, null);
                        editor.putString(sharedpref_address, null);

                        //new Firebase Id
                        editor.putString(sharedpref_typeofuser, null);
                        editor.putString(sharedpref_firebaseid, "");
                        editor.putString(sharedpref_latlng, null);
                        editor.putString(sharedpref_name_of_shop, null);
                        editor.putString(sharedpref_gstno, null);
                        editor.putString(sharedpref_upino, null);
                        editor.putString(sharedpref_panno, null);
                        editor.putString(sharedpref_status, null);
                        editor.putString(sharedpref_datetime, null);


                        editor.apply();
                        startActivity(new Intent(UserDashboard.this, Login.class));
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

    public void bankSearch(String s) {

        if (tempUserProductResponseResponses1.size() > 0) tempUserProductResponseResponses1.clear();
        for (int i = 0; i < tempUserProductResponseResponses.size(); i++) {

            if (tempUserProductResponseResponses.get(i).getName_of_shop().toLowerCase().contains(s.toLowerCase())) {
                tempUserProductResponseResponses1.add(tempUserProductResponseResponses.get(i));
            }
        }
        userProductAdapter = new UserProductAdapter(UserDashboard.this, tempUserProductResponseResponses1, UserDashboard.this);
        recyclerView.setAdapter(userProductAdapter);
        userProductAdapter.notifyDataSetChanged();
    }


    public void setUpView() {

        userDash_presenter.getUserProduct();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(UserDashboard.this);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void showStreetValuesInAlert(final List<String> data_list) {

        android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder(this);
        View v2 = getLayoutInflater().inflate(R.layout.recycler_alert, null);


        mBuilder.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });


        RecyclerView recyclerView = (RecyclerView) v2.findViewById(R.id.recycler);
        TextView btn_done = (TextView) v2.findViewById(R.id.btn_done);
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(v2.getContext());
        recyclerView.setLayoutManager(layoutManager);


        streetAdapter = new StreetAdapter(data_list);
        recyclerView.setAdapter(streetAdapter);
        streetAdapter.notifyDataSetChanged();

        mBuilder.setView(v2);
        final android.support.v7.app.AlertDialog dialog = mBuilder.create();
        dialog.show();

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempUserSelectedProductResponseResponses = streetAdapter.getSelectesProductList();
                if (tempUserSelectedProductResponseResponses.size() > 0) {
                    for (int i = 0; i < tempUserSelectedProductResponseResponses.size(); i++) {
                        Log.e("jhgjhf", tempUserSelectedProductResponseResponses.get(i));
                    }
                }
                userDash_presenter.filterProductByProductName(tempUserProductResponseResponses, tempUserSelectedProductResponseResponses);
                dialog.dismiss();
            }
        });
    }

    public void showLocationValuesInAlert(final List<String> data_list) {

        android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder(this);
        View v2 = getLayoutInflater().inflate(R.layout.recycler_alert, null);


        mBuilder.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });


        RecyclerView recyclerView = (RecyclerView) v2.findViewById(R.id.recycler);
        TextView btn_done = (TextView) v2.findViewById(R.id.btn_done);
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(v2.getContext());
        recyclerView.setLayoutManager(layoutManager);


        cityAdapter = new CityAdapter(data_list);
        recyclerView.setAdapter(cityAdapter);
        cityAdapter.notifyDataSetChanged();

        mBuilder.setView(v2);
        final android.support.v7.app.AlertDialog dialog = mBuilder.create();
        dialog.show();

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempUserSelectedLocationResponseResponses = cityAdapter.getSelectesProductList();
                if (tempUserSelectedLocationResponseResponses.size() > 0) {
                    for (int i = 0; i < tempUserSelectedLocationResponseResponses.size(); i++) {
                        Log.e("jhgjhf", tempUserSelectedLocationResponseResponses.get(i));
                    }
                }
                userDash_presenter.filterProductByLocationName(tempUserProductResponseResponses, tempUserSelectedLocationResponseResponses);
                dialog.dismiss();
            }
        });
    }


    /**
     * manually opening / closing bottom sheet on button click
     */


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:

                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                    btnBottomSheet.setText("Close sheet");
                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                    btnBottomSheet.setText("Expand sheet");
                }
                return true;

            case R.id.action_notification:

                Intent i = new Intent(getApplicationContext(), NotificationActivity.class);
                i.putExtra("type", "user");
                i.putExtra("userid", sharedPreferences.getString(CommonHelper.sharedpref_userid, ""));
                startActivity(i);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.user_menu, menu);

        return true;

    }


    @Override
    public void loadUserData(List<UserProductResponseResponse> userProductResponseResults) {

        tempUserProductResponseResponses = userProductResponseResults;


        userProductAdapter = new UserProductAdapter(UserDashboard.this, userProductResponseResults, UserDashboard.this);
        recyclerView.setAdapter(userProductAdapter);
        userProductAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadUserDataProduct(List<UserProductResponseResponse> userProductResponseResults) {
        Log.e("size===>", userProductResponseResults.size() + "");

        for (int i = 0; i < userProductResponseResults.size(); i++) {
            Log.e("===>", userProductResponseResults.get(i).getProductname());
        }

        userProductAdapter = new UserProductAdapter(UserDashboard.this, userProductResponseResults, UserDashboard.this);
        recyclerView.setAdapter(userProductAdapter);
        userProductAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadUserDataLocation(List<UserProductResponseResponse> userProductResponseResults) {
        Log.e("size===>", userProductResponseResults.size() + "");

        for (int i = 0; i < userProductResponseResults.size(); i++) {
            Log.e("===>", userProductResponseResults.get(i).getCity());
        }

        userProductAdapter = new UserProductAdapter(UserDashboard.this, userProductResponseResults, UserDashboard.this);
        recyclerView.setAdapter(userProductAdapter);
        userProductAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadUserDataByPriceRange(List<UserProductResponseResponse> userProductResponseResults) {
        Log.e("size===>", userProductResponseResults.size() + "");
        userProductAdapter = new UserProductAdapter(UserDashboard.this, userProductResponseResults, UserDashboard.this);
        recyclerView.setAdapter(userProductAdapter);
        userProductAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message, Add_Product_Response responseObj) {
        Log.e("orderid==>", responseObj.getOrderid() + "");
        Toasty.success(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        FirebaseMessagingMessageSendController firebaseMessagingMessageSendController = new FirebaseMessagingMessageSendController(UserDashboard.this, networkClient, retrofit);
        try {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();

/*
            String nameofuser, String productname, String orderid,String quantity, String merchantid,
                    String merchant_firebaseid, String user_firebaseid,
                    String upiid, String productid, String productcost, String mobileno,
                    String userid,String address,String orderapproval,String pickupdate,
                    String ordercashtype,String price,String email,String unit
                    */

/*            String.valueOf(userProductResponseResponses.get(position).getProduct_id()), "233",
                    userProductResponseResponses.get(position).getProductname(),
                    String.valueOf(userProductResponseResponses.get(position).getProduct_cost()),
                    sharedPreferences.getString(CommonHelper.sharedpref_name, ""),
                    etAddress.getText().toString(),
                    String.valueOf(totprice),
                    sharedPreferences.getString(CommonHelper.sharedpref_email, ""),
                    sharedPreferences.getString(CommonHelper.sharedpref_mobileno, ""),
                    refreshedToken,
                    etQuantity.getText().toString(),
                    etUnit.getText().toString(),
                    "Pending",
                    "cod",
                    "Pending",
                    " ", sharedPreferences.getString(CommonHelper.sharedpref_userid, ""),
                    String.valueOf(userProductResponseResponses.get(position).getUserid()*/

                            /*String productid,String orderid,String productname, String productcost,
                            String nameofuser,String address,String price,String email,String mobile,
                            String firebaseid
                            ,String quantity,String unit,String orderstatus,String ordercashtype,
                            String orderapproval,String pickupdate,String userid,String merchantId*/


            firebaseMessagingMessageSendController.SendMessageUserFirebase(
                    sharedPreferences.getString(CommonHelper.sharedpref_name, ""), "Pending",
                    productName, String.valueOf(responseObj.getOrderid()), etQuantity.getText().toString(),
                    merchantid, merchantfirebaseid
                    , refreshedToken, sharedPreferences.getString(CommonHelper.sharedpref_upino, ""),
                    String.valueOf(productid), String.valueOf(productcost),
                    sharedPreferences.getString(CommonHelper.sharedpref_mobileno, ""), sharedPreferences.getString(CommonHelper.sharedpref_userid, ""),
                    userAddress, "Pending",
                    " ", "cod", String.valueOf(totprice),
                    sharedPreferences.getString(CommonHelper.sharedpref_email, ""), userUnit, responseObj.getImage()
            );
            Log.e("from userDashBoard=>", "merchant id=>" + merchantid + "user id=>" + sharedPreferences.getString(CommonHelper.sharedpref_userid, ""));


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void showDialogd(int position, List<UserProductResponseResponse> userProductResponseResponses) {
        Log.e("posssss", "" + position);


        if (isNetworkAvailable(getApplicationContext())) {

            showdialogforOrder(UserDashboard.this, position, userProductResponseResponses);

        } else onMessageError("Internet Not Available");


    }


    @Override
    public void showDeleteDialog(int position, String title) {

    }

    @Override
    public void calIntent(String mobileno) {


        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;

        }
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", mobileno, null));
        startActivity(intent);


    }


    public void showdialogforOrder(final Activity activity, final int position, final List<UserProductResponseResponse> userProductResponseResponses) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        final AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        LayoutInflater factory = LayoutInflater.from(this);
        final View view = factory.inflate(R.layout.order_confirmation_dialog, null);

        etQuantity = (EditText) view.findViewById(R.id.etQty);
        etQuantity.requestFocus();
        final EditText etUnit = (EditText) view.findViewById(R.id.etUnit);

        final EditText etAddress = (EditText) view.findViewById(R.id.etAddress);

        Button btnRequest = (Button) view.findViewById(R.id.btnRequest);

        merchantid = String.valueOf(userProductResponseResponses.get(position).getUserid());
        productName = userProductResponseResponses.get(position).getProductname();
        merchantfirebaseid = userProductResponseResponses.get(position).getFirebaseid();
        productid = userProductResponseResponses.get(position).getProduct_id();
        productcost = userProductResponseResponses.get(position).getProduct_cost();

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etQuantity.getText().toString().equalsIgnoreCase("")) {
                    Toasty.warning(getApplicationContext(), "Enter Quantity", Toast.LENGTH_SHORT).show();
                } else if (etUnit.getText().toString().equalsIgnoreCase("")) {
                    Toasty.warning(getApplicationContext(), "Choose  Unit", Toast.LENGTH_SHORT).show();
                } else if (etAddress.getText().toString().equalsIgnoreCase("")) {
                    Toasty.warning(getApplicationContext(), "Enter Address", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("vcjhck", "df" + "fgbfg");
                    totprice = Integer.parseInt(etQuantity.getText().toString()) * (userProductResponseResponses.get(position).getProduct_cost());

                    final String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                    //

                    Log.e("fieewwwwww", "" + refreshedToken);
                    userAddress = etAddress.getText().toString();
                    userUnit = etUnit.getText().toString();
                    userDash_presenter.paceOrder(String.valueOf(userProductResponseResponses.get(position).getProduct_id()), "233",
                            userProductResponseResponses.get(position).getProductname(),
                            String.valueOf(userProductResponseResponses.get(position).getProduct_cost()),
                            sharedPreferences.getString(CommonHelper.sharedpref_name, ""),
                            etAddress.getText().toString(),
                            String.valueOf(totprice),
                            sharedPreferences.getString(CommonHelper.sharedpref_email, ""),
                            sharedPreferences.getString(CommonHelper.sharedpref_mobileno, ""),
                            refreshedToken,
                            etQuantity.getText().toString(),
                            etUnit.getText().toString(),
                            "Pending",
                            "cod",
                            "Pending",
                            " ", sharedPreferences.getString(CommonHelper.sharedpref_userid, ""), String.valueOf(userProductResponseResponses.get(position).getUserid()));

                    dialog.dismiss();
                }
            }
        });

        ImageView imClose = (ImageView) view.findViewById(R.id.imClose);

        etUnit.setText(userDash_presenter.UnitList().get(0));

        imClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        etUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sd_unit.showSpinerDialog();
            }
        });

        sd_unit.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {

                etUnit.setText(s.toString());
            }
        });
        dialog.setView(view);

        dialog.show();


    }

    @Override
    public void onMessageSuccess(String response) {
        Toasty.info(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onMessageError(String Error) {
        Toasty.info(getApplicationContext(), Error, Toast.LENGTH_SHORT).show();

    }


    protected void hideSoftKeyboard(EditText input) {

//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (imm != null) {
//            imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
//        }
    }
}
