package com.revature.services;

import java.util.List;

import com.revature.models.AdminAccount;
import com.revature.models.BankAccount;
import com.revature.models.User;

public interface EmployeeDao {

	public int createEmployeeAccount(String employeefirstName, String employeelastName, String employeeTitle);
	
	public boolean createEmployeeLogin(int employeeID, String employeeUsername, String employeePassword);
	
	public User employeeLoginValidation(String username, String password);
	
	public AdminAccount selectEmployeeInfoByID(int employeeID);
	
	public List<BankAccount> getUnApprovedAccounts();
	
	public boolean approveAccount(int customerID, int accountNumber, boolean accountApproved);
	
	List<BankAccount> getPendingAccounts(); 
	List<BankAccount> getCustomerAccounts();


}
