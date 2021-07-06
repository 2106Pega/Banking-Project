package com.company.Views.AllAccountView;

import com.company.Controllers.Endpoints.AllAccountEndpoint.AllAccountEndpoint;
import com.company.Controllers.Endpoints.Endpoint;
import com.company.Models.ModelTemplates.User;
import com.company.Views.View;

public class AllAccountView implements View {
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
