package com.example.admin.solidwaste.pojo.AdminPojo;

public class AdminDashboardPojo implements java.io.Serializable {
    private static final long serialVersionUID = 2175548823071088471L;
    private AdminDashboardPojoResponse Response;

    public AdminDashboardPojoResponse getResponse() {
        return this.Response;
    }

    public void setResponse(AdminDashboardPojoResponse Response) {
        this.Response = Response;
    }
}
