package controller;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JTextArea;

import model.AbstractObservationModel;
import server.Server;
import view.PatientenView;
import view.UserView;

public class UserController extends Controller {
	 Server s = new Server();
	public UserController() {
		setUserView();
		setPatientenView();
	}

	public void setUserView() {
		UserView userView = new UserView("UK DSM | Abteilung: KIM");
		userView.setVisible(true);

	}

	public void setPatientenView() {
		PatientenView patientenView = new PatientenView("Patient");
		patientenView.setVisible(true);
		setPatientenViewActionListener(patientenView);

	}

	private void setPatientenViewActionListener(PatientenView patientenView) {
		JButton BackButton = patientenView.getBackButton();

		BackButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				patientenView.dispose();
				setUserView();
			}

		});
		JButton PatientIDButton = patientenView.getPatientIDButton();

		PatientIDButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
		
		 String result = "";
		try { 
		 
		
		ArrayList<String> PatientIDArray = s.getPatients();
		 
		// ArrayList<String> PatientIDArray = new ArrayList<>(Arrays.asList("Buenos Aires", "C�rdoba", "La Plata")); 
		 
					 if(PatientIDArray.size() == 0) {
			 PatientenView.observation().setText("No Patient IDs can be found");
		 }else {
		 for (int i = 0; i < PatientIDArray.size(); i++) {
		      System.out.println(PatientIDArray.get(i));
			  result = result + PatientIDArray.get(i)+"\n"; 
		 }
		 PatientenView.observation().setText(result);
		 
			}
			}
		
		// add catch
		
			 catch(Exception e1) { 
				 System.out.println("Cannot Connect to the Server");  
			 }
		}});
		JButton searchDataButton = patientenView.getsearchDataButton();

		searchDataButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
		
		 String result = "";
		 String input = "" + PatientenView.getPatientenTextField().getText().toString();
		 System.out.println(input);
		
		 
		 
		 try {
		 
		 ArrayList<ObservationModel> observationArray = s.getObservationsOfPatient(input);
		 
		 if(observationArray.size() == 0) {
			 PatientenView.observation().setText("No observations can be found for this patient");
		 }else {
			 
		 for (int i = 0; i < observationArray.size(); i++) {
		      System.out.println(observationArray.get(i).toString());
			  result = result + observationArray.get(i).toString(); 
		 }
		 PatientenView.observation().setText(result);
		 }}
		 
		 catch(Exception e1) { 
			 System.out.println("Cannot Search in the Server");  
		 }
			}
			
			
			
			private void setUserViewActionListener(UserView userView) {
				JButton PatientenakteButton = userView.getPatientenakteButton();

				PatientenakteButton.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						userView.dispose();
						setPatientenView();

					}
				});

				JButton LogoutButton = userView.getLogoutButton();

				LogoutButton.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						userView.dispose();
						setLogin();

					}
//JTextArea showObservation = userView.getshowObservation();

				});
			}
		});
	}

	//protected PatientenView searchDataButton() {
		// TODO Auto-generated method stub
		//return null;
	}
//}