package com.company.Views;

import com.company.Controllers.Endpoints.Endpoint;
import com.company.Models.ModelTemplates.User;

public class MyAccountMenu implements View{
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
