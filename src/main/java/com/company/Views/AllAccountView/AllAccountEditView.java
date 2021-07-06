package com.company.Views.AllAccountView;

import com.company.Controllers.Endpoints.AllAccountEndpoint.AllAccountEndpoint;
import com.company.Controllers.Endpoints.AllAccountEndpoint.AllAccountEndpointEdit;
import com.company.Controllers.Endpoints.Endpoint;
import com.company.Models.ModelTemplates.User;
import com.company.Views.View;

public class AllAccountEditView implements View {
    @Override
    public String[] body() {
        return new String[]{
                "Would you like to approve or disapprove account: (1 for approve 2 for disapprove)"
        };

    }

    @Override
    public Endpoint getEndpoint() {
        return this.endpoint;

    }

    @Override
    public String getViewName() {
        return "account_accounts_edit_view";

    }

    @Override
    public void setUser(User user) {

    }

    @Override
    public User getUser() {
        return null;
    }
    private Endpoint endpoint = new AllAccountEndpointEdit();
}
