package com.example.admin.solidwaste.pojo.AdminPojo;

public class AdminDashboardPojoResponseResponse implements java.io.Serializable {
    private static final long serialVersionUID = -8435526129253575729L;
    private String Approved;
    private String Collected;
    private String Pending;
    private String Processed;

    public String getApproved() {
        return this.Approved;
    }

    public void setApproved(int Approved) {
        this.Approved = String.valueOf(Approved);
    }

    public String getCollected() {
        return this.Collected;
    }

    public void setCollected(int Collected) {
        this.Collected =String.valueOf(Collected);
    }

    public String getPending() {
        return this.Pending;
    }

    public void setPending(int Pending) {
        this.Pending =String.valueOf(Pending);
    }

    public String getProcessed() {
        return this.Processed;
    }

    public void setProcessed(int Processed) {
        this.Processed = String.valueOf(Processed);
    }
}
