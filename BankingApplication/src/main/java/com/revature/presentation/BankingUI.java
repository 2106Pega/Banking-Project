package com.revature.presentation;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        
        String userChoice = scanner.next();

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
        
        String customerAcountType = null;
        boolean validAccountType = false;
        do {
          System.out.println("\nEnter the number corresponding to Account Type");
          System.out.println("1 Checking Account");
          System.out.println("2 Savings Account");
          System.out.print("\nEnter Your Choice of Account Type (1 or 2) : ");
          customerAcountType = scanner.next();
          
          if(validNumber(customerAcountType))
          {
        	  if ((Integer.parseInt(customerAcountType) == 1)) {
                  customerAcountType = "Checking Account";
                  validAccountType = true;
              } else if((Integer.parseInt(customerAcountType) == 2)) {
                  customerAcountType = "Savings Account";
                  validAccountType = true;
              }
              else {
            	  System.out.print("\nInvalid Account Type");
            	  validAccountType = false;
              }
          }
          else {
        	  System.out.print("\nInvalid Account Type");
        	  validAccountType = false;
          }
          
        }while(!(validAccountType));
        
        double initialDeposit = 0;
        String amount;
        do {
        	System.out.print("\nEnter Initial Deposit Amount : ");
        	amount = scanner.next();
        	if(validNumber(amount)) {
        		initialDeposit = Double.parseDouble(amount);
        		if(initialDeposit <= 0) {
        			System.out.print("\nDeposit Amount Must be greater than 0");
        			amount = null;
        		}
        	}
        	else {
            	System.out.print("\nInvalid Deposit Amount");
        	}
        }while(!(validNumber(amount)));
        
        boolean isValid = false;
        String customerUsername;
        String customerPassword;
        do {
            System.out.print("\nCreate Username : ");
            customerUsername = scanner.next();
            System.out.print("Create Password : ");
            customerPassword = scanner.next(); 
            
            if((isValidUsernamePswd(customerUsername)))
            {
            	if((isValidUsernamePswd(customerPassword)))
                {
            		isValid = true;
                }
            }
            else {
            	isValid = false;
                System.out.println("\nInvalid Username or Password !! username and password must contain number characters and alphabets");
            }
            	
            
        }while(!(isValid));
        	
        BankAccount bankAccount = new BankAccount(0, customerFirstName, customerLastName, customerAcountType, 0, initialDeposit, false);
        User user = new User(0, customerUsername, customerPassword);
        //		CustomerAccount customerAccount =  new CustomerAccount();
        customerAccount.createAccount(user, bankAccount);

        System.out.println("\nPlease Save Your Account Information");
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
            System.out.println("6 Apply for Another BankAccount");
            System.out.println("9 Previous Menu (Log Out)");
            System.out.println("0 Exit From Banking Application\n");
            System.out.print("Enter Your Choice : ");


            //int customerMenuChoice = Integer.parseInt(scanner.next());
            String customerMenuChoice = scanner.next();

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
                	List < BankAccount > bankAccountList = customerAccount.loadAllAccounts(bankAccount.getCustomerID());
                    for (BankAccount account: bankAccountList) {
                    	account.displayAccountDetails();            
                    }
                    customerMenu(bankAccount);
                    break;
                case "6":
                    createAnotherAccount(bankAccount);
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


    private void createAnotherAccount(BankAccount bankAccount) {
    	String customerAcountType = null;
        boolean validAccountType = false;
    	do {
            System.out.println("\nEnter the number corresponding to Account Type");
            System.out.println("1 Checking Account");
            System.out.println("2 Savings Account");
            System.out.print("\nEnter Your Choice of Account Type (1 or 2) : ");
            customerAcountType = scanner.next();
            
            if(validNumber(customerAcountType))
            {
          	  if ((Integer.parseInt(customerAcountType) == 1)) {
                    customerAcountType = "Checking Account";
                    validAccountType = true;
                } else if((Integer.parseInt(customerAcountType) == 2)) {
                    customerAcountType = "Savings Account";
                    validAccountType = true;
                }
                else {
              	  System.out.print("\nInvalid Account Type");
              	  validAccountType = false;
                }
            }
            else {
          	  System.out.print("\nInvalid Account Type");
          	  validAccountType = false;
            }
            
          }while(!(validAccountType));
          
          double initialDeposit = 0;
          String amount;
          do {
          	System.out.print("\nEnter Initial Deposit Amount : ");
          	amount = scanner.next();
          	if(validNumber(amount)) {
          		initialDeposit = Double.parseDouble(amount);
          		if(initialDeposit <= 0) {
          			System.out.print("\nDeposit Amount Must be greater than 0");
          			amount = null;
          		}
          	}
          	else {
              	System.out.print("\nInvalid Deposit Amount");
          	}
          }while(!(validNumber(amount)));
          

          customerAccount.createAnotherBankAccount(bankAccount,customerAcountType, initialDeposit);

          System.out.println("\nNew Account Created successfully, waiting for employee approvel \n");
          
          customerLogin();
		
	}
	private void adminLogin() {
        System.out.println("\n***** Admin Login Page *****\n");
        System.out.println("Enter the number corresponding to the choices");
        System.out.println("1 Create Employee Account");
        System.out.println("2 Employee Login");
        System.out.println("9 Previous Menu (Log Out)");
        System.out.println("0 Exit From Banking Application \n");
        System.out.print("Enter Your Choice : ");
    
        String adminMenuChoice = scanner.next();
        
        switch (adminMenuChoice.toLowerCase().trim()) {
        case "1":
        	createEmployeeAccount();
            break;
        case "2":
        	employeeLogin();
            break;
        case "9":
            welcomeMenu();
            break;
        case "0":
        	System.out.println("\nThanks for using NewWorld Banking Application");
            System.exit(1);
            Runtime.getRuntime().halt(0);
            break;
        default:
            System.out.print("\nOption is not valid, try again!\n");
            adminLogin();
        }
    }


    private void createEmployeeAccount() {
    	System.out.print("\nEnter Your First Name : ");
        String employeeFirstName = scanner.next();

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

        employeeLogin();
		
	}
	private void employeeLogin() {
        System.out.print("\n***** Employee Login Page *****\n");
        System.out.print("Enter Username : ");
        String loginUsername = scanner.next();
        System.out.print("Enter Your Account Password : ");
        String loginPassword = scanner.next();

        User user = new User(0, loginUsername, loginPassword);

        AdminAccount adminAccount = employeeAccount.loginValidation(user);

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
        System.out.println("2 Approve/Reject Bank Accounts One By One");
        System.out.println("3 Search Bank Accounts By Account Number");
        System.out.println("9 Back To Welcome Menu (Log Out)");
        System.out.println("0 Exit From Banking Application \n");
        System.out.print("Enter Your Choice : ");

        String employeeMenuChoice = scanner.next();

        if ((employeeMenuChoice.toLowerCase().trim().equalsIgnoreCase("1"))) {

            List < BankAccount > bankAccountList = employeeAccount.loadUnapprovedAccounts();

            for (BankAccount account: bankAccountList) {

                System.out.printf("%-5s %-5s %-5s\n", "Customer ID	", ":", account.getCustomerID());
                System.out.printf("%-5s %-5s %-5s\n", "Name		", ":", account.getFirstName() + " " + account.getLastName());
                System.out.printf("%-5s %-5s %-5s\n", "Account Number  ", ":", account.getAccountNumber());
                System.out.println("------------------------------\n");
            }

            employeeMenu(adminAccount);
        } else if ((employeeMenuChoice.toLowerCase().trim().equalsIgnoreCase("2"))) {

            List < BankAccount > bankAccountList = employeeAccount.loadUnapprovedAccounts();
            for (BankAccount accountAccount: bankAccountList) {

                //approveORrejectAccount(accountAccount, adminAccount);
                if (!(approveORrejectAccount(accountAccount))) {
                    break;
                }
            }
            employeeMenu(adminAccount);

        } 
        else if ((employeeMenuChoice.toLowerCase().trim().equalsIgnoreCase("3"))) {
        	int accountNumber = 0;
        	int count = 0;
        	
        	do {	
        		if (count == 3) {
                    System.out.print("\nSorry, Your 3 attempt is over\n");
                	logger.warn("Employee had 3 failed attempt to access customer account : " + accountNumber +" , employee_id: " + adminAccount.getEmployeeID());

                	employeeMenu(adminAccount);
                }       		
        		System.out.print("\nEnter Bank Account Number : ");
            	String accountInput = scanner.next().trim();          	
            	try {
            		if(accountInput.replace(" ", "").length() == 8) {
                		accountNumber = Integer.parseInt(accountInput);
                		
                		BankAccount account = employeeAccount.getUnapprovedAccount(accountNumber);
                    	if(account !=null) {
                    		approveORrejectAccount(account);
                    		employeeMenu(adminAccount);
                    	}
                    	else {
                    		System.out.println("\nNo bank account with this account number !! \n");
                    		employeeMenu(adminAccount);
                    	}
            		}
            		else {
                		System.out.println("\n***** Note: Account Number must have 8 digits *****");
                	}
         		
            	} catch(Exception e) {
            		System.out.println("\nInvalid Account Number !! ");
            	}
        		
            	count++;
        	} while(accountNumber !=8);
        	
        }
        else if ((employeeMenuChoice.toLowerCase().trim().equalsIgnoreCase("9"))) {
            System.out.print("employeeMenu choose 9 ");
            welcomeMenu();
        } else if ((employeeMenuChoice.toLowerCase().trim().equalsIgnoreCase("0"))) {
            System.out.print("Thanking for using NewWorld Banking Application");
            System.exit(0);
        } else {
            employeeMenu(adminAccount);
        }
    }


    private boolean approveORrejectAccount(BankAccount account) {

        String anotherCustomer = null;
        boolean accountApproved = false;
        boolean continueAnotherAccount = false;

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
                	//accountApproved = true;
                    System.out.print("Account Approved Successfully !! ");
                	logger.info("Employee approved customer bank account successfully, approved bankaccount = " + account.getAccountNumber());

                } else {
                	accountApproved = false;
                    System.out.print("Account cannot be updated at this moment ");
                }
            } else if (validateYesNo(wantToApprove) == 0){
                accountApproved = false;
                System.out.print("Account is rejected and can be reviewed later !! ");
            }
            else {
            	System.out.print("\nInvalid User input \n");
            	accountApproved = false;
            	approveORrejectAccount(account);
            }

            System.out.print("\n\nDo you want to continue with another account? (yes/no) ");
            anotherCustomer = scanner.next();

            if((validateYesNo(anotherCustomer) == 1)){
            	continueAnotherAccount = true;
            }
            else {
            	continueAnotherAccount = false;
            	System.out.print("\nInvalid User input \n");
            	continueAnotherAccount = false;
            }

            return continueAnotherAccount;

        } while (continueAnotherAccount);
    }

    private int validateYesNo(String input) {
        if ((input.toLowerCase().equals("yes")) || (input.toLowerCase().equals("y"))) {
            return 1;
        } else if ((input.toLowerCase().equals("no")) || (input.toLowerCase().equals("n"))) {
            return 0;
        } else {
            return -1;
        }

    }

    private void depositeMoney(BankAccount bankAccount) {
        double depositAmount = 0;
        String amount;
        int count = 0;
        do {
            if (count == 3) {
                System.out.print("\nSorry, Your 3 attempt is over\n");
            	logger.warn("customer had 3 failed attempt to deposite money , customer_id = " + bankAccount.getCustomerID());

                customerMenu(bankAccount);
            }

            System.out.print("Enter Deposit Amount : ");
            amount = scanner.next();
        	if(validNumber(amount)) {
        		depositAmount = Double.parseDouble(amount);
        		if(depositAmount <= 0) {
        			System.out.print("\n***** Note : Please enter the amount great than 0 *****\n");
        			amount = null;
        		}
        	}
        	else {
            	System.out.println("\nInvalid Deposit Amount");
        	}
        	count++;
        } while ((depositAmount <= 0) || (!(validNumber(amount))));

        bankAccount = customerAccount.addMoney(bankAccount, depositAmount);

        if (bankAccount != null) {
            System.out.println("Deposit of $" + depositAmount + " added successfully to your account");
        	logger.info("customer deposited $" + depositAmount + " successfully to the account , customer_id = " + bankAccount.getCustomerID());

        }
        customerMenu(bankAccount);
    }


    /**********************************/

    private void withdrawMoney(BankAccount bankAccount) {
        double withdrawAmount = 0;
        String amount;
        int count = 0;
        do {
            if (count == 3) {
                System.out.print("\nSorry, Your 3 attempt is over\n");
            	logger.warn("customer had 3 failed attempt to withdraw money , customer_id = " + bankAccount.getCustomerID());
                customerMenu(bankAccount);
            }

            System.out.print("Enter Withdraw Amount : ");
            amount = scanner.next();

            if(validNumber(amount)) {
            	withdrawAmount = Double.parseDouble(amount);
            	if (withdrawAmount <= 0) {
                    System.out.print("\n***** Note : Please enter the amount great than 0 and less than you current account balance *****\n");
                } else if ((withdrawAmount >= bankAccount.getAccountBalance())) {
                    System.out.print("\n***** Note : Insufficient balance in your account*****\n");
                }
        	}
        	else {
            	System.out.println("\nInvalid Withdraw Amount");
        	}

            count++;

        } while ((withdrawAmount <= 0) || (withdrawAmount >= bankAccount.getAccountBalance()) || (!(validNumber(amount))));

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
            String accountInput = scanner.next();
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

    
    private boolean isValidUsernamePswd(String input)
    {
        // Regex to check valid password.
        String regex = "^(?=.*[0-9])"
                       + "(?=.*[a-z])(?=.*[A-Z])"
                       + "(?=.*[@#$%^&+=])"
                       + "(?=\\S+$).{8,20}$";
  
        Pattern p = Pattern.compile(regex);
  
        if (input == null) { return false; }
        Matcher m = p.matcher(input);
        return m.matches();
    } 
    
    private boolean validNumber(String input) {
    	try{
    	    double isValid = Double.parseDouble(input);
    	    return true;
    	} catch(Exception e) {
    	    return false;
    	    
    	}
    	
    }
}