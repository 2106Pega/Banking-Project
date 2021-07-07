package com.company.Controllers.Endpoints.AuthenticatedEndpoints.EmployeeEndpoints;

import com.company.Controllers.DataAccess.DAOImpls.AccountDAOImpl;
import com.company.Controllers.DataAccess.DAOImpls.UserDAOImpl;
import com.company.Controllers.Endpoints.AuthenticatedEndpoints.AccountsPredataHelper;
import com.company.Controllers.Endpoints.Endpoint;
import com.company.MainDriver;
import com.company.Models.ModelTemplates.Account;
import com.company.Models.ModelTemplates.User;

import java.util.ArrayList;

public class AllAccountEndpointEdit implements Endpoint {
    @Override
    public String[] response(String[] parameters) {
        if ( impl.getUserByUsername(this.myAccount.getUsername()).getAccessLevel() != 2){
            return new String[]{
                    "success",
                    "menu_view"
            };
        }
        int input;
        try{
            input = Integer.parseInt(parameters[0]);
            if ( input != 1 && input != 2){
               throw new Exception();
            }
        }catch (Exception e){
            return new String[]{
                    "error",
                    "Invalid entry"
            };
        }
        AccountDAOImpl impl = new AccountDAOImpl();

        if ( input == 1){
            accountToModify.setApproved(true);
            accountToModify.setReviewed(true);
            MainDriver.logTransaction(accountToModify,"Initial Load");
            impl.saveAccount(accountToModify);
        }else {
            accountToModify.setApproved(false);
            accountToModify.setReviewed(true);
            impl.saveAccount(accountToModify);
        }
        return new String[]{
                "success",
                "all_accounts_view"
        };
    }
    @Override
    public String preData(){
        if ( impl.getUserByUsername(this.myAccount.getUsername()).getAccessLevel() != 2){
            return "";
        }
        AccountsPredataHelper helper = new AccountsPredataHelper();
        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(accountToModify);
        String builder = "Your are now editing : \n" +
                helper.allAccountsToPredataString(accounts);
        return builder;
    }

    @Override
    public void setObject(Object object){
        accountToModify = (Account) object;
    }
    @Override
    public void setCurrentUser(User user) {
        this.myAccount = user;
    }
    private User myAccount;
    private UserDAOImpl impl = new UserDAOImpl();
    private Account accountToModify;
}
