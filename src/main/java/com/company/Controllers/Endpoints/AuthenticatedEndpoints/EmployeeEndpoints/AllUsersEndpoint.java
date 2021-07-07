package com.company.Controllers.Endpoints.AuthenticatedEndpoints.EmployeeEndpoints;

import com.company.Controllers.DataAccess.DAOImpls.UserDAOImpl;
import com.company.Controllers.Endpoints.Endpoint;
import com.company.MVCAggregate;
import com.company.Models.ModelTemplates.Account;
import com.company.Models.ModelTemplates.User;
import dnl.utils.text.table.TextTable;

import java.util.ArrayList;

public class AllUsersEndpoint implements Endpoint {
    @Override
    public String preData() {
        if ( impl.getUserByUsername(this.myAccount.getUsername()).getAccessLevel() != 2){
            return "";
        }
        String[] columns = new String[]{
                "index",
                "Username",
                "First Name",
                "Last name",
                "Number of Accounts"
        };
        allUserAccounts = new UserDAOImpl().getAllUsers();
        String[][] rows = new String[allUserAccounts.size()][columns.length];
        for ( int i =0 ; i < allUserAccounts.size(); i++){
            User user = allUserAccounts.get(i);
            ArrayList<Account> accounts = user.getAccounts();
            rows[i][0] = String.valueOf(i+1);
            rows[i][1] = user.getUsername();
            rows[i][2] = user.getFirst_name();
            rows[i][3] = user.getLast_name();
            rows[i][4] = String.valueOf(user.getAccounts().size());
        }
        TextTable textTable = new TextTable(columns,rows);
        textTable.printTable();
        return "";
    }

    @Override
    public void setAggregate(MVCAggregate aggregate) {
        this.aggregate = aggregate;
    }

    @Override
    public String[] response(String[] parameters) {
        if ( impl.getUserByUsername(this.myAccount.getUsername()).getAccessLevel() != 2){
            return new String[]{
                    "success",
                    "menu_view"
            };
        }
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
        if ( user.getAccounts().size() == 0){
            return new String[]{
                    "error",
                    "Selected customer has no active accounts"
            };
        }
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
    public void setCurrentUser(User user) {
        this.myAccount = user;
    }

    @Override
    public MVCAggregate getAggregate() {
        return null;
    }

    private User myAccount;
    private UserDAOImpl impl = new UserDAOImpl();
    private ArrayList<User> allUserAccounts;
    private MVCAggregate aggregate;
}
