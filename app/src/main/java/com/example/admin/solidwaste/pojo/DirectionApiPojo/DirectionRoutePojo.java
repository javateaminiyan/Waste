
package com.example.admin.solidwaste.pojo.DirectionApiPojo;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class DirectionRoutePojo {

    @SerializedName("geocoded_waypoints")
    private List<GeocodedWaypoint> mGeocodedWaypoints;
    @SerializedName("routes")
    private List<Route> mRoutes;
    @SerializedName("status")
    private String mStatus;

    public List<GeocodedWaypoint> getGeocodedWaypoints() {
        return mGeocodedWaypoints;
    }

    public void setGeocodedWaypoints(List<GeocodedWaypoint> geocodedWaypoints) {
        mGeocodedWaypoints = geocodedWaypoints;
    }

    public List<Route> getRoutes() {
        return mRoutes;
    }

    public void setRoutes(List<Route> routes) {
        mRoutes = routes;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
