package com.company.Views.TransferFundView;

import com.company.Controllers.Endpoints.Endpoint;
import com.company.Models.ModelTemplates.User;
import com.company.Views.View;

public class TransferFundsView implements View {
    @Override
    public String[] body() {
        return new String[]{
                "Please select the account you would like to sends from from",
                "Please enter the routing number of the account you would like to send funds to",
                "Please enter the account number of the account you would like to send funds to "
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
