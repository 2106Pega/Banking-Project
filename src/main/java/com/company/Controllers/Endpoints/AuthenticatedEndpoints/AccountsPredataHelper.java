package com.company.Controllers.Endpoints.AuthenticatedEndpoints;

import com.company.Controllers.DataAccess.DAOImpls.UserDAOImpl;
import com.company.Models.ModelTemplates.Account;
import com.company.Models.ModelTemplates.User;
import dnl.utils.text.table.TextTable;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AccountsPredataHelper {

    public String allAccountsToPredataString(ArrayList<Account> allAccounts){
        UserDAOImpl userDAO = new UserDAOImpl();

        String[] columns = new String[]{
                "Index",
                "Account Number",
                "Routing Number",
                "Is Approved",
                "Username",
                "Amount"
        };
        String[][] data = new String[allAccounts.size()][6];


        for ( int i = 0 ; i < allAccounts.size(); i++){
            Account currentAccount = allAccounts.get(i);
            data[i][0] = String.valueOf(i+1);
            data[i][1] = String.valueOf(currentAccount.getAccount_number());
            data[i][2] = String.valueOf(currentAccount.getRouting_number());
            data[i][3] = accountStatus(currentAccount.isApproved(),currentAccount.isReviewed());
            data[i][4] = userDAO.accountToUser(currentAccount).getUsername();
            data[i][5] = formatter.format(currentAccount.getBalance());

        }
        TextTable textTable = new TextTable(columns,data);
        textTable.printTable();
        return "";
    }
    private String accountStatus(boolean approved, boolean review){
        if ( !approved && review == false){
            return  "PENDING";
        }else if ( approved && review == true){
            return "ACTIVE";
        }else if ( !approved && review == true ){
            return "DENIED";
        }
        return "ACTIVE";
    }
    private DecimalFormat formatter = new DecimalFormat("#.##");
}
