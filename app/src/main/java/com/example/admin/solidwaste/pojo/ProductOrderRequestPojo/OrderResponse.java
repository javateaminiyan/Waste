package com.example.admin.solidwaste.pojo.ProductOrderRequestPojo;

public class OrderResponse {
    private String Message;
    private int StatusCode;

    public String getMessage() {
        return this.Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public int getStatusCode() {
        return this.StatusCode;
    }

    public void setStatusCode(int StatusCode) {
        this.StatusCode = StatusCode;
    }
}
