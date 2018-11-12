package com.example.admin.solidwaste.pojo.AdminPojo;

public class AdminDashboardPojoResponse implements java.io.Serializable {
    private static final long serialVersionUID = -5304021715917298261L;
    private AdminDashboardPojoResponseResponse[] Response;
    private int StatusCode;
    private String StatusMessage;

    public AdminDashboardPojoResponseResponse[] getResponse() {
        return this.Response;
    }

    public void setResponse(AdminDashboardPojoResponseResponse[] Response) {
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
