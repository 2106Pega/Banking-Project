package com.revature.presentation;

import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.revature.models.AdminAccount;
import com.revature.models.BankAccount;
import com.revature.models.CustomerAccount;
import com.revature.models.EmployeeAccount;
import com.revature.models.User;


public class BankingUI {

    private static Scanner scanner = new Scanner(System.in);
    //	private static CustomerFuncationalities CustomerFuncationalities =  new CustomerFuncationalities();
    private static CustomerAccount customerAccount = new CustomerAccount();
    private static EmployeeAccount employeeAccount = new EmployeeAccount();
    final static Logger logger = Logger.getLogger(BankingUI.class);

    
    public static void loadLog4j() {
		String log4jConfPath = System.getProperty("D:\\Revature\\Projects\\Banking-Project\\BankingApplication\\src\\test\\resources\\log4j.properties");
		PropertyConfigurator.configure(log4jConfPath);

    }
    public void welcomeMenu() {
    	logger.info("User Accessed the Welcome Menu page");
        System.out.println("\n\n***************************************");
        System.out.println("Welcome to NewWorld Banking Application");
        System.out.println("***************************************");
        System.out.println("\nEnter the number corresponding to the choices");
        System.out.println("1 Create Account");
        System.out.println("2 Customer Login");
        System.out.println("3 Admin Login");
        System.out.println("0 Exit \n");
        System.out.print("Enter Your Choice : ");
        //int userChoice = Integer.parseInt(scanner.next());
/**
        if (userChoice == 1) {
            createNewAccount();
        } else if (userChoice == 2) {
            customerLogin();
        } else if (userChoice == 3) {
            // admin login and its menu
            adminLogin();
        } else if (userChoice == 0) {
            System.exit(0);
        } else {
            //check if user entered a number that matches with our option
            // check if user entered int, string or character

            //if not right choice loop the menu again
            welcomeMenu();
        }
        
 */       
        
        String userChoice = scanner.next().trim();

        switch (userChoice.toLowerCase().trim()) {
            case "1":
            	createNewAccount();
                break;
            case "2":
            	customerLogin();
                break;
            case "3":
            	adminLogin();
                break;
            case "0":
                //terminate the program
            	System.out.println("\nThanks for using NewWorld Banking Application");
                System.exit(1);
                Runtime.getRuntime().halt(0);
                break;
            default:
                System.out.print("\nOption is not valid, try again!\n");
                welcomeMenu();
        }
    }


    private void createNewAccount() {
        System.out.print("\nEnter your First Name : ");
        String customerFirstName = scanner.next();
        System.out.print("Enter your Last Name : ");
        String customerLastName = scanner.next();

        System.out.println("\nEnter the number corresponding to Account Type");
        System.out.println("1 Checking Account");
        System.out.println("2 Savings Account");
        System.out.print("\nEnter Your Choice of Account Type (1 or 2) : ");
        String customerAcountType = scanner.next();

        System.out.print("\nEnter Initial Deposit Amount : ");
        double initialDeposit = Double.parseDouble(scanner.next());

        System.out.print("\nCreate Username : ");
        String customerUsername = scanner.next();
        System.out.print("Create Password : ");
        String customerPassword = scanner.next();

        if (Integer.parseInt(customerAcountType) == 1) {
            customerAcountType = "Checking Account";
        } else {
            customerAcountType = "Savings Account";
        }

        BankAccount bankAccount = new BankAccount(0, customerFirstName, customerLastName, customerAcountType, 0, initialDeposit, false);
        User user = new User(0, customerUsername, customerPassword);
        //		CustomerAccount customerAccount =  new CustomerAccount();
        customerAccount.createAccount(user, bankAccount);

        System.out.print("\nPlease Save Your Account Information\n");
        bankAccount.displayAccountDetails();

        System.out.print("\n***** Note: The Bank Employee Have To Approve Your Account For Full Functionalities. *****\n");
        // calling customer login to see if the account is approved
        customerLogin();
    }


    private void customerLogin() {
        System.out.print("\n***** Customer Login Page *****\n");
        System.out.print("\nEnter Username : ");
        String loginUsername = scanner.next();

        System.out.print("Enter Your Account Password : ");
        String loginPassword = scanner.next();

        User user = new User(0, loginUsername, loginPassword);

        BankAccount bankAccount = customerAccount.loginValidation(user);

        //		CustomerAccount customerAccount =  new CustomerAccount();
        if (bankAccount != null) {
            
        	System.out.print("\nLogin Successfully !!, Welcome " + bankAccount.getFirstName() + " " + bankAccount.getLastName() + "\n");
        	logger.info("Customer login Successfully !! , customer_id = " + bankAccount.getCustomerID());
        	if(bankAccount.isAccountApproved()) {
            	customerMenu(bankAccount);
            }
            else {
            	bankAccount.displayAccountDetails();
            	System.out.println("\n***** Note: The Bank Employee Have To Approve Your Account For Full Functionalities. *****");
            	logger.warn("Customer login Successfully !! but disabled fuctions due to unapproved account, customer_id = " + bankAccount.getCustomerID());

            	welcomeMenu();
            }
        } else {
            System.out.print("\nCustomer Login Failed !!");
        	logger.error("Customer login Failed !! , employee_id = " + loginUsername);
            welcomeMenu();
        }
    }


    public void customerMenu(BankAccount bankAccount) {
    	logger.info("Customer login successfully and accessed customerMenu page, customer_id = " + bankAccount.getCustomerID());
        if (bankAccount != null) {
            System.out.println("\n***** Customer Login Page *****\n");
            System.out.println("Enter the number corresponding to the choices");
            System.out.println("1 Deposit Money");
            System.out.println("2 Withdraw Money");
            System.out.println("3 Transfer Money");
            System.out.println("4 Check Balance");
            System.out.println("5 Display Account Details");
            System.out.println("9 Previous Menu (Log Out)");
            System.out.println("0 Exit From Banking Application\n");
            System.out.print("Enter Your Choice : ");


            //int customerMenuChoice = Integer.parseInt(scanner.next());
            String customerMenuChoice = scanner.next().trim();

            switch (customerMenuChoice.toLowerCase().trim()) {
                case "1":
                    depositeMoney(bankAccount);
                    break;
                case "2":
                    withdrawMoney(bankAccount);
                    break;
                case "3":
                    transferMoney(bankAccount);
                    break;
                case "4":
                    displayAccountBalance(bankAccount);
                    break;
                case "5":
                    bankAccount.displayAccountDetails();
                    customerMenu(bankAccount);
                    break;
                case "9":
                    //call previous menu
                    welcomeMenu();
                    break;
                case "0":
                    //terminate the program
                	System.out.println("\nThanks for using NewWorld Banking Application");
                    System.exit(1);
                    Runtime.getRuntime().halt(0);
                    break;
                default:
                    System.out.print("\nOption is not valid, try again!");
                    customerMenu(bankAccount);
            }
        }
    }


    private void adminLogin() {
        System.out.println("\n***** Admin Login Page *****\n");
        System.out.println("Enter the number corresponding to the choices");
        System.out.println("1 Create Employee Account");
        System.out.println("2 Employee Login");
        System.out.println("9 Previous Menu (Log Out)");
        System.out.println("0 Exit From Banking Application \n");
        System.out.print("Enter Your Choice : ");

        int adminMenuChoice = Integer.parseInt(scanner.next());

        if (adminMenuChoice == 1) {

            System.out.print("\nEnter Your First Name : ");
            String employeeFirstName = scanner.next();
            scanner.next();
            System.out.print("Enter Your Last Name : ");
            String employeeLastName = scanner.next();
            System.out.print("Enter Your Job Title : ");
            String employeeTitle = scanner.next();

            System.out.print("Create Username : ");
            String employeeUsername = scanner.next();
            System.out.print("Create Password : ");
            String employeePassword = scanner.next();

            AdminAccount adminAccount = new AdminAccount(0, employeeFirstName, employeeLastName, employeeTitle);
            User user = new User(0, employeeUsername, employeePassword);

            if (employeeAccount.createAccount(user, adminAccount)) {
                System.out.print("\nEmployee Account Created Successfully !! \n");
            } else {
                System.out.println("\nUnable to Create Account at this moment, please try again later !! \n");
            }

            // calling employee login
            employeeLogin();


        } else if (adminMenuChoice == 2) {
            employeeLogin();
        } else if (adminMenuChoice == 2) {
            employeeLogin();
        } else if (adminMenuChoice == 9) {
            System.out.print("employeeMenu choose 9 ");
            welcomeMenu();
        } else if (adminMenuChoice == 0) {
            System.out.print("Thanking for using NewWorld Banking Application");
            System.exit(0);
        } else {
            welcomeMenu();
        }
    }


    private void employeeLogin() {
        System.out.println("***** Employee Login Page *****\n");
        System.out.print("Enter Username : ");
        String loginUsername = scanner.next();
        System.out.print("Enter Your Account Password : ");
        String loginPassword = scanner.next();

        User user = new User(0, loginUsername, loginPassword);

        AdminAccount adminAccount = employeeAccount.loginValidation(user);

        //		CustomerAccount customerAccount =  new CustomerAccount();
        if ((adminAccount != null)) {
            System.out.print("\nLogin Successfully !!, Welcome " + adminAccount.getEmployeeFirstName() + " " + adminAccount.getEmployeeLastName() + "\n");
        	logger.info("Employee login Successfully !! , employee_id = " + adminAccount.getEmployeeID());
            employeeMenu(adminAccount);
        } else {
            System.out.print("\nEmployee Login Failed !!");
        	logger.error("Employee login Failed !! , employee_id = " + loginUsername);

            welcomeMenu();
        }

    }


    private void employeeMenu(AdminAccount adminAccount) {
    	logger.info("Employee login successfully and accessed Employee Menu page, , employee_id = " + adminAccount.getEmployeeID());
        System.out.println("\n***** Employee Menu Page *****\n");
        System.out.println("Enter the number corresponding to the choices");
        System.out.println("1 View All Unapproved BankAccounts");
        System.out.println("2 Approve/Reject Bank Accounts By Customer ID");
        System.out.println("9 Back To Welcome Menu (Log Out)");
        System.out.println("0 Exit From Banking Application \n");
        System.out.print("Enter Your Choice : ");

        int employeeMenuChoice = Integer.parseInt(scanner.next());

        if (employeeMenuChoice == 1) {

            List < BankAccount > bankAccountList = employeeAccount.loadUnapprovedAccounts();

            for (BankAccount account: bankAccountList) {

                System.out.printf("%-5s %-5s %-5s\n", "Customer ID	", ":", account.getCustomerID());
                System.out.printf("%-5s %-5s %-5s\n", "Name		", ":", account.getFirstName() + " " + account.getLastName());
                System.out.printf("%-5s %-5s %-5s\n", "Account Number  ", ":", account.getAccountNumber());
                System.out.println("------------------------------\n");
            }

            employeeMenu(adminAccount);
        } else if (employeeMenuChoice == 2) {

            List < BankAccount > bankAccountList = employeeAccount.loadUnapprovedAccounts();
            for (BankAccount accountAccount: bankAccountList) {

                //approveORrejectAccount(accountAccount, adminAccount);
                if (!(approveORrejectAccount(accountAccount))) {
                    break;
                }
            }
            employeeMenu(adminAccount);



        } else if (employeeMenuChoice == 9) {
            System.out.print("employeeMenu choose 9 ");
            welcomeMenu();
        } else if (employeeMenuChoice == 0) {
            System.out.print("Thanking for using NewWorld Banking Application");
            System.exit(0);
        } else {
            welcomeMenu();
        }
    }


    private boolean approveORrejectAccount(BankAccount account) {

        String anotherCustomer = null;
        boolean accountApproved = false;

        System.out.println("------------------------------\n");
        System.out.printf("%-5s %-5s %-5s\n", "Customer ID	", ":", account.getCustomerID());
        System.out.printf("%-5s %-5s %-5s\n", "Name		", ":", account.getFirstName() + " " + account.getLastName());
        System.out.printf("%-5s %-5s %-5s\n", "Account Number  ", ":", account.getAccountNumber());


        do {
            System.out.print("Do you want to approve this account ? (yes/no) ");
            String wantToApprove = scanner.next();

            if (validateYesNo(wantToApprove) == 1) {
                accountApproved = true;

                boolean accountUpdated = employeeAccount.approveAccount(account.getCustomerID(), account.getAccountNumber(), accountApproved);

                if (accountUpdated) {
                    System.out.print("Account Approved Successfully !! ");
                	logger.info("Employee approved customer bank account successfully, approved bankaccount = " + account.getAccountNumber());

                } else {
                    System.out.print("Account cannot be updated at this moment ");
                }
            } else {
                accountApproved = false;
            }

            System.out.println("accout = " + account);

            System.out.print("\n\nDo you want to continue with another account? (yes/no) ");
            anotherCustomer = scanner.next();

            if ((validateYesNo(anotherCustomer) == 0)) {
                return false;
            } else return true;



        } while (!(validateYesNo(anotherCustomer) == 1));
    }

    private int validateYesNo(String input) {
        if ((input.toLowerCase().equals("yes")) || (input.toLowerCase().equals("y"))) {
            System.out.println("validateYesNo : 1");
            return 1;
        } else if ((input.toLowerCase().equals("no")) || (input.toLowerCase().equals("n"))) {
            System.out.println("validateYesNo : 0");
            return 0;
        } else {
            System.out.println("validateYesNo : -1");
            return -1;
        }

    }


    //	public boolean checkIntegerInput (int userInput) {
    //		try{
    //			
    //			if(Integer.parseInt(userInput)) {
    //				return true;
    //			}
    //			else {
    //				return false;
    //			}
    //			
    //       }catch (NumberFormatException ex) {
    //           System.out.println("The number is not an integer ");
    //       }
    //		return false;
    //	}


    private void depositeMoney(BankAccount bankAccount) {
        double depositAmount;
        int count = 0;
        do {
            if (count == 3) {
                System.out.print("\nSorry, Your 3 attempt is over\n");
            	logger.warn("customer had 3 failed attempt to deposite money , customer_id = " + bankAccount.getCustomerID());

                customerMenu(bankAccount);
            }

            System.out.print("Enter Deposit Amount : ");
            depositAmount = scanner.nextDouble();

            if (depositAmount <= 0) {
                System.out.print("\n***** Note : Please enter the amount great than 0 *****\n");
            }

            count++;

        } while (depositAmount <= 0);

        bankAccount = customerAccount.addMoney(bankAccount, depositAmount);

        if (bankAccount != null) {
            System.out.println("Deposit of $" + depositAmount + " added successfully to your account");
        	logger.info("customer deposited $" + depositAmount + " successfully to the account , customer_id = " + bankAccount.getCustomerID());

        }
        customerMenu(bankAccount);
    }


    /**********************************/

    private void withdrawMoney(BankAccount bankAccount) {
        double withdrawAmount;
        int count = 0;
        do {
            if (count == 3) {
                System.out.print("\nSorry, Your 3 attempt is over\n");
            	logger.warn("customer had 3 failed attempt to withdraw money , customer_id = " + bankAccount.getCustomerID());
                customerMenu(bankAccount);
            }

            System.out.print("Enter Withdraw Amount : ");
            withdrawAmount = scanner.nextDouble();


            if (withdrawAmount <= 0) {
                System.out.print("\n***** Note : Please enter the amount great than 0 and less than you current account balance *****\n");
            } else if ((withdrawAmount >= bankAccount.getAccountBalance())) {
                System.out.print("\n***** Note : Insufficient balance in your account*****\n");
            }

            count++;

        } while ((withdrawAmount <= 0) || (withdrawAmount >= bankAccount.getAccountBalance()));

        // bankAccount = customerAccount.withdrawMoney(bankAccount, withdrawAmount);

        if (customerAccount.withdrawMoney(bankAccount, withdrawAmount) != null) {
            System.out.println("\nSuccessfully withdraw the $" + withdrawAmount + " from your bank account");
        	logger.info("customer withdraw $" + withdrawAmount + " successfully from the account , customer_id = " + bankAccount.getCustomerID());

        } else {
            System.out.println("\nFailed to withdraw the $" + withdrawAmount + " from your bank account");
        	logger.error("customer failed to withdraw $" + withdrawAmount + " from account " + bankAccount.getAccountNumber());

        }

        customerMenu(bankAccount);
    }

    private BankAccount transferMoney(BankAccount bankAccount) {

        double transferAmount = 0;
        int count = 0;
        int transferAccountNumber = 0;

        do {
            if (count == 3) {
                System.out.print("\nSorry, Your 3 attempt is over\n");
            	logger.warn("customer had 3 failed attempt to transfer money , customer_id = " + bankAccount.getCustomerID());

                customerMenu(bankAccount);
            }
            
            System.out.print("Enter Account Number You Would Like Transfer To : ");
            //int transferAccountNumber = scanner.nextInt();
            String accountInput = scanner.next().trim();
            try {
            	
            	if(accountInput.replace(" ", "").length() == 8) {
            		transferAccountNumber = Integer.parseInt(accountInput);
            		
            		if (bankAccount.getAccountNumber() == transferAccountNumber) {
                        System.out.println("\nInvalid Account Number !! ");
                        System.out.println("You cannot transfer money between same account\n");
                        customerMenu(bankAccount);
                    }
            		else {
            			System.out.print("Enter Transfer Amount : ");
                        transferAmount = scanner.nextDouble();


                        if (transferAmount <= 0) {
                            System.out.print("\n***** Note : Please enter the amount great than 0 and less than you current account balance *****\n");
                        } else if ((transferAmount >= bankAccount.getAccountBalance())) {
                            System.out.print("\n***** Note : Insufficient balance in your account*****\n");
                        }
            		}
            	}
            	else {
            		System.out.println("\n***** Note: Account Number must have 8 digits *****");
            	}

            }catch (Exception e) {
            	System.out.println("\nInvalid Account Number !! ");
            	transferMoney(bankAccount);
            }

//            System.out.print("Enter Transfer Amount : ");
//            transferAmount = scanner.nextDouble();
//
//
//            if (transferAmount <= 0) {
//                System.out.print("\n***** Note : Please enter the amount great than 0 and less than you current account balance *****\n");
//            } else if ((transferAmount >= bankAccount.getAccountBalance())) {
//                System.out.print("\n***** Note : Insufficient balance in your account*****\n");
//            }

            count++;

        } while ((transferAmount <= 0) || (transferAmount >= bankAccount.getAccountBalance()));

        
        BankAccount transferToAccount = customerAccount.transferingMoney(bankAccount, transferAccountNumber, transferAmount);

        if (transferToAccount != null) {
            System.out.println("\nSuccessfully transfered $" + transferAmount + " from your bank account");
        	logger.info("customer transfered $" + transferAmount + " successfully from account " + bankAccount.getAccountNumber() + " to account " + transferAccountNumber);

        } else {
            System.out.println("\nFailed to transfer the $" + transferAmount + " from your bank account");
        	logger.error("customer failed to transfer $" + transferAmount + " from account " + bankAccount.getAccountNumber() + " to account " + transferAccountNumber);

        }
        bankAccount = customerAccount.loadCustomerInfoByID(bankAccount.getCustomerID());
        customerMenu(bankAccount);

        return bankAccount;

    }

    private void displayAccountBalance(BankAccount bankAccount) {
        System.out.printf("%-10s %-10s %-10s\n", "Account Number	", ":", bankAccount.getAccountNumber());
        System.out.printf("%-10s %-10s %-10s\n", "Account Type	", ":", bankAccount.getAccountType());
        System.out.printf("%-10s %-10s %-10s\n", "Account Balance ", ":", bankAccount.getAccountBalance());

        customerMenu(bankAccount);
    }

}