
package com.example.admin.solidwaste.pojo.MapPlacedetailsPojo;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Close {

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

        public Close.Builder withDay(Long day) {
            mDay = day;
            return this;
        }

        public Close.Builder withTime(String time) {
            mTime = time;
            return this;
        }

        public Close build() {
            Close close = new Close();
            close.mDay = mDay;
            close.mTime = mTime;
            return close;
        }

    }

}
