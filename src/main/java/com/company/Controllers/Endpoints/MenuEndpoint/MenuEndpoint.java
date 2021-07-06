package com.company.Controllers.Endpoints.MenuEndpoint;

import com.company.Controllers.DataAccess.DAOImpls.UserDAOImpl;
import com.company.Controllers.Endpoints.Endpoint;
import com.company.Controllers.Endpoints.MenuEndpoint.MenuBuilderHelper.MenuItem;
import com.company.Models.ModelTemplates.User;
import com.company.Controllers.Endpoints.MenuEndpoint.MenuBuilderHelper.MenuBuilderHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MenuEndpoint implements Endpoint {
    @Override
    public String[] response(String[] parameters) {
        try{
          Integer.parseInt(parameters[0]);
        }catch (Exception e){
            return new String[]{
                    "error",
                    "Invalid entry"
            };
        }
        int selection = Integer.parseInt(parameters[0]);
        if ( selection <= 0 || selection > menuItems.size()){
            return new String[]{
                    "error",
                    "Invalid entry"
            };
        }

        return new String[]{
                "success",
                this.menuItems.get( Integer.parseInt(parameters[0])-1 ).view_name
        };
    }
    @Override
    public String preData(){
        User user = this.getCurrentUser();
        System.out.println(user.getUsername());
        UserDAOImpl userDAO = new UserDAOImpl();
        User userDb = userDAO.getUserByUsername(user.getUsername());
        MenuBuilderHelper menuBuilderHelper = new MenuBuilderHelper();
        if ( userDb.getAccessLevel() == 1 && !access_level_one_added){
            access_level_one_added = true;
            menuItems.addAll(menuBuilderHelper.access_level_one());
        }
        if ( userDb.getAccessLevel() == 2 && !access_level_two_added){
            access_level_two_added = true;
            menuItems.addAll(menuBuilderHelper.access_level_two());
        }

        return menuBuilderHelper.menuItemsToString(menuItems);
    }
    @Override
    public User getCurrentUser(){
        return currentUser;
    }
    @Override
    public void setCurrentUser(User user){
        this.currentUser = user;
    }

    public boolean access_level_one_added = false;
    public boolean access_level_two_added = false;
    public User currentUser;
    public ArrayList<MenuItem> menuItems = new ArrayList<>();
}
