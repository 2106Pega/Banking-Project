package com.company.Controllers.Endpoints.MenuEndpoint.MenuBuilderHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MenuBuilderHelper {

    public ArrayList<MenuItem> access_level_one(){
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Apply for account","account_apply_view"));
        menuItems.add(new MenuItem("View my Accounts", "my_accounts_view"));

        return menuItems;
    }
    public ArrayList<MenuItem> access_level_two(){
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("View All Accounts","all_accounts_view"));
        menuItems.add(new MenuItem("View all Users","all_users_view"));
        menuItems.add(new MenuItem("View all Transactions","all_transactions_view"));

        return menuItems;
    }
    public String menuItemsToString(ArrayList<MenuItem> items){
        StringBuilder builder = new StringBuilder();
        for ( int i = 0 ; i < items.size(); i++){
            builder.append((i+1) + ":" + items.get(i).title +"\r\n");
        }
        return builder.toString();
    }

}
