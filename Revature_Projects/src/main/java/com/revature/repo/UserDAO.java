package com.revature.repo;

import java.util.List;
import java.util.Scanner;

import com.revature.models.Employee;
import com.revature.models.User;

public interface UserDAO {
	
	//Data Access Object 
	// Abstraction from the database
	// provides the CRUD methods for the rest of the project. 
	
	
	//Create
	public boolean insertUser(User f);
	public boolean CreateUserApplication(User f);
	public boolean AcceptUserApplication(User f);
	//public boolean LogTransaction(User f, String accountType, float balanceChange);
	
	//Read
	public List<User> selectUserById(int id);
	public List<User> selectUserByAllCredentials(String username, String password, int id);
	public List<User> selectUsersByFirstName(String firstName);
	public List<User> selectAllUsers();
	public List<Employee> selectEmployee(String username, String password, int id);
	public List<User> selectAllPendingApplications();
	
	//Update 
	public void updateUser(User f);
	public void updateUserCheckingBalance(float balanceChange, boolean depositing, User f, Scanner scanner, UserDAO userDao);
	public void updateUserSavingsBalance(float balanceChange, boolean depositing, User f, Scanner scanner, UserDAO userDao);
	
	//Delete 
	public void deleteUser(User f);
	public boolean RemoveUserApplication(User f);
}
