package com.company.Views;

import com.company.Views.LoginView.LoginView;

import java.util.HashMap;
import java.util.Map;

public class ViewManager {

    public ViewManager(){
        this.viewManager = new HashMap<>();
        this.viewManager.put("login",new LoginView());
    }

    private Map<String, View> viewManager;
}
