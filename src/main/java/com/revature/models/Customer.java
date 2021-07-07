package com.revature.models;

public class Customer {
	
	private int customer_id;
	private String first_name;
	private String last_name;
	private String phone_number;
	private String email;
	
	public Customer(int customer_id, String first_name, String last_name, String phone_number, String email) {
		this.setCustomerId(customer_id);
		this.setFirstName(first_name);
		this.setLastName(last_name);
		this.setPhoneNumber(phone_number);
		this.setEmail(email);
	}

	public int getCustomerId() {
		return customer_id;
	}

	public void setCustomerId(int customer_id) {
		this.customer_id = customer_id;
	}

	public String getFirstName() {
		return first_name;
	}

	public void setFirstName(String first_name) {
		this.first_name = first_name;
	}

	public String getLastName() {
		return last_name;
	}

	public void setLastName(String last_name) {
		this.last_name = last_name;
	}

	public String getPhoneNumber() {
		return phone_number;
	}

	public void setPhoneNumber(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
