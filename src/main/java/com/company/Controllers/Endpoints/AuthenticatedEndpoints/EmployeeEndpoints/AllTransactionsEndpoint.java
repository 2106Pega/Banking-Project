package com.company.Controllers.Endpoints.AuthenticatedEndpoints.EmployeeEndpoints;

import com.company.Controllers.DataAccess.DAOImpls.TransactionDAOImpl;
import com.company.Controllers.DataAccess.DAOImpls.UserDAOImpl;
import com.company.Controllers.Endpoints.Endpoint;
import com.company.Models.ModelTemplates.Account;
import com.company.Models.ModelTemplates.Transaction;
import com.company.Models.ModelTemplates.User;
import dnl.utils.text.table.TextTable;

import java.util.ArrayList;

public class AllTransactionsEndpoint implements Endpoint {
    @Override
    public String[] response(String[] parameters) {
        return new String[]{
                "success",
                "menu_view"
        };
    }

    @Override
    public String preData() {
        if ( impl.getUserByUsername(this.myAccount.getUsername()).getAccessLevel() != 2){
            return "";
        }
        /*
        All transaction
        with account/ routing
        with username
         */
        String[] columns = new String[]{
                "Index",
                "Username",
                "Routing Number",
                "Account Number",
                "Transaction Type",
                "Transaction Amount",
                "Date"
        };
        ArrayList<String[]> rowsList = new ArrayList<>();
        ArrayList<User> allUsers = new UserDAOImpl().getAllUsers();
        TransactionDAOImpl transactionDAO = new TransactionDAOImpl();

        int indexCount = 1;
        for ( User user : allUsers){
            ArrayList<Account> usersAccounts = user.getAccounts();
            for ( Account account: usersAccounts){
                ArrayList<Transaction> transactions = transactionDAO.accountsTransactions(account);
                if ( transactions.size() == 0 ){
                    continue;
                }
                for ( Transaction transaction : transactions) {
                    String[] row = new String[]{
                            String.valueOf(indexCount),
                            user.getUsername(),
                            String.valueOf(account.getRouting_number()),
                            String.valueOf(account.getAccount_number()),
                            transaction.getRequestType(),
                            String.valueOf(transaction.getAmount()),
                            String.valueOf(transaction.getDate())
                    };
                    rowsList.add(row);
                    indexCount++;
                }
            }
        }
        String[][] rows = new String[rowsList.size()][7];
        for ( int i = 0; i < rowsList.size(); i++){
            rows[i] = rowsList.get(i);
        }
        TextTable textTable = new TextTable(columns,rows);

        textTable.printTable();


        return "";
    }
    @Override
    public void setCurrentUser(User user) {
        this.myAccount = user;
    }

    private User myAccount;
    private UserDAOImpl impl = new UserDAOImpl();

}
