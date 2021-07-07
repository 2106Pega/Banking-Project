package com.company.Views.AuthenticatedViews.EmployeeViews;

import com.company.Controllers.Endpoints.AuthenticatedEndpoints.EmployeeEndpoints.AllAccountEndpointEdit;
import com.company.Controllers.Endpoints.Endpoint;
import com.company.Models.ModelTemplates.User;
import com.company.Views.ViewTemplate;

public class AllAccountEditViewTemplate implements ViewTemplate {
    @Override
    public String[] body() {
        return new String[]{
                "Would you like to approve or disapprove account: (1 for approve 2 for disapprove)"
        };

    }

    @Override
    public Endpoint getEndpoint() {
        return this.endpoint;

    }

    @Override
    public String getViewName() {
        return "account_accounts_edit_view";

    }

    @Override
    public void setUser(User user) {

    }

    @Override
    public User getUser() {
        return null;
    }
    private Endpoint endpoint = new AllAccountEndpointEdit();
}
