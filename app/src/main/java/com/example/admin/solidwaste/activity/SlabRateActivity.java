package com.example.admin.solidwaste.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.admin.solidwaste.Interface.SlabRateContract;
import com.example.admin.solidwaste.R;
import com.example.admin.solidwaste.adapter.SlabRate.ProductsAdapter;
import com.example.admin.solidwaste.di.component.DaggerISlabRateComponent;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.di.module.SlabRatePresenterModule;
import com.example.admin.solidwaste.pojo.SlabRatePojo.Product;
import com.example.admin.solidwaste.pojo.SlabRatePojo.Products;
import com.example.admin.solidwaste.presenter.SlabRatePresenter.SlabRate;
import com.example.admin.solidwaste.sharedprefshelper.SharedPrefModule;
import com.example.admin.solidwaste.utils.CommonHelper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import io.reactivex.annotations.Nullable;

public class SlabRateActivity extends AppCompatActivity implements SlabRateContract.view{

    @Nullable
    @BindView(R.id.rv_products)
    RecyclerView rv_products;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    SlabRate slabRate;

    String pref_userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slab_rate);
        ButterKnife.bind(this);
        initToolbar();
        initRecyclerView();


        DaggerISlabRateComponent.builder()
                .slabRatePresenterModule(new SlabRatePresenterModule(this))
                .sharedPrefModule(new SharedPrefModule(this))
                .networkClient(new NetworkClient(CommonHelper.BASE_URL))
                .build()
                .inject(this);



        pref_userid = sharedPreferences.getString(CommonHelper.sharedpref_userid,"");


        slabRate.getSlabRate(pref_userid);
    }

    public void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Slab Rate");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
    public void initRecyclerView(){
        rv_products.setLayoutManager(new LinearLayoutManager(SlabRateActivity.this));
        rv_products.setHasFixedSize(true);
    }



    @Override
    public void ViewSlabRate(Products slabBeanList) {
        if(slabBeanList!=null){
            List<Product> productList = slabBeanList.getProducts();
            ProductsAdapter productsAdapter = new ProductsAdapter(productList,SlabRateActivity.this);
            rv_products.setAdapter(productsAdapter);
        }else{

            Toasty.normal(getApplicationContext(),"No data found",Toast.LENGTH_LONG).show();

        }
    }
}
