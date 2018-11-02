package com.example.admin.solidwaste.adapter;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.solidwaste.R;
import com.example.admin.solidwaste.RoomDB.NotificationDatabase;
import com.example.admin.solidwaste.activity.OrderDetailsActivity;
import com.example.admin.solidwaste.pojo.NotificationBean;
import com.example.admin.solidwaste.pojo.ProductOrderRequestPojo.MyRequestResponseResponse;
import com.example.admin.solidwaste.utils.CommonHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<NotificationBean> beanList;
    private List<String> selectedLocationList=new ArrayList<>();
    Context context;
    String mType;

    public NotificationAdapter(List<NotificationBean> beanList, Context context,String mType) {
        this.beanList = beanList;
        this.context = context;
        this.mType = mType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row_layout, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        Log.e("rremovvvv",""+beanList.get(position).isIsviewd());

        holder.text1.setText(beanList.get(position).getMsg());
        holder.tv_time.setText(parseDateToddMMyyyy(beanList.get(position).getTime()));



//        holder.img_notification_icon.setImageDrawable(context.getResources().getDrawable(beanList.get(position).getImagid()));

        if(beanList.get(position).isIsviewd()){
      //  if(!beanList.get(position).isIsviewd()){
            holder.lay_item.setBackgroundColor(context.getResources().getColor(R.color.transparent));
            holder.img_notification_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_notification_not_visited));

        }else{
            holder.lay_item.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.img_notification_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_notification_visited));

        }
        holder.lay_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                NotificationDatabase database = Room.databaseBuilder(context, NotificationDatabase.class, CommonHelper.ROOM_DBNAME)
                        .fallbackToDestructiveMigration()
                        .build();
              database.notificationDao().UpdateDate(true,beanList.get(position).getUid());

              Log.e("vghv=>",beanList.get(position).getOrderid());

             /* if(mType.equalsIgnoreCase("user")){
                  Log.e("vghv=>",beanList.get(position).getOrderid()+"user");
                  Intent i = new Intent(context, OrderDetailsActivity.class);

                  i.putExtra("products", new MyRequestResponseResponse(beanList.get(position).getProductCost(),
                          beanList.get(position).getAddress(),
                          beanList.get(position).getQuantity(),
                          beanList.get(position).getProductid(),
                          beanList.get(position).getOrderstatus(),
                          Integer.parseInt(beanList.get(position).getOrderid()),
                          beanList.get(position).getMobile(),
                          beanList.get(position).getOrderapproval(),
                          beanList.get(position).getPickupdate(),
                          beanList.get(position).getOrdercashtype(),
                          beanList.get(position).getUserid(),
                          beanList.get(position).getDatetime(),
                          beanList.get(position).getUnit(),
                          beanList.get(position).getFirebaseid(),
                          beanList.get(position).getPrice(),
                          beanList.get(position).getProductname(),
                          beanList.get(position).getNameofuser(),
                          beanList.get(position).getEmail()));
                  context.startActivity(i);
              }else*/
             if(mType.equalsIgnoreCase("merchant")){
                  Log.e("vghv=>",beanList.get(position).getDatetime()+"merchant");
                  Log.e("vghv=>",beanList.get(position).getUserid()+" getUserid");
                  Log.e("vghv=>",beanList.get(position).getMerchantId()+" getMerchantId");
                  Log.e("vghv=>",beanList.get(position).getImageUrl()+" getImageUrl");
                  Intent i = new Intent(context, OrderDetailsActivity.class);

                  i.putExtra("products", new MyRequestResponseResponse(beanList.get(position).getProductCost(),
                          beanList.get(position).getAddress(),
                          beanList.get(position).getQuantity(),
                          beanList.get(position).getProductid(),
                          beanList.get(position).getOrderstatus(),
                          Integer.parseInt(beanList.get(position).getOrderid()),
                          beanList.get(position).getMobile(),
                          beanList.get(position).getOrderapproval(),
                          beanList.get(position).getPickupdate(),
                          beanList.get(position).getOrdercashtype(),
                          beanList.get(position).getUserid(),
                          beanList.get(position).getDatetime(),
                          beanList.get(position).getUnit(),
                          beanList.get(position).getFirebaseid(),
                          beanList.get(position).getPrice(),
                          beanList.get(position).getProductname(),
                          beanList.get(position).getNameofuser(),
                          beanList.get(position).getEmail(),
                          beanList.get(position).getImageUrl()
                          ));
                  context.startActivity(i);
              }



                    }
                }).start();

                if(!beanList.get(position).isIsviewd()){
                    beanList.get(position).setIsviewd(true);
                    holder.lay_item.setBackgroundColor(context.getResources().getColor(R.color.white));
                    holder.img_notification_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_notification_visited));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return beanList.size();
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

    public List<NotificationBean> getList() {
        return beanList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text1,tv_time;
        LinearLayout lay_item;
        ImageView img_notification_icon;



        public ViewHolder(View itemView) {
            super(itemView);
            text1 = (TextView) itemView.findViewById(R.id.text1);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            lay_item = (LinearLayout) itemView.findViewById(R.id.lay_item);
            img_notification_icon = (ImageView) itemView.findViewById(R.id.img_notification_icon);
        }
    }
}
