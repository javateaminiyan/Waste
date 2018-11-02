
package com.example.admin.solidwaste.pojo.MapPojo;

import java.util.List;

import javax.annotation.Generated;

import com.example.admin.solidwaste.pojo.MapPlacedetailsPojo.OpeningHours;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Result {

    @SerializedName("geometry")
    private Geometry mGeometry;
    @SerializedName("icon")
    private String mIcon;
    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("photos")
    private List<Photo> mPhotos;
    @SerializedName("place_id")
    private String mPlaceId;
    @SerializedName("reference")
    private String mReference;
    @SerializedName("scope")
    private String mScope;
    @SerializedName("types")
    private List<String> mTypes;

    public OpeningHours getmOpeningHours() {
        return mOpeningHours;
    }

    public void setmOpeningHours(OpeningHours mOpeningHours) {
        this.mOpeningHours = mOpeningHours;
    }

    @SerializedName("vicinity")

    private String mVicinity;
    private String Rating;
private OpeningHours mOpeningHours;
    public Geometry getmGeometry() {
        return mGeometry;
    }

    public void setmGeometry(Geometry mGeometry) {
        this.mGeometry = mGeometry;
    }

    public String getmIcon() {
        return mIcon;
    }

    public void setmIcon(String mIcon) {
        this.mIcon = mIcon;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public List<Photo> getmPhotos() {
        return mPhotos;
    }

    public void setmPhotos(List<Photo> mPhotos) {
        this.mPhotos = mPhotos;
    }

    public String getmPlaceId() {
        return mPlaceId;
    }

    public void setmPlaceId(String mPlaceId) {
        this.mPlaceId = mPlaceId;
    }

    public String getmReference() {
        return mReference;
    }

    public void setmReference(String mReference) {
        this.mReference = mReference;
    }

    public String getmScope() {
        return mScope;
    }

    public void setmScope(String mScope) {
        this.mScope = mScope;
    }

    public List<String> getmTypes() {
        return mTypes;
    }

    public void setmTypes(List<String> mTypes) {
        this.mTypes = mTypes;
    }

    public String getmVicinity() {
        return mVicinity;
    }

    public void setmVicinity(String mVicinity) {
        this.mVicinity = mVicinity;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public Geometry getGeometry() {
        return mGeometry;
    }

    public void setGeometry(Geometry geometry) {
        mGeometry = geometry;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<Photo> getPhotos() {
        return mPhotos;
    }

    public void setPhotos(List<Photo> photos) {
        mPhotos = photos;
    }

    public String getPlaceId() {
        return mPlaceId;
    }

    public void setPlaceId(String placeId) {
        mPlaceId = placeId;
    }

    public String getReference() {
        return mReference;
    }

    public void setReference(String reference) {
        mReference = reference;
    }

    public String getScope() {
        return mScope;
    }

    public void setScope(String scope) {
        mScope = scope;
    }

    public List<String> getTypes() {
        return mTypes;
    }

    public void setTypes(List<String> types) {
        mTypes = types;
    }

    public String getVicinity() {
        return mVicinity;
    }

    public void setVicinity(String vicinity) {
        mVicinity = vicinity;
    }

}
