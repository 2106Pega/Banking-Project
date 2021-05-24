package com.paul.model;

public class Message {
	public int messageID;
	public int senderID;
	public int recipientID;
	public double balance;
	
	public static final int EMPLOYEE_RID = -1;
	
	public Message(int sId, int rId, double bal) {
		this.messageID = -1;
		this.senderID = sId;
		this.recipientID = rId;
		this.balance = bal;
	}
	public Message(int mId, int sId, int rId, double bal) {
		this.messageID = mId;
		this.senderID = sId;
		this.recipientID = rId;
		this.balance = bal;
	}
}
