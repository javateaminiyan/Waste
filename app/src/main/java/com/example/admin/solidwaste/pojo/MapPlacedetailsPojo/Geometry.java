
package com.example.admin.solidwaste.pojo.MapPlacedetailsPojo;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Geometry {

    @SerializedName("location")
    private Location mLocation;
    @SerializedName("viewport")
    private Viewport mViewport;

    public Location getLocation() {
        return mLocation;
    }

    public Viewport getViewport() {
        return mViewport;
    }

    public static class Builder {

        private Location mLocation;
        private Viewport mViewport;

        public Geometry.Builder withLocation(Location location) {
            mLocation = location;
            return this;
        }

        public Geometry.Builder withViewport(Viewport viewport) {
            mViewport = viewport;
            return this;
        }

        public Geometry build() {
            Geometry geometry = new Geometry();
            geometry.mLocation = mLocation;
            geometry.mViewport = mViewport;
            return geometry;
        }

    }

}
