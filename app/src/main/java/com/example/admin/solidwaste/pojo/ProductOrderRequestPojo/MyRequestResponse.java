package com.example.admin.solidwaste.pojo.ProductOrderRequestPojo;

public class MyRequestResponse {
    private MyRequestResponseResponse[] Response;
    private int StatusCode;
    private String StatusMessage;

    public MyRequestResponseResponse[] getResponse() {
        return this.Response;
    }

    public void setResponse(MyRequestResponseResponse[] Response) {
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
