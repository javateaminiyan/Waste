package com.example.admin.solidwaste.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.solidwaste.Interface.GetProductContract;
import com.example.admin.solidwaste.Interface.IShowDialogContract;
import com.example.admin.solidwaste.R;
import com.example.admin.solidwaste.activity.NewProductActivity;
import com.example.admin.solidwaste.pojo.RegisteredProduct.GetProductResponseResponseResponse;
import com.vatsal.imagezoomer.ImageZoomButton;
import com.vatsal.imagezoomer.ZoomAnimation;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> implements Filterable, GetProductContract.view {

    List<GetProductResponseResponseResponse> productListFiltered;
    List<GetProductResponseResponseResponse> productList;
    Context context;

   IShowDialogContract productAdapter;

    ZoomAnimation zoomAnimation;



    public ProductAdapter(List<GetProductResponseResponseResponse> productList, Context context, IShowDialogContract productAdapter) {
        this.productList = productList;
        this.context = context;
        this.productListFiltered = productList;
        this.productAdapter =productAdapter;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reg_products_item, parent, false);
        ProductHolder productHolder = new ProductHolder(view);
        return productHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, final int position) {

        holder.tvProductName.setText("Product :" + productList.get(position).getProductname());
        holder.tvProductPrice.setText("Product Price :  \u20B9 " + productList.get(position).getProduct_cost() + "/" + productList.get(position).getProductunit());
        holder.tvType.setText("Type :" + productList.get(position).getType());
        holder.tvColor.setText("Color :" + productList.get(position).getColor());
        holder.tvGrade.setText("Grade :" + productList.get(position).getGrade());


        if (productList.get(position).getImageupload() == null) {
            Glide.with(context)
                    .load("http://via.placeholder.com/300.png")
                    .into(holder.ivProductImage);
        } else {
            Glide.with(context)
                    .load(productList.get(position).getImageupload())
                    .into(holder.ivProductImage);
        }


        holder.ivEditProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(context, NewProductActivity.class);
                i.putExtra("productname",productList.get(position).getProductname());
                i.putExtra("cost",productList.get(position).getProduct_cost());
                i.putExtra("userid",productList.get(position).getUserid());
                i.putExtra("productunit",productList.get(position).getProductunit());
                i.putExtra("pro_id",productList.get(position).getProduct_id());
                i.putExtra("product_description",productList.get(position).getProductdescription());
                i.putExtra("type",productList.get(position).getType());
                i.putExtra("color",productList.get(position).getColor());
                i.putExtra("grade",productList.get(position).getGrade());
                context.startActivity(i);

Log.e("reeeeeee",""+productList.get(position).getProduct_id());
            }
        });

        holder.ivDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productAdapter.showDeleteDialog(position,"delete");

            }
        });


        holder.ivProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (zoomAnimation!=null){
                    Activity activity = (Activity) context;
                    zoomAnimation = new ZoomAnimation(activity);
                    zoomAnimation.zoom(v, 300);

                }
//                ViewProductActivity viewProductActivity = (ViewProductActivity)context;
//                viewProductActivity.zoomMethod(v,200);

            }
        });


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String charString = constraint.toString();

                if (charString.isEmpty()) {
                    productList = productListFiltered;
                } else {
                    List<GetProductResponseResponseResponse> filteredList = new ArrayList<>();

                    for (GetProductResponseResponseResponse row : productList) {
                        if (row.getProductname().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    productList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = productList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                productList = (ArrayList<GetProductResponseResponseResponse>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void loadData(List<GetProductResponseResponseResponse> getProductResponseResponseResult) {

    }

    @Override
    public void deleteSuccess(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteFailure(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }

    public class ProductHolder extends RecyclerView.ViewHolder {

        ImageView  ivEditProduct, ivDeleteProduct;
        ImageZoomButton ivProductImage;

        TextView tvProductName, tvProductPrice, tvType, tvColor, tvGrade;

        public ProductHolder(View itemView) {
            super(itemView);
            ivProductImage = (ImageZoomButton) itemView.findViewById(R.id.ivProduct);
            ivEditProduct = (ImageView) itemView.findViewById(R.id.im_edit);
            ivDeleteProduct = (ImageView) itemView.findViewById(R.id.im_delete);
            tvProductPrice = (TextView) itemView.findViewById(R.id.tvProductPrice);
            tvProductName = (TextView) itemView.findViewById(R.id.tvProductName);
            tvType = (TextView) itemView.findViewById(R.id.tvType);
            tvColor = (TextView) itemView.findViewById(R.id.tvColor);
            tvGrade = (TextView) itemView.findViewById(R.id.tvGrade);

        }
    }




}
