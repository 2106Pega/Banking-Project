//package com.revature.util;
//
//MainDriver
//
//package com.revature;
//import java.awt.*;
////import java.awt.event.ActionListener;
////import java.awt.event.MouseEvent;
////import java.awt.event.MouseListener;
//
//import javax.swing.*;
//import java.io.Serializable;
//import java.util.List;
//import java.util.Scanner;
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.FlowLayout;
//import java.awt.Graphics;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseEvent;
//
//import javax.swing.JButton;
//import javax.swing.JComponent;
//import javax.swing.JFrame;
//import javax.swing.WindowConstants;
//
//import com.revature.models.User;
//import com.revature.repo.UserDAO;
//import com.revature.repo.UserDAOImpl;
//
//import java.util.InputMismatchException;
//
//public class MainDriver /*implements java.io.Serializable, java.awt.event.MouseListener*/
//{	
//	static void ReturningUserLogin(Scanner scanner)
//	{
//		//String secondAnswer;
//		boolean done = false;
//		while (!done)
//        {
//			//Scanner scanner = new Scanner(System.in);
//            try
//            {
//            	System.out.println("Please enter your username.");
//        		String username = scanner.nextLine();
//        		System.out.println("Please enter your password.");
//        		String password = scanner.nextLine();
//        		//Verify username and password combo through database.
//        		//If that works, 
//        		done = true;
//        		//scanner.close();
//        		System.out.println("Login successful.");
//        		UserOptionsMenu(scanner);
//            }
//            catch (InputMismatchException e)
//            {
//                System.out.println("Please enter a valid input.");
//                //scanner.nextLine();  // Clear invalid input from scanner buffer.
//            }
//        }
//	}
//	
//	static void NewUserRegistration(Scanner scanner)
//	{
//		//String secondAnswer;
//		boolean done = false;
//		while (!done)
//        {
//			//Scanner scanner = new Scanner(System.in);
//            try
//            {
//            	System.out.println("Please enter your first name.");
//        		String firstName = scanner.nextLine();
//        		System.out.println("Please enter your last name.");
//        		String lastName = scanner.nextLine();
//        		System.out.println("Please enter your phone number.");
//        		String phoneNumber = scanner.nextLine();
//        		System.out.println("Please enter your email address.");
//        		String emailAddress = scanner.nextLine();
//            	System.out.println("Please enter your username.");
//        		String username = scanner.nextLine();
//        		System.out.println("Please enter your password.");
//        		String password = scanner.nextLine();
//            	User user = new User(0, firstName, lastName, phoneNumber, emailAddress);
//            	
//            	UserDAO userDao = new UserDAOImpl();
//            	
//            	userDao.insertUser(user);
//            	
//            	List<User> persistedUsers = userDao.selectUsersByFirstName("Grant");
//            	
//            	System.out.println(persistedUsers);
//            	System.out.println("User registration completed.");
//            	done = true;
//            	UserOptionsMenu(scanner);
//            }
//            catch (InputMismatchException e)
//            {
//                System.out.println("Please enter a valid input.");
//                //scanner.nextLine();  // Clear invalid input from scanner buffer.
//            }
//        }
//	}
//		
//	static void UserOptionsMenu(Scanner scanner)
//	{
//		boolean done = false;
//		while (!done)
//        {
//            try
//            {
//        		System.out.println("Choose your next action by entering its corresponding number."
//       				 + "\n 1) Make a deposit. \n 2) Make a withdrawal. \n 3) Transfer money to another person's account. \n 4) Quit.");
//        		//Scanner scanner = new Scanner(System.in);
//        		int secondAnswer = scanner.nextInt();
//            	if (secondAnswer == 1)
//    			{
//    				System.out.println("How much would you like to deposit?");
//    				float depositAmount = scanner.nextFloat();
//    				while (depositAmount <= 0)
//    				//String depositAmount = scanner.nextLine();
//    				//System.out.println(depositAmount);
//    				//while (Float.parseFloat(depositAmount) <= 0)
//    				{
//    					try
//    					{
//        					System.out.println("Deposit must be greater than $0. How much would you like to deposit?");
//        					depositAmount = scanner.nextFloat();
//        					//depositAmount = scanner.nextLine();
//    					}
//    					catch (InputMismatchException e)
//    		            {
//    		                System.out.println("Please enter a valid numerical input.");
//    		                //scanner.nextLine();  // Clear invalid input from scanner buffer.
//    		            }
//    				}
//    				System.out.println("Into which account would you like to deposit money? Answer 'checking' or 'savings'.");
//    				String thirdAnswer = scanner.nextLine();
//    				if (thirdAnswer.equals("checking"))
//    				{
//    					//Deposit money into their checking account.
//    					System.out.println("$" + depositAmount + " has been deposited into your checking account.");
//    					done = true;
//    					UserOptionsMenu(scanner);
//    				}
//    				else if (thirdAnswer.equals("savings"))
//    				{
//    					//Deposit money into their savings account.
//    					System.out.println("$" + depositAmount + " has been deposited into your savings account.");
//    					done = true;
//    					UserOptionsMenu(scanner);
//    				}
//    			}
//            }
//            catch (InputMismatchException e)
//            {
//                System.out.println("Please enter a valid input.");
//                //scanner.nextLine();  // Clear invalid input from scanner buffer.
//            }
//        }
//	}
//	
//	public static void main(String[] args)
//	{
//		//createMainWindow();
//        
//        //User user = new User("Grant", "Bevier", "4126969696", "grantbevier@gmail.com");
//        //User user = new User(1, "Grant", "Bevier", "4126969696", "grantbevier@gmail.com");
//		
//		Scanner scanner = new Scanner(System.in);
//		System.out.println("Hello! Welcome to Revature Bank. Are you a returning user? Enter 'yes' or 'no'.");
//		String firstAnswer;
//		boolean done = false;
//        while (!done)
//        {
//        	//System.out.println("Hello! Welcome to Revature Bank. Are you a returning user? Enter 'yes' or 'no'.");
//            try
//            {
//                firstAnswer = scanner.nextLine();
//                if (firstAnswer.equals("yes"))
//        		{
//                    done = true;
//                    //scanner.close();
//                    ReturningUserLogin(scanner);
//                }
//                else if (firstAnswer.equals("no"))
//                {
//                    NewUserRegistration(scanner);
//                }
//                else
//                {
//                	System.out.println("Please enter a valid input.");
//                }
//            }
//            catch (InputMismatchException e)
//            {
//                System.out.println("Please enter a valid input.");
//                //scanner.nextLine();  // Clear invalid input from scanner buffer.
//            }
//        }
//        
////		int secondAnswer = scanner.nextInt();    
////        System.out.println("Exiting...");
////		
////		//String firstAnswer = scanner.nextLine();
////		//System.out.println(firstAnswer);
////		//if (firstAnswer == "yes")
////		if (firstAnswer.equals("yes"))
////		{
////			System.out.println("Please enter your username.");
////			String username = scanner.nextLine();
////			System.out.println("Please enter your password.");
////			String password = scanner.nextLine();
////			//Verify username and password combo through database.
////			//If that works, 
////			System.out.println("Login successful. Choose your next action by entering its corresponding number."
////							 + "\n 1) Make a deposit. \n 2) Make a withdrawal. \n 3) Transfer money to another person's account.");
////			int secondAnswer = scanner.nextInt();
////			if (secondAnswer == 1)
////			{
////				System.out.println("How much would you like to deposit?");
////				//float depositAmount = scanner.nextFloat();
////				//while (depositAmount <= 0)
////				String depositAmount = scanner.nextLine();
////				System.out.println(depositAmount);
////				while (Float.parseFloat(depositAmount) <= 0)
////				{
////					System.out.println("Deposit must be greater than $0. How much would you like to deposit?");
////					//depositAmount = scanner.nextFloat();
////					depositAmount = scanner.nextLine();
////				}
////				System.out.println("Into which account would you like to deposit money? Answer 'checking' or 'savings'.");
////				String thirdAnswer = scanner.nextLine();
////				if (thirdAnswer.equals("checking"))
////				{
////					//Deposit money into their checking account.
////				}
////				else if (thirdAnswer.equals("savings"))
////				{
////					//Deposit money into their savings account.
////				}
////				else
////				{
////					System.out.println("Please enter an eligible command.");
////				}
////			}
////			
////		}
////		else if (firstAnswer == "no")
////		{
////			System.out.println("Please enter your new username.");
////			String username = scanner.nextLine();
////			System.out.println("Please enter your new password.");
////			String password = scanner.nextLine();
////		}
////		else
////		{
////			System.out.println("Please enter an eligible command.");
////		}
//		
////		User user = new User("Grant", "Bevier", "4126969696", "grantbevier@gmail.com");
////		
////    	UserDAO userDao = new UserDAOImpl();
////    	
////    	userDao.insertUser(user);
////    	
////    	List<User> persistedUsers = userDao.selectUsersByFirstName("Grant");
////    	
////    	System.out.println(persistedUsers);
//	}
//
//	private static void createMainWindow()
//	{
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		int screenWidth = screenSize.width;
//		int screenHeight = screenSize.height;
//		
//		JFrame window = new JFrame("Hello World!"); // Make new JFrame object  
//		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        window.setSize(screenWidth, screenHeight);  // Set size of window
//        window.setVisible(true);                    // Show window
//        
//        //JLabel label = new JLabel("Hello World!");
//        //label.setPreferredSize(new Dimension(300, 100));
//        //window.getContentPane().add(label, BorderLayout.CENTER);
//        
//        JButton registerButton = new JButton("Register");
//        //registerButton.setBounds(150, 150, 100, 100);
//        registerButton.setBounds(screenWidth * 2 / 5, screenHeight * 3 / 5, screenWidth / 4, screenHeight / 5);
//        registerButton.setVisible(true);
//        
//        registerButton.addMouseListener(new java.awt.event.MouseAdapter()
//        {
//        	//public void mouseEntered(MouseEvent event) {
//        	public void mouseClicked(MouseEvent event)
//        	{
//        		//registerButton.setBackground(Color.GREEN);
//        		RegisterScreen();
//        	}
//        });
//        
//        JButton loginButton = new JButton("Log In");
//        loginButton.setBounds(screenWidth * 2 / 5, screenHeight * 2 / 5, screenWidth / 4, screenHeight / 5);
//        loginButton.setVisible(true);
//        //loginButton.addActionListener(new ActionListener());
//        //loginButton.addMouseListener(MouseListener l);
//        loginButton.setActionCommand("Login");
//        if (loginButton.isVisible())
//        {
//        	System.out.print("Nani");
//        }
//        
//        loginButton.addMouseListener(new java.awt.event.MouseAdapter()
//        {
//        	//public void mouseEntered(MouseEvent event) {
//        	public void mouseClicked(MouseEvent event)
//        	{
//        		//registerButton.setBackground(Color.GREEN);
//        		LoginScreen();
//        	}
//        });
//        
//        JPasswordField passwordField = new JPasswordField("Password");
//        //passwordField.SwingConstants = HORIZONTAL;
//        passwordField.setText("Enter password");
//        passwordField.setBounds(30, 30, 30, 30);
//        passwordField.setVisible(true);
//        
//        window.add(registerButton);
//        window.add(loginButton);
//        window.add(passwordField);
//	}
//	
//	public static void LoginScreen()
//    {
//		Scanner scanner = new Scanner(System.in);
//    	System.out.println("Please enter your username.");
//		String username = scanner.nextLine();
//		System.out.println("Please enter your password.");
//		String password = scanner.nextLine();
//    }
//	
//	public static void RegisterScreen()
//    {
//    	
//    }
//	
//	//@Override
//    public void actionPerformed(ActionEvent e)
//    {
//        String command = e.getActionCommand();
//        if (command.equals("Login"))
//        {
//            LoginScreen();
//        } else if (command.equals("Register"))
//        {
//            RegisterScreen();
//        }
//    }
//
//	//@Override
//	//public void mouseClicked(MouseEvent e) {
//    public void mouseClicked(MouseEvent e, JFrame window) {
//		// TODO Auto-generated method stub
//		//if (loginButton.getMousePosition() == true)
//		//if (window.getMousePosition() == true)
//    	if (window.getMousePosition().x > 300 && window.getMousePosition().x < 600
//    			&& window.getMousePosition().y > 300 && window.getMousePosition().y < 600)
//		{
//    		//Scanner scanner = new Scanner(System.in);
//			LoginScreen();
//		}
//    	else if (window.getMousePosition().x > 300 && window.getMousePosition().x < 600
//				&& window.getMousePosition().y > 300 && window.getMousePosition().y < 600)
//    	{
//    		//Scanner scanner = new Scanner(System.in);
//    		RegisterScreen();
//    	}
//	}
//
////	//@Override
////	public void mousePressed(MouseEvent e) {
////		// TODO Auto-generated method stub
////		if (loginButton.getMousePosition() == true) {
////			
////		}
////	}
////
////	//@Override
////	public void mouseReleased(MouseEvent e) {
////		// TODO Auto-generated method stub
////		
////	}
////
////	//@Override
////	public void mouseEntered(MouseEvent e) {
////		// TODO Auto-generated method stub
////		
////	}
////
////	//@Override
////	public void mouseExited(MouseEvent e) {
////		// TODO Auto-generated method stub
////		
////	}
//}
//
//-----------------------------------------------------------------------------------------------------------------------------------------------------------
//MainDriverTwo

//package com.revature;
///*import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.FlowLayout;
//import java.awt.Graphics;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;*/
//import java.awt.*;
//import java.awt.event.*;
//
//import javax.swing.JButton;
//import javax.swing.JComponent;
//import javax.swing.JFrame;
//import javax.swing.WindowConstants;
//
//public class MainDriverTwo extends JComponent implements ActionListener {
//  public static void main(String[] args) {
//
//      JFrame frame = new JFrame("NEW PAINT PROGRAME!");
//      JButton ovalButton = new JButton("Oval");
//      ovalButton.setActionCommand("Oval");
//
//      JButton rectangleButton = new JButton("Rectangle");
//      rectangleButton.setActionCommand("Rectangle");
//
//      MainDriverTwo paint = new MainDriverTwo();
//      ovalButton.addActionListener(paint);
//      rectangleButton.addActionListener(paint);
//      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//      frame.setLayout(new FlowLayout());
//      frame.add(paint);
//      frame.add(ovalButton);
//      frame.add(rectangleButton);
//
//      frame.pack();
//      frame.setVisible(true);
//
//  }
//
//  @Override
//  public Dimension getPreferredSize() {
//      return new Dimension(500, 500);
//
//  }
//
//  private void drawOval() {
//      Graphics g = this.getGraphics();
//      g.setColor(Color.red);
//      g.fillOval(0, 0, 100, 100);
//  }
//
//  private void drawRectangle() {
//      Graphics g = this.getGraphics();
//      g.setColor(Color.green);
//      g.fillRect(150, 150, 100, 100);
//  }
//
//  @Override
//  public void actionPerformed(ActionEvent e) {
//      String command = e.getActionCommand();
//      if (command.equals("Oval")) {
//          drawOval();
//      } else if (command.equals("Rectangle")) {
//          drawRectangle();
//      }
//
//  }
//
//}
//-----------------------------------------------------------------------------------------------------------------------------------------------------------
//MainDriverThree
//
//package com.revature;
//
//import java.util.List;
//
//import com.revature.models.User;
//import com.revature.repo.UserDAO;
//import com.revature.repo.UserDAOImpl;
//
//public class MainDriverThree
//{
//	public static void main(String[] args)
//	{
//		//User user = new User("Grant", "Bevier", "4126969696", "grantbevier@gmail.com");
//		//User user = new User(0, "Grant", "Bevier", "4126969696", "grantbevier@gmail.com"); //worked when there were 5 columns in my table, the first being serial
//		//User user = new User(0, "Grant", "Bevier", "4126969696", "grantbevier@gmail.com", "Mistyjello", "PV55W0RD");
//		User user = new User(0, "Grant", "Bevier", "4126969696", "grantbevier@gmail.com", "Mistyjello", "PV55W0RD", "0", "0", 0, "0", "0", 0);
//		
//		UserDAO userDao = new UserDAOImpl();
//		
//		userDao.insertUser(user);
//		
//		List<User> persistedUsers = userDao.selectUsersByFirstName("Grant");
//		
//		System.out.println(persistedUsers);
//	}
//}
//-----------------------------------------------------------------------------------------------------------------------------------------------------------
//MainDriverFour
//
//package com.revature;
//import java.awt.*;
////import java.awt.event.ActionListener;
////import java.awt.event.MouseEvent;
////import java.awt.event.MouseListener;
//
//import javax.swing.*;
//import java.io.Serializable;
//import java.util.List;
//import java.util.Scanner;
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.FlowLayout;
//import java.awt.Graphics;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseEvent;
//
//import javax.swing.JButton;
//import javax.swing.JComponent;
//import javax.swing.JFrame;
//import javax.swing.WindowConstants;
//
//import com.revature.models.User;
//import com.revature.repo.UserDAO;
//import com.revature.repo.UserDAOImpl;
//
//import java.util.InputMismatchException;
//
//public class MainDriverFour /*implements java.io.Serializable, java.awt.event.MouseListener*/
//{	
//	static void ReturningUserLogin(Scanner scanner, UserDAO userDao) throws InvalidCredentialsException
//	{
//		//String secondAnswer;
//		String username;
//		String password;
//		boolean done = false;
//		while (!done)
//        {
//			//Scanner scanner = new Scanner(System.in);
//            try
//            {
//            	System.out.println("Please enter your username.");
//        		//String username = scanner.nextLine();
//            	username = scanner.nextLine();
//        		System.out.println("Please enter your password.");
//        		//String password = scanner.nextLine();
//        		password = scanner.nextLine();
//        		System.out.println("Please enter your customer ID number.");
//        		int id = scanner.nextInt();
//        		if (userDao.selectUserByAllCredentials(username, password, id).size() >= 1)
//        		{
//            		done = true;
//            		//scanner.close();
//            		System.out.println("Login successful.");
//            		UserOptionsMenu(scanner, userDao);
//        		}
//        		else
//        		{
//        			throw new InvalidCredentialsException();
//        		}
//            }
//            catch (InvalidCredentialsException e)
//            {
//                InvalidInputMessage();
//                scanner.nextLine();  // Clear invalid input from scanner buffer.
//            }
//            catch (InputMismatchException e)
//            {
//                InvalidInputMessage();
//                scanner.nextLine();  // Clear invalid input from scanner buffer.
//            }
//
//        }
//	}
//	
//	static void NewUserRegistration(Scanner scanner, UserDAO userDao)
//	{
//		//String secondAnswer;
//		boolean done = false;
//		while (!done)
//        {
//			//Scanner scanner = new Scanner(System.in);
//            try
//            {
//            	System.out.println("Please enter your first name.");
//        		String firstName = scanner.nextLine();
//        		System.out.println("Please enter your last name.");
//        		String lastName = scanner.nextLine();
//        		System.out.println("Please enter your phone number.");
//        		String phoneNumber = scanner.nextLine();
//        		System.out.println("Please enter your email address.");
//        		String emailAddress = scanner.nextLine();
//            	System.out.println("Please enter your username.");
//        		String username = scanner.nextLine();
//        		System.out.println("Please enter your password.");
//        		String password = scanner.nextLine();
//            	User user = new User(0, firstName, lastName, phoneNumber, emailAddress, username, password);
//            	
//            	//UserDAO userDao = new UserDAOImpl();
//            	
//            	userDao.insertUser(user);
//            	
//            	System.out.println("User registration completed.");
//            	done = true;
//            	UserOptionsMenu(scanner, userDao);
//            }
//            catch (InputMismatchException e)
//            {
//                System.out.println("Please enter a valid input.");
//                //e.printStackTrace();
//                //scanner.nextLine();  // Clear invalid input from scanner buffer.
//            }
//        }
//	}
//		
//	static void UserOptionsMenu(Scanner scanner, UserDAO userDao)
//	{
//		boolean done = false;
//		while (!done)
//        {
//            try
//            {
//        		System.out.println("Choose your next action by entering its corresponding number."
//       				 + "\n 1) Make a deposit. \n 2) Make a withdrawal. \n 3) Transfer money to another person's account. \n 4) Quit.");
//        		//Scanner scanner = new Scanner(System.in);
//        		int secondAnswer = scanner.nextInt();
//            	if (secondAnswer == 1)
//    			{
//    				System.out.println("How much would you like to deposit?");
//    				float depositAmount = scanner.nextFloat();
//    				while (depositAmount <= 0)
//    				//String depositAmount = scanner.nextLine();
//    				//System.out.println(depositAmount);
//    				//while (Float.parseFloat(depositAmount) <= 0)
//    				{
//    					try
//    					{
//        					System.out.println("Deposit must be greater than $0. How much would you like to deposit?");
//        					depositAmount = scanner.nextFloat();
//        					//depositAmount = scanner.nextLine();
//    					}
//    					catch (InputMismatchException e)
//    		            {
//    		                System.out.println("Please enter a valid numerical input.");
//    		                //scanner.nextLine();  // Clear invalid input from scanner buffer.
//    		            }
//    				}
//    				System.out.println("Into which account would you like to deposit money? Answer 'checking' or 'savings'.");
//    				String thirdAnswer = scanner.nextLine();
//    				if (thirdAnswer.equals("checking"))
//    				{
//    					//Deposit money into their checking account.
//    					System.out.println("$" + depositAmount + " has been deposited into your checking account.");
//    					done = true;
//    					UserOptionsMenu(scanner, userDao);
//    				}
//    				else if (thirdAnswer.equals("savings"))
//    				{
//    					//Deposit money into their savings account.
//    					System.out.println("$" + depositAmount + " has been deposited into your savings account.");
//    					done = true;
//    					UserOptionsMenu(scanner, userDao);
//    				}
//    			}
//            	if (secondAnswer == 4)
//            	{
//            		System.exit(0);
//            	}
//            }
//            catch (InputMismatchException e)
//            {
//                System.out.println("Please enter a valid input.");
//                //e.printStackTrace();
//                //scanner.nextLine();  // Clear invalid input from scanner buffer.
//            }
//        }
//	}
//	
//	public static void InvalidInputMessage()
//	{
//		System.out.println("Please enter a valid input.");
//	}
//	
//	public static void main(String[] args) throws InvalidCredentialsException
//	{
//		UserDAO userDao = new UserDAOImpl();
//		//createMainWindow();		
//		Scanner scanner = new Scanner(System.in);
//		System.out.println("Hello! Welcome to Revature Bank. Are you a returning user? Enter 'yes' or 'no'.");
//		String firstAnswer;
//		boolean done = false;
//        while (!done)
//        {
//            try
//            {
//                firstAnswer = scanner.nextLine();
//                if (firstAnswer.equals("yes"))
//        		{
//                    done = true;
//                    //scanner.close();
//                    ReturningUserLogin(scanner, userDao);
//                }
//                else if (firstAnswer.equals("no"))
//                {
//                    NewUserRegistration(scanner, userDao);
//                }
//                else
//                {
//                	InvalidInputMessage();
//                }
//            }
//            catch (InputMismatchException e)
//            {
//            	InvalidInputMessage();
//                //scanner.nextLine();  // Clear invalid input from scanner buffer.
//            }
//        }
//        
////		int secondAnswer = scanner.nextInt();    
////        System.out.println("Exiting...");
////		
////		//String firstAnswer = scanner.nextLine();
////		//System.out.println(firstAnswer);
////		//if (firstAnswer == "yes")
////		if (firstAnswer.equals("yes"))
////		{
////			System.out.println("Please enter your username.");
////			String username = scanner.nextLine();
////			System.out.println("Please enter your password.");
////			String password = scanner.nextLine();
////			//Verify username and password combo through database.
////			//If that works, 
////			System.out.println("Login successful. Choose your next action by entering its corresponding number."
////							 + "\n 1) Make a deposit. \n 2) Make a withdrawal. \n 3) Transfer money to another person's account.");
////			int secondAnswer = scanner.nextInt();
////			if (secondAnswer == 1)
////			{
////				System.out.println("How much would you like to deposit?");
////				//float depositAmount = scanner.nextFloat();
////				//while (depositAmount <= 0)
////				String depositAmount = scanner.nextLine();
////				System.out.println(depositAmount);
////				while (Float.parseFloat(depositAmount) <= 0)
////				{
////					System.out.println("Deposit must be greater than $0. How much would you like to deposit?");
////					//depositAmount = scanner.nextFloat();
////					depositAmount = scanner.nextLine();
////				}
////				System.out.println("Into which account would you like to deposit money? Answer 'checking' or 'savings'.");
////				String thirdAnswer = scanner.nextLine();
////				if (thirdAnswer.equals("checking"))
////				{
////					//Deposit money into their checking account.
////				}
////				else if (thirdAnswer.equals("savings"))
////				{
////					//Deposit money into their savings account.
////				}
////				else
////				{
////					System.out.println("Please enter an eligible command.");
////				}
////			}
////			
////		}
////		else if (firstAnswer == "no")
////		{
////			System.out.println("Please enter your new username.");
////			String username = scanner.nextLine();
////			System.out.println("Please enter your new password.");
////			String password = scanner.nextLine();
////		}
////		else
////		{
////			System.out.println("Please enter an eligible command.");
////		}
//		
////		User user = new User("Grant", "Bevier", "4126969696", "grantbevier@gmail.com");
////		
////    	UserDAO userDao = new UserDAOImpl();
////    	
////    	userDao.insertUser(user);
////    	
////    	List<User> persistedUsers = userDao.selectUsersByFirstName("Grant");
////    	
////    	System.out.println(persistedUsers);
//	}
//
//	private static void createMainWindow()
//	{
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		int screenWidth = screenSize.width;
//		int screenHeight = screenSize.height;
//		
//		JFrame window = new JFrame("Hello World!"); // Make new JFrame object  
//		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        window.setSize(screenWidth, screenHeight);  // Set size of window
//        window.setVisible(true);                    // Show window
//        
//        //JLabel label = new JLabel("Hello World!");
//        //label.setPreferredSize(new Dimension(300, 100));
//        //window.getContentPane().add(label, BorderLayout.CENTER);
//        
//        JButton registerButton = new JButton("Register");
//        //registerButton.setBounds(150, 150, 100, 100);
//        registerButton.setBounds(screenWidth * 2 / 5, screenHeight * 3 / 5, screenWidth / 4, screenHeight / 5);
//        registerButton.setVisible(true);
//        
//        registerButton.addMouseListener(new java.awt.event.MouseAdapter()
//        {
//        	//public void mouseEntered(MouseEvent event) {
//        	public void mouseClicked(MouseEvent event)
//        	{
//        		//registerButton.setBackground(Color.GREEN);
//        		RegisterScreen();
//        	}
//        });
//        
//        JButton loginButton = new JButton("Log In");
//        loginButton.setBounds(screenWidth * 2 / 5, screenHeight * 2 / 5, screenWidth / 4, screenHeight / 5);
//        loginButton.setVisible(true);
//        //loginButton.addActionListener(new ActionListener());
//        //loginButton.addMouseListener(MouseListener l);
//        loginButton.setActionCommand("Login");
//        if (loginButton.isVisible())
//        {
//        	System.out.print("Nani");
//        }
//        
//        loginButton.addMouseListener(new java.awt.event.MouseAdapter()
//        {
//        	//public void mouseEntered(MouseEvent event) {
//        	public void mouseClicked(MouseEvent event)
//        	{
//        		//registerButton.setBackground(Color.GREEN);
//        		LoginScreen();
//        	}
//        });
//        
//        JPasswordField passwordField = new JPasswordField("Password");
//        //passwordField.SwingConstants = HORIZONTAL;
//        passwordField.setText("Enter password");
//        passwordField.setBounds(30, 30, 30, 30);
//        passwordField.setVisible(true);
//        
//        window.add(registerButton);
//        window.add(loginButton);
//        window.add(passwordField);
//	}
//	
//	public static void LoginScreen()
//    {
//		Scanner scanner = new Scanner(System.in);
//    	System.out.println("Please enter your username.");
//		String username = scanner.nextLine();
//		System.out.println("Please enter your password.");
//		String password = scanner.nextLine();
//    }
//	
//	public static void RegisterScreen()
//    {
//    	
//    }
//	
//	//@Override
//    public void actionPerformed(ActionEvent e)
//    {
//        String command = e.getActionCommand();
//        if (command.equals("Login"))
//        {
//            LoginScreen();
//        } else if (command.equals("Register"))
//        {
//            RegisterScreen();
//        }
//    }
//
//	//@Override
//	//public void mouseClicked(MouseEvent e) {
//    public void mouseClicked(MouseEvent e, JFrame window) {
//		// TODO Auto-generated method stub
//		//if (loginButton.getMousePosition() == true)
//		//if (window.getMousePosition() == true)
//    	if (window.getMousePosition().x > 300 && window.getMousePosition().x < 600
//    			&& window.getMousePosition().y > 300 && window.getMousePosition().y < 600)
//		{
//    		//Scanner scanner = new Scanner(System.in);
//			LoginScreen();
//		}
//    	else if (window.getMousePosition().x > 300 && window.getMousePosition().x < 600
//				&& window.getMousePosition().y > 300 && window.getMousePosition().y < 600)
//    	{
//    		//Scanner scanner = new Scanner(System.in);
//    		RegisterScreen();
//    	}
//	}
//
////	//@Override
////	public void mousePressed(MouseEvent e) {
////		// TODO Auto-generated method stub
////		if (loginButton.getMousePosition() == true) {
////			
////		}
////	}
////
////	//@Override
////	public void mouseReleased(MouseEvent e) {
////		// TODO Auto-generated method stub
////		
////	}
////
////	//@Override
////	public void mouseEntered(MouseEvent e) {
////		// TODO Auto-generated method stub
////		
////	}
////
////	//@Override
////	public void mouseExited(MouseEvent e) {
////		// TODO Auto-generated method stub
////		
////	}
//}
//-----------------------------------------------------------------------------------------------------------------------------------------------------------
//UserDAOImplOld
//
//package com.revature.repo;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.InputMismatchException;
//import java.util.List;
//import java.util.Scanner;
//
//import com.revature.MainDriverFive;
//import com.revature.MainDriverFour;
//import com.revature.models.User;
//import com.revature.util.ConnectionFactory;
//
//public class UserDAOImplOld implements UserDAO {
//
//	@Override
//	public boolean insertUser(User f)
//	{	
//		//Created a sql query in terrible fashion
//		//String sql = "INSERT INTO User_table (User_name,User_description,User_price) VALUES(?,?,?)";
//		//String sql = "INSERT INTO customers (User_firstName,User_lastName,User_phoneNumber,User_emailAddress) VALUES(?,?,?,?)";
//		//String sql = "INSERT INTO customers (first_name,last_name,phone_number,email_address) VALUES(?,?,?,?)"; //WORKED
//		//String sql = "INSERT INTO customers (customer_user_id,first_name,last_name,phone_number,email_address) VALUES(?,?,?,?,?)";
//		//String sql = "INSERT INTO customers (first_name,last_name,phone_number,email_address) VALUES "
//		//+ "('" + f.getFirstName() + "','" + f.getLastName() + "','" + f.getPhoneNumber() + "','" + f.getEmailAddress() + "');";
//		String sql = "INSERT INTO customers (first_name,last_name,phone_number,email_address,username,password) VALUES(?,?,?,?,?,?)";
//		String sqlTwo = "INSERT INTO accounts (checking_account_number,checking_routing_number,checking_account_balance,"
//				+ "savings_account_number,savings_routing_number,savings_account_balance) VALUES(?,?,?,?,?,?)";
//		
//		//Created a connection
//		try(Connection conn = ConnectionFactory.getConnection();)
//		{
//			
//			//created a prepared statement object to be sent to our db
//			PreparedStatement ps = conn.prepareStatement(sql);
//			ps.setString(1, f.getFirstName());
//			ps.setString(2, f.getLastName());
//			ps.setString(3, f.getPhoneNumber());
//			ps.setString(4, f.getEmailAddress());
//			ps.setString(5, f.getUsername());
//			ps.setString(6, f.getPassword());
////			ps.setInt (1, f.getId());
////			ps.setString(2, f.getFirstName());
////			ps.setString(3, f.getLastName());
////			ps.setString(4, f.getPhoneNumber());
////			ps.setString(5, f.getEmailAddress());
//			ps.execute();
//			
//			PreparedStatement psTwo = conn.prepareStatement(sqlTwo);
//			ps.setString(1, f.getCheckingAccountNumber());
//			ps.setString(2, f.getCheckingRoutingNumber());
//			ps.setFloat(3, f.getCheckingAccountBalance());
//			ps.setString(4, f.getSavingsAccountNumber());
//			ps.setString(5, f.getSavingsRoutingNumber());
//			ps.setFloat(6, f.getSavingsAccountBalance());
//			psTwo.execute();
//		}
//		catch (SQLException e)
//		{
//			e.printStackTrace();
//		}
//		return true;
//	}
//
//	@Override
//	public User selectUserById(int id)
//	{
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<User> selectUsersByFirstName(String firstName)
//	{
//
//		List<User> databaseUsers = new ArrayList<>();
//		
//		//This time we're using a prepared statement. 
//		//prepared statements protect us from sql injections 
//		//    (SQL injection is where we send sql commands pretending to be values. 
////				e.g. insert into tables values (delete from table Users;)
//		// (they're easier for visualization of sql commands) 
//		
//		
//		//String sql = "select * from customers where firstName = ? ;";
//		String sql = "select * from customers where first_name = ? ;";
//		
//		//try with resources syntax 
//		try(Connection conn = ConnectionFactory.getConnection())
//		{
//			
//			PreparedStatement ps = conn.prepareStatement(sql);
//			ps.setString(1, firstName);
//			
//			//we expect rows and columns back from our db 
//			ResultSet rs = ps.executeQuery();
//			
//			//we want to convert those columsn and rows into objects. 
////			while(rs.next()) {
////				databaseUsers.add(new User(
////						rs.getString("User_firstName"),
////						rs.getString("User_lastName"),
////						rs.getString(3),
////						rs.getString(4)
////						));
////			}
//			while(rs.next())
//			{
//				databaseUsers.add(new User(
////						rs.getString("first_name"),
////						rs.getString("last_name"),
////						rs.getString(3),
////						rs.getString(4)
//						rs.getInt("customer_user_id"),
//						rs.getString("first_name"),
//						rs.getString("last_name"),
//						rs.getString(4),
//						rs.getString(5),
//						rs.getString(6),
//						rs.getString(7),
//						rs.getString(8),
//						rs.getString(9),
//						rs.getFloat(10),
//						rs.getString(11),
//						rs.getString(12),
//						rs.getFloat(13)
//						));
//			}
//		}
//		catch (SQLException e)
//		{
//			e.printStackTrace();
//		}
//		return databaseUsers;
//	}
//
//	@Override
//	public List<User> selectUserByAllCredentials(String username, String password, int id)
//	{
//		List<User> databaseUsers = new ArrayList<>();
//		//String sql = "select * from customers where first_name = ? ;";
//		String sql = "select * from customers where username = ? AND password = ? AND customer_user_id = ? ;";
//		
//		try(Connection conn = ConnectionFactory.getConnection())
//		{
//			PreparedStatement ps = conn.prepareStatement(sql);
//			ps.setString(1, username);
//			ps.setString(2, password);
//			ps.setInt(3, id);
//
//			ResultSet rs = ps.executeQuery();
//			
//			while(rs.next())
//			{
//				databaseUsers.add(new User(
//						rs.getInt("customer_user_id"),
//						rs.getString("first_name"),
//						rs.getString("last_name"),
//						rs.getString(4),
//						rs.getString(5),
//						rs.getString(6),
//						rs.getString(7),
//						rs.getString(8),
//						rs.getString(9),
//						rs.getFloat(10),
//						rs.getString(11),
//						rs.getString(12),
//						rs.getFloat(13)
//						));
//			}
//		}
//		catch (SQLException e)
//		{
//			e.printStackTrace();
//		}
//		catch (InputMismatchException e)
//      {
//			MainDriverFour.InvalidInputMessage();
//      }
//		catch (Exception e)
//      {
//			e.printStackTrace();
//      }
//		return databaseUsers;
//	}
//
//	@Override
//	public void updateUserCheckingBalance(float depositAmount, User user, Scanner scanner, UserDAO userDao)
//	{
//		//String sql = "update accounts where username = ? AND password = ? AND customer_user_id = ? ;";
//		//String sqlTwo = "set checking_account_balance = checking_account_balance + where username = ? AND password = ? AND customer_user_id = ? ;";
//		//String sql = "update accounts set checking_account_balance = checking_account_balance " + String.valueOf(depositAmount) + " where username = ? AND password = ? AND customer_user_id = ? ;";
//
//		try(Connection conn = ConnectionFactory.getConnection())
//		{
//			//String sql = "update accounts set checking_account_balance = ? where username = ? AND password = ? AND customer_user_id = ? ;";
//			String sql = "update customers set checking_account_balance = ? where username = ? AND password = ? AND customer_user_id = ? ;";
//			PreparedStatement ps = conn.prepareStatement(sql);	
//			float newBalance = user.getCheckingAccountBalance() + depositAmount;
//			//ps.setString(1, user.getUsername());
//			//ps.setString(2, user.getPassword());
//			//ps.setInt(3, user.getId());
//			ps.setFloat(1, newBalance);
//			ps.setString(2, user.getUsername());
//			ps.setString(3, user.getPassword());
//			ps.setInt(4, user.getId());
//
//			ResultSet rs = ps.executeQuery();
//		}
//		catch (SQLException e)
//		{
//			e.printStackTrace();
//		}
//		catch (InputMismatchException e)
//      {
//			MainDriverFour.InvalidInputMessage();
//      }
//		catch (Exception e)
//      {
//			e.printStackTrace();
//      }
//		System.out.println("Deposit successful.");
//		MainDriverFive.UserOptionsMenu(scanner, userDao, user);
//		//return true;
//	}
//	
//	@Override
//	public List<User> selectUsersByLastName(String lastName) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	
//	@Override
//	public List<User> selectAllUsers() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void updateUser(User f) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void deleteUser(User f) {
//		// TODO Auto-generated method stub
//
//	}
//}