package com.company.Views.DepositView;

import com.company.Controllers.Endpoints.DepositEndpoint.DepositEndpoint;
import com.company.Controllers.Endpoints.Endpoint;
import com.company.Models.ModelTemplates.User;
import com.company.Views.View;

public class DepositView implements View {
    @Override
    public String[] body() {
        return new String[]{
                "Please enter a deposit amount"
        };
    }

    @Override
    public Endpoint getEndpoint() {
        return endpoint;
    }

    @Override
    public String getViewName() {
        return "deposit_view";
    }

    @Override
    public void setUser(User user) {

    }

    @Override
    public User getUser() {
        return null;
    }
    
    private Endpoint endpoint = new DepositEndpoint();
}
