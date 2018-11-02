
package com.example.admin.solidwaste.pojo.MapPlacedetailsPojo;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class PlaceDetailsMapPojo {

    @SerializedName("html_attributions")
    private List<Object> mHtmlAttributions;
    @SerializedName("result")
    private Result mResult;
    @SerializedName("status")
    private String mStatus;

    public List<Object> getHtmlAttributions() {
        return mHtmlAttributions;
    }

    public Result getResult() {
        return mResult;
    }

    public String getStatus() {
        return mStatus;
    }

    public static class Builder {

        private List<Object> mHtmlAttributions;
        private Result mResult;
        private String mStatus;

        public PlaceDetailsMapPojo.Builder withHtmlAttributions(List<Object> htmlAttributions) {
            mHtmlAttributions = htmlAttributions;
            return this;
        }

        public PlaceDetailsMapPojo.Builder withResult(Result result) {
            mResult = result;
            return this;
        }

        public PlaceDetailsMapPojo.Builder withStatus(String status) {
            mStatus = status;
            return this;
        }

        public PlaceDetailsMapPojo build() {
            PlaceDetailsMapPojo placeDetailsMapPojo = new PlaceDetailsMapPojo();
            placeDetailsMapPojo.mHtmlAttributions = mHtmlAttributions;
            placeDetailsMapPojo.mResult = mResult;
            placeDetailsMapPojo.mStatus = mStatus;
            return placeDetailsMapPojo;
        }

    }

}
