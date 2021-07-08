import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.BankDAO;
import com.revature.BankDAOImpl;
import com.revature.User;

public class BankDAOImplTest {

	static User u = new User();
	BankDAO bd = new BankDAOImpl();
	
	@BeforeClass
    public static void setup() {
		u.setAccountId(1);
		
	}
	
	@Test
	public void testCheckAccount(){
		assertTrue(bd.checkAccount(u));
		u.setAccountId(-1);
		assertFalse(bd.checkAccount(u));
		
	}
	
	@Test
	public void testTestUsername(){
		String un = "Employee";
		assertFalse(bd.testUsername(un));
		un = "NewEmployee";
		assertTrue(bd.testUsername(un));
	}
}
