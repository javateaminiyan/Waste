package com.example.admin.solidwaste.pojo.UserProductPojo;

public class UserProductResponse {
    private UserProductResponseResponse[] Response;
    private int StatusCode;
    private String StatusMessage;

    public UserProductResponseResponse[] getResponse() {
        return this.Response;
    }

    public void setResponse(UserProductResponseResponse[] Response) {
        this.Response = Response;
    }

    public int getStatusCode() {
        return this.StatusCode;
    }

    public void setStatusCode(int StatusCode) {
        this.StatusCode = StatusCode;
    }

    public String getStatusMessage() {
        return this.StatusMessage;
    }

    public void setStatusMessage(String StatusMessage) {
        this.StatusMessage = StatusMessage;
    }
}
