package com.company.Controllers.Endpoints.AccountApplyEndpoint;

import com.company.Controllers.DataAccess.DAOImpls.AccountDAOImpl;
import com.company.Controllers.Endpoints.Endpoint;
import com.company.Models.ModelTemplates.User;

import java.text.DecimalFormat;

public class AccountApplyEndpoint implements Endpoint {
    @Override
    public String[] response(String[] parameters) {
        double enteredAmount;
        try{
            enteredAmount = Double.parseDouble(parameters[0]);
            if ( enteredAmount <= 0 ){
                throw new Exception();
            }
        }catch (Exception e){
            return new String[]{
                    "error",
                    "Invalid entry, must be a numeric value greater than 0"
            };
        }
        DecimalFormat formatter = new DecimalFormat("#.##");
        enteredAmount = Double.parseDouble(formatter.format(enteredAmount));
        AccountDAOImpl accountDAO = new AccountDAOImpl();
        accountDAO.generateAccount(this.user,enteredAmount);
        
        return new String[]{
          "success",
          "my_accounts_view"
        };
    }

    @Override
    public void setCurrentUser(User user) {
        this.user = user;
    }
    private User user;
}
