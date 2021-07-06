package com.company.MVCAggregate;

import com.company.Controllers.DataAccess.DAOImpls.UserDAOImpl;
import com.company.Controllers.Endpoints.Endpoint;
import com.company.Models.ModelTemplates.User;
import com.company.Views.AccountApplyView.AccountApplyView;
import com.company.Views.AllAccountView.AllAccountEditView;
import com.company.Views.AllAccountView.AllAccountView;
import com.company.Views.AllUsersView.AllUsersAccountView;
import com.company.Views.AllUsersView.AllUsersView;
import com.company.Views.DepositView.DepositView;
import com.company.Views.InitialView.InitialView;
import com.company.Views.LoginView.LoginView;
import com.company.Views.MenuView.MenuView;
import com.company.Views.MyAccountOptionsView.MyAccountOptionsView;
import com.company.Views.MyAccountsView.MyAccountsView;
import com.company.Views.RegistrationView.RegistrationView;
import com.company.Views.TransferView.TransferView;
import com.company.Views.View;
import com.company.Views.WithdrawlView.WithdrawlView;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MVCAggregate {

    public MVCAggregate(){
        this.initializeViews();
        this.connectAggregate();
        this.currentView = this.title_view.get("initial_view");

    }
    public void initializeViews(){
        this.title_view = new HashMap<>();
        this.title_view.put("initial_view", new InitialView());
        this.title_view.put("login_view",new LoginView());
        this.title_view.put("registration_view", new RegistrationView());
        this.title_view.put("menu_view",new MenuView());
        this.title_view.put("all_accounts_view",new AllAccountView());
        this.title_view.put("account_accounts_edit_view",new AllAccountEditView());
        this.title_view.put("all_users_view", new AllUsersView());
        this.title_view.put("all_user_accounts_view", new AllUsersAccountView());
        this.title_view.put("account_apply_view",new AccountApplyView());
        this.title_view.put("my_accounts_view",new MyAccountsView());
        this.title_view.put("my_account_options", new MyAccountOptionsView());
        this.title_view.put("withdrawl_view", new WithdrawlView());
        this.title_view.put("deposit_view",new DepositView());
        this.title_view.put("transfer_view",new TransferView());
        
    }
    public void connectAggregate(){
        for ( Map.Entry<String, View> title_view_set: this.title_view.entrySet()){
            title_view_set.getValue().getEndpoint().setAggregate(this);
        }

    }

    public void startDispatcher(){

        String[]  requiredResponse;
        Scanner scanner = new Scanner(System.in);

        loop1:
        while (true ){
         String preData = this.currentView.getEndpoint().preData() ;
            if ( preData != null ){
                System.out.println(preData);
            }
            requiredResponse = new String[this.currentView.body().length];
            if ( currentUser != null) {
                System.out.println("\t\t\tEnter MENU to go back to menu at anytime or RELOAD to reload view");
            }
            for ( int i = 0 ; i < requiredResponse.length; i++){
                System.out.println(this.currentView.body()[i]);
                String input = scanner.nextLine();
                if ( currentUser != null){
                    if ( input.equals("MENU")){
                        this.currentView = this.title_view.get("menu_view");
                        continue  loop1;
                    }else if ( input.equals("RELOAD")){
                        continue loop1;
                    }
                }
                requiredResponse[i] = input;
            }
            Endpoint endpoint = this.currentView.getEndpoint();
            this.clearScreenSimulation();
            String[] response = endpoint.response(requiredResponse);
            if (response[0].equals("error")){
                clearScreenSimulation();
                System.out.println("ERROR: " + response[1] + " please reenter");
            }else{
                System.out.println(response[0] + ": " + response[1]);
                if (currentView.getViewName().equals("login_view")){
                    User successfulUser = new UserDAOImpl().getUserByUsername(requiredResponse[0]);
                    intializeUserToEndpoint(successfulUser);
                }
                this.currentView = this.title_view.get(response[1]);


            }

        }

    }

    private void clearScreenSimulation(){
        StringBuilder builder = new StringBuilder();
        builder.append("\r\n\r\n");
        for ( int i = 0 ;i < 100; i++){
            builder.append("\r\n\r\n");
        }
        System.out.println(builder.toString());
    }
    private void intializeUserToEndpoint(User user){
        for ( Map.Entry<String, View> viewEntry: title_view.entrySet()){
            View temp = viewEntry.getValue();
            temp.setUser(user);
            temp.getEndpoint().setCurrentUser(user);
        }
        this.currentUser = user;

    }
    public Map<String,View> getTitle_view(){
        return title_view;
    }


    private User currentUser = null;
    private View currentView;
    private Map<String, View> title_view;

}
