package com.example.admin.solidwaste.pojo;

public class SendOTPResponse {
    private String address;
    private String city;
    private String panno;
    private String mobile;
    private String gstno;
    private String name_of_shop;
    private String typeofuser;
    private String upino;
    private int userid;
    private String password;
    private String datetime;
    private String firebaseid;
    private String name;
    private String email;
    private String latlng;
    private String status;

    public SendOTPResponse() {
    }

    public SendOTPResponse(String address, String city, String panno, String mobile, String gstno, String name_of_shop, String typeofuser, String upino, int userid, String password, String datetime, String firebaseid, String name, String email, String latlng, String status) {

        this.address = address;
        this.city = city;
        this.panno = panno;
        this.mobile = mobile;
        this.gstno = gstno;
        this.name_of_shop = name_of_shop;
        this.typeofuser = typeofuser;
        this.upino = upino;
        this.userid = userid;
        this.password = password;
        this.datetime = datetime;
        this.firebaseid = firebaseid;
        this.name = name;
        this.email = email;
        this.latlng = latlng;
        this.status = status;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPanno() {
        return this.panno;
    }

    public void setPanno(String panno) {
        this.panno = panno;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGstno() {
        return this.gstno;
    }

    public void setGstno(String gstno) {
        this.gstno = gstno;
    }

    public String getName_of_shop() {
        return this.name_of_shop;
    }

    public void setName_of_shop(String name_of_shop) {
        this.name_of_shop = name_of_shop;
    }

    public String getTypeofuser() {
        return this.typeofuser;
    }

    public void setTypeofuser(String typeofuser) {
        this.typeofuser = typeofuser;
    }

    public String getUpino() {
        return this.upino;
    }

    public void setUpino(String upino) {
        this.upino = upino;
    }

    public int getUserid() {
        return this.userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatetime() {
        return this.datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getFirebaseid() {
        return this.firebaseid;
    }

    public void setFirebaseid(String firebaseid) {
        this.firebaseid = firebaseid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLatlng() {
        return this.latlng;
    }

    public void setLatlng(String latlng) {
        this.latlng = latlng;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
