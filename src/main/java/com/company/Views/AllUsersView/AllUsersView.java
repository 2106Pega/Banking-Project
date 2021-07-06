package com.company.Views.AllUsersView;

import com.company.Controllers.Endpoints.AllUsersEndpoint.AllUsersEndpoint;
import com.company.Controllers.Endpoints.Endpoint;
import com.company.Models.ModelTemplates.User;
import com.company.Views.View;

public class AllUsersView implements View {
    @Override
    public String[] body() {
        return new String[]{
                "Please select an user whos account you wish to view"
        };
    }

    @Override
    public Endpoint getEndpoint() {
        return allUsersEndpoint;
    }

    @Override
    public String getViewName() {
        return "all_users_view";
    }

    @Override
    public void setUser(User user) {

    }

    @Override
    public User getUser() {
        return null;
    }
    private Endpoint allUsersEndpoint = new AllUsersEndpoint();
}
