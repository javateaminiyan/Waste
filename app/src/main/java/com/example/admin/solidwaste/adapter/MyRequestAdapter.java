package com.example.admin.solidwaste.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.admin.solidwaste.R;
import com.example.admin.solidwaste.activity.OrderDetailsActivity;
import com.example.admin.solidwaste.pojo.ProductOrderRequestPojo.MyRequestResponseResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyRequestAdapter extends RecyclerView.Adapter<MyRequestAdapter.ProductHolder> implements Filterable {

    List<MyRequestResponseResponse> productListFiltered;
    List<MyRequestResponseResponse> productList;
    Context context;
    String mType;

    public MyRequestAdapter(List<MyRequestResponseResponse> productList, Context context, String type) {
        this.productList = productList;
        this.context = context;
        this.productListFiltered = productList;
        this.mType = type;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.myorder_item, parent, false);
        ProductHolder productHolder = new ProductHolder(view);
        return productHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, final int position) {


        holder.tvProductName.setText("Product :" + productList.get(position).getProductname());
        holder.tvAddress.setText("Address : " + productList.get(position).getAddress());
        holder.tvName.setText("Name :" + productList.get(position).getNameofuser());
        holder.tvQty.setText("Total Qty :" + productList.get(position).getQuantity());
        holder.tvMobile.setText("Mobile No :" + productList.get(position).getMobile());
        holder.tvStatus.setText("Status  :" + productList.get(position).getOrderstatus());
        holder.tvOrderId.setText("OrderId : "+productList.get(position).getOrderid());

        Log.e("firebase=>user", productList.get(position).getUserid()+"");

        holder.cardView_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("dfbjhfb",mType+"");


                if(mType.equalsIgnoreCase("merchant")) {
                    Intent i = new Intent(context, OrderDetailsActivity.class);

                    i.putExtra("products", new MyRequestResponseResponse(productList.get(position).getProductcost(),
                            productList.get(position).getAddress(),
                            productList.get(position).getQuantity(),
                            productList.get(position).getProductid(),
                            productList.get(position).getOrderstatus(),
                            productList.get(position).getOrderid(),
                            productList.get(position).getMobile(),
                            productList.get(position).getOrderapproval(),
                            productList.get(position).getPickupdate(),
                            productList.get(position).getOrdercashtype(),
                            productList.get(position).getUserid(),
                            productList.get(position).getDatetime(),
                            productList.get(position).getUnit(),
                            productList.get(position).getFirebaseid(),
                            productList.get(position).getPrice(),
                            productList.get(position).getProductname(),
                            productList.get(position).getNameofuser(),
                            productList.get(position).getEmail(),
                            productList.get(position).getproductimage()));
                    context.startActivity(i);
                }
            }
        });


        Log.e("jkllllll", "" + productList.get(position).getDatetime());

        if (!productList.get(position).getDatetime().equalsIgnoreCase("")) {


            String newDate = parseDateToddMMyyyy(productList.get(position).getDatetime());


            Log.e("datttttt", "" + newDate);
            holder.tvMonth.setText(newDate);


        }


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
                    List<MyRequestResponseResponse> filteredList = new ArrayList<>();

                    for (MyRequestResponseResponse row : productList) {
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

                productList = (ArrayList<MyRequestResponseResponse>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ProductHolder extends RecyclerView.ViewHolder {

        TextView tvProductName, tvAddress, tvName, tvQty, tvMobile, tvStatus, tvMonth, tvYear, tvDay, tvTime,tvOrderId;
        CardView cardView_order;

        public ProductHolder(View itemView) {
            super(itemView);

            tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            tvProductName = (TextView) itemView.findViewById(R.id.tvProduct);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvQty = (TextView) itemView.findViewById(R.id.tvTotQty);
            tvMobile = (TextView) itemView.findViewById(R.id.tvMobileno);
            tvStatus = (TextView) itemView.findViewById(R.id.tvStatus);
            tvMonth = (TextView) itemView.findViewById(R.id.tvMonth);
            tvYear = (TextView) itemView.findViewById(R.id.tvYear);
            tvDay = (TextView) itemView.findViewById(R.id.tvDate);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            tvOrderId = (TextView)itemView.findViewById(R.id.tvOrderId);
            cardView_order = (CardView) itemView.findViewById(R.id.cv_order);
        }
    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        String outputPattern = "MMM \n \n  dd \n \n  yyyy \n\n   h:mm a";
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
}
