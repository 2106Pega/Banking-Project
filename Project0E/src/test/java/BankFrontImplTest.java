
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.revature.BankFront;
import com.revature.BankFrontImpl;


public class BankFrontImplTest {

	BankFront bf = new BankFrontImpl();
	
	@Test
	public void testIsNumeric() {
		assertFalse(bf.isNumeric("a"));
		assertFalse(bf.isNumeric("a1"));
		assertTrue(bf.isNumeric("2313"));
		assertTrue(bf.isNumeric("0"));
	}
}
