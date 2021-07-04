package controller;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JTextArea;

import org.hl7.fhir.r4.model.Enumerations;
import server.Server;
import model.AbstractObservationModel;
import model.CategorialObservationModel;
import view.PatientenView;
import view.UserView;

public class UserController extends Controller {
	Server s = new Server();

	public UserController(String user) {
		s.createPatient("Hey", "Ho", Enumerations.AdministrativeGender.MALE);
		s.createPatient("Herbert", "Hans", Enumerations.AdministrativeGender.MALE);
		s.createPatient("Helmut", "Gustav", Enumerations.AdministrativeGender.MALE);
		String idP = s.createPatient("Lars", "Sahne", Enumerations.AdministrativeGender.FEMALE);

	
		System.out.println(idP);
		
		setPatientenView(user);
	}

	public void setUserView() {
		UserView userView = new UserView("UK DSM | Abteilung: KIM");
		userView.setVisible(true);
		setUserViewActionListener(userView);

	}

	public void setPatientenView(String user) {
		PatientenView patientenView = new PatientenView(user);
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

					

					if (PatientIDArray.size() == 0) {
						PatientenView.observation().setText("No Patient IDs can be found");
					} else {
						for (int i = 0; i < PatientIDArray.size(); i++) {
							System.out.println(PatientIDArray.get(i));
							result = result + PatientIDArray.get(i) + "\n";
						}
						System.out.println(result);
						PatientenView.observation().setText(result);
					}
				}

				// add catch

				catch (Exception e1) {
					System.out.println("Cannot Connect to the Server");
				}
			}
		});
		JButton searchDataButton = patientenView.getsearchDataButton();

		searchDataButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String result = "";
				String input = "" + PatientenView.getPatientenTextField().getText().toString();
				System.out.println(input);

				try {

					ArrayList<AbstractObservationModel> observationArray = s.getObservationsOfPatient(input);

					if (observationArray.size() == 0) {
						PatientenView.observation().setText("No observations can be found for this patient");
					} else {

						for (int i = 0; i < observationArray.size(); i++) {
							System.out.println(observationArray.get(i).getObservationSystem());
							result = result + observationArray.get(i).getPatientID() + "||\n"
									+ observationArray.get(i).getObservationSystem() + "||\n"
									+ observationArray.get(i).getObservationCode() + "||\n"
									+ observationArray.get(i).getObservationID();
						}
						PatientenView.observation().setText(result);
						System.out.println(result);
					}
				}

				catch (Exception e1) {
					System.out.println("No Observations have been found due to an error");
				}
			}

			
		});
	}

	private void setUserViewActionListener(UserView userView) {
		JButton logoutButton = userView.getLogoutButton();

		logoutButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				Controller controller = new Controller();
		 		controller.start();
				userView.setVisible(false);
				
			}
		});
		
		JButton PatientenakteButton = userView.getPatientenakteButton();

		PatientenakteButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				userView.dispose();
				setPatientenView();

		

			}

		});
	}

	
	
	public void setPatientenView() {
		String user = "admin";
		PatientenView patientenView = new PatientenView(user);
		patientenView.setVisible(true);
		setPatientenViewActionListener(patientenView);

		
	}
}
//}