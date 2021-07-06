package com.company.Controllers.Endpoints.AllAccountEndpoint;

import com.company.Controllers.DataAccess.DAOImpls.AccountDAOImpl;
import com.company.Controllers.Endpoints.Endpoint;
import com.company.Models.ModelTemplates.Account;

import java.util.ArrayList;

public class AllAccountEndpointEdit implements Endpoint {
    @Override
    public String[] response(String[] parameters) {
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
            impl.saveAccount(accountToModify);
        }else {
            accountToModify.setApproved(false);
            impl.saveAccount(accountToModify);
        }
        return new String[]{
                "success",
                "all_accounts_view"
        };
    }
    @Override
    public String preData(){
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
    private Account accountToModify;
}
