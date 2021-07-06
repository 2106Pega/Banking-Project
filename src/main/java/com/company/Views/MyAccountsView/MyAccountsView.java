package com.company.Views.MyAccountsView;

import com.company.Controllers.Endpoints.Endpoint;
import com.company.Controllers.Endpoints.MyAccountsEndpoint.MyAccountsEndpoint;
import com.company.Models.ModelTemplates.User;
import com.company.Views.View;

public class MyAccountsView implements View {
    @Override
    public String[] body() {
        return new String[]{
                "Please select an account you would like to manage"
        };
    }

    @Override
    public Endpoint getEndpoint() {
        return this.endpoint;
    }

    @Override
    public String getViewName() {
        return "my_account_options";
    }

    @Override
    public void setUser(User user) {

    }

    @Override
    public User getUser() {
        return null;
    }
    private Endpoint endpoint = new MyAccountsEndpoint();
}
