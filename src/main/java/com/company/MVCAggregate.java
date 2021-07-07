package com.company;

import com.company.Controllers.DataAccess.DAOImpls.UserDAOImpl;
import com.company.Controllers.Endpoints.Endpoint;
import com.company.Models.ModelTemplates.User;
import com.company.Views.AuthenticatedViews.EmployeeViews.*;
import com.company.Views.AuthenticatedViews.CustomerViews.DepositViewTemplate;
import com.company.Views.InitialView.InitialViewTemplate;
import com.company.Views.InitialView.LoginViewTemplate;
import com.company.Views.AuthenticatedViews.MenuViewTemplate;
import com.company.Views.AuthenticatedViews.CustomerViews.MyAccountOptionsViewTemplate;
import com.company.Views.AuthenticatedViews.CustomerViews.MyAccountsViewTemplate;
import com.company.Views.AuthenticatedViews.CustomerViews.MyTransactionsViewTemplate;
import com.company.Views.AuthenticatedViews.CustomerViews.TransferViewTemplate;
import com.company.Views.InitialView.RegistrationViewTemplate;
import com.company.Views.ViewTemplate;
import com.company.Views.AuthenticatedViews.CustomerViews.WithdrawlViewTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static com.company.MainDriver.logger;

public class MVCAggregate {

    public MVCAggregate(){
        this.initializeViews();
        this.connectAggregate();
        this.currentViewTemplate = this.title_view.get("initial_view");

    }
    public void initializeViews(){
        this.title_view = new HashMap<>();
        this.title_view.put("initial_view", new InitialViewTemplate());
        this.title_view.put("login_view",new LoginViewTemplate());
        this.title_view.put("registration_view", new RegistrationViewTemplate());
        this.title_view.put("menu_view",new MenuViewTemplate());
        this.title_view.put("all_accounts_view",new AllAccountViewTemplate());
        this.title_view.put("account_accounts_edit_view",new AllAccountEditViewTemplate());
        this.title_view.put("all_users_view", new AllUsersViewTemplate());
        this.title_view.put("all_user_accounts_view", new AllUsersAccountViewTemplate());
        this.title_view.put("account_apply_view",new AccountApplyViewTemplate());
        this.title_view.put("my_accounts_view",new MyAccountsViewTemplate());
        this.title_view.put("my_account_options", new MyAccountOptionsViewTemplate());
        this.title_view.put("withdrawl_view", new WithdrawlViewTemplate());
        this.title_view.put("deposit_view",new DepositViewTemplate());
        this.title_view.put("transfer_view",new TransferViewTemplate());
        this.title_view.put("my_transactions_view",new MyTransactionsViewTemplate());
        this.title_view.put("all_transactions_view",new AllTransactionsView());

    }
    public void connectAggregate(){
        for ( Map.Entry<String, ViewTemplate> title_view_set: this.title_view.entrySet()){
            title_view_set.getValue().getEndpoint().setAggregate(this);
        }

    }

    public void startDispatcher(){
        String[]  requiredResponse;
        Scanner scanner = new Scanner(System.in);

        loop1:
        while (true ){
         String preData = this.currentViewTemplate.getEndpoint().preData() ;
            if ( preData != null ){
                System.out.println(preData);
            }
            requiredResponse = new String[this.currentViewTemplate.body().length];
            if ( currentUser != null) {
                System.out.println("\t\t\tEnter MENU to go back to menu at anytime or RELOAD to reload view");
            }
            for ( int i = 0 ; i < requiredResponse.length; i++){
                System.out.println(this.currentViewTemplate.body()[i]);
                String input = scanner.nextLine();
                if ( currentUser != null){
                    if ( input.equals("MENU")){
                        this.currentViewTemplate = this.title_view.get("menu_view");
                        continue  loop1;
                    }else if ( input.equals("RELOAD")){
                        continue loop1;
                    }
                }
                requiredResponse[i] = input;
            }
            Endpoint endpoint = this.currentViewTemplate.getEndpoint();
            this.clearScreenSimulation();
            String[] response = endpoint.response(requiredResponse);
            if (response[0].equals("error")){
                clearScreenSimulation();
                System.out.println("ERROR: " + response[1] + " please reenter");
            }else{
                System.out.println(response[0] + ": " + response[1]);
                if (currentViewTemplate.getViewName().equals("login_view")){
                    User successfulUser = new UserDAOImpl().getUserByUsername(requiredResponse[0]);
                    intializeUserToEndpoint(successfulUser);
                }
                this.currentViewTemplate = this.title_view.get(response[1]);


            }

        }

    }

    private static void clearScreenSimulation(){

        StringBuilder builder = new StringBuilder();
        builder.append("\r\n\r\n");
        for ( int i = 0 ;i < 100; i++){
            builder.append("\r\n\r\n");
        }
        System.out.println(builder.toString());


    }
    private void intializeUserToEndpoint(User user){
        for ( Map.Entry<String, ViewTemplate> viewEntry: title_view.entrySet()){
            ViewTemplate temp = viewEntry.getValue();
            temp.setUser(user);
            temp.getEndpoint().setCurrentUser(user);
        }
        this.currentUser = user;

    }
    public Map<String, ViewTemplate> getTitle_view(){
        return title_view;
    }


    public User currentUser = null;
    private ViewTemplate currentViewTemplate;
    private Map<String, ViewTemplate> title_view;

}
