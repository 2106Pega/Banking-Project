package com.company.Views.InitialView;

import com.company.Controllers.Endpoints.Endpoint;
import com.company.Models.ModelTemplates.User;
import com.company.Views.View;

public class InitialView implements View {
    @Override
    public String[] body() {
        return new String[]{
                "1 to go to the login\r\n2 to go to registration",
        };
    }

    @Override
    public Endpoint getEndpoint() {
        return parameters -> {
            if ( parameters[0].equals("1")){
                return new String[]{
                        "success",
                        "login_view"
                };
            }else if ( parameters[0].equals("2")){
                return new String[]{
                        "success",
                        "registration_view"
                };
            }else{
                return new String[]{
                        "error",
                        "Invalid entry"
                };
            }
        };
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

    private User user;
}
