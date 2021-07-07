package com.revature.repo;

import java.util.List;

import com.revature.exceptions.PasswordMismatchException;
import com.revature.exceptions.UserNotFoundExecption;
import com.revature.models.Account;
import com.revature.models.User;

public interface UserDAO {

	//READ
	public User selectUserByUsername(String username) throws UserNotFoundExecption;

	public User selectUserByPassword(String username, String password) throws PasswordMismatchException;
	
	public List<User> selectAllUsers();
	
	//CREATE
	public void createNewUser(String username, String password, String firstName, String lastName, int accountType);

}