package com.revature.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.util.ConnectionFactory;

public class AccountDaoImpl implements AccountDAO {

	@Override
	public void createApplication(int userId, double startingBalance) {

		//SQL query
		String sql = "INSERT INTO applications_table (user_id, balance) VALUES(?, ?);";

		//Creating connection
		try(Connection conn = ConnectionFactory.getConnection();) {

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, userId);
			ps.setDouble(2, startingBalance);

			ps.execute();
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Account> selectAllApplications() {

		List<Account> applicationList = new ArrayList<>();

		//SQL query
		String sql = "SELECT application_id, a.user_id, first_name, last_name, username, balance FROM applications_table a JOIN user_table u ON a.user_id = u.user_id;";

		//Creating connection
		try(Connection conn = ConnectionFactory.getConnection();) {

			PreparedStatement ps = conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				applicationList.add(new Account(
						rs.getInt("application_id"),
						rs.getInt("user_id"),
						rs.getString("first_name"),
						rs.getString("last_name"),
						rs.getString("username"),
						rs.getDouble("balance")));
			}

			return applicationList;
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateAccount(int accountId, double depositAmount) {

		String sql = "UPDATE bank_table SET balance = balance + ? WHERE account_id = ?;";

		//Creating connection
		try(Connection conn = ConnectionFactory.getConnection();) {

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setDouble(1, depositAmount);
			ps.setInt(2, accountId);

			ps.execute();
		}

		catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void deleteAccount(Account acc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createAccount(int userId, double startingBalance) {

		//SQL query
		String sql = "INSERT INTO bank_table (user_id, balance) VALUES(?, ?);";

		//Creating connection
		try(Connection conn = ConnectionFactory.getConnection();) {

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, userId);
			ps.setDouble(2, startingBalance);

			ps.execute();
		}

		catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void deleteApplication(Account acc) {

		//SQL query
		String sql = "DELETE FROM applications_table WHERE application_id = ?;";

		//Creating connection
		try(Connection conn = ConnectionFactory.getConnection();) {

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, acc.getApplicationId());

			ps.execute();
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Account> selectAccountsbyUser(int userId) {

		List<Account> accountList = new ArrayList<>();

		//SQL query
		String sql = "SELECT account_id, balance FROM bank_table WHERE user_id = ?;";

		//Creating connection
		try(Connection conn = ConnectionFactory.getConnection();) {

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, userId);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				accountList.add(new Account(
						rs.getInt("account_id"),
						rs.getDouble("balance")));
			}
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
		return accountList;
	}

	@Override
	public Account selectAccountbyId(int accountId) {

		String sql = "SELECT account_id, balance FROM bank_table WHERE account_id = ?;";

		//Creating connection
		try(Connection conn = ConnectionFactory.getConnection();) {

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, accountId);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Account accountFound = new Account(
						rs.getInt("account_id"),
						rs.getDouble("balance"));
				return accountFound;
			}
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Account> selectAllAccounts() {

		List<Account> accountList = new ArrayList<>();

		//SQL query
		String sql = "SELECT user_id, account_id, balance FROM bank_table;";

		//Creating connection
		try(Connection conn = ConnectionFactory.getConnection();) {

			PreparedStatement ps = conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				accountList.add(new Account(
						rs.getInt("user_id"),
						rs.getInt("account_id"),
						rs.getDouble("balance")));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return accountList;
	}

}
