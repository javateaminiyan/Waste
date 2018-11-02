
package com.example.admin.solidwaste.pojo.GeneralResponse;

import com.google.gson.annotations.SerializedName;

public class GeneralResponseExtract {

    @SerializedName("StatusCode")
    private Long mStatusCode;
    @SerializedName("StatusMessage")
    private String mStatusMessage;

    public Long getStatusCode() {
        return mStatusCode;
    }

    public void setStatusCode(Long statusCode) {
        mStatusCode = statusCode;
    }

    public String getStatusMessage() {
        return mStatusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        mStatusMessage = statusMessage;
    }

}
