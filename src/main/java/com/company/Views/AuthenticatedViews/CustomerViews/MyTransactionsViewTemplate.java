package com.company.Views.AuthenticatedViews.CustomerViews;

import com.company.Controllers.Endpoints.Endpoint;
import com.company.Controllers.Endpoints.AuthenticatedEndpoints.CustomerEndpoints.MyTransactionsEndpoint;
import com.company.Models.ModelTemplates.User;
import com.company.Views.ViewTemplate;

public class MyTransactionsViewTemplate implements ViewTemplate {
    @Override
    public String[] body() {
        return new String[]{
                "Enter any key to go back to menu"
        };
    }

    @Override
    public Endpoint getEndpoint() {
        return this.endpoint;
    }

    @Override
    public String getViewName() {
        return "my_transactions_view";
    }

    @Override
    public void setUser(User user) {

    }

    @Override
    public User getUser() {
        return null;
    }

    private Endpoint endpoint = new MyTransactionsEndpoint();
}
