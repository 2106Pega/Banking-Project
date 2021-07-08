package com.paul.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.paul.model.Account;
import com.paul.model.Client;
import com.paul.model.Message;
import com.paul.model.User;

import com.paul.model.*;

public class BankDAOImpl implements BankDAO {
	Connection connection;
	PreparedStatement statement;

	@Override
	public ArrayList<Message> getMessageInbox(int recipID) {
		ArrayList<Message> messages = new ArrayList<Message>(); 
		try {
			connection = DAOUtil.getConnection();
			String sql = "SELECT * FROM messages WHERE recipient_id = ?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, recipID);
			ResultSet allMessages = statement.executeQuery();
			while(allMessages.next()) {
				Message msg = new Message(allMessages.getInt("message_id"), allMessages.getInt("sender_id"), allMessages.getInt("recipient_id"), allMessages.getDouble("balance"));
				messages.add(msg);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}finally {
			closeResources(); 
		}
		return messages;
	}

	@Override
	public ArrayList<Account> getAccounts(int ownerID) {
		ArrayList<Account> accounts = new ArrayList<Account>(); 
		try {
			connection = DAOUtil.getConnection();
			String sql = "SELECT * FROM accounts WHERE owner_id = ? GROUP BY account_id";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, ownerID);
			ResultSet allAccounts = statement.executeQuery();
			while(allAccounts.next()) {
				Account acct = new Account(allAccounts.getInt("account_id"), allAccounts.getInt("owner_id"), allAccounts.getDouble("balance"));
				accounts.add(acct);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}finally {
			closeResources(); 
		}
		return accounts;
	}

	@Override
	public ArrayList<Client> getAllClients() {
		ArrayList<Client> clients = new ArrayList<Client>(); 
		try {
			connection = DAOUtil.getConnection();
			String sql = "SELECT * FROM clients";
			statement = connection.prepareStatement(sql);
			ResultSet allClients = statement.executeQuery();
			while(allClients.next()) {
				Client newClient = new Client(allClients.getInt("id"), allClients.getString("username"),
						allClients.getString("password"));
				clients.add(newClient);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}finally {
			closeResources(); 
		}
		return clients; 
	}
	
	private void closeResources() {
		try {
			if (statement != null) {
				statement.close();
			}
			if(connection != null) {
				connection.close(); 
			}
		} catch(SQLException ex ) {
			ex.printStackTrace();
		}
	}

	@Override
	public User verifyUser(String uname, String passwd, int accountType) {
		User foundUser = null;
		String sql;
		switch(accountType) {
		case User.EMPLOYEE_TYPE:
			sql = "SELECT * FROM employees WHERE username = ? AND password = ?";
			break;
		case User.CLIENT_TYPE: default: //If type is not valid, default to user table
			sql = "SELECT * FROM clients WHERE username = ? AND password = ?";
			break;
		}
		
		try {
			connection = DAOUtil.getConnection();	
			statement = connection.prepareStatement(sql);
			statement.setString(1, uname);
			statement.setBytes(2, passwd.getBytes());
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				switch(accountType) {
				case User.EMPLOYEE_TYPE:
					foundUser = new Employee(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
					break;
				case User.CLIENT_TYPE: default:
					foundUser = new Client(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}finally {
			closeResources(); 
		}
		return foundUser;
	}

	@Override
	public void sendMessage(Message msg) {
		String sql = "INSERT INTO public.messages "
				+ "(sender_id, recipient_id, balance) "
				+ "VALUES(?, ?, ?);";
		try {
			connection = DAOUtil.getConnection();	
			statement = connection.prepareStatement(sql);
			statement.setInt(1, msg.senderID);
			statement.setInt(2, msg.recipientID);
			statement.setDouble(3, msg.balance);
			statement.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeResources(); 
		}
	}

	@Override
	public Client getClient(int userID) {
		Client cli = null;
		try {
			connection = DAOUtil.getConnection();
			String sql = "SELECT * FROM clients where id = ?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, userID);
			ResultSet allClients = statement.executeQuery();
			while (allClients.next()) {
				cli = new Client(allClients.getInt("id"), allClients.getString("username"),
						allClients.getString("password"));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}finally {
			closeResources(); 
		}
		return cli;
	}

	@Override
	public void deleteMessage(int msgID) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM public.messages WHERE message_id = ?;";
		try {
			connection = DAOUtil.getConnection();	
			statement = connection.prepareStatement(sql);
			statement.setInt(1, msgID);
			statement.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeResources(); 
		}
	}

	@Override
	public void createBankAccount(int ownerID, double balance) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO public.accounts (balance, owner_id) VALUES(?, ?);";
		try {
			connection = DAOUtil.getConnection();	
			statement = connection.prepareStatement(sql);
			statement.setDouble(1, balance);
			statement.setInt(2, ownerID);
			statement.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeResources(); 
		}
	}

	@Override
	public void commitAccount(Account acct) {
		String sql = "UPDATE public.accounts SET balance=? WHERE account_id=?;";
		try {
			connection = DAOUtil.getConnection();	
			statement = connection.prepareStatement(sql);
			statement.setDouble(1, acct.getBalance());
			statement.setInt(2, acct.getAccountID());
			statement.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeResources(); 
		}
	}

	@Override
	public void commitUser(String uname, String passwd, int accountType) {
		String sql;
		switch (accountType) {
		case User.EMPLOYEE_TYPE:
			sql = "INSERT INTO public.employees (username, \"password\") VALUES(?, ?);";
			break;
		case User.CLIENT_TYPE: default: //If type is not valid, default to user table
			sql = "INSERT INTO public.clients (username, \"password\") VALUES(?, ?);";
			break;
		}
		try {
			connection = DAOUtil.getConnection();	
			statement = connection.prepareStatement(sql);
			statement.setString(1, uname);
			statement.setBytes(2, passwd.getBytes());
			statement.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeResources(); 
		}
	}
}
