package com.revature;



import org.apache.log4j.Logger;

import com.revature.presentation.BankFrontImpl;



public class MainDriver {
	
	
	
	
	//final static Logger logger = Logger.getLogger(MainDriver.class);

	public static void main(String[] args) {

		
		BankFrontImpl.frontEnd(new BankFrontImpl());
		
		
		
		//logger.log("xxx");
		
		

	}

}
