package com.company.Views.AccountApplyView;

import com.company.Controllers.Endpoints.Endpoint;
import com.company.Models.ModelTemplates.User;
import com.company.Views.View;

public class AccountApplyView implements View {
    @Override
    public String[] body() {
        return new String[]{
                "Please enter a starting amount"
        };

    }

    @Override
    public Endpoint getEndpoint() {
        return null;
    }

    @Override
    public String getViewName() {
        return null;
    }

    @Override
    public void setUser(User user) {

    }

    @Override
    public User getUser() {
        return null;
    }
}
