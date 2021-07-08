package com.revature.repo;

import java.util.List;

import com.revature.models.Fund;
import com.revature.models.User;

public interface UserDAO {
	/**
	 * Enter CRUD methods to implement
	 */
	
	//Create
	public boolean insertUser(User newUser);
	
	//Read
	public User selectUserByUsername(String username);
	public User selectUserById(int id);
	public List<User> selectUnapprovedUsers();
	public List<Fund> selectFundsById(int id);
	public List<Fund> selectFundsByUsername(String username);
	public List<Fund> selectFundsByUser(User user);
	
	//Update 
	public boolean updateUserInformation(User user);
	
	//Delete 
	public boolean deleteUser(User user);
}
