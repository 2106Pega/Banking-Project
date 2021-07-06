package com.company.Views.TransferView;

import com.company.Controllers.Endpoints.Endpoint;
import com.company.Controllers.Endpoints.TransactionsEndpoint.TransactionEndpoint;
import com.company.Controllers.Endpoints.TransferEndpoint.TransferEndpoint;
import com.company.Models.ModelTemplates.User;
import com.company.Views.View;

public class TransferView implements View {
    @Override
    public String[] body() {
        return new String[]{
                "Please enter a the amount you would like to transfer",
                "Please enter the ROUTING number of reciever",
                "Please enter the ACCOUNT number of the reciever"
        };
    }

    @Override
    public Endpoint getEndpoint() {
        return endpoint;
    }

    @Override
    public String getViewName() {
        return "transfer_view";
    }

    @Override
    public void setUser(User user) {

    }

    @Override
    public User getUser() {
        return null;
    }
    
    private Endpoint endpoint = new TransferEndpoint();
}
