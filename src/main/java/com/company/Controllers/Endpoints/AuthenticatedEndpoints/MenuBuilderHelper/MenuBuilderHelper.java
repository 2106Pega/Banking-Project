package com.company.Controllers.Endpoints.AuthenticatedEndpoints.MenuBuilderHelper;

import java.util.ArrayList;

public class MenuBuilderHelper {

    public ArrayList<MenuItem> access_level_one(){
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Apply for account","account_apply_view"));
        menuItems.add(new MenuItem("View my Accounts", "my_accounts_view"));
        menuItems.add(new MenuItem("View my transactions","my_transactions_view"));
        
        return menuItems;
    }
    public ArrayList<MenuItem> access_level_two(){
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("View All Accounts","all_accounts_view"));
        menuItems.add(new MenuItem("View all Users","all_users_view"));
        menuItems.add(new MenuItem("View all Transactions","all_transactions_view"));

        return menuItems;
    }


}
