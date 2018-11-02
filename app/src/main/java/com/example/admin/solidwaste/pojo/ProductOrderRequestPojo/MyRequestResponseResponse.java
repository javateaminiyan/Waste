package com.example.admin.solidwaste.pojo.ProductOrderRequestPojo;

import android.os.Parcel;
import android.os.Parcelable;

public class MyRequestResponseResponse  implements Parcelable{

    public static final Creator CREATOR = new Creator() {
        public MyRequestResponseResponse createFromParcel(Parcel in) {
            return new MyRequestResponseResponse(in);
        }

        public MyRequestResponseResponse[] newArray(int size) {
            return new MyRequestResponseResponse[size];
        }
    };


    private String productcost;
    private String address;
    private String quantity;
    private String productid;
    private String orderstatus;
    private int orderid;
    private String mobile;
    private String orderapproval;
    private String pickupdate;
    private String ordercashtype;
    private String userid;
    private String datetime;
    private String unit;
    private String firebaseid;
    private String price;
    private String productname;
    private String nameofuser;
    private String email;
    private String productimage;

    public MyRequestResponseResponse(String productcost, String address, String quantity, String productid, String orderstatus, int orderid, String mobile, String orderapproval, String pickupdate, String ordercashtype, String userid, String datetime, String unit, String firebaseid, String price, String productname, String nameofuser, String email,String productimage) {
        this.productcost = productcost;
        this.address = address;
        this.quantity = quantity;
        this.productid = productid;
        this.orderstatus = orderstatus;
        this.orderid = orderid;
        this.mobile = mobile;
        this.orderapproval = orderapproval;
        this.pickupdate = pickupdate;
        this.ordercashtype = ordercashtype;
        this.userid = userid;
        this.datetime = datetime;
        this.unit = unit;
        this.firebaseid = firebaseid;
        this.price = price;
        this.productname = productname;
        this.nameofuser = nameofuser;
        this.email = email;
        this.productimage = productimage;
    }

    public MyRequestResponseResponse(Parcel in) {

        this.productcost = in.readString();
        this.address=in.readString();
        this.quantity=in.readString();
        this.productid=in.readString();
        this.orderstatus=in.readString();
        this.orderid=in.readInt();
        this.mobile=in.readString();
        this.orderapproval=in.readString();
        this.pickupdate=in.readString();
        this.ordercashtype=in.readString();
        this.userid=in.readString();
        this.datetime=in.readString();
        this.unit=in.readString();
        this.firebaseid=in.readString();
        this.price=in.readString();
        this.productname=in.readString();
        this.nameofuser=in.readString();
        this.email=in.readString();
        this.productimage = in.readString();
    }

    public String getproductimage() {
        return productimage;
    }

    public void setproductimage(String productimage) {
        this.productimage = productimage;
    }

    public String getProductcost() {
        return this.productcost;
    }

    public void setProductcost(String productcost) {
        this.productcost = productcost;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQuantity() {
        return this.quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProductid() {
        return this.productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getOrderstatus() {
        return this.orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public int getOrderid() {
        return this.orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrderapproval() {
        return this.orderapproval;
    }

    public void setOrderapproval(String orderapproval) {
        this.orderapproval = orderapproval;
    }

    public String getPickupdate() {
        return this.pickupdate;
    }

    public void setPickupdate(String pickupdate) {
        this.pickupdate = pickupdate;
    }

    public String getOrdercashtype() {
        return this.ordercashtype;
    }

    public void setOrdercashtype(String ordercashtype) {
        this.ordercashtype = ordercashtype;
    }

    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDatetime() {
        return this.datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getFirebaseid() {
        return this.firebaseid;
    }

    public void setFirebaseid(String firebaseid) {
        this.firebaseid = firebaseid;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductname() {
        return this.productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getNameofuser() {
        return this.nameofuser;
    }

    public void setNameofuser(String nameofuser) {
        this.nameofuser = nameofuser;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.productcost);
        dest.writeString(this.address);
        dest.writeString(this.quantity);
        dest.writeString(this.productid);
        dest.writeString(this.orderstatus);
        dest.writeInt(this.orderid);
        dest.writeString(this.mobile);
        dest.writeString(this.orderapproval);
        dest.writeString(this.pickupdate);
        dest.writeString(this.ordercashtype);
        dest.writeString(this.userid);
        dest.writeString(this.datetime);
        dest.writeString(this.unit);
        dest.writeString(this.firebaseid);
        dest.writeString(this.price);
        dest.writeString(this.productname);
        dest.writeString(this.nameofuser);
        dest.writeString(this.email);
        dest.writeString(this.productimage);

    }
}
