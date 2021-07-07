package com.company.Views.InitialView;

import com.company.Controllers.Endpoints.Endpoint;
import com.company.Controllers.Endpoints.InitialEndpoints.RegistrationEndpoint;
import com.company.Models.ModelTemplates.User;
import com.company.Views.ViewTemplate;

public class RegistrationViewTemplate implements ViewTemplate {
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
        return "registration_view";
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