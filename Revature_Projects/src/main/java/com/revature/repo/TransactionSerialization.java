package com.revature.repo;

import java.awt.MenuItem;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class TransactionSerialization {
	
	public void actallySerializing() {
		
		
		String filename = "./sampleObjectfile.txt";
		MenuItems f = new  MenuItems("Apples",0,true);
		
//		writeObject(filename,f);
		
		System.out.println(readObject(filename));
	}
	
	
	
	public void writeObject(String filename, MenuItems mi) {
		
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
			
			oos.writeObject(mi);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public MenuItems readObject(String filename) {

		MenuItems saved = null;
		
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
			
			saved = (MenuItems) ois.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return saved;
	}
	

	@Override
	public boolean addToCooler(MenuItems m) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MenuItem selectMenuItemByName(String name) throws FoodDoesntExist {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MenuItems> selectAllMenuItems() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
