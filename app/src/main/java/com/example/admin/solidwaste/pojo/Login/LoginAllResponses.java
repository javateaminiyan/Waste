package com.example.admin.solidwaste.pojo.Login;

public class LoginAllResponses {

    public int userid ;
    public String name;
    public String email;
    public String mobile ;
    public String password;
    public String city ;
    public String address ;
    public String firebaseid ;
    public String typeofuser ;
    public String latlng ;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirebaseid() {
        return firebaseid;
    }

    public void setFirebaseid(String firebaseid) {
        this.firebaseid = firebaseid;
    }

    public String getTypeofuser() {
        return typeofuser;
    }

    public void setTypeofuser(String typeofuser) {
        this.typeofuser = typeofuser;
    }

    public String getLatlng() {
        return latlng;
    }

    public void setLatlng(String latlng) {
        this.latlng = latlng;
    }

    public String getName_of_shop() {
        return name_of_shop;
    }

    public void setName_of_shop(String name_of_shop) {
        this.name_of_shop = name_of_shop;
    }

    public String getGstno() {
        return gstno;
    }

    public void setGstno(String gstno) {
        this.gstno = gstno;
    }

    public String getUpino() {
        return upino;
    }

    public void setUpino(String upino) {
        this.upino = upino;
    }

    public String getPanno() {
        return panno;
    }

    public void setPanno(String panno) {
        this.panno = panno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public LoginAllResponses() {
    }

    public LoginAllResponses(int userid, String name, String email, String mobile, String password, String city, String address, String firebaseid, String typeofuser, String latlng, String name_of_shop, String gstno, String upino, String panno, String status, String datetime) {
        this.userid = userid;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.city = city;
        this.address = address;
        this.firebaseid = firebaseid;
        this.typeofuser = typeofuser;
        this.latlng = latlng;
        this.name_of_shop = name_of_shop;
        this.gstno = gstno;
        this.upino = upino;
        this.panno = panno;
        this.status = status;
        this.datetime = datetime;
    }

    public String name_of_shop;
    public String gstno ;
    public String upino ;
    public String panno ;
    public String status;
    public String datetime ;
}
