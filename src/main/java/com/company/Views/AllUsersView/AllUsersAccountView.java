package com.company.Views.AllUsersView;

import com.company.Controllers.Endpoints.AllUsersEndpoint.AllUsersAccountEndpoint;
import com.company.Controllers.Endpoints.Endpoint;
import com.company.Models.ModelTemplates.User;
import com.company.Views.View;

public class AllUsersAccountView implements View {
    @Override
    public String[] body() {
        return new String[]{
                "Please select an account by index to edit"
        };
    }

    @Override
    public Endpoint getEndpoint() {
        return endpoint;
    }

    @Override
    public String getViewName() {
        return "all_user_accounts_view";
    }

    @Override
    public void setUser(User user) {

    }

    @Override
    public User getUser() {
        return null;
    }

    private Endpoint endpoint = new AllUsersAccountEndpoint();
}
