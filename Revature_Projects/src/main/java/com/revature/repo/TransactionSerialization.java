//package com.revature.repo;
//
//import java.awt.MenuItem;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.util.List;
//
//public class TransactionSerialization {
//	
//	public void actallySerializing() {
//		
//		
//		String filename = "./sampleObjectfile.txt";
//		Transactions f = new  Transactions();
//		
////		writeObject(filename,f);
//		
//		System.out.println(readObject(filename));
//	}
//	
//	
//	
//	public void writeObject(String filename, MenuItems mi) {
//		
//		try {
//			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
//			
//			oos.writeObject(mi);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
//	
//	public Transactions readObject(String filename) {
//
//		MenuItems saved = null;
//		
//		try {
//			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
//			
//			saved = (MenuItems) ois.readObject();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return saved;
//	}
//	
//
//	
//	
//
//}
