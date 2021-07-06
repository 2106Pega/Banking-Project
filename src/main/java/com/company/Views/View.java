package com.company.Views;

import com.company.Controllers.Endpoints.Endpoint;
import com.company.Models.ModelTemplates.User;

public interface View {

    String[] body();
    Endpoint getEndpoint();
    String getViewName();
    void setUser(User user);
    User getUser();

}
