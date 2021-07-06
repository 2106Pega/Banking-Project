package com.company.Controllers.Endpoints.LoginEndpoint;

import com.company.Controllers.DataAccess.DAOImpls.UserDAOImpl;
import com.company.Controllers.Endpoints.Endpoint;
import com.company.Controllers.PasswordHelper.PasswordHelper;
import com.company.Models.ModelTemplates.User;

public class LoginEndpoint implements Endpoint {


    @Override
    public String[] response(String[] parameters) {
        String[] response = new String[2];
        UserDAOImpl impl = new UserDAOImpl();
        User user  = impl.getUserByUsername(parameters[0]);
        if ( user == null ) {
            response[0] = "error";
            response[1] = "User does not exist";
            return response;
        }
        String dbPassword = user.getPassword();
        PasswordHelper passwordHelper = new PasswordHelper();
        boolean isCorrectPassword = passwordHelper.isCorrectPassword(parameters[1],dbPassword);
        if ( !isCorrectPassword ){
            response[0] = "error";
            response[1] = "Incorrect password";
            return response;
        }
        response[0] = "success";
        response[1] = "menu_view";

        return response;


    }
}
