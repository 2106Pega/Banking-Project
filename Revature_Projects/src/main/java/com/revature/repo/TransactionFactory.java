package com.revature.repo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.revature.models.User;

public class TransactionFactory
{
	public void writeObject(String filename, Transactions transaction)
	{
		try
		{
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
			
			oos.writeObject(transaction);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public Transactions readObject(String filename)
	{
		Transactions saved = null;
		
		try
		{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
			
			saved = (Transactions) ois.readObject();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
		return saved;
	}
}
