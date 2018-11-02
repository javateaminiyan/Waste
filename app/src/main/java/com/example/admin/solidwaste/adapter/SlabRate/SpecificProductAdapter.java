package com.example.admin.solidwaste.adapter.SlabRate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.solidwaste.R;
import com.example.admin.solidwaste.pojo.SlabRatePojo.SlabDetails;

import java.util.List;

/**
 * Created by Admin on 25-09-2018.
 */

public class SpecificProductAdapter extends RecyclerView.Adapter<SpecificProductAdapter.ViewHolder>{
    List<SlabDetails> slabDetailsList;
    Context context;

    public SpecificProductAdapter(List<SlabDetails> slabDetailsList, Context context) {
        this.slabDetailsList = slabDetailsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slab_table_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.e("pos","pos"+position);
        Log.e("sno",""+slabDetailsList.get(position).getSno()+"");
        holder.tv_sno.setText(slabDetailsList.get(position).getSno()+"");
        holder.tv_pricerange.setText(slabDetailsList.get(position).getPriceRange());
        holder.tv_price.setText(slabDetailsList.get(position).getSubsriptioncharge());
    }

    @Override
    public int getItemCount() {
        return slabDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_sno,tv_pricerange,tv_price;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_sno = (TextView)itemView.findViewById(R.id.tv_sno);
            tv_pricerange = (TextView)itemView.findViewById(R.id.tv_pricerange);
            tv_price = (TextView)itemView.findViewById(R.id.tv_price);
        }
    }
}
