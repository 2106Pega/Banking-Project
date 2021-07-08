/*************************
 * Author: Jason Hubbs
 * Date: 07-07-21
 */
package com.revature;

import com.revature.presentation.Presentation;

public class MainDriver {
	//main program driver. Initializes the UI class and starts the program
	//at the initial user screen.
	public static void main(String[] args) {
		Presentation presentation = new Presentation();
		presentation.DisplayInitialPage();
	}

}
