package com.company.Views.RegistrationView;

import com.company.Controllers.Endpoints.Endpoint;
import com.company.Controllers.Endpoints.RegistrationEndpoint.RegistrationEndpoint;
import com.company.Models.ModelTemplates.User;
import com.company.Views.View;

public class RegistrationView implements View {
    @Override
    public String[] body() {
        return new String[]{
                "Please enter a username",
                "Please enter a password",
                "Please reenter your password",
                "Please enter your first name",
                "Please enter your last name"
        };

    }

    @Override
    public Endpoint getEndpoint() {
        return new RegistrationEndpoint();
    }

    @Override
    public String getViewName() {
        return null;
    }

    @Override
    public void setUser(User user) {

    }

    @Override
    public User getUser() {
        return null;
    }
    private User user;
}
