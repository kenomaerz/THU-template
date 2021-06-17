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

import com.mashape.unirest.http.exceptions.UnirestException;

import modelCTSNOMED.CTmodel;

public class ctcontroller {

	private CTmodel ctmodel;

	// constructor - Controller
	public ctcontroller() throws IOException, UnirestException {

		// create hashmaps for view and models
		HashMap<String, JFrame> views = new HashMap<String, JFrame>();
		HashMap<String, Object> models = new HashMap<String, Object>();
		System.out.println("created hashmaps for View and Models");

		ctmodel = new CTmodel();
		models.put("ctmodel",ctmodel);

		try {

			ctmodel.ctConcepts("age");
			ctmodel.ctDescriptions("age","finding");

		} catch (NullPointerException npe) {

			System.err.println("NO");

		}

	}

}
