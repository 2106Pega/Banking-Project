package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dao.BankDAO;
import dao.BankDAOImpl;
import model.Client;

public class BankTest {
	private static BankDAO dao = new BankDAOImpl();
	
	@Test
	public void DAOTest() {
		ArrayList<Client> clientList = dao.getAllClients();
		assertEquals(1, clientList.size()); //client list is 1 for now
	}
}
