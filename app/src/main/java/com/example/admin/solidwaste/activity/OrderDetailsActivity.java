package com.example.admin.solidwaste.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.admin.solidwaste.Interface.IFirebaseMessagingCallbackResponse;
import com.example.admin.solidwaste.Interface.OrderUpdateContract;
import com.example.admin.solidwaste.R;
import com.example.admin.solidwaste.di.component.DaggerOrderDetailsComponent;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.di.module.OrderDetailsModule;
import com.example.admin.solidwaste.fcmmessagesendcontroller.FirebaseMessagingMessageSendController;
import com.example.admin.solidwaste.paymenttransfergateway.PaymentActivity;
import com.example.admin.solidwaste.pojo.ProductOrderRequestPojo.MyRequestResponseResponse;
import com.example.admin.solidwaste.presenter.AdminPresenter.OrderUpdatePresenter.OrderUpdate;
import com.example.admin.solidwaste.sharedprefshelper.SharedPrefModule;
import com.example.admin.solidwaste.utils.CommonHelper;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;
import com.vatsal.imagezoomer.ImageZoomButton;
import com.vatsal.imagezoomer.ZoomAnimation;

import org.json.JSONException;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Retrofit;

public class OrderDetailsActivity extends AppCompatActivity implements OrderUpdateContract.view, IFirebaseMessagingCallbackResponse {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.spinner_orderstatus)
    Spinner spinner_order_status;

    @BindView(R.id.tv_address)
    TextView tvAddress;

    @BindView(R.id.tv_date)
    TextView tvDate;

    @BindView(R.id.tv_emailid)
    TextView tvEmail;

    @BindView(R.id.tv_orderid)
    TextView tvOrderId;

    @BindView(R.id.tv_price)
    TextView tvPrice;

    @BindView(R.id.tv_productcolor)
    TextView tvOrderColor;

    @BindView(R.id.tv_productname)
    TextView tvProductName;

    @BindView(R.id.tv_producttype)
    TextView tvProductType;

    @BindView(R.id.tv_quantity)
    TextView tvQuantity;

    @BindView(R.id.tv_totalprice)
    TextView tvTotalPrice;

    @BindView(R.id.tv_mobileno)
    TextView tvMobileNo;

    @BindView(R.id.tv_username)
    TextView tvUserName;


    @BindView(R.id.tv_delivery_date)
    TextView tvDeliveryDate;
    @BindView(R.id.tv_delivery_time)
    TextView tvDeliveryTime;

    @BindView(R.id.btn_submit)
    Button btnSubmit;


    @BindView(R.id.lin_date)
    LinearLayout linearLayoutDate;

    @BindView(R.id.lin_time)
    LinearLayout linearLayoutTime;

    @Inject
    SharedPreferences sharedPreferences;


    @BindView(R.id.cardlayout_order)
    CardView cardVieworder;


    @Inject
    Retrofit retrofit;
    @Inject
    NetworkClient networkClient;

    @Inject
    OrderUpdate orderPresenter;


    MyRequestResponseResponse rp__details;

    @BindView(R.id.im_product)
    ImageZoomButton imProduct;

    ZoomAnimation zoomAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Order  Details");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        DaggerOrderDetailsComponent.builder()
                .orderDetailsModule(new OrderDetailsModule(this))
                .sharedPrefModule(new SharedPrefModule(this))
                .networkClient(new NetworkClient(CommonHelper.BASE_URL))
                .build()
                .inject(this);

        Activity activity = new Activity();

        imProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (zoomAnimation != null) {
                    zoomAnimation = new ZoomAnimation(activity);
                    zoomAnimation.zoom(v, 300);

                }
            }
        });


        Bundle data = getIntent().getExtras();
        rp__details = (MyRequestResponseResponse) data.getParcelable("products");




        if (rp__details.getDatetime() != null) {
            String date = parseDateToddMMyyyy(rp__details.getDatetime());
            tvDate.setText("Date : " + date);
        }


        if(rp__details.getproductimage()!=null){
            Log.e("url=>",rp__details.getproductimage()+" vb");
            Glide.with(this).load(rp__details.getproductimage()).into(imProduct);
        }else{
            Log.e("no url=>",rp__details.getproductimage()+" vb");
        }

        tvAddress.setText(rp__details.getAddress());
        tvEmail.setText(rp__details.getEmail());
        tvOrderId.setText("Order Id : " + rp__details.getOrderid());
        tvPrice.setText("Price : \u20B9 " + rp__details.getProductcost() + "/" + rp__details.getUnit());
        tvMobileNo.setText(rp__details.getMobile());
        tvProductName.setText("Product Name : " + rp__details.getProductname());
        tvProductType.setText("Product Type : " + rp__details.getOrdercashtype());
        tvQuantity.setText("Quantity : " + rp__details.getQuantity());
        tvTotalPrice.setText("Total Price : \u20B9 " + rp__details.getPrice());
        tvUserName.setText(rp__details.getNameofuser());

        Log.e("values ", rp__details.getproductimage()+"xfbg");
        addOrderStatus(rp__details.getOrderstatus());


        spinner_order_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String val = parent.getItemAtPosition(position).toString();
                if (val.equalsIgnoreCase("pending")) {
                    btnSubmit.setText("Order Pending");
                    btnSubmit.setEnabled(true);
                    linearLayoutDate.setVisibility(View.GONE);
                    linearLayoutTime.setVisibility(View.GONE);
                } else if (val.equalsIgnoreCase("Cancelled")) {
                    btnSubmit.setText("Order Cancelled");
                    btnSubmit.setEnabled(true);
                    linearLayoutDate.setVisibility(View.GONE);
                    linearLayoutTime.setVisibility(View.GONE);


                } else if (val.equalsIgnoreCase("Collected")) {


                    btnSubmit.setText("Proceed to Payment");
                    btnSubmit.setEnabled(true);
                    linearLayoutDate.setVisibility(View.GONE);
                    linearLayoutTime.setVisibility(View.GONE);

                } else if (val.equalsIgnoreCase("Process")) {
                    btnSubmit.setText("Order Placed ");
                    btnSubmit.setEnabled(true);
                    linearLayoutDate.setVisibility(View.VISIBLE);
                    linearLayoutTime.setVisibility(View.VISIBLE);

                }else if (val.equalsIgnoreCase("Approved")) {
                    btnSubmit.setText("Order Approved");
                    btnSubmit.setEnabled(true);
                    linearLayoutDate.setVisibility(View.GONE);
                    linearLayoutTime.setVisibility(View.GONE);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (rp__details.getOrderstatus().equalsIgnoreCase("Collected")) {
            cardVieworder.setVisibility(View.GONE);
            btnSubmit.setFocusable(false);
        } else {

            btnSubmit.setFocusable(true);
            cardVieworder.setVisibility(View.VISIBLE);
        }
    }


    @OnClick(R.id.tv_delivery_date)
    public void chooseDate() {


        final Calendar myCalendar = Calendar.getInstance();
        int mYear = myCalendar.get(Calendar.YEAR);
        int mMonth = myCalendar.get(Calendar.MONTH);
        int mDate = myCalendar.get(Calendar.DATE);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tvDeliveryDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
            }
        }, mYear, mMonth, mDate);
        datePickerDialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
        datePickerDialog.show();
    }
    @OnClick(R.id.tv_delivery_time)
    public void chooseTime() {


        // TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(OrderDetailsActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                updateTime(selectedHour,selectedMinute);
            }
        }, hour, minute, false);//no 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }
    // Used to convert 24hr format to 12hr format with AM/PM values
    private void updateTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();
        tvDeliveryTime.setText(aTime);
    }
    @OnClick(R.id.btn_submit)
    public void UpdateOrder() {
        orderPresenter.updateOrder(spinner_order_status.getSelectedItem().toString(), String.valueOf(rp__details.getOrderid()), parseDateToddMMyyyy_update(tvDeliveryDate.getText().toString())+tvDeliveryTime.getText().toString());

        Log.e("valiueeee", "" + String.valueOf(rp__details.getOrderid()));
        Log.e("valiueeee", "" + spinner_order_status.getSelectedItem().toString());
        if (spinner_order_status.getSelectedItem().toString().equalsIgnoreCase("Process")) {

            if (tvDeliveryDate.getText().toString().equalsIgnoreCase("")) {
                Toasty.error(getApplicationContext(), "choose date", Toast.LENGTH_SHORT).show();
            } else {
                /*startActivity(new Intent(getApplicationContext(), AdminDashboard.class));

                finish();*/
            }
        } else if (spinner_order_status.getSelectedItem().toString().equalsIgnoreCase("Collected")) {

            Intent i = new Intent(getApplicationContext(), PaymentActivity.class);
            i.putExtra("amount", rp__details.getPrice());
            startActivity(i);


        }


    }

    public void addOrderStatus(String selectedvali) {

        ArrayList<String> orderlist = new ArrayList<>();
        orderlist.add("Cancelled");
        orderlist.add("Collected");
        orderlist.add("Approved");
        orderlist.add("Pending");
        orderlist.add("Process");


        ArrayAdapter<String> orderAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, orderlist);
        spinner_order_status.setAdapter(orderAdapter);


        int spinnerPosition = orderAdapter.getPosition(selectedvali);
        Log.e("status positiom", "" + selectedvali);
        Log.e("status positiom", "" + spinnerPosition);

        spinner_order_status.setSelection(spinnerPosition);

        if (spinnerPosition == 3) {
            linearLayoutDate.setVisibility(View.GONE);
            linearLayoutTime.setVisibility(View.GONE);

        }


    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        String outputPattern = "MMM  dd  yyyy  h:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


    public String parseDateToddMMyyyy_update(String time) {
        String inputPattern = "dd-MM-yyyy";
        String outputPattern = "yyyy-MM-dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


    @Override
    public void successMsg(String message) {
        Toasty.success(getApplicationContext(), message, Toast.LENGTH_SHORT).show();


        final String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("ddd", "FCM token: " + refreshedToken);

        Log.e("fooooooo", "" + rp__details.getFirebaseid());
        Log.e("fooooooo", "" + rp__details.getOrderid());
        Log.e("fooooooo", "" + sharedPreferences.getString(CommonHelper.sharedpref_userid,null)+" fg");
        Log.e("fooooooo", "" + rp__details.getUserid()+" fg");
//        Log.e("fooooooo", "" + rp__details.getPickupdate());

        FirebaseMessagingMessageSendController firebaseMessagingMessageSendController = new FirebaseMessagingMessageSendController(OrderDetailsActivity.this, networkClient, retrofit);
        try {

            firebaseMessagingMessageSendController.SendMessageMerchantFirebase
                    (sharedPreferences.getString(CommonHelper.sharedpref_name, null),
                            spinner_order_status.getSelectedItem().toString(),
                            String.valueOf(rp__details.getOrderid()),
                            sharedPreferences.getString(CommonHelper.sharedpref_userid,null)
                            , rp__details.getFirebaseid(),
                            refreshedToken
                            , rp__details.getProductname()
                            , rp__details.getProductid()
                            , rp__details.getProductcost()
                            , rp__details.getUserid()
                            , rp__details.getQuantity()
                            ,rp__details.getAddress()
                            ,rp__details.getOrderapproval()
                            ,rp__details.getPickupdate()
                            ,rp__details.getOrdercashtype()
                            ,rp__details.getPrice()
                            ,rp__details.getEmail()
                            ,rp__details.getUnit()

                    );




//            if (spinner_order_status.getSelectedItem().toString().equalsIgnoreCase("Delivered")) {
//                Intent i = new Intent(getApplicationContext(), PaymentActivity.class);
//                startActivity(i);
//            } else {
////                Intent i = new Intent(getApplicationContext(), AdminDashboard.class);
////                startActivity(i);
////                finish();
//            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void failureMsg(String message) {
        Toasty.error(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onMessageSuccess(String response) {
        Toasty.success(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onMessageError(String Error) {
        Toasty.error(getApplicationContext(), Error, Toast.LENGTH_SHORT).show();

    }
}
