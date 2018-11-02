
package com.example.admin.solidwaste.pojo.MapPlacedetailsPojo;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Photo {

    @SerializedName("height")
    private Long mHeight;
    @SerializedName("html_attributions")
    private List<String> mHtmlAttributions;
    @SerializedName("photo_reference")
    private String mPhotoReference;
    @SerializedName("width")
    private Long mWidth;

    public Long getHeight() {
        return mHeight;
    }

    public List<String> getHtmlAttributions() {
        return mHtmlAttributions;
    }

    public String getPhotoReference() {
        return mPhotoReference;
    }

    public Long getWidth() {
        return mWidth;
    }

    public static class Builder {

        private Long mHeight;
        private List<String> mHtmlAttributions;
        private String mPhotoReference;
        private Long mWidth;

        public Photo.Builder withHeight(Long height) {
            mHeight = height;
            return this;
        }

        public Photo.Builder withHtmlAttributions(List<String> htmlAttributions) {
            mHtmlAttributions = htmlAttributions;
            return this;
        }

        public Photo.Builder withPhotoReference(String photoReference) {
            mPhotoReference = photoReference;
            return this;
        }

        public Photo.Builder withWidth(Long width) {
            mWidth = width;
            return this;
        }

        public Photo build() {
            Photo photo = new Photo();
            photo.mHeight = mHeight;
            photo.mHtmlAttributions = mHtmlAttributions;
            photo.mPhotoReference = mPhotoReference;
            photo.mWidth = mWidth;
            return photo;
        }

    }

}
