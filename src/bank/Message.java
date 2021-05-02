package bank;

public class Message {
	int senderID;
	int recipientID;
	double balance;
	
	public Message() {}
	public Message(int sId, int rId, double bal) {
		this.senderID = sId;
		this.recipientID = rId;
		this.balance = bal;
	}
	
	public int getSenderID() {
		return senderID;
	}
	public void setSenderID(int senderID) {
		this.senderID = senderID;
	}
	public int getRecipientID() {
		return recipientID;
	}
	public void setRecipientID(int recipientID) {
		this.recipientID = recipientID;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	
}
