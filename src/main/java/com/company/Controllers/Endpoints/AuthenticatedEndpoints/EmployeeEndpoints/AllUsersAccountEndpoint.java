package com.company.Controllers.Endpoints.AuthenticatedEndpoints.EmployeeEndpoints;

import com.company.Controllers.DataAccess.DAOImpls.AccountDAOImpl;
import com.company.Controllers.DataAccess.DAOImpls.UserDAOImpl;
import com.company.Controllers.Endpoints.AuthenticatedEndpoints.AccountsPredataHelper;
import com.company.Controllers.Endpoints.Endpoint;
import com.company.MVCAggregate;
import com.company.Models.ModelTemplates.Account;
import com.company.Models.ModelTemplates.User;

import java.util.ArrayList;

public class AllUsersAccountEndpoint implements Endpoint {
    @Override
    public String[] response(String[] parameters) {
        if ( impl.getUserByUsername(this.myAccount.getUsername()).getAccessLevel() != 2){
            return new String[]{
                    "success",
                    "menu_view"
            };
        }
        AllAccountEndpointEdit accountUpdateEndpoint = new AllAccountEndpointEdit();
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
        AccountsPredataHelper helper = new AccountsPredataHelper();
        AccountDAOImpl impl = new AccountDAOImpl();
        usersAccounts = impl.getAccountByUser(this.userToUpdate);
        return helper.allAccountsToPredataString(this.usersAccounts);
    }

    @Override
    public void setObject(Object obj) {
        this.userToUpdate = (User) obj;
    }

    @Override
    public void setCurrentUser(User user) {
        this.myAccount = user;
    }

    @Override
    public User getCurrentUser() {
        return null;
    }

    @Override
    public MVCAggregate getAggregate() {
        return null;
    }

    @Override
    public void setAggregate(MVCAggregate aggregate) {

    }


    private UserDAOImpl impl = new UserDAOImpl();
    private User myAccount;
    private AllAccountEndpointEdit endpointEdit;
    private ArrayList<Account> usersAccounts;
    private User userToUpdate;
}
