package com.revature.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.revature.Bank.BankManager;
import com.revature.models.Fund;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;

public class FundDaoImpl implements FundDAO {
	private final Logger loggy = Logger.getLogger(FundDaoImpl.class);
	
	public FundDaoImpl() {
		super();
		loggy.setLevel(Level.ERROR);
	}
	
	@Override
	public boolean insertFund(Fund newFund) {
		loggy.info("Creating a new fund and inserting into db");
		boolean result = false;
		//Create a SQL query
		String sql = "INSERT INTO funds(fund_owner, fund_type, fund_amount)"
				+ " VALUES (?, ?, ?);";
		
		//Create a connection to the db
		try(Connection conn = ConnectionFactory.getConnection()) {
			//Prepare statement object to insert fund into the db
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, newFund.getFund_owner());
			ps.setString(2, newFund.getFund_type());
			ps.setDouble(3, newFund.getFund_amount());
			
			//Attempt to execute query into db
			ps.execute();
			result = true;
		} catch(SQLException e) {
			e.printStackTrace();
			loggy.error(e);
		}
		//Successfully created new fund!
		loggy.info("Successfully created new Fund in db");
		return result;
	}

	@Override
	public Fund selectFundById(int id) {
		loggy.info("Selecting fund by id");
		Fund result = null;
		//Create a SQL query
		String sql = "SELECT * FROM funds WHERE fund_id = ?;";
		
		//Create a connection to the db
		try(Connection conn = ConnectionFactory.getConnection()) {
			//Prepare statement object to insert fund into the db
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			//Attempt to execute query into db
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				result = new Fund(
					rs.getInt(1),
					rs.getInt(2),
					rs.getString(3),
					rs.getDouble(4)
					);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			loggy.error(e);
		}
		//Successfully retrieved the fund!
		loggy.info("Successfully retrieved Fund #" + id);
		return result;
	}

	@Override
	public List<Fund> selectFundsByOwnerId(int owner_id) {
		loggy.info("Retrieving list of funds from db belonging to user");
		List<Fund> result = new ArrayList<Fund>();
		//Create a SQL query
		String sql = "SELECT * FROM funds WHERE fund_owner = ?;";
		
		//Create a connection to the db
		try(Connection conn = ConnectionFactory.getConnection()) {
			//Prepare statement object to insert fund into the db
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, owner_id);
			
			//Attempt to execute query into db
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				result.add(new Fund(
					rs.getInt(1),
					rs.getInt(2),
					rs.getString(3),
					rs.getDouble(4)
					));
			}
		} catch(SQLException e) {
			e.printStackTrace();
			loggy.error(e);
		}
		//Successfully retrieved many funds related to the account!
		loggy.info("Successfully retrieved all Funds belonging to user #" + owner_id);
		return result;
	}

	@Override
	public List<Fund> selectFundsByOwner(User user) {
		loggy.info("Retrieving all funds from user #" + user.getUser_id());
		List<Fund> result = new ArrayList<Fund>();
		//Create a SQL query
		String sql = "SELECT * FROM funds WHERE fund_owner = ?;";
		
		//Create a connection to the db
		try(Connection conn = ConnectionFactory.getConnection()) {
			//Prepare statement object to insert fund into the db
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, user.getUser_id());
			
			//Attempt to execute query into db
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				result.add(new Fund(
					rs.getInt(1),
					rs.getInt(2),
					rs.getString(3),
					rs.getDouble(4)
					));
			}
		} catch(SQLException e) {
			e.printStackTrace();
			loggy.error(e);
		}
		//Successfully retrieved many funds related to the account!
		loggy.info("Successfully retrieved all funds relating to user #" + user.getUser_id());
		return result;
	}

	@Override
	public boolean updateFundInformation(Fund fund) {	
		boolean result = false;
		loggy.info("Updating funds for fund #" + fund.getFund_id());
		//Create a SQL query
		String sql = "UPDATE funds SET fund_type = ?, fund_amount = ? WHERE fund_id = ?;";
		
		//Create a connection to the db
		try(Connection conn = ConnectionFactory.getConnection()) {
			//Prepare statement object to user into the db
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, fund.getFund_type());
			ps.setDouble(2, fund.getFund_amount());
			ps.setInt(3, fund.getFund_id());
			
			//Attempt to execute query into db
			ps.executeUpdate();
			result = true;
		} catch(SQLException e) {
			e.printStackTrace();
			loggy.error(e);
		}
		loggy.info("Successfully updated funds #" + fund.getFund_id());
		return result;
	}

	@Override
	public boolean deleteFund(Fund fund) {
		loggy.info("Deleting fund #" + fund.getFund_id() + " from the db");
		boolean result = false;
		// Make the sql query to delete the fund
		String sql = "DELETE FROM funds WHERE fund_id = ?;";
		
		//Create a connection to the database
		try(Connection conn = ConnectionFactory.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, fund.getFund_id());

			ps.executeUpdate();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
			loggy.error(e);
		}
		loggy.info("Successfully deleted Fund #" + fund.getFund_id());
		return result;
	}

}
