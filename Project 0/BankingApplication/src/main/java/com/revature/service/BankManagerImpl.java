package com.revature.service;

import com.revature.exceptions.PasswordMismatchException;
import com.revature.exceptions.UserNotFoundExecption;
import com.revature.models.User;
import com.revature.repo.AccountDAO;
import com.revature.repo.UserDAO;

public class BankManagerImpl implements BankManager {
	
	AccountDAO aDao;
	UserDAO uDao;

	public BankManagerImpl(AccountDAO aDao, UserDAO uDao) {
		super();
		this.aDao = aDao;
		this.uDao = uDao;
	}

	public boolean authenticate(String username) {
		
		boolean userFound = false;
		
		try {
			User u = uDao.selectUserByUsername(username);
			if (u != null)
				userFound = true;
		} 
		catch (UserNotFoundExecption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userFound;
	}

	public boolean checkPassword(String username, String password) {
		
		boolean passwordMatch = false;
		
		try {
			User u = uDao.selectUserByPassword(username, password);
			if (u != null)
				passwordMatch = true;
			
		} 
		catch (PasswordMismatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return passwordMatch;
	}

	@Override
	public boolean validPassword(String password) {
		
		boolean valid = false;
		if (password.length() > 5)
			valid = true;
		return valid;
	}

	@Override
	public int checkAccountType(String username) {
		
		int accountType = 0;
		try {
			User u = uDao.selectUserByUsername(username);
			accountType = u.getAccountType();
		} 
		catch (UserNotFoundExecption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return accountType;
	}

	@Override
	public void applyBankAccount(String username, double startingBalance) {
		// TODO Auto-generated method stub'
		User u = new User();
		try {
			u = uDao.selectUserByUsername(username);
		} 
		catch (UserNotFoundExecption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		aDao.createApplication(u.getId(), startingBalance);
	}

	@Override
	public String checkStartingBalance(String startingBalance) {
		String balanceStatement = "Please input a valid number.";
		try {
			double numericBalance = Double.parseDouble(startingBalance);
			if (numericBalance < 0)
				balanceStatement = "Please input a positive number.";
			else balanceStatement = startingBalance;
			return balanceStatement;
			
	    } 
		catch (final NumberFormatException e) {
	    	e.printStackTrace();
	    	
	    }
		
		//double numericBalance = Double.parseDouble(startingBalance);
		
			
		return null;
	}

	@Override
	public void approveBankAccount(String username, double startingBalance) {

		User u = new User();
		try {
			u = uDao.selectUserByUsername(username);
		} 
		catch (UserNotFoundExecption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		aDao.createAccount(u.getId(), startingBalance);	
	}

	@Override
	public User selectUser(String username) {
		// TODO Auto-generated method stub
		User u = new User();
		try {
			u = uDao.selectUserByUsername(username);
		} 
		catch (UserNotFoundExecption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return u;
	}
}
