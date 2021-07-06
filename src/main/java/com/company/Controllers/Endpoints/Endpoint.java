package com.company.Controllers.Endpoints;

import com.company.MVCAggregate.MVCAggregate;
import com.company.Models.ModelTemplates.User;

public interface Endpoint {
    String[] response(String[] parameters);
    default String preData(){
        return null;
    }
    default void setObject(Object obj){

    }

    default void setCurrentUser(User user){ //Acts as SESSIONS in HTTP
        return;
    }
    default User getCurrentUser(){
        return null;
    }

    default MVCAggregate getAggregate(){
        /*
        Works as a direct connection to the views if one has to be initilalized preload
        Acts like a handler for GET request
        works directly with set object
         */
        return null;
    }
    default void setAggregate(MVCAggregate aggregate){

    }

}
