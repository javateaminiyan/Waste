package com.example.admin.solidwaste.pojo.SlabRatePojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {

    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("productname")
    @Expose
    private String productname;
    @SerializedName("SlabDetails")
    @Expose
    private List<SlabDetails> slabDetails = null;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public List<SlabDetails> getSlabDetails() {
        return slabDetails;
    }

    public void setSlabDetails(List<SlabDetails> slabDetails) {
        this.slabDetails = slabDetails;
    }

}