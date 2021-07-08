package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.BankAccount;
import com.revature.models.CustomerAccount;
import com.revature.models.EmployeeAccount;
import com.revature.util.ConnectionFactory;

public class BankDaoImpl implements BankDao {

	/**
	 * Inserts a new bank account into the bank database.
	 * @param	account: the account to be added to the database
	 * @return	true if the insertion is successful, false if an error is encountered
	 */
	@Override
	public int insertBankAccount(BankAccount account) {
		
		String sql = "INSERT INTO bank (balance, is_approved, customer_id) VALUES (?, ?, ?) RETURNING account_id;";
		int id = -1;
		
		try (Connection conn = ConnectionFactory.getConnection();) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, account.getBalance());
			ps.setBoolean(2, account.isApproved());
			ps.setInt(3, account.getCustomerId());
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				id = rs.getInt("account_id");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;
		
	}

	/**
	 * Inserts a new customer account into the bank database.
	 * @param	account: the account to be added to the database
	 * @return	true if the insertion is successful, false if an error is encountered
	 */
	@Override
	public boolean insertCustomerAccount(CustomerAccount account) {
		
		String sql = "INSERT INTO customer_accounts (user_name, pass_word) VALUES (?, ?);";
		
		try (Connection conn = ConnectionFactory.getConnection();) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, account.getUsername());
			ps.setString(2, account.getPassword());
			
			ps.execute();
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/**
	 * Inserts a new employee account into the bank database.
	 * @param	account: the account to be added to the database
	 * @return	true if the insertion is successful, false if an error is encountered
	 */
	@Override
	public boolean insertEmployeeAccount(EmployeeAccount account) {
		
		String sql = "INSERT INTO employee_accounts (user_name, pass_word) VALUES (?, ?);";
		
		try (Connection conn = ConnectionFactory.getConnection();) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, account.getUsername());
			ps.setString(2, account.getPassword());
			
			ps.execute();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	/**
	 * Uses a unique ID number to find the corresponding bank account in the database.
	 * @param	id: the ID of the desired account
	 * @return 	the account with the specified ID number if found, null otherwise
	 */
	@Override
	public BankAccount selectBankAccountById(int id) {
		
		String sql = "SELECT * FROM bank WHERE account_id = ?;";
		BankAccount account = null;
		
		try (Connection conn = ConnectionFactory.getConnection();) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				account = new BankAccount(rs.getInt("account_id"),
					rs.getDouble("balance"),
					rs.getBoolean("is_approved"),
					rs.getInt("customer_id"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return account;
	}

	/**
	 * Finds and returns a list of bank accounts associated with the given customer ID.
	 * @param	id: the unique customer account ID used to locate the accounts
	 * @return	a list of bank accounts associated with the customer ID
	 */
	@Override
	public List<BankAccount> selectBankAccountsByCustomer(int id) {
		
		String sql = "SELECT * FROM bank WHERE customer_id = ?;";
		List<BankAccount> accountList = new ArrayList<>();
		
		try (Connection conn = ConnectionFactory.getConnection();) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				accountList.add(new BankAccount(
						rs.getInt("account_id"),
						rs.getDouble("balance"),
						rs.getBoolean("is_approved"),
						rs.getInt("customer_id")
						));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return accountList;
	}

	/**
	 * Returns a list of all bank accounts in the database.
	 * @return 	a list of all bank accounts in the database
	 */
	@Override
	public List<BankAccount> selectAllBankAccounts() {
		
		String sql = "SELECT * FROM bank;";
		List<BankAccount> accountList = new ArrayList<>();
		
		try (Connection conn = ConnectionFactory.getConnection();) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				accountList.add(new BankAccount(
						rs.getInt("account_id"),
						rs.getDouble("balance"),
						rs.getBoolean("is_approved"),
						rs.getInt("customer_id")
						));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return accountList;
	}
	
	
	/**
	 * Finds the customer account associated with a given (unique) username.
	 * @param 	the username used to find the account
	 * @return	the customer account associated with the given username
	 */
	@Override
	public CustomerAccount selectCustomerAccountByUsername(String username) {
		String sql = "SELECT * FROM customer_accounts WHERE user_name = ?;";
		CustomerAccount account = null;
		
		try (Connection conn = ConnectionFactory.getConnection();) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				account = new CustomerAccount(rs.getInt("customer_id"),
						rs.getString("user_name"),
						rs.getString("pass_word"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return account;
	}
	
	/**
	 * Finds the customer account associated with a given (unique) username.
	 * @param 	the username used to find the account
	 * @return	the customer account associated with the given username
	 */
	@Override
	public EmployeeAccount selectEmployeeAccountByUsername(String username) {
		String sql = "SELECT * FROM employee_accounts WHERE user_name = ?;";
		EmployeeAccount account = null;
		
		try (Connection conn = ConnectionFactory.getConnection();) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				account = new EmployeeAccount(rs.getInt("employee_id"),
						rs.getString("user_name"),
						rs.getString("pass_word"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return account;
	}


	/**
	 * Update the balance of an existing bank account in the database.
	 * @param	account: the bank account to be updated
	 */
	@Override
	public void updateBankAccount(BankAccount account) {
		
		String sql = "UPDATE bank SET balance = ?, is_approved = ?, customer_id = ? where account_id = ?;";
		
		try (Connection conn = ConnectionFactory.getConnection();) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, account.getBalance());
			ps.setBoolean(2, account.isApproved());
			ps.setInt(3, account.getCustomerId());
			ps.setInt(4, account.getId());
			
			ps.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Update the balance of an existing bank account in the database.
	 * @param	account: the bank account to be updated
	 */
	@Override
	public void updateCustomerAccount(CustomerAccount account) {
		
		String sql = "UPDATE customer_accounts SET user_name = ?, pass_word = ? WHERE customer_id = ?;";
		
		try (Connection conn = ConnectionFactory.getConnection();) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, account.getUsername());
			ps.setString(2, account.getPassword());
			ps.setInt(3, account.getId());
			
			ps.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Remove an existing bank account from the database.
	 * @param	account: the bank account to be removed
	 */
	@Override
	public void deleteBankAccount(BankAccount account) {
		
		String sql = "DELETE FROM bank WHERE account_id = ?;";
		
		try (Connection conn = ConnectionFactory.getConnection();) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, account.getId());
			
			ps.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Remove an existing customer account and all associated bank accounts from the database.
	 * @param	account: the customer account to be removed
	 */
	@Override
	public void deleteCustomerAccount(CustomerAccount account) {
		
		String sql = "DELETE FROM bank WHERE customer_id = ?;"
				+ "DELETE FROM customer_accounts WHERE customer_id = ?;";
		
		try (Connection conn = ConnectionFactory.getConnection();) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, account.getId());
			ps.setInt(2, account.getId());
			
			ps.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Calls a function to make a transfer of money from one account to another.
	 * @param	sender - the account money is being sent from
	 * @param	receiver - the account money is being sent to
	 * @param	amount - the amount of money to be sent
	 */
	@Override
	public void transfer(BankAccount sender, BankAccount receiver, double amount) {
		
		String sql = "SELECT transfer(?::int, ?::int, ?::decimal);";
		
		try (Connection conn = ConnectionFactory.getConnection();) {
			
			PreparedStatement cs = conn.prepareStatement(sql);
			cs.setInt(1, sender.getId());
			cs.setInt(2, receiver.getId());
			cs.setDouble(3, amount);
			
			cs.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	
	
}
