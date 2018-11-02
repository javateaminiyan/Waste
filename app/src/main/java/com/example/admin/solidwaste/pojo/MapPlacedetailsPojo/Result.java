
package com.example.admin.solidwaste.pojo.MapPlacedetailsPojo;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Result {

    @SerializedName("address_components")
    private List<AddressComponent> mAddressComponents;
    @SerializedName("adr_address")
    private String mAdrAddress;
    @SerializedName("formatted_address")
    private String mFormattedAddress;
    @SerializedName("formatted_phone_number")
    private String mFormattedPhoneNumber;
    @SerializedName("geometry")
    private Geometry mGeometry;
    @SerializedName("icon")
    private String mIcon;
    @SerializedName("id")
    private String mId;
    @SerializedName("international_phone_number")
    private String mInternationalPhoneNumber;
    @SerializedName("name")
    private String mName;
    @SerializedName("opening_hours")
    private OpeningHours mOpeningHours;
    @SerializedName("photos")
    private List<Photo> mPhotos;
    @SerializedName("place_id")
    private String mPlaceId;
    @SerializedName("plus_code")
    private PlusCode mPlusCode;
    @SerializedName("rating")
    private Double mRating;
    @SerializedName("reference")
    private String mReference;
    @SerializedName("reviews")
    private List<Review> mReviews;
    @SerializedName("scope")
    private String mScope;
    @SerializedName("types")
    private List<String> mTypes;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("utc_offset")
    private Long mUtcOffset;
    @SerializedName("vicinity")
    private String mVicinity;
    @SerializedName("website")
    private String mWebsite;

    public List<AddressComponent> getAddressComponents() {
        return mAddressComponents;
    }

    public String getAdrAddress() {
        return mAdrAddress;
    }

    public String getFormattedAddress() {
        return mFormattedAddress;
    }

    public String getFormattedPhoneNumber() {
        return mFormattedPhoneNumber;
    }

    public Geometry getGeometry() {
        return mGeometry;
    }

    public String getIcon() {
        return mIcon;
    }

    public String getId() {
        return mId;
    }

    public String getInternationalPhoneNumber() {
        return mInternationalPhoneNumber;
    }

    public String getName() {
        return mName;
    }

    public OpeningHours getOpeningHours() {
        return mOpeningHours;
    }

    public List<Photo> getPhotos() {
        return mPhotos;
    }

    public String getPlaceId() {
        return mPlaceId;
    }

    public PlusCode getPlusCode() {
        return mPlusCode;
    }

    public Double getRating() {
        return mRating;
    }

    public String getReference() {
        return mReference;
    }

    public List<Review> getReviews() {
        return mReviews;
    }

    public String getScope() {
        return mScope;
    }

    public List<String> getTypes() {
        return mTypes;
    }

    public String getUrl() {
        return mUrl;
    }

    public Long getUtcOffset() {
        return mUtcOffset;
    }

    public String getVicinity() {
        return mVicinity;
    }

    public String getWebsite() {
        return mWebsite;
    }

    public static class Builder {

        private List<AddressComponent> mAddressComponents;
        private String mAdrAddress;
        private String mFormattedAddress;
        private String mFormattedPhoneNumber;
        private Geometry mGeometry;
        private String mIcon;
        private String mId;
        private String mInternationalPhoneNumber;
        private String mName;
        private OpeningHours mOpeningHours;
        private List<Photo> mPhotos;
        private String mPlaceId;
        private PlusCode mPlusCode;
        private Double mRating;
        private String mReference;
        private List<Review> mReviews;
        private String mScope;
        private List<String> mTypes;
        private String mUrl;
        private Long mUtcOffset;
        private String mVicinity;
        private String mWebsite;

        public Result.Builder withAddressComponents(List<AddressComponent> addressComponents) {
            mAddressComponents = addressComponents;
            return this;
        }

        public Result.Builder withAdrAddress(String adrAddress) {
            mAdrAddress = adrAddress;
            return this;
        }

        public Result.Builder withFormattedAddress(String formattedAddress) {
            mFormattedAddress = formattedAddress;
            return this;
        }

        public Result.Builder withFormattedPhoneNumber(String formattedPhoneNumber) {
            mFormattedPhoneNumber = formattedPhoneNumber;
            return this;
        }

        public Result.Builder withGeometry(Geometry geometry) {
            mGeometry = geometry;
            return this;
        }

        public Result.Builder withIcon(String icon) {
            mIcon = icon;
            return this;
        }

        public Result.Builder withId(String id) {
            mId = id;
            return this;
        }

        public Result.Builder withInternationalPhoneNumber(String internationalPhoneNumber) {
            mInternationalPhoneNumber = internationalPhoneNumber;
            return this;
        }

        public Result.Builder withName(String name) {
            mName = name;
            return this;
        }

        public Result.Builder withOpeningHours(OpeningHours openingHours) {
            mOpeningHours = openingHours;
            return this;
        }

        public Result.Builder withPhotos(List<Photo> photos) {
            mPhotos = photos;
            return this;
        }

        public Result.Builder withPlaceId(String placeId) {
            mPlaceId = placeId;
            return this;
        }

        public Result.Builder withPlusCode(PlusCode plusCode) {
            mPlusCode = plusCode;
            return this;
        }

        public Result.Builder withRating(Double rating) {
            mRating = rating;
            return this;
        }

        public Result.Builder withReference(String reference) {
            mReference = reference;
            return this;
        }

        public Result.Builder withReviews(List<Review> reviews) {
            mReviews = reviews;
            return this;
        }

        public Result.Builder withScope(String scope) {
            mScope = scope;
            return this;
        }

        public Result.Builder withTypes(List<String> types) {
            mTypes = types;
            return this;
        }

        public Result.Builder withUrl(String url) {
            mUrl = url;
            return this;
        }

        public Result.Builder withUtcOffset(Long utcOffset) {
            mUtcOffset = utcOffset;
            return this;
        }

        public Result.Builder withVicinity(String vicinity) {
            mVicinity = vicinity;
            return this;
        }

        public Result.Builder withWebsite(String website) {
            mWebsite = website;
            return this;
        }

        public Result build() {
            Result result = new Result();
            result.mAddressComponents = mAddressComponents;
            result.mAdrAddress = mAdrAddress;
            result.mFormattedAddress = mFormattedAddress;
            result.mFormattedPhoneNumber = mFormattedPhoneNumber;
            result.mGeometry = mGeometry;
            result.mIcon = mIcon;
            result.mId = mId;
            result.mInternationalPhoneNumber = mInternationalPhoneNumber;
            result.mName = mName;
            result.mOpeningHours = mOpeningHours;
            result.mPhotos = mPhotos;
            result.mPlaceId = mPlaceId;
            result.mPlusCode = mPlusCode;
            result.mRating = mRating;
            result.mReference = mReference;
            result.mReviews = mReviews;
            result.mScope = mScope;
            result.mTypes = mTypes;
            result.mUrl = mUrl;
            result.mUtcOffset = mUtcOffset;
            result.mVicinity = mVicinity;
            result.mWebsite = mWebsite;
            return result;
        }

    }

}
