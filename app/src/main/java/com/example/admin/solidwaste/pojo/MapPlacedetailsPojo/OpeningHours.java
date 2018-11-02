
package com.example.admin.solidwaste.pojo.MapPlacedetailsPojo;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class OpeningHours {

    @SerializedName("open_now")
    private Boolean mOpenNow;
    @SerializedName("periods")
    private List<Period> mPeriods;
    @SerializedName("weekday_text")
    private List<String> mWeekdayText;

    public Boolean getOpenNow() {
        return mOpenNow;
    }

    public List<Period> getPeriods() {
        return mPeriods;
    }

    public List<String> getWeekdayText() {
        return mWeekdayText;
    }

    public static class Builder {

        private Boolean mOpenNow;
        private List<Period> mPeriods;
        private List<String> mWeekdayText;

        public OpeningHours.Builder withOpenNow(Boolean openNow) {
            mOpenNow = openNow;
            return this;
        }

        public OpeningHours.Builder withPeriods(List<Period> periods) {
            mPeriods = periods;
            return this;
        }

        public OpeningHours.Builder withWeekdayText(List<String> weekdayText) {
            mWeekdayText = weekdayText;
            return this;
        }

        public OpeningHours build() {
            OpeningHours openingHours = new OpeningHours();
            openingHours.mOpenNow = mOpenNow;
            openingHours.mPeriods = mPeriods;
            openingHours.mWeekdayText = mWeekdayText;
            return openingHours;
        }

    }

}
