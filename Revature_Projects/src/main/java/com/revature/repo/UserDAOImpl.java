package com.revature.repo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.revature.MainDriver;
import com.revature.models.Employee;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;

public class UserDAOImpl implements UserDAO {

	@Override
	public boolean CreateUserApplication(User user)
	{
		String sql = "INSERT INTO pending_applications (first_name,last_name,phone_number,email_address,username,password) VALUES(?,?,?,?,?,?)";
		try(Connection conn = ConnectionFactory.getConnection();)
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getPhoneNumber());
			ps.setString(4, user.getEmailAddress());
			ps.setString(5, user.getUsername());
			ps.setString(6, user.getPassword());
			ps.execute();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return true;
	}
	
	@Override
	public boolean AcceptUserApplication(User user)
	{
		String sql = "INSERT INTO customers (first_name,last_name,phone_number,email_address,username,password,"
				+ "checking_account_balance,savings_account_balance) VALUES(?,?,?,?,?,?,?,?)";
		try(Connection conn = ConnectionFactory.getConnection();)
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getPhoneNumber());
			ps.setString(4, user.getEmailAddress());
			ps.setString(5, user.getUsername());
			ps.setString(6, user.getPassword());
			ps.setFloat(7, 0);
			ps.setFloat(8, 0);
			ps.execute();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		RemoveUserApplication(user);
		return true;
	}
	
	@Override
	public boolean RemoveUserApplication(User user)
	{
		String sql = "DELETE FROM pending_applications WHERE first_name = ? AND last_name = ? AND phone_number = ?";
				   //+ " AND email_address = ? AND username = ? AND password = ? VALUES(?,?,?,?,?,?)";
		String firstName = "";
		try(Connection conn = ConnectionFactory.getConnection();)
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			firstName = user.getFirstName();
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getPhoneNumber());
			//ps.setString(4, user.getEmailAddress());
			//ps.setString(5, user.getUsername());
			//ps.setString(6, user.getPassword());
			ps.execute();
			System.out.print(firstName + "'s user application removed from pending applications.");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return true;
	}
	
	public List<User> selectAllPendingApplications()
	{
		List<User> databaseUsers = new ArrayList<>();
		String sql = "select * from pending_applications";

		try(Connection conn = ConnectionFactory.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();

			while(rs.next())
			{
				databaseUsers.add(new User(
//						rs.getInt("customer_user_id"),
//						rs.getString("first_name"),
//						rs.getString("last_name"),
//						rs.getString(4),
//						rs.getString(5),
//						rs.getString(6),
//						rs.getString(7),
//						rs.getString(8),
//						rs.getString(9),
//						rs.getFloat(10),
//						rs.getString(11),
//						rs.getString(12),
//						rs.getFloat(13)
						0,
						rs.getString("first_name"),
						rs.getString("last_name"),
						rs.getString("phone_number"),
						rs.getString("email_address"),
						rs.getString("username"),
						rs.getString("password"),
						"0",
						"0",
						0,
						"0",
						"0",
						0
						));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return databaseUsers;
	}
	
	@Override
	public boolean insertUser(User f)
	{	
		//String sql = "INSERT INTO customers (first_name,last_name,phone_number,email_address) VALUES(?,?,?,?)"; //WORKED
		//String sql = "INSERT INTO customers (first_name,last_name,phone_number,email_address,username,password) VALUES(?,?,?,?,?,?)";
		//String sqlTwo = "INSERT INTO accounts (checking_account_number,checking_routing_number,checking_account_balance,"
				//+ "savings_account_number,savings_routing_number,savings_account_balance) VALUES(?,?,?,?,?,?)";
		String sql = "INSERT INTO customers (first_name,last_name,phone_number,email_address,username,password,"
				+ "checking_account_balance,savings_account_balance) VALUES(?,?,?,?,?,?,?,?)";
		//Created a connection
		try(Connection conn = ConnectionFactory.getConnection();)
		{
			//created a prepared statement object to be sent to our db
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, f.getFirstName());
			ps.setString(2, f.getLastName());
			ps.setString(3, f.getPhoneNumber());
			ps.setString(4, f.getEmailAddress());
			ps.setString(5, f.getUsername());
			ps.setString(6, f.getPassword());
			ps.setFloat(7, f.getCheckingAccountBalance());
			ps.setFloat(8, f.getSavingsAccountBalance());
			ps.execute();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public List<User> selectUserById(int id)
	{
		List<User> databaseUsers = new ArrayList<>();
		
		String sql = "select * from customers where customer_user_id = ? ;";

		try(Connection conn = ConnectionFactory.getConnection())
		{
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();

			while(rs.next())
			{
				databaseUsers.add(new User(
						rs.getInt("customer_user_id"),
						rs.getString("first_name"),
						rs.getString("last_name"),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8),
						rs.getString(9),
						rs.getFloat(10),
						rs.getString(11),
						rs.getString(12),
						rs.getFloat(13)
						));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return databaseUsers;
	}

	@Override
	public List<User> selectUsersByFirstName(String firstName)
	{
		List<User> databaseUsers = new ArrayList<>();
		
		//This time we're using a prepared statement. 
		//prepared statements protect us from sql injections 
		//    (SQL injection is where we send sql commands pretending to be values. 
//				e.g. insert into tables values (delete from table Users;)
		// (they're easier for visualization of sql commands) 
		
		
		//String sql = "select * from customers where firstName = ? ;";
		String sql = "select * from customers where first_name = ? ;";
		
		//try with resources syntax 
		try(Connection conn = ConnectionFactory.getConnection())
		{
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, firstName);
			
			//we expect rows and columns back from our db 
			ResultSet rs = ps.executeQuery();
			
			//we want to convert those columsn and rows into objects. 
//			while(rs.next()) {
//				databaseUsers.add(new User(
//						rs.getString("User_firstName"),
//						rs.getString("User_lastName"),
//						rs.getString(3),
//						rs.getString(4)
//						));
//			}
			while(rs.next())
			{
				databaseUsers.add(new User(
//						rs.getString("first_name"),
//						rs.getString("last_name"),
//						rs.getString(3),
//						rs.getString(4)
						rs.getInt("customer_user_id"),
						rs.getString("first_name"),
						rs.getString("last_name"),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8),
						rs.getString(9),
						rs.getFloat(10),
						rs.getString(11),
						rs.getString(12),
						rs.getFloat(13)
						));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return databaseUsers;
	}

	@Override
	public List<User> selectUserByAllCredentials(String username, String password, int id)
	{
		List<User> databaseUsers = new ArrayList<>();
		//String sql = "select * from customers where first_name = ? ;";
		String sql = "select * from customers where username = ? AND password = ? AND customer_user_id = ? ;";
		
		try(Connection conn = ConnectionFactory.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setInt(3, id);

			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				databaseUsers.add(new User(
						rs.getInt("customer_user_id"),
						rs.getString("first_name"),
						rs.getString("last_name"),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8),
						rs.getString(9),
						rs.getFloat(10),
						rs.getString(11),
						rs.getString(12),
						rs.getFloat(13)
						));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (InputMismatchException e)
        {
			MainDriver.InvalidInputMessage();
        }
		catch (Exception e)
        {
			e.printStackTrace();
        }
		return databaseUsers;
	}

	@Override
	public List<Employee> selectEmployee(String username, String password, int id)
	{
		List<Employee> databaseEmployees = new ArrayList<>();
		String sql = "select * from employees where username = ? AND password = ? AND employee_user_id = ? ;";
		
		try(Connection conn = ConnectionFactory.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setInt(3, id);

			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				databaseEmployees.add(new Employee(
						rs.getInt("employee_user_id"),
						rs.getString("username"),
						rs.getString("password")
						));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (InputMismatchException e)
        {
			MainDriver.InvalidInputMessage();
        }
		catch (Exception e)
        {
			e.printStackTrace();
        }
		return databaseEmployees;
	}
	
	//public void logTransaction()
	//{
	//	
	//}
	
	@Override
	public void updateUserCheckingBalance(float balanceChange, boolean depositing, User user, Scanner scanner, UserDAO userDao/*, File transactionLog*/)
	{
		//String sql = "update accounts where username = ? AND password = ? AND customer_user_id = ? ;";
		//String sqlTwo = "set checking_account_balance = checking_account_balance + where username = ? AND password = ? AND customer_user_id = ? ;";
		//String sql = "update accounts set checking_account_balance = checking_account_balance " + String.valueOf(depositAmount) + " where username = ? AND password = ? AND customer_user_id = ? ;";

		try(Connection conn = ConnectionFactory.getConnection())
		{
			//String sql = "update accounts set checking_account_balance = ? where username = ? AND password = ? AND customer_user_id = ? ;";
			String sql = "update customers set checking_account_balance = ? where username = ? AND password = ? AND customer_user_id = ? ;";
			PreparedStatement ps = conn.prepareStatement(sql);
			float priorBalance = user.getCheckingAccountBalance();
			float newBalance;
			if (depositing)
			{
				newBalance = priorBalance + balanceChange;
				System.out.println("$" + balanceChange + " has been deposited into your checking account.");
				try
				{
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");  
					LocalDateTime now = LocalDateTime.now();  
				    //FileWriter myWriter = new FileWriter("Transaction Log.txt");
				    FileWriter myWriter = new FileWriter("C:\\Users\\grant\\Desktop\\Transaction Log.txt", true);
					myWriter.write(now + ": User " + user.getId() + " deposited $" + balanceChange + " into their checking account.\n");
				    myWriter.close();
				    //System.out.println("Successfully wrote to the file.");
				}
				catch (IOException e)
				{
				    System.out.println("An error occurred.");
				    e.printStackTrace();
				}
			}
			else if ((priorBalance - balanceChange) > 0)
			{
				newBalance = priorBalance - balanceChange;
				System.out.println("$" + balanceChange + " has been withdrawn from your checking account.");
				try
				{
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");  
					LocalDateTime now = LocalDateTime.now();  
				    //FileWriter myWriter = new FileWriter("Transaction Log.txt");
					FileWriter myWriter = new FileWriter("C:\\Users\\grant\\Desktop\\Transaction Log.txt", true);
					myWriter.write(now + ": User " + user.getId() + " withdrew $" + balanceChange + " from their checking account.\n");
				    myWriter.close();
				    //System.out.println("Successfully wrote to the file.");
				}
				catch (IOException e)
				{
				    System.out.println("An error occurred.");
				    e.printStackTrace();
				}
			}
			else
			{
				System.out.println("Prior balance was only $" + priorBalance + " so only that much was able to be withdrawn.");
				newBalance = 0;
				try
				{
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");  
					LocalDateTime now = LocalDateTime.now();  
				    //FileWriter myWriter = new FileWriter("Transaction Log.txt");
				    FileWriter myWriter = new FileWriter("C:\\Users\\grant\\Desktop\\Transaction Log.txt", true);
				    myWriter.write(now + ": User " + user.getId() + " withdrew $" + priorBalance + " from their checking account.\n");
				    myWriter.close();
				    //System.out.println("Successfully wrote to the file.");
				}
				catch (IOException e)
				{
				    System.out.println("An error occurred.");
				    e.printStackTrace();
				}
			}
			user.setCheckingAccountBalance(newBalance);
			//System.out.print("New checking account balance: " + user.getCheckingAccountBalance());
			ps.setFloat(1, newBalance);
			ps.setString(2, user.getUsername());
			ps.setString(3, user.getPassword());
			ps.setInt(4, user.getId());

			ResultSet rs = ps.executeQuery();
//			try
//			{
//				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");  
//				LocalDateTime now = LocalDateTime.now();  
//			    FileWriter myWriter = new FileWriter("Transaction Log.txt");
//			    if (depositing)
//			    {
//				    myWriter.write(now + ": User " + user.getId() + " deposited $" + balanceChange + "\n");
//			    }
//			    else
//			    {
//				    myWriter.write(now + ": User " + user.getId() + " withdrew $" + balanceChange + "\n");
//			    }
//			    myWriter.close();
//			    System.out.println("Successfully wrote to the file.");
//			}
//			catch (IOException e)
//			{
//			    System.out.println("An error occurred.");
//			    e.printStackTrace();
//			}
		}
		catch (SQLException e)
		{
			//e.printStackTrace();
		}
		catch (InputMismatchException e)
        {
			MainDriver.InvalidInputMessage();
        }
		catch (Exception e)
        {
			e.printStackTrace();
        }
		MainDriver.UserOptionsMenu(scanner, userDao, user);
		//return true;
	}
	
	@Override
	public void updateUserSavingsBalance(float balanceChange, boolean depositing, User user, Scanner scanner, UserDAO userDao/*, File transactionLog*/)
	{
		try(Connection conn = ConnectionFactory.getConnection())
		{
			String sql = "update customers set savings_account_balance = ? where username = ? AND password = ? AND customer_user_id = ? ;";
			PreparedStatement ps = conn.prepareStatement(sql);
			float priorBalance = user.getSavingsAccountBalance();
			float newBalance;
			if (depositing)
			{
				newBalance = priorBalance + balanceChange;
				System.out.println("$" + balanceChange + " has been deposited into your savings account.");
				try
				{
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");  
					LocalDateTime now = LocalDateTime.now();  
				    //FileWriter myWriter = new FileWriter("Transaction Log.txt");
				    FileWriter myWriter = new FileWriter("C:\\Users\\grant\\Desktop\\Transaction Log.txt", true);
					myWriter.write(now + ": User " + user.getId() + " deposited $" + balanceChange + " into their savings account.\n");
				    myWriter.close();
				    //System.out.println("Successfully wrote to the file.");
				}
				catch (IOException e)
				{
				    System.out.println("An error occurred.");
				    e.printStackTrace();
				}
			}
			else if ((user.getSavingsAccountBalance() - balanceChange) > 0)
			{
				newBalance = priorBalance - balanceChange;
				System.out.println("$" + balanceChange + " has been withdrawn from your savings account.");
				try
				{
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");  
					LocalDateTime now = LocalDateTime.now();  
				    //FileWriter myWriter = new FileWriter("Transaction Log.txt");
				    FileWriter myWriter = new FileWriter("C:\\Users\\grant\\Desktop\\Transaction Log.txt", true);
					myWriter.write(now + ": User " + user.getId() + " withdrew $" + balanceChange + " from their savings account.\n");
				    myWriter.close();
				    //System.out.println("Successfully wrote to the file.");
				}
				catch (IOException e)
				{
				    System.out.println("An error occurred.");
				    e.printStackTrace();
				}
			}
			else
			{
				newBalance = 0;
				System.out.println("Prior balance was only $" + priorBalance + " so only that much was able to be withdrawn.");
				try
				{
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");  
					LocalDateTime now = LocalDateTime.now();  
				    //FileWriter myWriter = new FileWriter("Transaction Log.txt");
				    FileWriter myWriter = new FileWriter("C:\\Users\\grant\\Desktop\\Transaction Log.txt", true);
					myWriter.write(now + ": User " + user.getId() + " withdrew $" + priorBalance + " from their savings account.\n");
				    myWriter.close();
				    System.out.println("Successfully wrote to the file.");
				}
				catch (IOException e)
				{
				    System.out.println("An error occurred.");
				    e.printStackTrace();
				}
			}
			user.setSavingsAccountBalance(newBalance);
			//System.out.print("New savings account balance is " + user.getSavingsAccountBalance());
			ps.setFloat(1, newBalance);
			ps.setString(2, user.getUsername());
			ps.setString(3, user.getPassword());
			ps.setInt(4, user.getId());

			ResultSet rs = ps.executeQuery();
		}
		catch (SQLException e)
		{
			//e.printStackTrace();
		}
		catch (InputMismatchException e)
        {
			MainDriver.InvalidInputMessage();
        }
		catch (Exception e)
        {
			e.printStackTrace();
        }
		MainDriver.UserOptionsMenu(scanner, userDao, user);
		//return true;
	}
	
	@Override
	public List<User> selectAllUsers() {
		List<User> databaseUsers = new ArrayList<>();
	
		String sql = "select * from customers;";
		
		try(Connection conn = ConnectionFactory.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				databaseUsers.add(new User(
						rs.getInt("customer_user_id"),
						rs.getString("first_name"),
						rs.getString("last_name"),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8),
						rs.getString(9),
						rs.getFloat(10),
						rs.getString(11),
						rs.getString(12),
						rs.getFloat(13)
						));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return databaseUsers;
	}

	@Override
	public void updateUser(User f) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteUser(User f) {
		// TODO Auto-generated method stub

	}
}
