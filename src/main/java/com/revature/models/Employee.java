package com.revature.models;

public class Employee extends GenericModel{
	private String employeeName;
	public Employee(int primaryKey, String employeeName) {
		super(primaryKey);
		this.employeeName = employeeName;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public String toString() {
		return employeeName;
	}
}
