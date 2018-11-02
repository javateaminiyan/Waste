package com.example.admin.solidwaste.pojo.SlabRatePojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SlabDetails {

    @SerializedName("Sno")
    @Expose
    private Integer sno;
    @SerializedName("price_range")
    @Expose
    private String priceRange;
    @SerializedName("subsriptioncharge")
    @Expose
    private String subsriptioncharge;

    public Integer getSno() {
        return sno;
    }

    public void setSno(Integer sno) {
        this.sno = sno;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public String getSubsriptioncharge() {
        return subsriptioncharge;
    }

    public void setSubsriptioncharge(String subsriptioncharge) {
        this.subsriptioncharge = subsriptioncharge;
    }

}