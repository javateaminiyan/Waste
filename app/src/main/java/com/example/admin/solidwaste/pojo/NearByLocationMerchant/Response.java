
package com.example.admin.solidwaste.pojo.NearByLocationMerchant;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Response {

    @SerializedName("address")
    private String mAddress;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("latlng")
    private String mLatlng;
    @SerializedName("mobile")
    private String mMobile;
    @SerializedName("name_of_shop")
    private String mNameOfShop;
    @SerializedName("Response")
    private List<Response> mResponse;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("StatusCode")
    private Long mStatusCode;
    @SerializedName("StatusMessage")
    private String mStatusMessage;
    @SerializedName("typeofuser")
    private String mTypeofuser;
    @SerializedName("userid")
    private Long mUserid;

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getLatlng() {
        return mLatlng;
    }

    public void setLatlng(String latlng) {
        mLatlng = latlng;
    }

    public String getMobile() {
        return mMobile;
    }

    public void setMobile(String mobile) {
        mMobile = mobile;
    }

    public String getNameOfShop() {
        return mNameOfShop;
    }

    public void setNameOfShop(String nameOfShop) {
        mNameOfShop = nameOfShop;
    }

    public List<Response> getResponse() {
        return mResponse;
    }

    public void setResponse(List<Response> response) {
        mResponse = response;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public Long getStatusCode() {
        return mStatusCode;
    }

    public void setStatusCode(Long statusCode) {
        mStatusCode = statusCode;
    }

    public String getStatusMessage() {
        return mStatusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        mStatusMessage = statusMessage;
    }

    public String getTypeofuser() {
        return mTypeofuser;
    }

    public void setTypeofuser(String typeofuser) {
        mTypeofuser = typeofuser;
    }

    public Long getUserid() {
        return mUserid;
    }

    public void setUserid(Long userid) {
        mUserid = userid;
    }

}
