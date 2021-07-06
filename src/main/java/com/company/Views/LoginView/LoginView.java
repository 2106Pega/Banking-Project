package com.company.Views.LoginView;

import com.company.Controllers.Endpoints.Endpoint;
import com.company.Controllers.Endpoints.LoginEndpoint.LoginEndpoint;
import com.company.Models.ModelTemplates.User;
import com.company.Views.View;

public class LoginView implements View {


    @Override
    public String[] body() {
        String[] prompts = new String[2];
        prompts[0] = "Please enter username";
        prompts[1] = "Please enter a password";

        return prompts;
    }
    @Override
    public Endpoint getEndpoint() {
        return endpoint;
    }

    @Override
    public String getViewName() {
        return "login_view";
    }

    @Override
    public void setUser(User user) {

    }

    @Override
    public User getUser() {
        return null;
    }

    private User user;
    private Endpoint endpoint = new LoginEndpoint();
}
