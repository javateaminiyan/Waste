package com.example.admin.solidwaste.pojo.ProductRegistrationPojo;

public class ProductPriceSuggessionResponse {
    private ProductPriceSuggessionResponseResponse[] Response;
    private int StatusCode;
    private String StatusMessage;

    public ProductPriceSuggessionResponseResponse[] getResponse() {
        return this.Response;
    }

    public void setResponse(ProductPriceSuggessionResponseResponse[] Response) {
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
