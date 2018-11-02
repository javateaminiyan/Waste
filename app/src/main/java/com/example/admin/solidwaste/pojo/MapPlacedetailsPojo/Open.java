
package com.example.admin.solidwaste.pojo.MapPlacedetailsPojo;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Open {

    @SerializedName("day")
    private Long mDay;
    @SerializedName("time")
    private String mTime;

    public Long getDay() {
        return mDay;
    }

    public String getTime() {
        return mTime;
    }

    public static class Builder {

        private Long mDay;
        private String mTime;

        public Open.Builder withDay(Long day) {
            mDay = day;
            return this;
        }

        public Open.Builder withTime(String time) {
            mTime = time;
            return this;
        }

        public Open build() {
            Open open = new Open();
            open.mDay = mDay;
            open.mTime = mTime;
            return open;
        }

    }

}
