package com.revature.Models;

public class Log_Entry 
{
	private int id;
	private int source;
	private int receiving;
	private String type;
	private int amount;
	public Log_Entry(int id, int source, int receiving, String type, int amount) {
		super();
		this.id = id;
		this.source = source;
		this.receiving = receiving;
		this.type = type;
		this.amount = amount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public int getReceiving() {
		return receiving;
	}
	public void setReceiving(int receiving) {
		this.receiving = receiving;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Entry id: " + id + ", Source account id: " + source + ", Receiving account id: " + receiving + ", Type: " + type + " Amount: " + amount;
	}
	
	
}
