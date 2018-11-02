package com.example.admin.solidwaste.activity;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import com.example.admin.solidwaste.Interface.GetProductContract;
import com.example.admin.solidwaste.Interface.IShowDialogContract;
import com.example.admin.solidwaste.R;
import com.example.admin.solidwaste.adapter.ProductAdapter;
import com.example.admin.solidwaste.di.component.DaggerIRegisteredProductComponent;
import com.example.admin.solidwaste.di.module.NetworkClient;
import com.example.admin.solidwaste.di.module.RegisteredProductPresenterModule;
import com.example.admin.solidwaste.pojo.ProductRegistrationPojo.Product;
import com.example.admin.solidwaste.pojo.RegisteredProduct.GetProductResponseResponseResponse;
import com.example.admin.solidwaste.pojo.UserProductPojo.UserProductResponseResponse;
import com.example.admin.solidwaste.presenter.AdminPresenter.RegProductPresenter.GetProduct;
import com.example.admin.solidwaste.sharedprefshelper.SharedPrefModule;
import com.example.admin.solidwaste.utils.CommonHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Retrofit;

public class ViewProductActivity extends AppCompatActivity implements GetProductContract.view, IShowDialogContract {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    ArrayList<Product> p = new ArrayList<>();

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    Retrofit retrofit;
    @Inject
    NetworkClient networkClient;

    @Inject
    GetProduct getProductPresenter;

    List<GetProductResponseResponseResponse> s;

    ProductAdapter productAdapter;
    private SearchView searchView;

    String pref_userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reg_product);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registered Products");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        setUpView();


        DaggerIRegisteredProductComponent.builder()
                .registeredProductPresenterModule(new RegisteredProductPresenterModule(this))
                .sharedPrefModule(new SharedPrefModule(this))
                .networkClient(new NetworkClient(CommonHelper.BASE_URL))
                .build()
                .inject(this);


        pref_userid = sharedPreferences.getString(CommonHelper.sharedpref_userid, "");


        getProductPresenter.getProduct(pref_userid);

    }

    private void additems() {
//        mShimmerViewContainer.startShimmerAnimation();
//        mShimmerViewContainer.setVisibility(View.VISIBLE);
//
//        mShimmerViewContainer.stopShimmerAnimation();
//        mShimmerViewContainer.setVisibility(View.GONE);
    }

    private void setUpView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ViewProductActivity.this);
        recyclerView.setLayoutManager(layoutManager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.prod_search_menu, menu);


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                productAdapter.getFilter().filter(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                productAdapter.getFilter().filter(newText.toLowerCase());

                return false;
            }
        });

        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        Intent i = new Intent(getApplicationContext(), AdminDashboard.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();

//        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mShimmerViewContainer.stopShimmerAnimation();
    }


    @Override
    public void loadData(List<GetProductResponseResponseResponse> getProductResponseResponseResult) {
        s = getProductResponseResponseResult;


        if (s.size() > 0) {
            productAdapter = new ProductAdapter(getProductResponseResponseResult, ViewProductActivity.this, ViewProductActivity.this);
            recyclerView.setAdapter(productAdapter);
            productAdapter.notifyDataSetChanged();

        } else {
//
//           mShimmerViewContainer.startShimmerAnimation();
//        mShimmerViewContainer.setVisibility(View.VISIBLE);
//
//        mShimmerViewContainer.stopShimmerAnimation();
//        mShimmerViewContainer.setVisibility(View.GONE);
        }

    }

    @Override
    public void showDialogd(int position, List<UserProductResponseResponse> userProductResponseResponses) {

    }


    @Override
    public void showDeleteDialog(int position, String title) {


        if (title.equalsIgnoreCase("delete")) {
            ShowAlertDialog(position, s.get(position).getUserid(), s.get(position).getProduct_id());

        }

    }

    @Override
    public void calIntent(String mobileno) {

    }

    public void ShowAlertDialog(final int position, final String userid, final int productid) {

        new AlertDialog.Builder(ViewProductActivity.this,R.style.MyAlertDialogTheme)
                .setTitle("Delete Product")
                .setMessage("Are you sure want to delete item?")

                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getProductPresenter.deleteProduct(userid, productid);
                        s.remove(position);
                        productAdapter.notifyItemRemoved(position);
                        productAdapter.notifyItemRangeChanged(position, s.size());
                        productAdapter.notifyDataSetChanged();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    public void deleteSuccess(String message) {

        Toasty.success(getApplicationContext(), message, Toast.LENGTH_LONG).show();

//        productAdapter.notifyItemRemoved(0);


    }

    @Override
    public void deleteFailure(String message) {
        Toasty.info(getApplicationContext(), message, Toast.LENGTH_LONG).show();

    }


}
