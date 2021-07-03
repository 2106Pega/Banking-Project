package com.revature.pzero;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.revature.pzero.presentation.DisplayTerminal;

public class MainDriver {
	
	public static void main(String[] args) {
		DisplayTerminal terminal = new DisplayTerminal();
		terminal.welcome();
		System.out.println("MainDriver: END.");
	}

}
