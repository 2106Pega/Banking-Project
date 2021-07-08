package com.revature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.utl.ConnectionFactory;

public class BankDAOImpl implements BankDAO {
	static final Logger debugLog = Logger.getLogger("debugLogger");
	static final Logger TransactionLog = Logger.getLogger("transactionLogger");
	
	public User insertNewUser(User u) {
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "INSERT INTO public.users\r\n"
					+ "(user_name, user_password, first_name, last_name, is_employee)\r\n"
					+ "VALUES(?, ?, ?, ?, ?);";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, u.getUser_name());
			ps.setString(2, u.getPassword());
			ps.setString(3, u.getFirst_name());
			ps.setString(4, u.getLast_name());
			ps.setBoolean(5, false);

			ps.execute();
			
			u = selectUserbyLogIn(u);
			
			sql =     "INSERT INTO public.accounts\r\n"
					+ "(user_id, account_name, approved, balance)\r\n"
					+ "VALUES(?, ? , false, 0.00);";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, u.getUser_Id());
			ps.setString(2, u.getUser_name() + "_Account");
			ps.execute();
			TransactionLog.info("Created New User");
		}
		catch (SQLException e) {
			debugLog.warn(e);
		}	
		return u;
	}
	

	public User selectUserbyId(User u) {
		//System.out.println(u.getUser_Id());
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "SELECT * FROM users WHERE user_id = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, u.getUser_Id());
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				u.setAccountId(rs.getInt("user_id"));
				u.setUser_name(rs.getString("user_name"));
				u.setPassword(rs.getString("user_password"));
				u.setFirst_name(rs.getString("first_Name"));
				u.setLast_name(rs.getString("last_Name"));
				u.setEmployee(rs.getBoolean("is_employee"));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return u;	
	}

	public User selectUserbyLogIn(User u) {
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "SELECT * FROM users\r\n"
					+ "WHERE user_name = ? AND user_password =  ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, u.getUser_name());
			ps.setString(2, u.getPassword());
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				u.setUser_Id(rs.getInt("user_id"));
				//System.out.println(u.getUser_Id());
				u.setUser_name(rs.getString("user_name"));
				//System.out.println(u.getUser_name());
				u.setPassword(rs.getString("user_password"));
				//System.out.println(u.getPassword());
				u.setFirst_name(rs.getString("first_Name"));
				//System.out.println(u.getFirst_name());
				u.setLast_name(rs.getString("last_Name"));
				//System.out.println(u.getLast_name());
				u.setEmployee(rs.getBoolean("is_employee"));
				//System.out.println(u.isEmployee());
				selectBalance(u);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return u;
	}

	public void selectBalance(User u) {
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "SELECT * FROM accounts Where user_id = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			//System.out.println(u.getUser_Id());
			ps.setInt(1, u.getUser_Id());
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				//System.out.println("1");
				u.setBalance(rs.getInt("balance"));
				u.setApproved(rs.getBoolean("approved"));
				u.setAccountId(rs.getInt("account_id"));
			}
			//System.out.println(u.getBalance());
		}
		catch (SQLException e) {
			debugLog.warn(e);
		}
	}

	public boolean updateWithdraw(User u, int Amount) {
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "UPDATE accounts\r\n"
					+ "SET balance = balance - ?\r\n"
					+ "WHERE user_id = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, Amount);
			ps.setInt(2, u.getUser_Id());
			//System.out.println(Ammount + u.getBalance());
			
			ps.execute();
			TransactionLog.info("Withdraw, user id: " + u.getUser_Id() + " Amount: $" + Amount);
		}
		catch (SQLException e) {
			debugLog.warn(e);
		}
		return true;
	}

	public boolean updateDeposit(User u, int Amount) {
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "UPDATE accounts\r\n"
					+ "SET balance = balance + ?\r\n"
					+ "WHERE account_id = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, Amount);
			ps.setInt(2, u.getAccountId());
			
			ps.execute();
			TransactionLog.info("Depost, user id: " + u.getUser_Id() + " Amount: $" + Amount);
		}
		catch (SQLException e) {
			debugLog.warn(e);
		}
		return true;
	}

	@Override
	public boolean testUsername(String un) {
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "SELECT user_name FROM users\r\n"
					+ "WHERE user_name = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, un);
		
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				return false;
			}
		}
		catch (SQLException e) {
			return true;
		}
		return true;
	}
	
	public boolean checkAccount(User u) {
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "SELECT * FROM accounts\r\n"
					+ "WHERE account_id = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, u.getAccountId());
		
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				return true;
			}
		}
		catch (SQLException e) {
			debugLog.warn(e);
		}
		return false;
	}


	@Override
	public void approveAllAccounts() {
		try(Connection conn = ConnectionFactory.getConnection()){
			String sql = "UPDATE accounts\r\n"
					+ "SET approved = true\r\n"
					+ "WHERE approved = false;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.execute();
			TransactionLog.info("Accounts Approved");
		}
		catch (SQLException e) {
			debugLog.warn(e);
		}	
	}


	@Override
	public List<User> getUnapproved() {
		try(Connection conn = ConnectionFactory.getConnection()){
			List<User> uList = new ArrayList<User>();;
			String sql = "Select * from accounts\r\n"
					+ "WHERE approved = false;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.executeQuery();
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				User u = new User();
				u.setAccount_name(rs.getString("account_name"));
				uList.add(u);
			}
			return uList;
		}
		catch (SQLException e) {
			debugLog.warn(e);
		}
		return null;
	}
}
