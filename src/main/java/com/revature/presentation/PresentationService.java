package com.revature.presentation;

import java.net.Inet4Address;
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
	private Pattern decimalPattern;
	Matcher emailMatcher;
	Matcher decimalMatcher;
	
	public PresentationService() {
		this.uDao = new UserDAOImpl();
		this.cDao = new CustomerDAOImpl();
		this.eDao = new EmployeeDAOImpl();
		this.aDao = new AccountDAOImpl();
		log = Logger.getLogger(PresentationService.class.getName());
		emailPattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
		decimalPattern = Pattern.compile("([0-9].*)\\\\.([0-9].*)");
	}
	
	public boolean TransferBetweenAccounts(double transferAmount, int targetAccountID, int sourceAccountID, Customer customer) {
		Account account = aDao.GetAccountByAccountID(sourceAccountID);
		if(account.getBalance() < transferAmount || account.getCustomer_id() != customer.getCustomerId()) {
			log.info("ERROR: Transfer between account " + targetAccountID + " and " + sourceAccountID + " failed due to insufficient balance");
			return false;
		}else if(aDao.TransferMoneyBetweenAccounts(transferAmount, targetAccountID, sourceAccountID, customer)) {
			log.info("SUCCESS: Transfer between account " + targetAccountID + " and " + sourceAccountID + " succeeded");
			return true;
		}
		log.info("ERROR: Transfer between account " + targetAccountID + " and " + sourceAccountID + " failed due to unknown error...");
		return false;
	}
	
	public List<Customer> GetAllCustomers(){
		List<Customer> customers = cDao.GetAllCustomers();
		if(customers.size() > 0) {
			log.info("SUCCESS: Fetching all customers");
			return customers;
		}
		log.info("ERROR: Failed to fetch any customers");
		return customers;
	}
	
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
	
	public User GetEmployeeByUsernameAndPassword(String username, String password) {
		User user = eDao.getEmployeeByUsernameAndPassword(username, password);
		if(user != null) {
			log.info("SUCCESS: Fetching user by username and password");
			return user;
		}
		log.info("ERROR: Failure fetching user by username and password");
		return user;
	}
	
	public List<Account> GetPendingAccounts(){
		List<Account> accounts = eDao.getPendingAccounts();
		if(accounts.size() > 0) {
			log.info("SUCCESS: Fetching pending accounts");
			return accounts;
		}
		log.info("ERROR: No available pending accounts");
		return accounts;
	}
	
	public Customer GetCustomerByUsernameAndPassword(String username, String password){
		User user = null;
		Customer customer = null;
		if((user = uDao.getUserByUsernameAndPassword(username, password)) != null) {
			if((customer = cDao.getCustomerByUser(user)) != null) {
				log.info("SUCCESS: Fetched customer by username and password");
				return customer;
			}
		}
		log.info("ERROR: Unable to fetch customer by username and password");
		return customer;
	}
	
	public List<Account> GetAccountsByCustomer(int customerID) {
		List<Account> accounts = null;
		if((accounts = aDao.GetAccountsByCustomerID(customerID)) != null) {
			log.info("SUCCESS: Fetched accounts by customer");
			return accounts;
		}
		log.info("ERROR: Unable to fetch accounts by customer");
		return accounts;
	}

	public boolean InsertAccountByCustomer(Customer customer, double depositAmount) {
		if(depositAmount < 0) {
			log.info("ERROR: Unable to create new account due to incorrect deposit amount (" + depositAmount + ")");
			return false;
		}
		if(aDao.insertAccount(new Account(0, customer.getCustomerId(), depositAmount, false))) {
			log.info("SUCCESS: New account created (" + depositAmount + ")");
			return true;
		}
		log.info("ERROR: Unable to create new account due to unknown reason (" + depositAmount + ")");
		return false;
	}
	
	public boolean InsertCustomer(String username, String password, String first_name, String last_name, String phoneNumber, String email) {
		User user = new User(username, password);
		if(uDao.insertUser(user)) {
			Customer customer = new Customer(uDao.getUserByUsernameAndPassword(username, password).getUser_id(), first_name, last_name, phoneNumber, email);
			if(cDao.insertCustomer(customer)) {
				log.info("SUCCESS: New customer created");
				return true;
			}else {
				System.out.println("Error .cDaoinsertCustomer");
			}
		}
		log.info("ERROR: Unable to create new customer");
		return false;
	}
	
	public boolean ApproveAccount(String substr) {
		if(eDao.approveAccount(substr))
			return true;
		return false;
	}
	
	public boolean DenyAccount(String substr) {
		if(eDao.denyAccount(substr))
			return true;
		return false;
	}
	
	public boolean VerifyPassword(String password) {
		if(password.length() > 7)
			return true;
		return false;
	}
	
	public boolean VerifyUsername(String username) {
		if(username.length() > 5)
			return true;
		return false;
	}
	
	public boolean VerifyName(String name) {
		if(name.length() > 1)
			return true;
		return false;
	}
	
	public boolean VerifyEmail(String email) {
        emailMatcher = emailPattern.matcher(email);
        if(emailMatcher.matches())
        	return true;
        return false;
	}
	
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
