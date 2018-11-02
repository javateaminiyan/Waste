
package com.example.admin.solidwaste.pojo.MapPlacedetailsPojo;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AddressComponent {

    @SerializedName("long_name")
    private String mLongName;
    @SerializedName("short_name")
    private String mShortName;
    @SerializedName("types")
    private List<String> mTypes;

    public String getLongName() {
        return mLongName;
    }

    public String getShortName() {
        return mShortName;
    }

    public List<String> getTypes() {
        return mTypes;
    }

    public static class Builder {

        private String mLongName;
        private String mShortName;
        private List<String> mTypes;

        public AddressComponent.Builder withLongName(String longName) {
            mLongName = longName;
            return this;
        }

        public AddressComponent.Builder withShortName(String shortName) {
            mShortName = shortName;
            return this;
        }

        public AddressComponent.Builder withTypes(List<String> types) {
            mTypes = types;
            return this;
        }

        public AddressComponent build() {
            AddressComponent addressComponent = new AddressComponent();
            addressComponent.mLongName = mLongName;
            addressComponent.mShortName = mShortName;
            addressComponent.mTypes = mTypes;
            return addressComponent;
        }

    }

}
