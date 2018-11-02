package com.example.admin.solidwaste.pojo.RegisteredProduct;

public class GetProductResponseResponse {
    private GetProductResponseResponseResponse[] Response;
    private int StatusCode;
    private String StatusMessage;

    public GetProductResponseResponseResponse[] getResponse() {
        return this.Response;
    }

    public void setResponse(GetProductResponseResponseResponse[] Response) {
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
