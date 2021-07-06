package com.company.Controllers.Endpoints.MyAccountsEndpoint;

import com.company.Controllers.DataAccess.DAOImpls.AccountDAOImpl;
import com.company.Controllers.Endpoints.AllAccountEndpoint.AccountsPredataHelper;
import com.company.Controllers.Endpoints.Endpoint;
import com.company.MVCAggregate.MVCAggregate;
import com.company.Models.ModelTemplates.Account;
import com.company.Models.ModelTemplates.User;

import java.util.ArrayList;

public class MyAccountsEndpoint implements Endpoint {
    @Override
    public String[] response(String[] parameters) {
        int selection = -1;
        
        try{
            selection = Integer.parseInt(parameters[0]);
            if ( selection <= 0 || selection > this.myAccounts.size()){
                throw new Exception();
            }
            Account account = this.myAccounts.get(selection-1);
            if (!account.isApproved()){
                return new String[]{
                        "error",
                        "Account is not approved"
                };
            }
        }catch (Exception e){
            return new String[]{
                    "error",
                    "Invalid entry or account is not active"
            };
        }
        this.mvcAggregate
                .getTitle_view()
                .get("my_account_options")
                .getEndpoint()
                .setObject(myAccounts.get(selection-1));
        
        return new String[]{
                "success",
                "my_account_options"
        };
        /*
        Should be intialized with the selected account
         */
    }

    @Override
    public String preData() {
        AccountDAOImpl impl = new AccountDAOImpl();
        this.myAccounts = impl.getAccountByUser(getCurrentUser());
        AccountsPredataHelper helper = new AccountsPredataHelper();
        return helper.allAccountsToPredataString(this.myAccounts);
    }

    @Override
    public void setObject(Object obj) {

    }

    @Override
    public void setCurrentUser(User user) {
        this.currentLogin = user;
    }

    @Override
    public User getCurrentUser() {
        return this.currentLogin;
    }

    @Override
    public MVCAggregate getAggregate() {
        return null;
    }

    @Override
    public void setAggregate(MVCAggregate aggregate) {
        this.mvcAggregate = aggregate;
    }
    private User currentLogin;
    private ArrayList<Account> myAccounts;
    private MVCAggregate mvcAggregate;
}
