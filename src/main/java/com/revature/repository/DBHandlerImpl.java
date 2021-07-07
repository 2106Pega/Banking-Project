package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.BankAccount;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;

public class DBHandlerImpl implements DBHandler{

	@Override
	public void insertNewUser(User user) {
		String sql = "INSERT INTO users VALUES (?,?,?,?);";
		
		try(Connection conn = ConnectionFactory.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getFirstName());
			ps.setString(4, user.getLastName());
			
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertNewBankAccount(BankAccount acc) {
		String sql = "INSERT INTO bank_accounts(u_user_name, account_name, balance) VALUES (?,?,?);";
		
		try(Connection conn = ConnectionFactory.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, acc.getUsernameRef());
			ps.setString(2, acc.getName());
			ps.setDouble(3, acc.getBalance());
			
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public BankAccount selectBankAccountByName(User user, String name) {
		String sql = "SELECT * FROM bank_accounts WHERE u_user_name = ? AND account_name = ?;";
		
		try(Connection conn = ConnectionFactory.getConnection()) {			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, name);
			
			return selectBankAccountHelper(ps);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<BankAccount> selectBankAccountsByUserName(String username) {
		String sql = "SELECT * FROM bank_accounts WHERE u_user_name = ?;";
		
		try(Connection conn = ConnectionFactory.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			
			return selectMultipleBankAccountsHelper(ps);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<BankAccount> selectBankAccountsToBeApproved() {
		String sql = "SELECT * FROM bank_accounts WHERE is_approved = false;";
		
		try(Connection conn = ConnectionFactory.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			
			return selectMultipleBankAccountsHelper(ps);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public User selectUserByUsername(String username) {
		String sql = "SELECT * FROM users WHERE user_name = ?;";
		
		try(Connection conn = ConnectionFactory.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			
			return selectUserHelper(ps);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public User selectCustomerByUsername(String username) {
		String sql = "SELECT * FROM users WHERE user_name = ? AND is_employee = false;";
		
		try(Connection conn = ConnectionFactory.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			
			return selectUserHelper(ps);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<User> selectCustomersOrderedByLastName() {
		String sql = "SELECT * FROM users WHERE is_employee = false ORDER BY last_name;";
		
		try(Connection conn = ConnectionFactory.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);	
			
			return selectMultipleUsersHelper(ps);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public User selectUserByUsernameAndPassword(String username, String password) {
		String sql = "SELECT * FROM users WHERE user_name = ? AND pass_word = ?;";
		try(Connection conn = ConnectionFactory.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			
			return selectUserHelper(ps);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void updateBalance(BankAccount acc, double newBalance) {
		String sql = "UPDATE bank_accounts SET balance = ? WHERE account_id = ?;";
		
		try(Connection conn = ConnectionFactory.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, newBalance);
			ps.setInt(2, acc.getAccountId());
			
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateBankAccountApproval(BankAccount acc) {
		String sql = "UPDATE bank_accounts SET is_approved = true WHERE account_id = ?;";
		
		try(Connection conn = ConnectionFactory.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, acc.getAccountId());
			
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

//	@Override
//	public void deleteBankAccount(BankAccount acc) {
//		String sql = "DELETE FROM bank_accounts WHERE account_id = ?;";
//		
//		try(Connection conn = ConnectionFactory.getConnection()) {
//			PreparedStatement ps = conn.prepareStatement(sql);
//			ps.setDouble(1, acc.getAccountId());
//			
//			ps.execute();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	
	private List<BankAccount> selectMultipleBankAccountsHelper(PreparedStatement ps) throws SQLException {
		List<BankAccount> accs = new ArrayList<BankAccount>();
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			accs.add(new BankAccount(
					rs.getInt("account_id"),
					rs.getString("u_user_name"),
					rs.getString("account_name"),
					rs.getDouble("balance"),
					rs.getBoolean("is_approved")
					));
		}
		return accs;
	}
	
	private BankAccount selectBankAccountHelper(PreparedStatement ps) throws SQLException {
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			return new BankAccount(
					rs.getInt("account_id"),
					rs.getString("u_user_name"),
					rs.getString("account_name"),
					rs.getDouble("balance"),
					rs.getBoolean("is_approved")
					);
		}
		return null;
	}
	
	private List<User> selectMultipleUsersHelper(PreparedStatement ps) throws SQLException {
		List<User> users = new ArrayList<User>();
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			users.add(new User(
					rs.getString("user_name"),
					rs.getString("pass_word"),
					rs.getString("first_name"),
					rs.getString("last_name"),
					rs.getBoolean("is_employee")
					));
		}
		return users;
	}

	private User selectUserHelper(PreparedStatement ps) throws SQLException {
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			return new User(
					rs.getString("user_name"),
					rs.getString("pass_word"),
					rs.getString("first_name"),
					rs.getString("last_name"),
					rs.getBoolean("is_employee")
					);
		}
		return null;
	}
	

}
