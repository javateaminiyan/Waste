package com.example.admin.solidwaste.pojo;

import com.google.gson.annotations.SerializedName;

public class SendOTP {

    @SerializedName("Response")
    private SendOTPResponse[] Response;
    private int PassKey;
    private int StatusCode;

    public SendOTP() {
    }

    public SendOTP(SendOTPResponse[] response, int passKey, int statusCode) {

        Response = response;
        PassKey = passKey;
        StatusCode = statusCode;
    }

    public SendOTPResponse[] getResponse() {
        return this.Response;
    }

    public void setResponse(SendOTPResponse[] Response) {
        this.Response = Response;
    }

    public int getPassKey() {
        return this.PassKey;
    }

    public void setPassKey(int PassKey) {
        this.PassKey = PassKey;
    }

    public int getStatusCode() {
        return this.StatusCode;
    }

    public void setStatusCode(int StatusCode) {
        this.StatusCode = StatusCode;
    }
}
