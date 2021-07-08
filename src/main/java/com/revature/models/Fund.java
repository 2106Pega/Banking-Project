package com.revature.models;

public class Fund {
	private int fund_id;
	private int fund_owner;
	private String fund_type;
	private double fund_amount;
	
	public Fund(int fund_id, int fund_owner, String fund_type, double fund_amount) {
		super();
		this.fund_id = fund_id;
		this.fund_owner = fund_owner;
		this.fund_type = fund_type;
		this.fund_amount = fund_amount;
	}
	
	public Fund(int fund_owner, String fund_type, double fund_amount) {
		super();
		this.fund_id = 0;
		this.fund_owner = fund_owner;
		this.fund_type = fund_type;
		this.fund_amount = fund_amount;
	}
	
	public int getFund_id() {
		return fund_id;
	}
	public void setFund_id(int fund_id) {
		this.fund_id = fund_id;
	}
	public int getFund_owner() {
		return fund_owner;
	}
	public void setFund_owner(int fund_owner) {
		this.fund_owner = fund_owner;
	}
	public String getFund_type() {
		return fund_type;
	}
	public void setFund_type(String fund_type) {
		this.fund_type = fund_type;
	}
	public double getFund_amount() {
		return fund_amount;
	}
	public void setFund_amount(double fund_amount) {
		this.fund_amount = fund_amount;
	}
	
	@Override
	public String toString() {
		return "Fund [fund_id=" + fund_id + ", fund_owner=" + fund_owner + ", fund_type=" + fund_type + ", fund_amount="
				+ fund_amount + "]";
	}
}
