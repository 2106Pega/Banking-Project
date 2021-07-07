package com.revature.models;

public class Transactions {
	
	private String ID;
	private String date;
	private String description;
	private String type;
	private double amount;
	private double banlance;
	public Transactions(String iD, String date, String description, String type, double amount, double banlance) {
		super();
		ID = iD;
		this.date = date;
		this.description = description;
		this.type = type;
		this.amount = amount;
		this.banlance = banlance;
	}
	@Override
	public String toString() {
		return "Transactions [ID=" + ID + ", date=" + date + ", description=" + description + ", type=" + type
				+ ", amount=" + amount + ", banlance=" + banlance + "]";
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getBanlance() {
		return banlance;
	}
	public void setBanlance(double banlance) {
		this.banlance = banlance;
	}


}
