package com.example.admin.solidwaste.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.solidwaste.Interface.MyRequestContract;
import com.example.admin.solidwaste.R;
import com.example.admin.solidwaste.adapter.MyRequestAdapter;
import com.example.admin.solidwaste.di.component.DaggerMyRequestComponent;
import com.example.admin.solidwaste.di.module.MyRequestPresenterModule;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.pojo.ProductOrderRequestPojo.MyRequestResponseResponse;
import com.example.admin.solidwaste.presenter.ProductOrderRequest.MyRequestPresenter;
import com.example.admin.solidwaste.sharedprefshelper.SharedPrefModule;
import com.example.admin.solidwaste.utils.CommonHelper;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

import static com.example.admin.solidwaste.utils.CommonHelper.sharedpref_typeofuser;

public class MyRequestActivity extends AppCompatActivity implements MyRequestContract.view {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.linNodata)
    LinearLayout lin_Nodata;

    MyRequestAdapter myRequestAdapter;


    @BindView(R.id.tv_approved)
    TextView tv_approved;
    @BindView(R.id.tv_completed)
    TextView tv_completed;
    @BindView(R.id.tv_pending)
    TextView tv_pending;
    @BindView(R.id.tv_delivered)
    TextView tv_delivered;


    ProgressDialog dialog;


    String pref_userId, type;

    @Inject
    SharedPreferences sharedPreferences;

    BottomSheetBehavior sheetBehavior;
    @Inject
    Retrofit retrofit;
    @Inject
    NetworkClient networkClient;

    @Inject
    MyRequestPresenter myRequestPresenter;
    List<MyRequestResponseResponse> myfilterlist;
    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout shimmerFrameLayout;

    @BindView(R.id.img_close)
    ImageView bootSheetClose;

    @BindView(R.id.bottom_sheet)
    LinearLayout layoutBottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_request);

        ButterKnife.bind(this);


        initToolbar();


        DaggerMyRequestComponent.builder()
                .myRequestPresenterModule(new MyRequestPresenterModule(this))
                .sharedPrefModule(new SharedPrefModule(this))
                .networkClient(new NetworkClient(CommonHelper.BASE_URL))
                .build()
                .inject(this);

        lin_Nodata.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmerAnimation();


//        Intent i = getIntent();
//         type = i.getStringExtra("type");


        pref_userId = sharedPreferences.getString(CommonHelper.sharedpref_userid, null);

   //     Log.e("TAG", "MY_REQUESTACTVITY" + sharedPreferences.getString(sharedpref_typeofuser, null));

        myRequestPresenter.loadMyData(pref_userId, sharedPreferences.getString(sharedpref_typeofuser, null));

        dialog = new ProgressDialog(MyRequestActivity.this);
        dialog.setMessage("Loading");


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

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyRequestActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        bootSheetClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetBehavior.setHideable(true);
                sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });


        tv_delivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<MyRequestResponseResponse> myRequestResponseResponseList = new ArrayList<>();
                for (MyRequestResponseResponse myRequestResponseResponse : myfilterlist) {
                    if (myRequestResponseResponse.getOrderstatus().equalsIgnoreCase("Delivered"))
                        myRequestResponseResponseList.add(myRequestResponseResponse);
                }
                changeAdapter(myRequestResponseResponseList);

            }
        });
        tv_completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<MyRequestResponseResponse> myRequestResponseResponseList = new ArrayList<>();
                for (MyRequestResponseResponse myRequestResponseResponse : myfilterlist) {
                    if (myRequestResponseResponse.getOrderstatus().equalsIgnoreCase("Cancelled"))
                        myRequestResponseResponseList.add(myRequestResponseResponse);
                }
                changeAdapter(myRequestResponseResponseList);

            }
        });
        tv_approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<MyRequestResponseResponse> myRequestResponseResponseList = new ArrayList<>();
                for (MyRequestResponseResponse myRequestResponseResponse : myfilterlist) {
                    if (myRequestResponseResponse.getOrderstatus().equalsIgnoreCase("Approved"))
                        myRequestResponseResponseList.add(myRequestResponseResponse);
                }
                changeAdapter(myRequestResponseResponseList);
            }
        });
        tv_pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<MyRequestResponseResponse> myRequestResponseResponseList = new ArrayList<>();
                for (MyRequestResponseResponse myRequestResponseResponse : myfilterlist) {
                    if (myRequestResponseResponse.getOrderstatus().equalsIgnoreCase("Pending"))
                        myRequestResponseResponseList.add(myRequestResponseResponse);

                }
                changeAdapter(myRequestResponseResponseList);
            }
        });


    }

    private void changeAdapter(List<MyRequestResponseResponse> myfilterlist) {
        myRequestAdapter = new MyRequestAdapter(myfilterlist, MyRequestActivity.this, sharedPreferences.getString(sharedpref_typeofuser, null));
        recyclerView.setAdapter(myRequestAdapter);
        myRequestAdapter.notifyDataSetChanged();

        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        if (myfilterlist.size() == 0) lin_Nodata.setVisibility(View.VISIBLE);
        else lin_Nodata.setVisibility(View.GONE);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("User Request");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }


    @Override
    public void loadData(ArrayList<MyRequestResponseResponse> myRequestResponseResponse) {

   //     Log.e("fdx", myRequestResponseResponse.get(0).getproductimage() + "fv");

        shimmerFrameLayout.stopShimmerAnimation();

        lin_Nodata.setVisibility(View.GONE);

        ArrayList<MyRequestResponseResponse> tempElements = new ArrayList<MyRequestResponseResponse>(myRequestResponseResponse);
        Collections.reverse(tempElements);
        myfilterlist = tempElements;

        ArrayList<MyRequestResponseResponse> mList = new ArrayList<>();

        for (MyRequestResponseResponse mylist : myfilterlist) {
            if (mylist.getOrderstatus().equalsIgnoreCase("pending")) mList.add(mylist);

        }


        myRequestAdapter = new MyRequestAdapter(mList, MyRequestActivity.this, sharedPreferences.getString(sharedpref_typeofuser, null));
        recyclerView.setAdapter(myRequestAdapter);
        myRequestAdapter.notifyDataSetChanged();


    }


    @Override
    public void showError(String message) {
        lin_Nodata.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);

        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View dview = li.inflate(R.layout.activity_network_error, null);

        builder.setView(dview);
        final android.support.v7.app.AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setCancelable(false);

        TextView tvRetry = (TextView) dview.findViewById(R.id.tv_retry);

        tvRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                myRequestPresenter.loadMyData(pref_userId, sharedPreferences.getString(sharedpref_typeofuser, null));
            }
        });
    }

    @Override
    public void showDialog() {
//        dialog.show();
    }

    @Override
    public void hideDialog() {
//        if(dialog!=null){
//            dialog.dismiss();
//        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter:

                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                    btnBottomSheet.setText("Close sheet");
                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                    btnBottomSheet.setText("Expand sheet");
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.admin_filter_menu, menu);

        return true;

    }


//
//    userProductAdapter = new UserProductAdapter(UserDashboard.this, userProductResponseResults, UserDashboard.this);
//        recyclerView.setAdapter(userProductAdapter);
//        userProductAdapter.notifyDataSetChanged();


}
