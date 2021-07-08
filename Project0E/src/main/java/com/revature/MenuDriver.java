package com.revature;

public class MenuDriver {

	private String bankStage;
	
	public MenuDriver(){
		bankStage = "Main Menu";
	}

	public String getBankStage() {
		return bankStage;
	}

	public void setBankStageMM() {
		this.bankStage = "Main Menu";
	}
	public void setBankStageEmployee() {
		this.bankStage = "Employee";
	}
	public void setBankStageCustomer() {
		this.bankStage = "Customer";
	}
	public void setBankStageExit() {
		this.bankStage = "Exit";
	}
}

