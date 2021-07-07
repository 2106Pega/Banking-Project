package com.company.Views.InitialView;

import com.company.Controllers.Endpoints.Endpoint;
import com.company.Controllers.Endpoints.InitialEndpoints.InitialViewEndpoint;
import com.company.Models.ModelTemplates.User;
import com.company.Views.ViewTemplate;
import dnl.utils.text.table.TextTable;

public class InitialViewTemplate implements ViewTemplate {
    @Override
    public String[] body() {

        return new String[]{
                "Please select an option"
        };
    }


    @Override
    public Endpoint getEndpoint() {
        return  this.endpoint;
    }

    @Override
    public String getViewName() {
        return "initial_view";
    }

    @Override
    public void setUser(User user) {

    }

    @Override
    public User getUser() {
        return null;
    }

    private Endpoint endpoint = new InitialViewEndpoint();
}
