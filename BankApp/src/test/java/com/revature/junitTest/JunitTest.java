package com.revature.junitTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;

import com.revature.dao.AccountsDao;
import com.revature.dao.AccountsDaoImpl;
import com.revature.dao.UsersDao;
import com.revature.dao.UsersDaoImpl;
import com.revature.models.Accounts;
import com.revature.models.Users;
import com.revature.service.AccountProcess;
import com.revature.service.TransactionsProcess;


public class JunitTest {
	
	@Mock
	Users fakeUsers;
	
	@Test
	public void testCreateUserDuplicateUserName() {
		
		
		
		
		Users newUser = new Users(1,"polloty", "Ben", "Hu");
		UsersDao userDao = new UsersDaoImpl();
		
		
		//fakeUsers = mock(Users.class);
			
		//userDao.createUser(newUser);  // create new customer in DB
		
		//LogProcess.userRegistration();

		assertEquals(newUser.getUser_name(), 
				(userDao.selectUserByUsername("polloty")).getUser_name());
		assertEquals(newUser.getFirstName(), 
				(userDao.selectUserByUsername("polloty")).getFirstName());
		assertEquals(newUser.getLastName(), 
				(userDao.selectUserByUsername("polloty")).getLastName());
		
		
	}
	
	
	
	  @Test public void testCreateUser2() {
	  
	  
	  Users newUser = new Users(1,"polloty", "Benjamin", "Hu"); 
	  Users newUser2 = new Users(1,"polloty110", "Benjamin", "Hun"); 
	  UsersDao userDao = new UsersDaoImpl();
	  
	 //userDao.createUser(newUser2); //LogProcess.userRegistration();
	  
	  assertEquals(newUser.getFirstName(),
	  (userDao.selectUserByUsername("polloty110")).getFirstName());
	  
	  
	  
	  assertEquals(newUser2.getUser_name(),
	  (userDao.selectUserByUsername("polloty110")).getUser_name());
	  assertEquals(newUser2.getFirstName(),
	  (userDao.selectUserByUsername("polloty110")).getFirstName());
	  assertEquals(newUser2.getLastName(),
	  (userDao.selectUserByUsername("polloty110")).getLastName());
	  
	  }
	  
	  
		
	  @Test public void testCustomerwithdraw() {
	  
		  int amount = 40;
		  Accounts account = new Accounts(2,"savings124", 100, true, 2);
		  
		  TransactionsProcess transProcess = mock(TransactionsProcess.class);
		  transProcess.withdrawal(account, amount);
		  
		  AccountsDao accountDao = new AccountsDaoImpl();
		  Accounts account2 = accountDao.selectAccountByAccountId(account.getAccountID());
		  
		  assertEquals(account2.getBanlance() + 40, account.getBanlance() , 0.00001);

	  }
	  
	  
	  @Test 
	  public void testCustomerDeposit() {
		  
		  int amount = 70;
		  Accounts account = new Accounts(2,"savings124", 60, true, 2);
		  
		  TransactionsProcess transProcess = mock(TransactionsProcess.class);
		  transProcess.deposit(account, amount);
		  
		  AccountsDao accountDao = new AccountsDaoImpl();
		  Accounts account2 = accountDao.selectAccountByAccountId(account.getAccountID());
		  
		  assertEquals(account2.getBanlance() - 70, account.getBanlance() , 0.00001);

	  }
	  
	  @Test 
	  public void testCustomerTransfer() {
		  
		  int amount = 70;
		  Accounts account1 = new Accounts(2,"savings124", 130, true, 2);
		  Accounts account2 = new Accounts(1,"savings123", 0, true, 1);
		  
		  TransactionsProcess transProcess = mock(TransactionsProcess.class);
		  transProcess.transfer(account1, account2, amount);
		  
		  AccountsDao accountDao = new AccountsDaoImpl();
		  Accounts account3 = accountDao.selectAccountByAccountId(account1.getAccountID());
		  Accounts account4 = accountDao.selectAccountByAccountId(account2.getAccountID());
		  
		  assertEquals(account3.getBanlance() + 10, account4.getBanlance() , 0.00001);

	  }
	  
	  
	  
	  @Test 
	  public void testCustomerAccountAdd() {
		  

		  Users newUser = new Users(5,"polloty", "Benjamin", "Hu"); 
		  Accounts account = new Accounts(10,"savings3344", 0, false, 5);
		  
		  AccountsDao accountDao = new AccountsDaoImpl();
		  accountDao.createAccount(account);
		  
		  List<Accounts> accountList = accountDao.selectAccountsByUserId(newUser.getID());
		  
		  assertEquals(false, accountList.get(0).isApprove());
		  assertEquals(false, accountList.get(1).isApprove());
	  }
	  
	  
	  
	  @Test 
	  public void testAccounWithdrawNegative() {
	  
		  int amount = -40;
		  Accounts account = new Accounts(2,"savings124", 60, true, 2);
		  
		  TransactionsProcess transProcess = mock(TransactionsProcess.class);
		  transProcess.withdrawal(account, amount);
		  
		  AccountsDao accountDao = new AccountsDaoImpl();
		  Accounts account2 = accountDao.selectAccountByAccountId(account.getAccountID());
		  
		  assertEquals(60, account.getBanlance() , 0.00001);

	  }
	  
	  
	  @Test 
	  public void testCustomerTransferNegative() {
		  
		  int amount = -70;
		  Accounts account1 = new Accounts(2,"savings124", 130, true, 2);
		  Accounts account2 = new Accounts(1,"savings123", 0, true, 1);
		  
		  TransactionsProcess transProcess = mock(TransactionsProcess.class);
		  transProcess.transfer(account1, account2, amount);
		  
		  AccountsDao accountDao = new AccountsDaoImpl();
		  Accounts account3 = accountDao.selectAccountByAccountId(account1.getAccountID());
		  Accounts account4 = accountDao.selectAccountByAccountId(account2.getAccountID());
		  
		  assertEquals(account3.getBanlance() + 10, account4.getBanlance() , 0.00001);

	  }
	  
	  
	  
	  @Test 
	  public void testCustomerDepositNegative() {
		  
		  int amount = -60;
		  Accounts account = new Accounts(2,"savings124", 60, true, 2);
		  
		  TransactionsProcess transProcess = mock(TransactionsProcess.class);
		  transProcess.deposit(account, amount);
		  
		  AccountsDao accountDao = new AccountsDaoImpl();
		  Accounts account2 = accountDao.selectAccountByAccountId(account.getAccountID());
		  
		  assertEquals(60, account.getBanlance() , 0.00001);

	  }
	  
	  
	  
	  @Test 
	  public void testEmployeeApproveAccount() {
		  

		  Accounts account = new Accounts(23,"saving3344", 0, false, 5);
		  
		  AccountsDao accountDao = new AccountsDaoImpl();

		  accountDao.approveAccounts(account.getAccountID());
		  account = accountDao.selectAccountByAccountId(16);
		  
		  assertEquals(true , account.isApprove());

	  }
	  
	  
	  
	  
	  
	  
	  
	 
	
	
	
	
	
	
	
	
	
	
	

}
