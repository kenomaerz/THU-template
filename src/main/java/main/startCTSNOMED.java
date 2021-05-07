package main;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;


import java.awt.EventQueue;
import java.util.HashMap;
import javax.swing.JFrame;

import controller.csvcontroller;

//import model1.model1;
//import view.csvview;

public class startCTSNOMED {
	
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				
					System.out.println("MAIN");
					// call csvcontroller
					new csvcontroller();
					
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		});
	}
}