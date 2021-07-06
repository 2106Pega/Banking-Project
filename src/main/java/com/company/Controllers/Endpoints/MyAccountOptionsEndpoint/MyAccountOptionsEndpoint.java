package com.company.Controllers.Endpoints.MyAccountOptionsEndpoint;

import com.company.Controllers.Endpoints.AllAccountEndpoint.AccountsPredataHelper;
import com.company.Controllers.Endpoints.Endpoint;
import com.company.MVCAggregate.MVCAggregate;
import com.company.Models.ModelTemplates.Account;
import com.company.Models.ModelTemplates.User;

import java.util.ArrayList;

public class MyAccountOptionsEndpoint implements Endpoint {
    @Override
    public String[] response(String[] parameters) {
        int input;
        try{
            input = Integer.parseInt(parameters[0]);
            if ( input > 3 || input < 1){
                throw new Exception();
            }
        }catch (Exception e){
            return new String[]{
                    "error",
                    "Invalid Entry"
            };
        }
        String success_view = null;
        if ( input == 1){
            success_view = "withdrawl_view";
        }else if ( input == 2){
            success_view = "deposit_view";
        }else{
            success_view = "transfer_view";
        }
        this.aggregate
                .getTitle_view()
                .get(success_view)
                .getEndpoint()
                .setObject(this.account);
        return new String[]{
                "success",
                success_view
        };
        
    }

    @Override
    public String preData() {
        ArrayList<Account> currentAccount = new ArrayList<>();
        currentAccount.add(this.account);
        AccountsPredataHelper helper = new AccountsPredataHelper();
        System.out.println(helper.allAccountsToPredataString(currentAccount));
        System.out.println("-------------------------");
        StringBuilder builder = new StringBuilder();
        builder.append("1: Would you like to withdrawl? \n");
        builder.append("2: Would you like to deposit? \n");
        builder.append("3: Would you like to make a transfer? \n");
        return builder.toString();
    }

    @Override
    public void setObject(Object obj) {
        this.account = (Account) obj;
    }

    @Override
    public void setCurrentUser(User user) {

    }

    @Override
    public User getCurrentUser() {
        return null;
    }

    @Override
    public MVCAggregate getAggregate() {
        return null;
    }

    @Override
    public void setAggregate(MVCAggregate aggregate) {
        this.aggregate = aggregate;
    }
    
    private Account account;
    private MVCAggregate aggregate;
}
