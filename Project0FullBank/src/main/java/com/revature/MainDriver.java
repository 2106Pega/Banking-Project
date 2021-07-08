package com.revature;

import com.revature.presentation.BankUi;
import com.revature.presentation.BankUiImpl;

public class MainDriver {
	
	public static void main(String[] args) {
		
		BankUi ui = new BankUiImpl();
		
		ui.displayWelcomeMenu();
		
	}

}
