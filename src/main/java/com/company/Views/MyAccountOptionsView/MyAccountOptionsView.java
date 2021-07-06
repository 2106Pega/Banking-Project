package com.company.Views.MyAccountOptionsView;

import com.company.Controllers.Endpoints.Endpoint;
import com.company.Controllers.Endpoints.MyAccountOptionsEndpoint.MyAccountOptionsEndpoint;
import com.company.Models.ModelTemplates.User;
import com.company.Views.View;

public class MyAccountOptionsView implements View {
    @Override
    public String[] body() {
        return new String[]{
            "Please select an option"
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
    
    private Endpoint endpoint = new MyAccountOptionsEndpoint();
}
