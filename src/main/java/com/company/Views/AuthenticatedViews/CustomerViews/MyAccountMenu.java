package com.company.Views.AuthenticatedViews.CustomerViews;

import com.company.Controllers.Endpoints.Endpoint;
import com.company.Models.ModelTemplates.User;
import com.company.Views.ViewTemplate;

public class MyAccountMenu implements ViewTemplate {
    @Override
    public String[] body() {
        return new String[]{
                "Please select an option by index: "
        };
    }

    @Override
    public Endpoint getEndpoint() {
        return null;
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
}
