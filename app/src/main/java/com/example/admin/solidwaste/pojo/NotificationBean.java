package com.example.admin.solidwaste.pojo;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.Intent;

import com.example.admin.solidwaste.activity.OrderDetailsActivity;

@Entity
public class NotificationBean {
    @PrimaryKey(autoGenerate = true)
    private int uid ;
    @ColumnInfo(name = "imageid")
    int imagid;
    @ColumnInfo(name = "msg")
    String msg;
    @ColumnInfo(name = "time")
    String time;
    @ColumnInfo(name="isViewed")
    boolean isviewd;
    @ColumnInfo(name = "userid")
    String userid;
    @ColumnInfo(name = "merchantId")
    String merchantId;
    @ColumnInfo(name = "imageUrl")
    String imageUrl;
    @ColumnInfo(name="productCost")
    String productCost;
    @ColumnInfo(name="Address")
    String Address;
    @ColumnInfo(name="Quantity")
    String Quantity;
    @ColumnInfo(name="Productid")
    String Productid;
    @ColumnInfo(name="Orderapproval")
    String Orderapproval;
    @ColumnInfo(name="Orderstatus")
    String Orderstatus;
    @ColumnInfo(name="Orderid")
    String Orderid;
    @ColumnInfo(name="Mobile")
    String Mobile;
    @ColumnInfo(name="Pickupdate")
    String Pickupdate;
    @ColumnInfo(name="Ordercashtype")
    String Ordercashtype;
    @ColumnInfo(name="Datetime")
    String Datetime;
    @ColumnInfo(name="Unit")
    String Unit;
    @ColumnInfo(name="Firebaseid")
    String Firebaseid;
    @ColumnInfo(name="Price")
    String Price;
    @ColumnInfo(name="Productname")
    String Productname;
    @ColumnInfo(name="Nameofuser")
    String Nameofuser;
    @ColumnInfo(name="Email")
    String Email;



    public NotificationBean( int imagid ,String msg, String time, boolean isviewd, String userid) {

        this.imagid = imagid;
        this.msg = msg;
        this.time = time;
        this.isviewd = isviewd;
        this.userid = userid;
    }
    public NotificationBean(int imagid, String msg, String time, boolean isviewd, String userid, String productCost, String address, String quantity, String productid, String orderapproval, String orderstatus, String orderid, String mobile, String pickupdate, String ordercashtype, String datetime, String unit, String firebaseid, String price, String productname, String nameofuser, String email, String merchantId, String imageUrl) {
        this.imagid = imagid;
        this.msg = msg;
        this.time = time;
        this.isviewd = isviewd;
        this.userid = userid;
        this.productCost = productCost;
        Address = address;
        Quantity = quantity;
        Productid = productid;
        Orderapproval = orderapproval;
        Orderstatus = orderstatus;
        Orderid = orderid;
        this.merchantId = merchantId;
        this.imageUrl = imageUrl;
        Mobile = mobile;
        Pickupdate = pickupdate;
        Ordercashtype = ordercashtype;
        Datetime = datetime;
        Unit = unit;
        Firebaseid = firebaseid;
        Price = price;
        Productname = productname;
        Nameofuser = nameofuser;
        Email = email;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public boolean isIsviewd() {
        return isviewd;
    }

    public void setIsviewd(boolean isviewd) {
        this.isviewd = isviewd;
    }

    public int getImagid() {
        return imagid;
    }

    public void setImagid(int imagid) {
        this.imagid = imagid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProductCost() {
        return productCost;
    }

    public void setProductCost(String productCost) {
        this.productCost = productCost;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getProductid() {
        return Productid;
    }

    public void setProductid(String productid) {
        Productid = productid;
    }

    public String getOrderapproval() {
        return Orderapproval;
    }

    public void setOrderapproval(String orderapproval) {
        Orderapproval = orderapproval;
    }

    public String getOrderstatus() {
        return Orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        Orderstatus = orderstatus;
    }

    public String getOrderid() {
        return Orderid;
    }

    public void setOrderid(String orderid) {
        Orderid = orderid;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getPickupdate() {
        return Pickupdate;
    }

    public void setPickupdate(String pickupdate) {
        Pickupdate = pickupdate;
    }

    public String getOrdercashtype() {
        return Ordercashtype;
    }

    public void setOrdercashtype(String ordercashtype) {
        Ordercashtype = ordercashtype;
    }

    public String getDatetime() {
        return Datetime;
    }

    public void setDatetime(String datetime) {
        Datetime = datetime;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getFirebaseid() {
        return Firebaseid;
    }

    public void setFirebaseid(String firebaseid) {
        Firebaseid = firebaseid;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getProductname() {
        return Productname;
    }

    public void setProductname(String productname) {
        Productname = productname;
    }

    public String getNameofuser() {
        return Nameofuser;
    }

    public void setNameofuser(String nameofuser) {
        Nameofuser = nameofuser;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
