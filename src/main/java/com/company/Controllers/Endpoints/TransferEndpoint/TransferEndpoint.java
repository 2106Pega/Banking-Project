package com.company.Controllers.Endpoints.TransferEndpoint;

import com.company.Controllers.DataAccess.DAO.TransactionsDAO;
import com.company.Controllers.DataAccess.DAOImpls.AccountDAOImpl;
import com.company.Controllers.DataAccess.DAOImpls.TransactionDAOImpl;
import com.company.Controllers.Endpoints.AllAccountEndpoint.AccountsPredataHelper;
import com.company.Controllers.Endpoints.Endpoint;
import com.company.Models.ModelTemplates.Account;

import java.util.ArrayList;

public class TransferEndpoint implements Endpoint {
    @Override
    public String[] response(String[] parameters) {
        try{
            double transferAmount = Double.parseDouble(parameters[0]);
            int routingNumber = Integer.parseInt(parameters[1]);
            int accountNumber = Integer.parseInt(parameters[2]);
            if ( transferAmount <= 0 ){
                return new String[]{
                        "error",
                        "Transfer amount must be greater than zero"
                };
            }
            if ( transferAmount > this.currentAccount.getBalance() ){
                return new String[]{
                        "error",
                        "Transfer amount cannot exceed balance"
                };
            }
            AccountDAOImpl accountDAO = new AccountDAOImpl();
            Account recieverAccount  = accountDAO.accountByAccountRoutingNumber(routingNumber,accountNumber);
            if ( recieverAccount == null){
                return new String[]{
                        "error",
                        "Reciever account does not exist please double check the account or routing number"
                };
            }
            recieverAccount.setBalance(recieverAccount.getBalance() + transferAmount);
            this.currentAccount.setBalance(this.currentAccount.getBalance()-transferAmount);
            accountDAO.saveAccount(recieverAccount);
            accountDAO.saveAccount(this.currentAccount);
            TransactionDAOImpl transactionDAO = new TransactionDAOImpl();
            
            transactionDAO.addTransaction("Recieved Transfer",recieverAccount.getAccount_id(),transferAmount);
            transactionDAO.addTransaction("Sent transfer",this.currentAccount.getAccount_id(),transferAmount);
            
            return new String[]{
              "success",
              "my_accounts_view"      
            };
        }catch (Exception e){
            return new String[]{
                    "error",
                    "Invalid Selection"
            };
        }
    }
    @Override
    public String preData() {
        ArrayList<Account> currentAccount = new ArrayList<>();
        currentAccount.add(this.currentAccount);
        AccountsPredataHelper helper = new AccountsPredataHelper();
        return (helper.allAccountsToPredataString(currentAccount));

    }

    @Override
    public void setObject(Object obj) {
        this.currentAccount = (Account) obj;
    }
    
    private Account currentAccount;
}
