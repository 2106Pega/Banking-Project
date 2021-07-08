package com.revature.repo;

import java.util.List;

import com.revature.models.Fund;
import com.revature.models.User;

public interface FundDAO {
	/*
	 * Implement CRUD methods
	 */
	
	//Create
	public boolean insertFund(Fund newFund);
	
	//Read
	public Fund selectFundById(int id);
	public List<Fund> selectFundsByOwnerId(int owner_id);
	public List<Fund> selectFundsByOwner(User user);
	
	//Update 
	public boolean updateFundInformation(Fund fund);
	
	//Not really needed
//	public boolean depositFunds(Fund fund, int amount);
//	public boolean withdrawFunds(Fund fund, int amount);
	
	//Delete 
	public boolean deleteFund(Fund fund);

}
