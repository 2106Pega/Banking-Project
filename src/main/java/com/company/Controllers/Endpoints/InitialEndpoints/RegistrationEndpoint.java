package com.company.Controllers.Endpoints.InitialEndpoints;

import com.company.Controllers.DataAccess.DAOImpls.UserDAOImpl;
import com.company.Controllers.Endpoints.Endpoint;
import com.company.Models.ModelTemplates.User;

public class RegistrationEndpoint implements Endpoint {

    @Override
    public String[] response(String[] parameters) {
        if ( !parameters[1].equals(parameters[2])){
            return new String[]{
                    "error",
                    "Passwords do not match"
            };
        }
        for ( String str: parameters){
            if ( str.length() == 0){
                return new String[]{
                        "error",
                        "All fields are required"
                };
            }
        }
        if ( parameters[0].length() < 5 || parameters[0].length() > 15){
            return new String[]{
                    "error",
                    "Username must be between 5 and 15 characters"
            };
        }
        UserDAOImpl impl = new UserDAOImpl();
        String request = impl.createUser(parameters[0],parameters[1],parameters[3],parameters[4]);
        if ( request.equals("Successfully created account") ){
            return new String[]{
                    "success",
                    "login_view"
            };
        }else{
            return new String[]{
              "error",
              request
            };
        }

    }
}
