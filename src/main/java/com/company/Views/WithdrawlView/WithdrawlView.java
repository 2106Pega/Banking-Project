package com.company.Views.WithdrawlView;

import com.company.Controllers.Endpoints.Endpoint;
import com.company.Controllers.Endpoints.WithdrawlEndpoint.WithdrawlEnpoint;
import com.company.Models.ModelTemplates.User;
import com.company.Views.View;

public class WithdrawlView implements View {
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
