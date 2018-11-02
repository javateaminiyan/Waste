package com.example.admin.solidwaste.pojo;

import com.google.gson.annotations.SerializedName;

public class Response {


    private String StatusCode;

    @SerializedName("Message")
    private String StatusMessage;

    public Response() {
    }

    public Response(String statusCode, String statusMessage) {
        StatusCode = statusCode;
        StatusMessage = statusMessage;
    }

    public String getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(String statusCode) {
        StatusCode = statusCode;
    }

    public String getStatusMessage() {
        return StatusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        StatusMessage = statusMessage;
    }
}
