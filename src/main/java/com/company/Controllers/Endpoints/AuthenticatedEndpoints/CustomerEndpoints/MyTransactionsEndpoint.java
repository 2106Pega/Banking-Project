package com.company.Controllers.Endpoints.AuthenticatedEndpoints.CustomerEndpoints;

import com.company.Controllers.DataAccess.DAOImpls.TransactionDAOImpl;
import com.company.Controllers.Endpoints.Endpoint;
import com.company.Models.ModelTemplates.Account;
import com.company.Models.ModelTemplates.Transaction;
import com.company.Models.ModelTemplates.User;
import dnl.utils.text.table.TextTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyTransactionsEndpoint implements Endpoint {
    @Override
    public String[] response(String[] parameters) {
        return new String[]{
                "success",
                "menu_view"
        };
    }

    @Override
    public String preData() {
        Map<Account,ArrayList<Transaction> > accountTransactions = new HashMap<>();
        TransactionDAOImpl transactionDAO = new TransactionDAOImpl();


        String[] columns = new String[]{
                "Routing Number",
                "Account Number",
                "Transaction Type",
                "Amount"
        };
        int rowCount = 0;
        for (Account account: currentUser.getAccounts()){
            ArrayList<Transaction> accountTransactionsList = transactionDAO.accountsTransactions(account);
            if ( accountTransactionsList.size() == 0){
                continue;
            }
            rowCount += accountTransactionsList.size();
            accountTransactions.put(account,accountTransactionsList);
        }
        String[][] rows = new String[rowCount][4];
        int currentRow = 0;
        for ( Map.Entry<Account,ArrayList<Transaction>> entrySet: accountTransactions.entrySet()){
            Account currentAccount = entrySet.getKey();
            ArrayList<Transaction> linkedTransactions = entrySet.getValue();
            for ( Transaction transaction: linkedTransactions){
                rows[currentRow][0] = String.valueOf(currentAccount.getRouting_number());
                rows[currentRow][1] = String.valueOf(currentAccount.getAccount_number());
                rows[currentRow][2] = transaction.getRequestType();
                rows[currentRow][3] = String.valueOf(transaction.getAmount());
                currentRow++;
            }
        }
        TextTable textTable = new TextTable(columns,rows);
        textTable.printTable();



        return "";
    }

    @Override
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    private User currentUser;
}
