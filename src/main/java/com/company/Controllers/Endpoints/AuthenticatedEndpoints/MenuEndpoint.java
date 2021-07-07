package com.company.Controllers.Endpoints.AuthenticatedEndpoints;

import com.company.Controllers.DataAccess.DAOImpls.UserDAOImpl;
import com.company.Controllers.Endpoints.Endpoint;
import com.company.Controllers.Endpoints.AuthenticatedEndpoints.MenuBuilderHelper.MenuItem;
import com.company.Models.ModelTemplates.User;
import com.company.Controllers.Endpoints.AuthenticatedEndpoints.MenuBuilderHelper.MenuBuilderHelper;
import dnl.utils.text.table.SeparatorPolicy;
import dnl.utils.text.table.TextTable;

import java.util.ArrayList;

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
        this.menuItems = new ArrayList<>();
        User user = this.getCurrentUser();
        UserDAOImpl userDAO = new UserDAOImpl();
        User userDb = userDAO.getUserByUsername(user.getUsername());
        MenuBuilderHelper menuBuilderHelper = new MenuBuilderHelper();
        menuItems.addAll(menuBuilderHelper.access_level_one());
        if ( userDb.getAccessLevel() == 2){
            access_level_two_added = true;
            menuItems.addAll(menuBuilderHelper.access_level_two());
        }
        String[] columns = new String[]{
                "Index",
                "Option"
        };
        String[][] rows = new String[menuItems.size()][2];
        for ( int i = 0 ; i < menuItems.size(); i++){
            rows[i][0] = String.valueOf(i+1);
            rows[i][1] = menuItems.get(i).title;
        }
        TextTable textTable = new TextTable(columns,rows);
        textTable.printTable();
        return "";
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
