
package com.example.admin.solidwaste.pojo.MapPlacedetailsPojo;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class PlusCode {

    @SerializedName("compound_code")
    private String mCompoundCode;
    @SerializedName("global_code")
    private String mGlobalCode;

    public String getCompoundCode() {
        return mCompoundCode;
    }

    public String getGlobalCode() {
        return mGlobalCode;
    }

    public static class Builder {

        private String mCompoundCode;
        private String mGlobalCode;

        public PlusCode.Builder withCompoundCode(String compoundCode) {
            mCompoundCode = compoundCode;
            return this;
        }

        public PlusCode.Builder withGlobalCode(String globalCode) {
            mGlobalCode = globalCode;
            return this;
        }

        public PlusCode build() {
            PlusCode plusCode = new PlusCode();
            plusCode.mCompoundCode = mCompoundCode;
            plusCode.mGlobalCode = mGlobalCode;
            return plusCode;
        }

    }

}
