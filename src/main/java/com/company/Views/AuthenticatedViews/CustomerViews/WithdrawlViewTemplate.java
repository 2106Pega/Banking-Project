package com.company.Views.AuthenticatedViews.CustomerViews;

import com.company.Controllers.Endpoints.Endpoint;
import com.company.Controllers.Endpoints.AuthenticatedEndpoints.CustomerEndpoints.WithdrawlEnpoint;
import com.company.Models.ModelTemplates.User;
import com.company.Views.ViewTemplate;

public class WithdrawlViewTemplate implements ViewTemplate {
    @Override
    public String[] body() {
        return new String[]{
                "Please enter a withdrawl amount"
                
        };
    }

    @Override
    public Endpoint getEndpoint() {
        return this.endpoint;
    }

    @Override
    public String getViewName() {
        return "withdrawl_view";
    }

    @Override
    public void setUser(User user) {

    }

    @Override
    public User getUser() {
        return null;
    }

    private Endpoint endpoint = new WithdrawlEnpoint();
}
