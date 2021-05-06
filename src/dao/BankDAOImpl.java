package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Account;
import model.Client;
import model.Message;

import model.*;

public class BankDAOImpl implements BankDAO {
	Connection connection;
	PreparedStatement statement;

	@Override
	public ArrayList<Message> getMessageInbox(int recipID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Account> getAccounts(int ownerID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Client> getAllClients() {
		ArrayList<Client> clients = new ArrayList<Client>(); 
		try {
			connection = DAOUtil.getConnection();
			String sql = "SELECT * FROM clients";
			statement = connection.prepareStatement(sql);
			ResultSet allClients= statement.executeQuery();
			while(allClients.next()) {
				Client newClient = new Client(allClients.getString("firstname"),
						allClients.getString("lastname"));
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
	
}
