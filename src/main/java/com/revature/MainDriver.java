package com.revature;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.database.DAOs;
import com.revature.presentation.IO;
import com.revature.presentation.IOImpl;
import com.revature.application.HelperFunctions;
import com.revature.application.Application;


public class MainDriver {
	public static final Logger logger= Logger.getLogger(MainDriver.class);
	public static final Scanner input= new Scanner(System.in);
	public static final DAOs daos= new DAOs();
	public static final IO io= new IOImpl(logger, input);
	public static final HelperFunctions appLayerTools= new Application(io, daos,logger);
	public static void main(String[] args) {
			appLayerTools.run();
		//appLayerTools.run();
	}

}
