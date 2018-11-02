
package com.example.admin.solidwaste.pojo.MapPlacedetailsPojo;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Review {

    @SerializedName("author_name")
    private String mAuthorName;
    @SerializedName("author_url")
    private String mAuthorUrl;
    @SerializedName("language")
    private String mLanguage;
    @SerializedName("profile_photo_url")
    private String mProfilePhotoUrl;
    @SerializedName("rating")
    private Long mRating;
    @SerializedName("relative_time_description")
    private String mRelativeTimeDescription;
    @SerializedName("text")
    private String mText;
    @SerializedName("time")
    private Long mTime;

    public String getAuthorName() {
        return mAuthorName;
    }

    public String getAuthorUrl() {
        return mAuthorUrl;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public String getProfilePhotoUrl() {
        return mProfilePhotoUrl;
    }

    public Long getRating() {
        return mRating;
    }

    public String getRelativeTimeDescription() {
        return mRelativeTimeDescription;
    }

    public String getText() {
        return mText;
    }

    public Long getTime() {
        return mTime;
    }

    public static class Builder {

        private String mAuthorName;
        private String mAuthorUrl;
        private String mLanguage;
        private String mProfilePhotoUrl;
        private Long mRating;
        private String mRelativeTimeDescription;
        private String mText;
        private Long mTime;

        public Review.Builder withAuthorName(String authorName) {
            mAuthorName = authorName;
            return this;
        }

        public Review.Builder withAuthorUrl(String authorUrl) {
            mAuthorUrl = authorUrl;
            return this;
        }

        public Review.Builder withLanguage(String language) {
            mLanguage = language;
            return this;
        }

        public Review.Builder withProfilePhotoUrl(String profilePhotoUrl) {
            mProfilePhotoUrl = profilePhotoUrl;
            return this;
        }

        public Review.Builder withRating(Long rating) {
            mRating = rating;
            return this;
        }

        public Review.Builder withRelativeTimeDescription(String relativeTimeDescription) {
            mRelativeTimeDescription = relativeTimeDescription;
            return this;
        }

        public Review.Builder withText(String text) {
            mText = text;
            return this;
        }

        public Review.Builder withTime(Long time) {
            mTime = time;
            return this;
        }

        public Review build() {
            Review review = new Review();
            review.mAuthorName = mAuthorName;
            review.mAuthorUrl = mAuthorUrl;
            review.mLanguage = mLanguage;
            review.mProfilePhotoUrl = mProfilePhotoUrl;
            review.mRating = mRating;
            review.mRelativeTimeDescription = mRelativeTimeDescription;
            review.mText = mText;
            review.mTime = mTime;
            return review;
        }

    }

}
