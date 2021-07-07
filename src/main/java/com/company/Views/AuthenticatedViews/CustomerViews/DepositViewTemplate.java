package com.company.Views.AuthenticatedViews.CustomerViews;

import com.company.Controllers.Endpoints.AuthenticatedEndpoints.CustomerEndpoints.DepositEndpoint;
import com.company.Controllers.Endpoints.Endpoint;
import com.company.Models.ModelTemplates.User;
import com.company.Views.ViewTemplate;

public class DepositViewTemplate implements ViewTemplate {
    @Override
    public String[] body() {
        return new String[]{
                "Please enter a deposit amount"
        };
    }

    @Override
    public Endpoint getEndpoint() {
        return endpoint;
    }

    @Override
    public String getViewName() {
        return "deposit_view";
    }

    @Override
    public void setUser(User user) {

    }

    @Override
    public User getUser() {
        return null;
    }
    
    private Endpoint endpoint = new DepositEndpoint();
}
