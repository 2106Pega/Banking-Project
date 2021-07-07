package com.revature.models;

import java.util.List;
import java.util.Objects;

public class Customer {
	String first_name;
	String last_name;
	int bank_membership_number;
	int ssn;
	List<Integer> bank_account_numbers;
	
	public Customer() {
		super();
	}
	
	public Customer(String first_name, String last_name, int bank_membership_number, int ssn) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.bank_membership_number = bank_membership_number;
		this.ssn = ssn;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public int getBank_membership_number() {
		return bank_membership_number;
	}

	public void setBank_membership_number(int bank_membership_number) {
		this.bank_membership_number = bank_membership_number;
	}

	public List<Integer> getBank_account_numbers() {
		return bank_account_numbers;
	}

	public void setBank_account_numbers(List<Integer> bank_account_numbers) {
		this.bank_account_numbers = bank_account_numbers;
	}
	
	public int getSsn() {
		return ssn;
	}

	public void setSsn(int ssn) {
		this.ssn = ssn;
	}

	@Override
	public String toString() {
		return "Customer [first_name=" + first_name + ", last_name=" + last_name + ", bank_membership_number="
				+ bank_membership_number + ", ssn=" + ssn + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(bank_account_numbers, bank_membership_number, first_name, last_name, ssn);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(bank_account_numbers, other.bank_account_numbers)
				&& bank_membership_number == other.bank_membership_number
				&& Objects.equals(first_name, other.first_name) && Objects.equals(last_name, other.last_name)
				&& ssn == other.ssn;
	}
}
