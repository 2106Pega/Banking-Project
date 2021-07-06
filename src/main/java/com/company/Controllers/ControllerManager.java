package com.company.Controllers;

import com.company.Controllers.Endpoints.Endpoint;
import com.company.Controllers.Endpoints.LoginEndpoint.LoginEndpoint;

import java.util.HashMap;
import java.util.Map;

public class ControllerManager {
    public ControllerManager() {
        this.type_endpoint = new HashMap<>();
        this.type_endpoint.put("login", new LoginEndpoint());
    }



    private Map<String, Endpoint> type_endpoint;
}
