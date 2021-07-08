import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.BankService;
import com.revature.BankServiceImpl;
import com.revature.User;

public class BankServiceImplTest {

	BankService bs = new BankServiceImpl();
	static User u = new User();
	
	@BeforeClass
    public static void setup() {
		u.setBalance(500);
	}
	
	@Test
	public void testWithdraw(){
		bs.withdraw(u, 300);
		assertEquals(u.getBalance(), 200, 0.001);
		assertTrue(bs.withdraw(u, 100));
		assertFalse(bs.withdraw(u, 1000));
	}
	
	@Test
	public void testDeposit(){
		bs.deposit(u, 300);
		assertEquals(u.getBalance(), 400, 0.001);		
	}
	
	
	@Test
	public void testTestNewUser() {
		String un = "Employee";
		assertFalse(bs.testNewUser(un));
		un = "NewEmployee";
		assertTrue(bs.testNewUser(un));
	}
}
