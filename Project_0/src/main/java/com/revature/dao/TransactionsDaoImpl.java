package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.Transactions;
import com.revature.util.ConnectionFactory;

public class TransactionsDaoImpl implements TransactionsDao {

	@Override
	public void createTranRecord(int accountNumber, double amount, String transactionType) {
		boolean accountNumberNull = false;
		if(accountNumber == -1)
			accountNumberNull = true;
		String sql1 = "SELECT account_type FROM accounts WHERE account_number = ?;";
		
		String sql2 = "INSERT INTO customer_transactions "
				+ "(a_id, account_type, transaction_amount, transaction_type) "
				+ "VALUES (?, ?, ?, ?)";

		String accountType = null;
		
		try(Connection conn = ConnectionFactory.getConnection()){
			PreparedStatement ps;
			ResultSet rs;
			if(!accountNumberNull) {
				ps = conn.prepareStatement(sql1);
				ps.setInt(1, accountNumber);
				rs = ps.executeQuery();
				accountType = rs.getString("account_type");
			}
			
			ps = conn.prepareStatement(sql2);
			if(!accountNumberNull) {
				ps.setInt(1, accountNumber);
			}
			else
				ps.setNull(1, java.sql.Types.NULL);
			ps.setString(2, accountType);
			ps.setDouble(3, amount);
			ps.setString(4, transactionType);			
			
			ps.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createTransferTranRecord(int accountNumber, double amount, String transactionType,
			int transferToNumber) {
		boolean accountNumberNull = false;
		if(accountNumber == -1)
			accountNumberNull = true;
		
		String sql = "INSERT INTO customer_transactions "
				+ "(a_id, transaction_type, transaction_amount, transfer_into_id) "
				+ "VALUES (?, ?, ?, ?)";
		
		try(Connection conn = ConnectionFactory.getConnection()){
			PreparedStatement ps;
					
			ps = conn.prepareStatement(sql);
			if(!accountNumberNull) {
				ps.setInt(1, accountNumber);
			}
			else
				ps.setNull(1, java.sql.Types.NULL);
			ps.setString(2, transactionType);
			ps.setDouble(3, amount);
			ps.setInt(4, transferToNumber);			
			
			ps.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createAppTranRecord(double amount, String transactionType, String accountType) {
		String sql = "INSERT INTO customer_transactions "
				+ "(transaction_type, transaction_amount, account_type) "
				+ "VALUES (?, ?, ?)";
		
		try(Connection conn = ConnectionFactory.getConnection()){
			PreparedStatement ps;
					
			ps = conn.prepareStatement(sql);
			ps.setString(1, transactionType);
			ps.setDouble(2, amount);
			ps.setString(3, accountType);			
			
			ps.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public List<Transactions> getAllTransactions() {

		List<Transactions> databaseTransactions = new ArrayList<Transactions>();
		String sql = "SELECT * FROM customer_transactions;";
		try(Connection conn = ConnectionFactory.getConnection()){
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				databaseTransactions.add(new Transactions(
						rs.getInt("transaction_id"),
						rs.getInt("a_id"),
						rs.getString("account_type"),
						rs.getString("transaction_type"),
						rs.getDouble("transaction_amount"),						
						rs.getInt("transfer_into_id")						
						));				
			}			
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return databaseTransactions;
	}

}
