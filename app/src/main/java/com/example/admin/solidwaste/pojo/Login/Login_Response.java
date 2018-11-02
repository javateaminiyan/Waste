package com.example.admin.solidwaste.pojo.Login;

import com.google.gson.annotations.SerializedName;

public class Login_Response {

    @SerializedName("Response")

    public LoginResponseList Response ;

    public LoginResponseList getResponse() {
        return Response;
    }

    public void setResponse(LoginResponseList response) {
        Response = response;
    }

    public Login_Response() {

    }

    public Login_Response(LoginResponseList response) {

        Response = response;
    }
}
