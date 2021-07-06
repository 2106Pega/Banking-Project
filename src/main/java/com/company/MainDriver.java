package com.company;


import com.company.MVCAggregate.MVCAggregate;


public class MainDriver {
    public static void main(String[] args){

        MVCAggregate aggregate = new MVCAggregate();
        aggregate.startDispatcher();


    }
}
