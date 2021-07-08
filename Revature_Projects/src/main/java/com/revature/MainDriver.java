package com.revature;
import java.awt.*;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;

import javax.swing.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.revature.exceptions.InvalidCredentialsException;
import com.revature.models.Employee;
import com.revature.models.User;
import com.revature.repo.UserDAO;
import com.revature.repo.UserDAOImpl;
import com.revature.util.ConnectionFactory;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class MainDriver
{
	public static void main(String[] args) throws InvalidCredentialsException
	{
		UserDAO userDao = new UserDAOImpl();
		try
		{
			File transactionLog = new File("C:\\Users\\grant\\Desktop\\Transaction Log.txt");
	        if (transactionLog.createNewFile())
	        {
	        	//System.out.println("File created: " + myObj.getName());
	        }
	        else
	        {
	        	//System.out.println("File already exists.");
	        }
		}
		catch (IOException e)
		{
			System.out.println("An error occurred. Likely an invalid file path.");
		    e.printStackTrace();
		}
		//createMainWindow();		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Hello! Welcome to Revature Bank. Are you a customer or an employee? Enter 'customer' or 'employee'.");
		String firstAnswer;
		String secondAnswer;
		boolean done = false;
        while (!done)
        {
            try
            {
                firstAnswer = scanner.nextLine();
                if (firstAnswer.equals("customer"))
        		{
                	try
                	{
                		System.out.println("Are you a returning customer? Enter 'yes' or 'no'.");
                		secondAnswer = scanner.nextLine();
                		if (secondAnswer.equals("yes"))
                		{
                            done = true;
                            ReturningUserLogin(scanner, userDao);
                		}
                		else if (secondAnswer.equals("no"))
                		{
                            done = true;
                            NewUserRegistration(scanner, userDao);
                		}
                		else
                		{
                			InvalidInputMessage();
                		}
                	}
                	catch (InputMismatchException e)
                	{
                		e.printStackTrace();
                	}
                }
                else if (firstAnswer.equals("employee"))
                {
                    EmployeeLogin(scanner, userDao);
                }
                else
                {
                	InvalidInputMessage();
                }
            }
            catch (InputMismatchException e)
            {
            	InvalidInputMessage();
                //scanner.nextLine();  // Clear invalid input from scanner buffer.
            }
        }
	}

	static void ReturningUserLogin(Scanner scanner, UserDAO userDao) throws InvalidCredentialsException/*, SQLException*/
	{
		String username;
		String password;
		int id;
		boolean done = false;
		while (!done)
        {
            try
            {
            	System.out.println("Please enter your username.");
        		//String username = scanner.nextLine();
            	username = scanner.nextLine();
        		System.out.println("Please enter your password.");
        		//String password = scanner.nextLine();
        		password = scanner.nextLine();
        		System.out.println("Please enter your customer ID number.");
        		id = scanner.nextInt();
        		if (userDao.selectUserByAllCredentials(username, password, id).size() >= 1)
        		{
            		done = true;
            		//scanner.close();
            		User user = userDao.selectUserByAllCredentials(username, password, id).get(0);
            		System.out.println("Login successful.");
            		UserOptionsMenu(scanner, userDao, user);
        		}
        		else
        		{
        			throw new InvalidCredentialsException();
        		}
            }
            catch (InvalidCredentialsException e)
            {
                InvalidInputMessage();
                scanner.nextLine();  // Clear invalid input from scanner buffer.
            }
            catch (InputMismatchException e)
            {
                InvalidInputMessage();
                scanner.nextLine();  // Clear invalid input from scanner buffer.
            }
            catch (Exception e)
    		{
    			e.printStackTrace();
    		}
        }
	}
	
	static void EmployeeLogin(Scanner scanner, UserDAO userDao)
	{
		String username;
		String password;
		int id;
		boolean done = false;
		while (!done)
        {
            try
            {
            	System.out.println("Please enter your username.");
            	username = scanner.nextLine();
        		System.out.println("Please enter your password.");
        		password = scanner.nextLine();
        		System.out.println("Please enter your employee ID number.");
        		id = scanner.nextInt();
        		if (userDao.selectEmployee(username, password, id).size() >= 1)
        		{
            		done = true;
            		//scanner.close();
            		Employee employee = userDao.selectEmployee(username, password, id).get(0);
            		System.out.println("Login successful.");
            		EmployeeOptionsMenu(scanner, userDao, employee);
        		}
        		else
        		{
        			throw new InvalidCredentialsException();
        		}
            }
            catch (InvalidCredentialsException e)
            {
                InvalidInputMessage();
                scanner.nextLine();  // Clear invalid input from scanner buffer.
            }
            catch (InputMismatchException e)
            {
                InvalidInputMessage();
                scanner.nextLine();  // Clear invalid input from scanner buffer.
            }
            catch (Exception e)
    		{
    			e.printStackTrace();
    		}
        }
	}
	
	static void NewUserRegistration(Scanner scanner, UserDAO userDao)
	{
		String firstName;
		String lastName;
		String phoneNumber;
		String emailAddress;
		String username;
		String password;
		String alphaRegex = ".*[A-Z].*";
		String numRegex   = ".*[0-9].*";
		boolean done = false;
		while (!done)
        {
			//Scanner scanner = new Scanner(System.in);
            try
            {
            	System.out.println("Please fill out the following forms to apply for your new account.\n");
            	System.out.println("Please enter your first name. (25 characters maximum and no numbers)");
        		firstName = scanner.nextLine();
        		System.out.println("Please enter your last name. (25 characters maximum and no numbers)");
        		lastName = scanner.nextLine();
        		System.out.println("Please enter your phone number. (only the ten numeric characters)");
        		phoneNumber = scanner.nextLine();
        		System.out.println("Please enter your email address. (100 characters maximum)");
        		emailAddress = scanner.nextLine();
            	System.out.println("Please enter your username. (30 characters maximum)");
        		username = scanner.nextLine();
        		System.out.println("Please enter your password. (50 characters maximum)");
        		password = scanner.nextLine();
        		if (firstName.length() > 25 || firstName.length() <= 0 || firstName.contains(numRegex))
        		{
        			System.out.print("First name contains invalid input.");
        		}
        		else if (lastName.length() > 25 || lastName.length() <= 0 || lastName.contains(numRegex))
        		{
        			System.out.print("Last name contains invalid input.");
        		}
        		else if (phoneNumber.length() != 10 || phoneNumber.contains(alphaRegex))
        		{
        			System.out.print("Phone number contains invalid input.");
        		}
        		else if (emailAddress.length() > 100 || emailAddress.length() <= 0 || !emailAddress.contains("@"))
        		{
        			System.out.print("Email address contains invalid input.");
        		}
        		else
        		{
                	User user = new User(0, firstName, lastName, phoneNumber, emailAddress, username, password, "0", "0", 0, "0", "0", 0);
                	userDao.CreateUserApplication(user);
                	System.out.println("Application submitted. If approved, you will be able to log in upon relaunching the app.\n"
                			+ "Thank you for using Revature Bank!");
                	done = true;
                	//UserOptionsMenu(scanner, userDao, user);
                	System.exit(0);
        		}
            }
            catch (InputMismatchException e)
            {
                System.out.println("Please enter a valid input.");
                //e.printStackTrace();
                //scanner.nextLine();  // Clear invalid input from scanner buffer.
            }
            catch (Exception e)
    		{
    			e.printStackTrace();
    		}
        }
	}
		
	public static void UserOptionsMenu(Scanner scanner, UserDAO userDao, User user)
	{
		int secondAnswer;
		boolean done = false;
		while (!done)
        {
            try
            {
        		System.out.println("Current checking account balance: $" + user.getCheckingAccountBalance()
        				+ "\nCurrent savings account balance: $" + user.getSavingsAccountBalance()
        				+ "\nChoose your next action by entering its corresponding number."
       				    + "\n1) Make a deposit. | 2) Make a withdrawal. | 3) Transfer money to another person's account. | "
       				    + "4) Accept money from another person's account. | 5) Quit.");
        		//Scanner scanner = new Scanner(System.in);
        		secondAnswer = scanner.nextInt();
            	if (secondAnswer == 1)
    			{
            		done = true;
    				DepositMenu(scanner, userDao, user);
    			}
            	else if (secondAnswer == 2)
    			{
            		done = true;
    				WithdrawalMenu(scanner, userDao, user);
    			}
            	else if (secondAnswer == 3)
    			{
            		done = true;
    				TransferToMenu(scanner, userDao, user);
    			}
            	else if (secondAnswer == 4)
    			{
            		done = true;
    				TransferFromMenu(scanner, userDao, user);
    			}
            	else if (secondAnswer == 5)
            	{
            		done = true;
            		System.out.println("Thank you for using Revature Bank! Goodbye!");
            		System.exit(0);
            	}
            }
            catch (InputMismatchException e)
            {
                System.out.println("Please enter a valid input.");
                //e.printStackTrace();
                scanner.nextLine();  // Clear invalid input from scanner buffer.
            }
            catch (Exception e)
    		{
    			e.printStackTrace();
    		}
        }
	}
	
	public static void EmployeeOptionsMenu(Scanner scanner, UserDAO userDao, Employee employee)
	{
		int secondAnswer;
		boolean done = false;
		while (!done)
        {
            try
            {
        		System.out.println("\nChoose your next action by entering its corresponding number."
       				    + "\n1) Approve or reject account applications. | 2) View customer accounts. | 3) View transaction log. | 4) Quit.");
        		secondAnswer = scanner.nextInt();
            	if (secondAnswer == 1)
    			{
            		done = true;
    				ApplicationManager(scanner, userDao, employee);
    			}
            	else if (secondAnswer == 2)
    			{
            		done = true;
    				CustomerAccountsMenu(scanner, userDao, employee);
    			}
            	else if (secondAnswer == 3)
    			{
            		done = true;
    				TransactionLog(scanner, userDao, employee);
    			}
            	else if (secondAnswer == 4)
            	{
            		done = true;
            		System.out.println("Thank you for using Revature Bank! Goodbye!");
            		System.exit(0);
            	}
            }
            catch (InputMismatchException e)
            {
                System.out.println("Please enter a valid input.");
                //e.printStackTrace();
                //scanner.nextLine();  // Clear invalid input from scanner buffer.
            }
            catch (Exception e)
    		{
    			e.printStackTrace();
    			//scanner.nextLine();  // Clear invalid input from scanner buffer.
    		}
        }
	}
	
	public static void ApplicationManager(Scanner scanner, UserDAO userDao, Employee employee)
	{
//		String answer;
//		System.out.println("Here are all pending account applications.");
//		for (User user : userDao.selectAllPendingApplications()) 
//		{ 
//		    System.out.println(user + "\n");
//		    System.out.println("Do you accept this application? Answer 'yes' or 'no'.");
//		}
		String answer;
		boolean done;
		for (User user : userDao.selectAllPendingApplications())
		{
			done = false;
			System.out.println(user + "\n");
		    //System.out.println("Do you accept this application? Answer 'yes' or 'no'.");
		    while (done == false)
			{
				try
				{
					System.out.println("Do you accept this application? Answer 'yes' or 'no'.");
					answer = scanner.nextLine();
					if (answer.equals("yes"))
					{
						userDao.insertUser(user);
						userDao.RemoveUserApplication(user);
						System.out.println(" Application accepted. User account created.");
						done = true;
						//UserOptionsMenu(scanner, userDao, user);
					}
					else if (answer.equals("no"))
					{
						userDao.RemoveUserApplication(user);
						System.out.println(" Application denied. User account not created.");
						done = true;
						//UserOptionsMenu(scanner, userDao, user);
					}
				}
				catch (InputMismatchException e)
	            {
	                System.out.println("Please enter a valid input.");
	                //scanner.nextLine();  // Clear invalid input from scanner buffer.
	            }
	            catch (Exception e)
	    		{
	    			e.printStackTrace();
	    			//scanner.nextLine();  // Clear invalid input from scanner buffer.
	    		}
			}
		}
		System.out.println("No more active applications.");
		EmployeeOptionsMenu(scanner, userDao, employee);
	}
	
	public static void CustomerAccountsMenu(Scanner scanner, UserDAO userDao, Employee employee)
	{
		String answer;
		int answerTwo;
		boolean done = false;
		boolean doneTwo = false;
		System.out.println("Would you like to view a specific account or a complete list of them? Answer 'specific' or 'list'.");
		while (done == false)
		{
			try
			{
				answer = scanner.nextLine();
				if (answer.equals("specific"))
				{
					while (doneTwo == false)
					{
						try
						{
							System.out.println("Enter user ID number.");
							answerTwo = scanner.nextInt();
							if (userDao.selectUserById(answerTwo).size() > 0)
							{
								User user = userDao.selectUserById(answerTwo).get(0);
								System.out.println("User " + user.getId() + " checking account balance: $" + user.getCheckingAccountBalance()
										+ "\nUser " + user.getId() + " savings account balance: $" + user.getSavingsAccountBalance());
								doneTwo = true;
								EmployeeOptionsMenu(scanner, userDao, employee);
							}
							else
							{
								System.out.println("Invalid input or no such user.");
								//scanner.nextLine();  // Clear invalid input from scanner buffer.
							}
						}
						catch (InputMismatchException e)
			            {
			                System.out.println("Please enter a valid input.");
			                //scanner.nextLine();  // Clear invalid input from scanner buffer.
			            }
			            catch (Exception e)
			    		{
			    			e.printStackTrace();
			    			//scanner.nextLine();  // Clear invalid input from scanner buffer.
			    		}
					}
					done = true;
					EmployeeOptionsMenu(scanner, userDao, employee);
				}
				else if (answer.equals("list"))
				{
					for (User user : userDao.selectAllUsers())
					{
						System.out.println("User " + user.getId() + " checking account balance: $" + user.getCheckingAccountBalance()
								+ "\nUser " + user.getId() + " savings account balance: $" + user.getSavingsAccountBalance());
					}
					done = true;
					EmployeeOptionsMenu(scanner, userDao, employee);
				}
				else
				{
					System.out.println("Invalid Input.");
					//scanner.nextLine();  // Clear invalid input from scanner buffer.
				}
			}
			catch (InputMismatchException e)
            {
                System.out.println("Please enter a valid input.");
                //scanner.nextLine();  // Clear invalid input from scanner buffer.
            }
            catch (Exception e)
    		{
    			e.printStackTrace();
    		}
		}
		answer = scanner.nextLine();
		if (answer.equals(""))
		{
			
		}
		for (User user : userDao.selectAllUsers())
		{
			done = false;
			System.out.println(user + "\n");
		    //System.out.println("Do you accept this application? Answer 'yes' or 'no'.");
		    while (done == false)
			{
				try
				{
					System.out.println("Do you accept this application? Answer 'yes' or 'no'.");
					answer = scanner.nextLine();
					if (answer.equals("yes"))
					{
						userDao.insertUser(user);
						userDao.RemoveUserApplication(user);
						System.out.println(" Application accepted. User account created.");
						done = true;
						//UserOptionsMenu(scanner, userDao, user);
					}
					else if (answer.equals("no"))
					{
						userDao.RemoveUserApplication(user);
						System.out.println(" Application denied. User account not created.");
						done = true;
						//UserOptionsMenu(scanner, userDao, user);
					}
				}
				catch (InputMismatchException e)
	            {
	                System.out.println("Please enter a valid input.");
	                //scanner.nextLine();  // Clear invalid input from scanner buffer.
	            }
	            catch (Exception e)
	    		{
	    			e.printStackTrace();
	    		}
			}
		}
		System.out.println("No more active applications.");
		EmployeeOptionsMenu(scanner, userDao, employee);
	}
	
	public static void TransactionLog(Scanner scanner, UserDAO userDao, Employee employee)
	{
		try
		{
			File transactionLog = new File("C:\\Users\\grant\\Desktop\\Transaction Log.txt");
			  
			BufferedReader br = new BufferedReader(new FileReader(transactionLog));
			  
			String st;
			while ((st = br.readLine()) != null)
			{
			    System.out.println(st);
			}
		}
		catch (FileNotFoundException e)
		{
			System.out.println("File not found.");
		}
		catch (IOException e)
		{
			System.out.println("IOException thrown.");
		} 
		EmployeeOptionsMenu(scanner, userDao, employee);
	}
	
	public static void DepositMenu(Scanner scanner, UserDAO userDao, User user)
	{
		System.out.println("How much would you like to deposit?");
		float depositAmount = scanner.nextFloat();
		while (depositAmount <= 0)
		//String depositAmount = scanner.nextLine();
		//while (Float.parseFloat(depositAmount) <= 0)
		{
			try
			{
				System.out.println("Deposit amount must be greater than $0. How much would you like to deposit?");
				depositAmount = scanner.nextFloat();
			}
			catch (InputMismatchException e)
            {
                System.out.println("Please enter a valid numerical input.");
                scanner.nextLine();  // Clear invalid input from scanner buffer.
            }
		}
		String answer;
		boolean done = false;
		while (done == false)
		{
			try
			{
				System.out.println("Into which account would you like to deposit money? Answer 'checking' or 'savings'.");
				answer = scanner.nextLine();
				if (answer.equals("checking"))
				{
					userDao.updateUserCheckingBalance(depositAmount, true, user, scanner, userDao);
					System.out.println("$" + depositAmount + " has been deposited into your checking account.");
					done = true;
					UserOptionsMenu(scanner, userDao, user);
				}
				else if (answer.equals("savings"))
				{
					userDao.updateUserSavingsBalance(depositAmount, true, user, scanner, userDao);
					System.out.println("$" + depositAmount + " has been deposited into your savings account.");
					done = true;
					UserOptionsMenu(scanner, userDao, user);
				}
			}
			catch (InputMismatchException e)
            {
                System.out.println("Please enter a valid input.");
                //scanner.nextLine();  // Clear invalid input from scanner buffer.
            }
            catch (Exception e)
    		{
    			e.printStackTrace();
    		}
		}
	}
	
	public static void WithdrawalMenu(Scanner scanner, UserDAO userDao, User user)
	{
		System.out.println("How much would you like to withdraw?");
		float withdrawalAmount = scanner.nextFloat();
		while (withdrawalAmount <= 0)
		//String withdrawalAmount = scanner.nextLine();
		//while (Float.parseFloat(withdrawalAmount) <= 0)
		{
			try
			{
				System.out.println("Withdrawal amount must be greater than $0. How much would you like to withdraw?");
				withdrawalAmount = scanner.nextFloat();
				//withdrawalAmount = scanner.nextLine();
			}
			catch (InputMismatchException e)
            {
                System.out.println("Please enter a valid numerical input.");
                scanner.nextLine();  // Clear invalid input from scanner buffer.
            }
		}
		String answer;
		boolean done = false;
		while (done == false)
		{
			try
			{
				System.out.println("From which account would you like to withdraw money? Answer 'checking' or 'savings'.");
				answer = scanner.nextLine();
				if (answer.equals("checking"))
				{
					userDao.updateUserCheckingBalance(withdrawalAmount, false, user, scanner, userDao);
					System.out.println("$" + withdrawalAmount + " has been withdrawn from your checking account.");
					done = true;
					UserOptionsMenu(scanner, userDao, user);
				}
				else if (answer.equals("savings"))
				{
					userDao.updateUserSavingsBalance(withdrawalAmount, false, user, scanner, userDao);
					System.out.println("$" + withdrawalAmount + " has been withdrawn from your savings account.");
					done = true;
					UserOptionsMenu(scanner, userDao, user);
				}
			}
			catch (InputMismatchException e)
            {
                System.out.println("Please enter a valid input.");
                scanner.nextLine();  // Clear invalid input from scanner buffer.
            }
            catch (Exception e)
    		{
    			e.printStackTrace();
    		}
		}
	}
	
	public static void TransferToMenu(Scanner scanner, UserDAO userDao, User user)
	{
		System.out.print("This feature is currently in development. Returning to main menu.\n");
		UserOptionsMenu(scanner, userDao, user);
//		System.out.println("How much would you like to transfer?");
//		float transferAmount = scanner.nextFloat();
//		while (withdrawalAmount <= 0)
//		//String withdrawalAmount = scanner.nextLine();
//		//while (Float.parseFloat(withdrawalAmount) <= 0)
//		{
//			try
//			{
//				System.out.println("Withdrawal amount must be greater than $0. How much would you like to withdraw?");
//				withdrawalAmount = scanner.nextFloat();
//				//withdrawalAmount = scanner.nextLine();
//			}
//			catch (InputMismatchException e)
//            {
//                System.out.println("Please enter a valid numerical input.");
//                scanner.nextLine();  // Clear invalid input from scanner buffer.
//            }
//		}
//		String answer;
//		boolean done = false;
//		while (done == false)
//		{
//			try
//			{
//				System.out.println("From which account would you like to withdraw money? Answer 'checking' or 'savings'.");
//				answer = scanner.nextLine();
//				if (answer.equals("checking"))
//				{
//					userDao.updateUserCheckingBalance(withdrawalAmount, false, user, scanner, userDao);
//					System.out.println("$" + withdrawalAmount + " has been withdrawn from your checking account.");
//					done = true;
//					UserOptionsMenu(scanner, userDao, user);
//				}
//				else if (answer.equals("savings"))
//				{
//					userDao.updateUserSavingsBalance(withdrawalAmount, false, user, scanner, userDao);
//					System.out.println("$" + withdrawalAmount + " has been withdrawn from your savings account.");
//					done = true;
//					UserOptionsMenu(scanner, userDao, user);
//				}
//			}
//			catch (InputMismatchException e)
//            {
//                System.out.println("Please enter a valid input.");
//                scanner.nextLine();  // Clear invalid input from scanner buffer.
//            }
//            catch (Exception e)
//    		{
//    			e.printStackTrace();
//    		}
//		}
	}
	
	public static void TransferFromMenu(Scanner scanner, UserDAO userDao, User user)
	{
		System.out.print("This feature is currently in development. Returning to main menu.\n");
		UserOptionsMenu(scanner, userDao, user);
	}
	
	public static void InvalidInputMessage()
	{
		System.out.println("Please enter a valid input.");
	}

	private static void createMainWindow()
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;
		
		JFrame window = new JFrame("Hello World!"); // Make new JFrame object  
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(screenWidth, screenHeight);  // Set size of window
        window.setVisible(true);                    // Show window
        
        //JLabel label = new JLabel("Hello World!");
        //label.setPreferredSize(new Dimension(300, 100));
        //window.getContentPane().add(label, BorderLayout.CENTER);
        
        JButton registerButton = new JButton("Register");
        //registerButton.setBounds(150, 150, 100, 100);
        registerButton.setBounds(screenWidth * 2 / 5, screenHeight * 3 / 5, screenWidth / 4, screenHeight / 5);
        registerButton.setVisible(true);
        
        registerButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
        	//public void mouseEntered(MouseEvent event) {
        	public void mouseClicked(MouseEvent event)
        	{
        		//registerButton.setBackground(Color.GREEN);
        		RegisterScreen();
        	}
        });
        
        JButton loginButton = new JButton("Log In");
        loginButton.setBounds(screenWidth * 2 / 5, screenHeight * 2 / 5, screenWidth / 4, screenHeight / 5);
        loginButton.setVisible(true);
        //loginButton.addActionListener(new ActionListener());
        //loginButton.addMouseListener(MouseListener l);
        loginButton.setActionCommand("Login");
        if (loginButton.isVisible())
        {
        	System.out.print("Nani");
        }
        
        loginButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
        	//public void mouseEntered(MouseEvent event) {
        	public void mouseClicked(MouseEvent event)
        	{
        		//registerButton.setBackground(Color.GREEN);
        		LoginScreen();
        	}
        });
        
        JPasswordField passwordField = new JPasswordField("Password");
        //passwordField.SwingConstants = HORIZONTAL;
        passwordField.setText("Enter password");
        passwordField.setBounds(30, 30, 30, 30);
        passwordField.setVisible(true);
        
        window.add(registerButton);
        window.add(loginButton);
        window.add(passwordField);
	}
	
	public static void LoginScreen()
    {
		Scanner scanner = new Scanner(System.in);
    	System.out.println("Please enter your username.");
		String username = scanner.nextLine();
		System.out.println("Please enter your password.");
		String password = scanner.nextLine();
    }
	
	public static void RegisterScreen()
    {
    	
    }
	
	//@Override
    public void actionPerformed(ActionEvent e)
    {
        String command = e.getActionCommand();
        if (command.equals("Login"))
        {
            LoginScreen();
        } else if (command.equals("Register"))
        {
            RegisterScreen();
        }
    }

	//@Override
	//public void mouseClicked(MouseEvent e) {
    public void mouseClicked(MouseEvent e, JFrame window) {
		// TODO Auto-generated method stub
		//if (loginButton.getMousePosition() == true)
		//if (window.getMousePosition() == true)
    	if (window.getMousePosition().x > 300 && window.getMousePosition().x < 600
    			&& window.getMousePosition().y > 300 && window.getMousePosition().y < 600)
		{
    		//Scanner scanner = new Scanner(System.in);
			LoginScreen();
		}
    	else if (window.getMousePosition().x > 300 && window.getMousePosition().x < 600
				&& window.getMousePosition().y > 300 && window.getMousePosition().y < 600)
    	{
    		//Scanner scanner = new Scanner(System.in);
    		RegisterScreen();
    	}
	}

//	//@Override
//	public void mousePressed(MouseEvent e) {
//		// TODO Auto-generated method stub
//		if (loginButton.getMousePosition() == true) {
//			
//		}
//	}
//
//	//@Override
//	public void mouseReleased(MouseEvent e) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	//@Override
//	public void mouseEntered(MouseEvent e) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	//@Override
//	public void mouseExited(MouseEvent e) {
//		// TODO Auto-generated method stub
//		
//	}
}
