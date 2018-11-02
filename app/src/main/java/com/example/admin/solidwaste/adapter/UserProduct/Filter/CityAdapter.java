package com.example.admin.solidwaste.adapter.UserProduct.Filter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.admin.solidwaste.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 15-09-2017.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    private List<String> beanList;
    private List<String> selectedLocationList=new ArrayList<>();

    public CityAdapter(List<String> beanList) {
        this.beanList = beanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_spinner_layout, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.text.setText(beanList.get(position));
       holder.product_check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked){
                   selectedLocationList.add(beanList.get(position));
               }else{
                   if(selectedLocationList.contains(beanList.get(position))){
                       selectedLocationList.remove(beanList.get(position));
                   }
               }
           }
       });
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    public  void filterList(ArrayList<String> filterdNames) {
        this.beanList = filterdNames;
        notifyDataSetChanged();
    }
    public List<String> getSelectesProductList(){
        return this.selectedLocationList;
    }

    public List<String> getList() {
        return beanList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout product_lay;
        TextView text;
        CheckBox product_check_box;

        public ViewHolder(View itemView) {
            super(itemView);
            product_lay = (LinearLayout) itemView.findViewById(R.id.product_lay);
            text = (TextView) itemView.findViewById(R.id.text);
            product_check_box = (CheckBox) itemView.findViewById(R.id.product_check_box);
        }
    }
}
