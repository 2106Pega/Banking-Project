package com.company.Views.AuthenticatedViews.EmployeeViews;

import com.company.Controllers.Endpoints.AuthenticatedEndpoints.CustomerEndpoints.AccountApplyEndpoint;
import com.company.Controllers.Endpoints.Endpoint;
import com.company.Models.ModelTemplates.User;
import com.company.Views.ViewTemplate;

public class AccountApplyViewTemplate implements ViewTemplate {
    @Override
    public String[] body() {
        return new String[]{
                "Please enter a starting amount",
        };

    }

    @Override
    public Endpoint getEndpoint() {
        return endpoint;
    }

    @Override
    public String getViewName() {
        return "account_apply_view";
    }

    @Override
    public void setUser(User user) {

    }

    @Override
    public User getUser() {
        return null;
    }
    private Endpoint endpoint = new AccountApplyEndpoint();
}
