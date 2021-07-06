package com.company.Controllers.Endpoints.DepositEndpoint;

import com.company.Controllers.DataAccess.DAOImpls.AccountDAOImpl;
import com.company.Controllers.DataAccess.DAOImpls.TransactionDAOImpl;
import com.company.Controllers.Endpoints.AllAccountEndpoint.AccountsPredataHelper;
import com.company.Controllers.Endpoints.Endpoint;
import com.company.Models.ModelTemplates.Account;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DepositEndpoint implements Endpoint {
    @Override
    public String[] response(String[] parameters) {
        try{
            Double.valueOf(parameters[0]);
            DecimalFormat formatter = new DecimalFormat("#.##");

            double depositAmount = Double.parseDouble(formatter.format(Double.parseDouble(parameters[0])));
            this.account.setBalance(this.account.getBalance()+depositAmount);
            new AccountDAOImpl().saveAccount(this.account);
            new TransactionDAOImpl().addTransaction("deposit",this.account.getAccount_id(), depositAmount);
            return new String[]{
                    "success",
                    "my_accounts_view"
                };
            
        }catch (Exception e){
            return new String[]{
                    "error",
                    "Invalid entry"
            };
        }
    }
    @Override
    public String preData() {
        ArrayList<Account> currentAccount = new ArrayList<>();
        currentAccount.add(this.account);
        AccountsPredataHelper helper = new AccountsPredataHelper();
        return (helper.allAccountsToPredataString(currentAccount));

    }

    @Override
    public void setObject(Object obj) {
        this.account = (Account) obj;
    }
    
    private Account account;
}
