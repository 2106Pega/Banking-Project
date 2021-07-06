package com.company.Views.WithdrawlDepositView;

import com.company.Controllers.Endpoints.Endpoint;
import com.company.Models.ModelTemplates.User;
import com.company.Views.View;

public class WithdrawlDepositView implements View {
    @Override
    public String[] body() {
        return new String[]{
                "Please select an account you wish to withdrawl or deposit to",
                "Would you like to withdrawl? (1 for yes 0 for no)",
                "Would you like to deposit? (1 for yes 0 fro no)",
                "Please enter an amount"
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
