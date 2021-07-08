/*************************
 * Author: Jason Hubbs
 * Date: 07-07-21
 */
package com.revature.presentation;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import com.revature.models.Account;
import com.revature.models.Customer;
import com.revature.models.User;
import com.revature.repo.AccountDAO;
import com.revature.repo.AccountDAOImpl;
import com.revature.repo.CustomerDAO;
import com.revature.repo.CustomerDAOImpl;
import com.revature.repo.EmployeeDAO;
import com.revature.repo.EmployeeDAOImpl;
import com.revature.repo.UserDAO;
import com.revature.repo.UserDAOImpl;

public class PresentationService {
	
	private UserDAO uDao;
	private CustomerDAO cDao;
	private EmployeeDAO eDao;
	private AccountDAO aDao;
	static Logger log;
	private Pattern emailPattern;
	Matcher emailMatcher;
	Matcher decimalMatcher;
	
	public PresentationService() {
		this.uDao = new UserDAOImpl();
		this.cDao = new CustomerDAOImpl();
		this.eDao = new EmployeeDAOImpl();
		this.aDao = new AccountDAOImpl();
		log = Logger.getLogger(PresentationService.class.getName());
		emailPattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
	}
	
	//returns true if transfer between accounts is completed, false if not
	public boolean TransferBetweenAccounts(double transferAmount, int targetAccountID, int sourceAccountID, Customer customer) {
		Account account = aDao.GetAccountByAccountID(sourceAccountID);
		if(account.getBalance() < transferAmount || transferAmount < 0 || account.getCustomer_id() != customer.getCustomerId()) {
			log.info("ERROR: Transfer between account " + targetAccountID + " and " + sourceAccountID + " failed due to insufficient balance");
			return false;
		}else if(aDao.TransferMoneyBetweenAccounts(transferAmount, targetAccountID, sourceAccountID, customer)) {
			log.info("SUCCESS: Transfer between account " + targetAccountID + " and " + sourceAccountID + " succeeded");
			return true;
		}
		log.info("ERROR: Transfer between account " + targetAccountID + " and " + sourceAccountID + " failed due to unknown error...");
		return false;
	}
	
	//returns all customers in a list
	public List<Customer> GetAllCustomers(){
		List<Customer> customers = cDao.GetAllCustomers();
		if(customers.size() > 0) {
			log.info("SUCCESS: Fetching all customers");
			return customers;
		}
		log.info("ERROR: Failed to fetch any customers");
		return customers;
	}
	
	// returns true if deposit into account is successful, false if not
	public boolean DepositIntoAccount(int accountID, double depositAmount, Customer customer) {
		//verify deposit amount
		if( depositAmount <= 0) {
			log.info("ERROR: Deposit into account " + accountID + " failed due to invalid deposit amount (" + depositAmount + ")");
			return false;
		}
		
		//verify accountID belongs to an account held by customer
		List<Account> accountsByCustomer = GetAccountsByCustomer(customer.getCustomerId());
	    for(Account a : accountsByCustomer){
	    	//if so, deposit depositAmount into account
	        if(a.getAccount_id() == accountID && a.isApproved() == true) {
	        	if(aDao.DepositIntoAccount(accountID, depositAmount)) {
	        		log.info("SUCCESS: Deposit into account " + accountID + " succeeded (" + depositAmount + ")");
					return true;
	        	}
	        }
	    }
	    //return false if no customer accounts matching accountID are found
	    log.info("ERROR: Deposit into account " + accountID + " failed due to unknown error... (" + depositAmount + ")");
		return false;
	}
	
	//returns true if withdrawal from account is successful, false if not
	public boolean WithdrawFromAccount(int accountID, double withdrawAmount, Customer customer) {
		if( withdrawAmount <= 0) {
			log.info("ERROR: Withdraw from account " + accountID + " failed due to invalid withdraw amount (" + withdrawAmount + ")");
			return false;
		}
		//verify accountID belongs to an account held by customer
		List<Account> accountsByCustomer = GetAccountsByCustomer(customer.getCustomerId());
	    for(Account a : accountsByCustomer){
	    	//if so, deposit depositAmount into account
	        if(a.getAccount_id() == accountID && a.isApproved() == true) {
	        	if(aDao.WithdrawFromAccount(accountID, withdrawAmount)) {
	    			log.info("SUCCESS: Withdraw from account " + accountID + " succeeded (" + withdrawAmount + ")");
					return true;
	        	}
	        }
	    }
	    //return false if no customer accounts matching accountID are found
	    log.info("ERROR: Withdraw from account " + accountID + " failed due to unknown error... (" + withdrawAmount + ")");
		return false;
	}
	
	//Returns the user associated with the username and password given as parameters
	public User GetEmployeeByUsernameAndPassword(String username, String password) {
		//check is user is null, if not then a valid user exists
		User user = eDao.GetEmployeeByUsernameAndPassword(username, password);
		if(user != null) {
			log.info("SUCCESS: Fetching user by username and password");
			return user;
		}
		log.info("ERROR: Failure fetching user by username and password");
		return user;
	}
	
	//returns a list of accounts 
	public List<Account> GetPendingAccounts(){
		//check if list is populated, if not, operation was unsuccessful
		List<Account> accounts = eDao.GetPendingAccounts();
		if(accounts.size() > 0) {
			log.info("SUCCESS: Fetching pending accounts");
			return accounts;
		}
		log.info("ERROR: No available pending accounts");
		return accounts;
	}
	
	//returns a customer based on the userID of the username and password. 
	public Customer GetCustomerByUsernameAndPassword(String username, String password){
		User user = null;
		Customer customer = null;
		//if user with matching credentials is found, the user object is used to return the 
		//associated customer.
		if((user = uDao.GetUserByUsernameAndPassword(username, password)) != null) {
			if((customer = cDao.GetCustomerByUser(user)) != null) {
				log.info("SUCCESS: Fetched customer by username and password");
				return customer;
			}
		}
		log.info("ERROR: Unable to fetch customer by username and password");
		return customer;
	}
	
	//returns a list of accounts based on the customerID
	public List<Account> GetAccountsByCustomer(int customerID) {
		List<Account> accounts = null;
		if((accounts = aDao.GetAccountsByCustomerID(customerID)) != null) {
			log.info("SUCCESS: Fetched accounts by customer");
			return accounts;
		}
		log.info("ERROR: Unable to fetch accounts by customer");
		return accounts;
	}

	//returns a boolean based on whether an account was successfully added
	public boolean InsertAccountByCustomer(Customer customer, double depositAmount) {
		//validated deposit amount
		if(depositAmount < 0) {
			log.info("ERROR: Unable to create new account due to incorrect deposit amount (" + depositAmount + ")");
			return false;
		}
		//if operation is successful, return true, elsewhere return false
		if(aDao.InsertAccount(new Account(0, customer.getCustomerId(), depositAmount, false))) {
			log.info("SUCCESS: New account created (" + depositAmount + ")");
			return true;
		}
		log.info("ERROR: Unable to create new account due to unknown reason (" + depositAmount + ")");
		return false;
	}
	
	//returns a boolean based on whether a customer was successfully added
	public boolean InsertCustomer(String username, String password, String first_name, String last_name, String phoneNumber, String email) {
		User user = new User(username, password);
		//if user is created successfully, then insert new customer
		if(uDao.InsertUser(user)) {
			Customer customer = new Customer(uDao.GetUserByUsernameAndPassword(username, password).getUser_id(), first_name, last_name, phoneNumber, email);
			//returns true if operation was successful, elsewhere returns false
			if(cDao.InsertCustomer(customer)) {
				log.info("SUCCESS: New customer created");
				return true;
			}else {
				System.out.println("Error .cDaoinsertCustomer");
			}
		}
		log.info("ERROR: Unable to create new customer");
		return false;
	}
	
	//returns true if account approval is successfully changed to true
	public boolean ApproveAccount(String substr) {
		if(eDao.ApproveAccount(substr))
			return true;
		return false;
	}
	
	//returns false is account is successfully deleted (being denied results in the
	//account being deleted from the database
	public boolean DenyAccount(String substr) {
		if(eDao.DenyAccount(substr))
			return true;
		return false;
	}
	
	//verifies password length
	public boolean VerifyPassword(String password) {
		if(password.length() > 7)
			return true;
		return false;
	}
	
	//verifies password length
	public boolean VerifyUsername(String username) {
		if(username.length() > 5)
			return true;
		return false;
	}
	
	//verifies name length
	public boolean VerifyName(String name) {
		if(name.length() > 1)
			return true;
		return false;
	}
	
	//verifies email using regular expression
	public boolean VerifyEmail(String email) {
        emailMatcher = emailPattern.matcher(email);
        if(emailMatcher.matches())
        	return true;
        return false;
	}
	
	//determines whether a string can be parsed into a double
	public boolean IsDoubleParsable(String stringDouble) {
		if(!(stringDouble.length() == 0 || stringDouble == null)) {
			try
			{
			  Double.parseDouble(stringDouble);
			}
			catch(NumberFormatException e)
			{
			  return false;
			}
		}else {
			return false;
		}
		return true;
	}
	
	//returns whether a string can be parsed into an integer
	public boolean IsIntParsable(String stringInt) {
		if(!(stringInt.length() == 0 || stringInt == null)) {
			try
			{
			  Integer.parseInt(stringInt);
			}
			catch(NumberFormatException e)
			{
			  return false;
			}
		}else {
			return false;
		}
		return true;
	}
	
	//verifies whether a string representing a phone number can be parsed numerically and is 10 in length
	public boolean VerifyPhoneNumber(String phoneNumber) {
		if(phoneNumber.length() == 10) {
			try {
			    Long.parseLong(phoneNumber);
			    return true;
			} catch (NumberFormatException e) {
			    System.out.println("Input String cannot be parsed to Integer.");
			}
		}
		return false;
	}

}
