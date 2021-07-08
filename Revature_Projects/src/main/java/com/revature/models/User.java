package com.revature.models;

import java.util.Objects;

public class User
{
	private int id;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String emailAddress;
	private String username;
	private String password;
	private String checkingAccountNumber;
	private String checkingRoutingNumber;
	private float checkingAccountBalance;
	private String savingsAccountNumber;
	private String savingsRoutingNumber;
	private float savingsAccountBalance;
	
	public User()
	{
		super();
	}

	public User(int id, String firstName, String lastName, String phoneNumber, String emailAddress, String username,
			String password, String checkingAccountNumber, String checkingRoutingNumber, float checkingAccountBalance,
			String savingsAccountNumber, String savingsRoutingNumber, float savingsAccountBalance)
	{
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
		this.username = username;
		this.password = password;
		this.checkingAccountNumber = checkingAccountNumber;
		this.checkingRoutingNumber = checkingRoutingNumber;
		this.checkingAccountBalance = checkingAccountBalance;
		this.savingsAccountNumber = savingsAccountNumber;
		this.savingsRoutingNumber = savingsRoutingNumber;
		this.savingsAccountBalance = savingsAccountBalance;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCheckingAccountNumber() {
		return checkingAccountNumber;
	}

	public void setCheckingAccountNumber(String checkingAccountNumber) {
		this.checkingAccountNumber = checkingAccountNumber;
	}

	public String getCheckingRoutingNumber() {
		return checkingRoutingNumber;
	}

	public void setCheckingRoutingNumber(String checkingRoutingNumber) {
		this.checkingRoutingNumber = checkingRoutingNumber;
	}

	public float getCheckingAccountBalance() {
		return checkingAccountBalance;
	}

	public void setCheckingAccountBalance(float checkingAccountBalance) {
		this.checkingAccountBalance = checkingAccountBalance;
	}

	public String getSavingsAccountNumber() {
		return savingsAccountNumber;
	}

	public void setSavingsAccountNumber(String savingsAccountNumber) {
		this.savingsAccountNumber = savingsAccountNumber;
	}

	public String getSavingsRoutingNumber() {
		return savingsRoutingNumber;
	}

	public void setSavingsRoutingNumber(String savingsRoutingNumber) {
		this.savingsRoutingNumber = savingsRoutingNumber;
	}

	public float getSavingsAccountBalance() {
		return savingsAccountBalance;
	}

	public void setSavingsAccountBalance(float savingsAccountBalance) {
		this.savingsAccountBalance = savingsAccountBalance;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(checkingAccountBalance);
		result = prime * result + ((checkingAccountNumber == null) ? 0 : checkingAccountNumber.hashCode());
		result = prime * result + ((checkingRoutingNumber == null) ? 0 : checkingRoutingNumber.hashCode());
		result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + id;
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + Float.floatToIntBits(savingsAccountBalance);
		result = prime * result + ((savingsAccountNumber == null) ? 0 : savingsAccountNumber.hashCode());
		result = prime * result + ((savingsRoutingNumber == null) ? 0 : savingsRoutingNumber.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (Float.floatToIntBits(checkingAccountBalance) != Float.floatToIntBits(other.checkingAccountBalance))
			return false;
		if (checkingAccountNumber == null) {
			if (other.checkingAccountNumber != null)
				return false;
		} else if (!checkingAccountNumber.equals(other.checkingAccountNumber))
			return false;
		if (checkingRoutingNumber == null) {
			if (other.checkingRoutingNumber != null)
				return false;
		} else if (!checkingRoutingNumber.equals(other.checkingRoutingNumber))
			return false;
		if (emailAddress == null) {
			if (other.emailAddress != null)
				return false;
		} else if (!emailAddress.equals(other.emailAddress))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id != other.id)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (Float.floatToIntBits(savingsAccountBalance) != Float.floatToIntBits(other.savingsAccountBalance))
			return false;
		if (savingsAccountNumber == null) {
			if (other.savingsAccountNumber != null)
				return false;
		} else if (!savingsAccountNumber.equals(other.savingsAccountNumber))
			return false;
		if (savingsRoutingNumber == null) {
			if (other.savingsRoutingNumber != null)
				return false;
		} else if (!savingsRoutingNumber.equals(other.savingsRoutingNumber))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", phoneNumber=" + phoneNumber
				+ ", emailAddress=" + emailAddress + ", username=" + username + ", password=" + password
				+ ", checkingAccountNumber=" + checkingAccountNumber + ", checkingRoutingNumber="
				+ checkingRoutingNumber + ", checkingAccountBalance=" + checkingAccountBalance
				+ ", savingsAccountNumber=" + savingsAccountNumber + ", savingsRoutingNumber=" + savingsRoutingNumber
				+ ", savingsAccountBalance=" + savingsAccountBalance + "]";
	}
}