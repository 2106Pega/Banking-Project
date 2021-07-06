package com.company.Views.MenuView;

import com.company.Controllers.Endpoints.Endpoint;
import com.company.Controllers.Endpoints.MenuEndpoint.MenuEndpoint;
import com.company.Models.ModelTemplates.User;
import com.company.Views.View;

public class MenuView implements View {
    @Override
    public String[] body() {
        return new String[]{
                "Please select an option"
        };
    }

    @Override
    public Endpoint getEndpoint() {
        return endpoint;
    }

    @Override
    public String getViewName() {
        return "menu_view";
    }

    @Override
    public void setUser(User user) {

    }

    @Override
    public User getUser() {
        return null;
    }
    private User user;
    private Endpoint endpoint = new MenuEndpoint();
}
