import org.apache.log4j.Logger;
import com.revature.BankDAO;
import com.revature.BankDAOImpl;
import com.revature.BankFront;
import com.revature.BankFrontImpl;
import com.revature.MenuDriver;
import com.revature.User;
import com.revature.utl.ConnectionFactory;


public class MainDriver {
	static final Logger debugLog = Logger.getLogger("debugLogger");
	static final Logger TransactionLog = Logger.getLogger("transactionLogger");
	
	public static void main(String[] args) {
		
		TransactionLog.info("Start Transaction Log");
		debugLog.info("Start Debug Log");
		
		
		User u = new User();
		MenuDriver md = new MenuDriver();
		boolean exit = false;
		BankFront bf = new BankFrontImpl();
		int userChoice = 0;
		bf.displayGreeting(); 					//Greet the customer

		
		while(!exit) {							//full loop
			//System.out.println(md.getBankStage());
			while(md.getBankStage() == "Main Menu") {	//main menu Loop
				bf.displayMainMenu();
				userChoice = bf.getUserChoice(3, userChoice);
				switch (userChoice) {
				case 1:							//log in
					debugLog.info("Main Menu 1");
					u = bf.logIn();
					if(u.isEmployee()) {
						md.setBankStageEmployee();
					}
					else if(u.isApproved()){
						md.setBankStageCustomer();
					}
					else {
						System.out.println("Your account either doesn't exist or is not approved");
					}
					break;
				case 2: 						//create account
					debugLog.info("Main Menu 2");
					bf.createAccount();
					break;
				case 3:							//exit
					debugLog.info("Main Menu 3");
					exit = true;
					md.setBankStageExit();
					bf.displayFairwell();
					break;
				default:
					debugLog.info("Main Menu default");
					break;
				}			
				
			}
			while(md.getBankStage() == "Customer") {			//Customer Loop
				bf.displayCustomerChoices();
				userChoice = bf.getUserChoice(5, userChoice);
				switch (userChoice) {
				case 1:								//View Balance
					debugLog.info("Customer 1");
					bf.displayBalance(u);
					break;
				case 2:								//Withdraw
					debugLog.info("Customer 2");
					bf.displayWithdrawMenu(u);
					break;
				case 3:								//Deposit
					debugLog.info("Customer 3");
					bf.displayDepositMenu(u);
					break;
				case 4:								//transfer;
					debugLog.info("Customer 4");
					bf.displayTransferMenu(u);
					break;
				case 5:								//return to main menu
					debugLog.info("Customer 5");
					md.setBankStageMM();
					break;
				default:
					debugLog.info("Customer default");
					break;
				}
			}
			
			while(md.getBankStage() == "Employee") {			//Employee Loop
				bf.displayEmployeeChoices();
				userChoice = bf.getUserChoice(3, userChoice);
				switch (userChoice) {
				case 1:								//View Account
					debugLog.info("Employee 1");
					bf.viewAccountMenu();
					break;
				case 2:								//Approve Account
					debugLog.info("Employee 2");
					bf.pendingAccountsMenu();
					break;
				case 3:								//return to main menu;
					debugLog.info("Employee 3");
					md.setBankStageMM();
					break;
				default:
					debugLog.info("Employee 4");
					break;
				}
			}
		}
	}
}
