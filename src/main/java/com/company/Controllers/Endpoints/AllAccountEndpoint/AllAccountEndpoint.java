package com.company.Controllers.Endpoints.AllAccountEndpoint;

import com.company.Controllers.DataAccess.DAOImpls.AccountDAOImpl;
import com.company.Controllers.Endpoints.Endpoint;
import com.company.MVCAggregate.MVCAggregate;
import com.company.Models.ModelTemplates.Account;
import com.company.Models.ModelTemplates.User;

import java.util.ArrayList;

public class AllAccountEndpoint implements Endpoint {
    @Override
    public String[] response(String[] parameters) {
        try{
            int paramaterCheck = Integer.parseInt(parameters[0]);
            if ( paramaterCheck <= 0 || paramaterCheck > accounts.size()){
                throw new Exception();
            }
        }catch (Exception e){
            return new String[]{
                    "error",
                    "Invalid input"
            };
        }
        int selectedAccount = Integer.parseInt(parameters[0]) -1;

                this.getAggregate()
                        .getTitle_view()
                        .get("account_accounts_edit_view")
                        .getEndpoint()
                        .setObject(
                                this.accounts.get(selectedAccount)
                        );

        return new String[]{
                "success",
                "account_accounts_edit_view"
        };


    }

    @Override
    public String preData() {
        AccountDAOImpl accountDAO = new AccountDAOImpl();
        AccountsPredataHelper helper = new AccountsPredataHelper();
        this.accounts = accountDAO.getAllAccounts();
        return helper.allAccountsToPredataString(this.accounts);

    }

    @Override
    public MVCAggregate getAggregate() {
        return this.aggregate;
    }

    @Override
    public void setAggregate(MVCAggregate aggregate) {
        this.aggregate = aggregate;
    }

    @Override
    public void setCurrentUser(User user) {

    }

    @Override
    public User getCurrentUser() {
        return null;
    }

    private MVCAggregate aggregate;
    private ArrayList<Account> accounts;
}
