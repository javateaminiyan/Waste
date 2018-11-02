
package com.example.admin.solidwaste.pojo.GeneralResponse;

import com.google.gson.annotations.SerializedName;

public class GeneralResponse {

    @SerializedName("Response")
    private GeneralResponseExtract mResponse;

    public GeneralResponseExtract getmResponse() {
        return mResponse;
    }

    public GeneralResponse() {
    }

    public void setmResponse(GeneralResponseExtract mResponse) {
        this.mResponse = mResponse;
    }

    public GeneralResponse(GeneralResponseExtract mResponse) {

        this.mResponse = mResponse;
    }
}
