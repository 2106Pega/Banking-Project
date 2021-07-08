package com.revature.models;

public abstract class GenericModel {
	private int primaryKey;

	public int getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(int primaryKey) {
		this.primaryKey = primaryKey;
	}

	public GenericModel(int primaryKey) {
		super();
		this.primaryKey = primaryKey;
	}
	public abstract String toString();
	public static boolean areEqual(GenericModel a, GenericModel b) {
		// TODO Auto-generated method stub
		if((a == null) || (b == null) ) {
			return a == b;
		}else {
			return a.getPrimaryKey() == b.getPrimaryKey();
		}
	}
}
