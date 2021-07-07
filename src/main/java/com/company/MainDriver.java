package com.company;


import com.company.Models.ModelTemplates.Account;
import org.apache.log4j.Logger;
public class MainDriver {


    public static final Logger logger = Logger.getLogger(MainDriver.class.getName());
    public static void logTransaction(Account account, String type){

        type = "-Transacation Type-" + type;
        String routingNumber = "-Routing number-" + account.getRouting_number();
        String accountNumber = "-Account number-" + account.getAccount_number();
        String currentBalance = "-Current Balance-" + account.getBalance();

        logger.info(type + routingNumber + accountNumber + currentBalance);
    }
    public static void main(String[] args){



        MVCAggregate aggregate = new MVCAggregate();
        aggregate.startDispatcher();
    }
}
