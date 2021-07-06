package com.company.Controllers.Endpoints.AllAccountEndpoint;

import com.company.Controllers.DataAccess.DAOImpls.UserDAOImpl;
import com.company.Models.ModelTemplates.Account;
import com.company.Models.ModelTemplates.User;

import java.util.ArrayList;

public class AccountsPredataHelper {

    public String allAccountsToPredataString(ArrayList<Account> allAccounts){
        StringBuilder builder = new StringBuilder();
        UserDAOImpl userDAO = new UserDAOImpl();
        builder.append(" \t" + "Account Number");
        builder.append(" \t" + "Balance ");
        builder.append(" \t" + "Is Approved");
        builder.append(" \t" + "Username \n");

        for ( int i = 0 ; i < allAccounts.size(); i++){
            Account currentAccount = allAccounts.get(i);
            builder.append( (i+1) + ":\t"
                    + currentAccount.getAccount_number() + "\t\t\t\t"
                    + currentAccount.getBalance() + "\t\t"
                    + currentAccount.isApproved() + "\t\t\t"
                    + userDAO.accountToUser(currentAccount).getUsername()
            );
            builder.append("\r\n");
        }

        return builder.toString();
    }
}
