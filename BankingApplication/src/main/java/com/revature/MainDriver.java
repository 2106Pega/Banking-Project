package com.revature;

import com.revature.presentation.BankingUI;

public class MainDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		System.out.println("Hello World");
		
//		try {
//			LogFile logFile = new LogFile("log.txt");
//			
//			logFile.logger.setLevel(Level.INFO);
//			
//			logFile.logger.info("Info msg");
//		}catch(Exception e ) {
//			
//		}
		
		BankingUI bankUI = new BankingUI();
		bankUI.welcomeMenu();

	}

}
