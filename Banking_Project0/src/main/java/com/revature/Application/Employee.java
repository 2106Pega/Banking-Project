/*
 * Employee handles any query requests by calling the extended DAO class provided. 
 * Employee also prints whatever is returned from the DAO queries
 */
package com.revature.Application;

import java.util.List;
import java.util.Scanner;

import com.revature.DAO.EmployeeDAOImpl;
import com.revature.Models.Account;
import com.revature.Models.Log_Entry;
import com.revature.Models.Unregistered_Account;
import com.revature.Models.User;

public class Employee extends EmployeeDAOImpl
{
	private User emp;

	public Employee(User input)
	{
		emp = input;
	}
	@Override
	public Unregistered_Account checkPendingAccount() {
		Unregistered_Account pendingAccount = super.checkPendingAccount();
		if(pendingAccount == null)
		{
			System.out.println("There are no pending accounts.");
			return null;
		}
		System.out.println("Would you like to (1)approve or (2)reject this account?");
		System.out.println(pendingAccount);
		Scanner empInput = new Scanner(System.in);
		int input = empInput.nextInt();
		empInput.nextLine();
		if(input == 1)
		{
			System.out.println("Account approved");
			approve(pendingAccount);
		}
		else
		{
			System.out.println("Account rejected");
			reject(pendingAccount);
		}
		return null;
	}



	@Override
	public List<Account> viewAccounts(String username) {
		List<Account> output = super.viewAccounts(username);
		for(Account x : output)
			System.out.println(x);
		return null;
	}

	@Override
	public List<Log_Entry> viewLog() {
		List<Log_Entry> output = super.viewLog();
		for(Log_Entry x : output)
			System.out.println(x);
		
		return null;
	}
//	
}
