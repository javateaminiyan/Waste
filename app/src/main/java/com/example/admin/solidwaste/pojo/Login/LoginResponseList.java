package com.example.admin.solidwaste.pojo.Login;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginResponseList {


    public int StatusCode ;
    public String StatusMessage ;

    public LoginResponseList() {
    }

    public int getStatusCode() {

        return StatusCode;
    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }

    public String getStatusMessage() {
        return StatusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        StatusMessage = statusMessage;
    }

    public List<LoginAllResponses> getResponse() {
        return Response;
    }

    public void setResponse(List<LoginAllResponses> response) {
        Response = response;
    }

    public LoginResponseList(int statusCode, String statusMessage, List<LoginAllResponses> response) {

        StatusCode = statusCode;
        StatusMessage = statusMessage;
        Response = response;
    }

    @SerializedName("Response")

    public List<LoginAllResponses> Response;
}
