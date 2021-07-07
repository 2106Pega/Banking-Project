package com.company.Views.AuthenticatedViews.EmployeeViews;

import com.company.Controllers.Endpoints.AuthenticatedEndpoints.EmployeeEndpoints.AllAccountEndpoint;
import com.company.Controllers.Endpoints.Endpoint;
import com.company.Models.ModelTemplates.User;
import com.company.Views.ViewTemplate;

public class AllAccountViewTemplate implements ViewTemplate {
    @Override
    public String[] body() {
        return new String[]{
                "Please select an account you would like to update"
        };
    }

    @Override
    public Endpoint getEndpoint() {
        return endpoint;
    }

    @Override
    public String getViewName() {
        return "all_accounts_view";
    }

    @Override
    public void setUser(User user) {

    }

    @Override
    public User getUser() {
        return null;
    }

    private Endpoint endpoint = new AllAccountEndpoint();
}
