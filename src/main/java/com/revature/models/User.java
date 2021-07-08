package com.revature.models;

import java.util.Objects;

public class User {
	private String username;
	private String passcode;
	
	public User(String username, String passcode) {
		super();
		this.username = username;
		this.passcode = passcode;
	}
	

	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPasscode() {
		return passcode;
	}


	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}


	@Override
	public int hashCode() {
		return Objects.hash(passcode, username);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(passcode, other.passcode) && Objects.equals(username, other.username);
	}


}
