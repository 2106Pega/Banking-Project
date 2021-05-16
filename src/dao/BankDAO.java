package dao;

import model.*;
import java.util.ArrayList;

public interface BankDAO {
	ArrayList<Message> getMessageInbox(int recipID);
	ArrayList<Account> getAccounts(int ownerID);
	ArrayList<Client> getAllClients();
	User verifyUser(String uname, String passwd, int type);
}
