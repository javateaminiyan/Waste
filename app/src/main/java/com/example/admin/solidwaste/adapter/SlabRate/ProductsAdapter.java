package com.example.admin.solidwaste.adapter.SlabRate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.solidwaste.R;
import com.example.admin.solidwaste.pojo.SlabRatePojo.Product;

import java.util.List;


/**
 * Created by Admin on 25-09-2018.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {
    List<Product> productList;
    Context context;

    public ProductsAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slab_item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.productId.setText(productList.get(position).getProductId()+"");
        holder.productName.setText(productList.get(position).getProductname()+"");
        initRecyclerView(holder.rv_specific_products,position);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void initRecyclerView(RecyclerView rv_specificproducts, int position){
        rv_specificproducts.setLayoutManager(new LinearLayoutManager(context));
        rv_specificproducts.setHasFixedSize(true);
        Log.e("slab==>",productList.get(position).getSlabDetails().size()+"");
        SpecificProductAdapter specificProductAdapter = new SpecificProductAdapter(productList.get(position).getSlabDetails(),context);
        rv_specificproducts.setAdapter(specificProductAdapter);

    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView productId,productName;
        RecyclerView rv_specific_products;
        public ViewHolder(View itemView) {
            super(itemView);
            productId = (TextView)itemView.findViewById(R.id.productId);
            productName = (TextView)itemView.findViewById(R.id.productName);
            rv_specific_products = (RecyclerView)itemView.findViewById(R.id.rv_specific_products);
        }
    }
}
