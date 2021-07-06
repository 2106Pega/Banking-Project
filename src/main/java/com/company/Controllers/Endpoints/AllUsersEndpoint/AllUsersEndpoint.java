package com.company.Controllers.Endpoints.AllUsersEndpoint;

import com.company.Controllers.DataAccess.DAOImpls.UserDAOImpl;
import com.company.Controllers.Endpoints.Endpoint;
import com.company.MVCAggregate.MVCAggregate;
import com.company.Models.ModelTemplates.Account;
import com.company.Models.ModelTemplates.User;

import java.util.ArrayList;

public class AllUsersEndpoint implements Endpoint {
    @Override
    public String preData() {
        StringBuilder userBuilder = new StringBuilder();
        userBuilder.append("Index\t\tUsername\t\tFirst name\t\tLast name\t\tNumber of accounts\n");
        allUserAccounts = new UserDAOImpl().getAllUsers();
        for ( int i =0 ; i < allUserAccounts.size(); i++){
            User user = allUserAccounts.get(i);
            ArrayList<Account> accounts = user.getAccounts();
            userBuilder.append((i+1) + ":\t\t\t" +user.getUsername()+"\t\t\t"+ user.getFirst_name()+ "\t\t" + user.getLast_name()+"\t\t" + accounts.size() +"\t\t"+"\n");
        }
        return String.format("%s",userBuilder.toString());
    }

    @Override
    public void setAggregate(MVCAggregate aggregate) {
        this.aggregate = aggregate;
    }

    @Override
    public String[] response(String[] parameters) {
        int response;
        try{
            response = Integer.parseInt(parameters[0]);
            if ( response <= 0 || response > allUserAccounts.size()){
                throw new Exception();
            }
        }catch (Exception e){
            return new String[]{
                    "error",
                    "Invalid entry"
            };
        }
        User user = allUserAccounts.get(response-1);
        this.aggregate
                .getTitle_view()
                .get("all_user_accounts_view")
                .getEndpoint()
                .setObject(user);
        return new String[]{
                "success",
                "all_user_accounts_view"
        };

    }

    @Override
    public MVCAggregate getAggregate() {
        return null;
    }

    private ArrayList<User> allUserAccounts;
    private MVCAggregate aggregate;
}
