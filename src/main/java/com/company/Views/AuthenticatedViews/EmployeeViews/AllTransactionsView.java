package com.company.Views.AuthenticatedViews.EmployeeViews;

import javax.swing.text.View;

import com.company.Controllers.Endpoints.AuthenticatedEndpoints.EmployeeEndpoints.AllTransactionsEndpoint;
import com.company.Controllers.Endpoints.Endpoint;
import com.company.Models.ModelTemplates.User;
import com.company.Views.ViewTemplate;

public class AllTransactionsView implements ViewTemplate {

    @Override
    public String[] body() {
        return new String[]{
          "Please enter any key to go back to menu"
        };
    }

    @Override
    public Endpoint getEndpoint() {
        return endpoint;
    }

    @Override
    public String getViewName() {
        return "all_transactions_view";
    }

    @Override
    public void setUser(User user) {

    }

    @Override
    public User getUser() {
        return null;
    }
    private Endpoint endpoint = new AllTransactionsEndpoint();
}
