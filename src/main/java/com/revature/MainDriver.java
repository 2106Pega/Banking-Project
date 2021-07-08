package com.revature;

import java.util.Scanner;

import com.revature.display.Display;


public class MainDriver {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		boolean ans = false;
		Display d = new Display();
		do {
			d.main_menu();
			System.out.println("Do you want to exit: ");
			System.out.println("1. Yes");
			System.out.println("2. No");
			System.out.println("Please enter the number of the options.");
			String answer = sc.nextLine();
			if(answer.equals("1"))
			{
				ans = false;
			}
			else if(answer.equals("2")) {
				ans = true;
			}
			else
			{
				System.out.println("Invalid option! Program ends.");
				break;
			}
		}while(ans);
		
	}

}
