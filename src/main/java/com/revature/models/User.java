package com.revature.models;

import java.util.Objects;

public class User {
	private String username;
	private String passcode;
	private boolean valid_c = false;
	
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




	public boolean isValid_c() {
		return valid_c;
	}




	public void setValid_c(boolean valid_c) {
		this.valid_c = valid_c;
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



	@Override
	public String toString() {
		return "UserImpl [username=" + username + "]";
	}
	
	

}
