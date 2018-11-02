
package com.example.admin.solidwaste.pojo.MapPlacedetailsPojo;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Period {

    @SerializedName("close")
    private Close mClose;
    @SerializedName("open")
    private Open mOpen;

    public Close getClose() {
        return mClose;
    }

    public Open getOpen() {
        return mOpen;
    }

    public static class Builder {

        private Close mClose;
        private Open mOpen;

        public Period.Builder withClose(Close close) {
            mClose = close;
            return this;
        }

        public Period.Builder withOpen(Open open) {
            mOpen = open;
            return this;
        }

        public Period build() {
            Period period = new Period();
            period.mClose = mClose;
            period.mOpen = mOpen;
            return period;
        }

    }

}
