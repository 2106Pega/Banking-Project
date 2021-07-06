package com.company.Controllers.Endpoints.AllUsersEndpoint;

import com.company.Controllers.DataAccess.DAOImpls.AccountDAOImpl;
import com.company.Controllers.Endpoints.AllAccountEndpoint.AccountsPredataHelper;
import com.company.Controllers.Endpoints.AllAccountEndpoint.AllAccountEndpointEdit;
import com.company.Controllers.Endpoints.Endpoint;
import com.company.MVCAggregate.MVCAggregate;
import com.company.Models.ModelTemplates.Account;
import com.company.Models.ModelTemplates.User;

import java.util.ArrayList;

public class AllUsersAccountEndpoint implements Endpoint {
    @Override
    public String[] response(String[] parameters) {
        AllAccountEndpointEdit accountUpdateEndpoint = new AllAccountEndpointEdit();
        return new String[0];
    }

    @Override
    public String preData() {
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

    private AllAccountEndpointEdit endpointEdit;
    private ArrayList<Account> usersAccounts;
    private User userToUpdate;
}
