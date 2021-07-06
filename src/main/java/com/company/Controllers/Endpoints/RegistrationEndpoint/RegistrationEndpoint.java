package com.company.Controllers.Endpoints.RegistrationEndpoint;

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
