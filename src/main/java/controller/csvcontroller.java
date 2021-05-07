package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model1.model1;

public class csvcontroller {

	private model1 csvmodel;

	// constructor - Controller
	public csvcontroller() throws IOException {

		// create hashmaps for view and models
		HashMap<String, JFrame> views = new HashMap<String, JFrame>();
		HashMap<String, Object> models = new HashMap<String, Object>();
		System.out.println("created hashmaps for View and Models");

		csvmodel = new model1();
		models.put("model1", csvmodel);

		try {

			csvmodel.csv();

		} catch (NullPointerException npe) {

			System.err.println("NO");

		}

	}

}
