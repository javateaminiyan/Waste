package com.example.admin.solidwaste.adapter.UserProduct;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.solidwaste.Interface.IShowDialogContract;
import com.example.admin.solidwaste.R;
import com.example.admin.solidwaste.pojo.UserProductPojo.UserProductResponseResponse;
import com.vatsal.imagezoomer.ImageZoomButton;
import com.vatsal.imagezoomer.ZoomAnimation;

import java.util.List;

public class UserProductAdapter extends RecyclerView.Adapter<UserProductAdapter.UserProductHolder> {

    List<UserProductResponseResponse> productResponseResultList, productResponseResutFiltered;
    Context mContext;

    IShowDialogContract iShowDialog;

    ZoomAnimation zoomAnimation;

    boolean isToggle;

    public UserProductAdapter(Context context, List<UserProductResponseResponse> userProductResponseResults, IShowDialogContract iShowDialog) {
        this.mContext = context;
        this.productResponseResultList = userProductResponseResults;
        this.iShowDialog = iShowDialog;
        this.productResponseResutFiltered = userProductResponseResults;
    }


    @NonNull
    @Override
    public UserProductAdapter.UserProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_product_item, parent, false);

        UserProductHolder userProductHolder = new UserProductHolder(view);

        return userProductHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final UserProductAdapter.UserProductHolder holder, final int position) {


        holder.tvColor.setText("Color :" + productResponseResultList.get(position).getColor());
        holder.tvTradeAddress.setText(productResponseResultList.get(position).getAddress());
        holder.tvGade.setText("Grade :" + productResponseResultList.get(position).getGrade());
        holder.tvType.setText("Type :" + productResponseResultList.get(position).getType());
        holder.tvPrice.setText("\u20B9 " + productResponseResultList.get(position).getProduct_cost() + "/" + productResponseResultList.get(position).getProductunit());

        holder.tvTradeName.setText(productResponseResultList.get(position).getName_of_shop());


        holder.tvProductName.setText("Product Name: " + productResponseResultList.get(position).getProductname());

        Log.e("dateeeee", "" + productResponseResultList.get(position).getDatetime());


        holder.imProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (productResponseResultList.get(position).getImageupload() == null) {
                    Glide.with(mContext)
                            .load("http://via.placeholder.com/300.png")
                            .into(holder.imProduct);
                } else {
                    Glide.with(mContext)
                            .load(productResponseResultList.get(position).getImageupload())
                            .into(holder.imProduct);
                }
            }
        });


        if (productResponseResultList.get(position).getImageupload() == null) {
            Glide.with(mContext)
                    .load("http://via.placeholder.com/300.png")
                    .into(holder.imProduct);
        } else {
            Glide.with(mContext)
                    .load(productResponseResultList.get(position).getImageupload())
                    .into(holder.imProduct);
        }

        holder.tvViewMobileNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (productResponseResultList.size() > 0) {
                    holder.tvViewMobileNo.setText("Call " + productResponseResultList.get(position).getMobile());

                    isToggle = true;
                }
                if (isToggle) {
                    if (holder.tvViewMobileNo.getText().toString().contains("Call")) {


                        iShowDialog.calIntent(productResponseResultList.get(position).getMobile());
                    }
                }


            }
        });


        holder.tvRequestSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iShowDialog.showDialogd(position, productResponseResultList);


            }


        });

        holder.imProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (zoomAnimation != null) {
                    Activity activity = (Activity) mContext;
                    zoomAnimation = new ZoomAnimation(activity);
                    zoomAnimation.zoom(v, 300);

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return productResponseResultList.size();
    }

//    @Override
//    public Filter getFilter() {
//        {
//            return new Filter() {
//                @Override
//                protected FilterResults performFiltering(CharSequence constraint) {
//                    String charString = constraint.toString();
//                    Filter.FilterResults filterResults = new Filter.FilterResults();
//
//                    if (productResponseResutFiltered.size() > 0) {
//                        if (charString.isEmpty()) {
//                            productResponseResultList = productResponseResutFiltered;
//                        } else {
//                            List<UserProductResponseResponse> filteredList = new ArrayList<>();
//
//                            for (UserProductResponseResponse row : productResponseResultList) {
//                                if (row.getColor().toLowerCase().contains(charString.toLowerCase()) ||
//                                        row.getCity().toLowerCase().contains(charString.toLowerCase()) ||
//                                        row.getName_of_shop().toLowerCase().contains(charString.toLowerCase())) {
//                                    filteredList.add(row);
//                                }
//                            }
//                            productResponseResultList = filteredList;
//                        }
//                        filterResults.values = productResponseResultList;
//                    }
//
//                    return filterResults;
//
//                }
//
//
//                @Override
//                protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
//
//                    productResponseResultList = (ArrayList<UserProductResponseResponse>) results.values;
//                    notifyDataSetChanged();
//                }
//            };
//
//        }
//    }


    public class UserProductHolder extends RecyclerView.ViewHolder {

        TextView tvType, tvGade, tvColor, tvPrice, tvTradeName, tvTradeAddress, tvProductName;
        TextView tvRequestSupplier, tvViewMobileNo;
        ImageZoomButton imProduct;


        public UserProductHolder(View itemView) {
            super(itemView);

            tvType = (TextView) itemView.findViewById(R.id.tvType);
            tvGade = (TextView) itemView.findViewById(R.id.tvGrade);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            tvColor = (TextView) itemView.findViewById(R.id.tvColor);
            tvTradeName = (TextView) itemView.findViewById(R.id.tvTradeName);
            tvTradeAddress = (TextView) itemView.findViewById(R.id.tvTradeAddress);
            tvProductName = (TextView) itemView.findViewById(R.id.tvProductName);

            tvRequestSupplier = (TextView) itemView.findViewById(R.id.tvRequestSupplier);
            tvViewMobileNo = (TextView) itemView.findViewById(R.id.tvViewMobile);

            imProduct = (ImageZoomButton) itemView.findViewById(R.id.im_productImage);


        }
    }


}
