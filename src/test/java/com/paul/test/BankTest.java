package com.paul.test;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.paul.dao.*;
import com.paul.model.*;
import com.paul.bank.*;

public class BankTest {
	private static BankDAO dao = new BankDAOImpl();
	private Client testClient;
	
	double epsilon = 0.000001d;
	
	@Before
	public void setup() {
		testClient = dao.getClient(4);
		testClient.getMyAccounts();
		testClient.accounts.get(0).setBalance(500);
		testClient.accounts.get(1).setBalance(300);
		dao.commitAccount(testClient.accounts.get(0));
		dao.commitAccount(testClient.accounts.get(1));
	}
	
	@Test
	public void commitAccountTest() {
		assertEquals(500, testClient.accounts.get(0).getBalance(), epsilon);
		assertEquals(300, testClient.accounts.get(1).getBalance(), epsilon);
	}
	
	@Test
	public void getMyAccountsTest() throws NoSuchAlgorithmException {
		Client cli = (Client) dao.verifyUser("paul", BankPortal.hashPasswd("based"), 1);
		cli.getMyAccounts();
		assertTrue(2 >= cli.accounts.size());
	}
	
	@Test
	public void depositTest() {
		testClient.deposit(400, 0);
		testClient.deposit(100, 1);
		testClient.getMyAccounts();
		assertEquals(900.0, testClient.accounts.get(0).getBalance(), epsilon);
		assertEquals(400.0, testClient.accounts.get(1).getBalance(), epsilon);
	}
	
	@Test
	public void withdrawTest() {
		testClient.withdraw(400, 0);
		testClient.withdraw(100, 1);
		testClient.getMyAccounts();
		assertEquals(100.0, testClient.accounts.get(0).getBalance(), epsilon);
		assertEquals(200.0, testClient.accounts.get(1).getBalance(), epsilon);
	}
	
	@Test
	public void negativeValuesTest() {
		assertTrue(testClient.deposit(-50, 0) == -1.0);
		assertTrue(testClient.withdraw(-50, 0) == -1.0);
	}
	
	@Test
	public void overdrawTest() {
		assertTrue(testClient.withdraw(600.0, 0) == -1.0);
		assertTrue(testClient.withdraw(400.0, 1) == -1.0);
	}
	
	@Test
	public void getAccountsDAOTest() {
		ArrayList<Account> accts = dao.getAccounts(4);
		assertEquals(500.0, accts.get(0).getBalance(), epsilon);
		assertEquals(300.0, accts.get(1).getBalance(), epsilon);
	}
	
	@Test
	public void verifyUserTest() {
		assertTrue(dao.verifyUser("test", "junit", User.CLIENT_TYPE).equals(testClient));
		assertFalse(dao.verifyUser("henry", "dog", User.CLIENT_TYPE).equals(testClient));
		assertEquals(null, dao.verifyUser("nonexistent", "null", 1));
	}
	
	@Test
	public void sendMessageTest() {
		Message msg = new Message(4, 1, 777.0);
		dao.sendMessage(msg);
		ArrayList<Message> msgs = dao.getMessageInbox(1);
		assertEquals(1, msgs.size());
		dao.deleteMessage(msgs.get(0).messageID);
		msgs = dao.getMessageInbox(1);
		assertEquals(0, msgs.size());
	}
	
	@Test
	public void getAllClientsTest() {
		assertTrue(1 <= dao.getAllClients().size());
	}
}
