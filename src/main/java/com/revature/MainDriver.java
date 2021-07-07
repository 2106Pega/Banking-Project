package com.revature;



import com.revature.presentation.Presentation;


public class MainDriver {
	
	public static void main(String[] args) {
		//UserDAO uDao = new UserDAOImpl();
		//CustomerDAO cDao = new CustomerDAOImpl();
		//User user = uDao.getUserByUsernameAndPassword("pettcat", "password");
		//Customer customer = cDao.getCustomerByUser(user);
		//System.out.println(customer.getFirstName());
		
		//AccountDAO aDao = new AccountDAOImpl();
		//Account account = new Account(0, customer.getCustomerId(), 10);
		//aDao.insertAccount(account);
		

		Presentation presentation = new Presentation();
		presentation.DisplayInitialPage();
	}

}
