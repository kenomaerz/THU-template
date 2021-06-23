package main;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;


import java.awt.EventQueue;
import java.util.HashMap;
import javax.swing.JFrame;

import controller.ctcontroller;
import modelCTSNOMED.CTmodel;
import view.CTView;

//import model1.model1;
//import view.csvview;

public class startCTSNOMED {
	
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				
				try {

					System.out.println("MAIN");

					CTmodel model = new CTmodel();
					ctcontroller controller = new ctcontroller(model, null);
					CTView view = new CTView(controller);

				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		});
	}
}