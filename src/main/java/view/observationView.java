package view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class observationView extends ViewStyle {
	private static final long serialVersionUID = 1L;

	public observationView() {
	super();
	setBounds(0, 0, 400, 625);
	setLocationRelativeTo(null);
	setTitle("Observation");

	this.initTextFields();
	this.initlabels();
	this.initButtons();
	}

	private JButton saveButton = new JButton("Save");
	private JButton cancelButton = new JButton("Cancel");

	private JTextField searchpatientID = new JTextField("");
	private JTextField observationillness = new JTextField("");
	private JTextField observationdisease = new JTextField("");
	private JTextField observationvalues = new JTextField("");
	private JTextField observationsocial = new JTextField("");
	private JTextField observationtemperature = new JTextField("");
	private JTextField observationpuls = new JTextField("");
	private JTextField observationrespiration = new JTextField("");
	private JTextField observationpressure = new JTextField("");
	

	private void initlabels() {
	JLabel title = new JLabel("Observation");
	JLabel border = new JLabel("______________________________________");
	JLabel searchpatientID = new JLabel("PatientID: ");
	JLabel observationillness = new JLabel("Illness: ");
	JLabel observationdisease = new JLabel("Severity of Disease: ");
	JLabel observationvalues = new JLabel("Patients values: ");
	JLabel observationsocial = new JLabel("Social status: ");
	JLabel observationtemperature = new JLabel("Body temperature: ");
	JLabel observationpuls = new JLabel("Puls rate: ");
	JLabel observationrespiration = new JLabel("Respiration rate:");
	JLabel observationpressure = new JLabel("Blood pressure:");
	



	// Set Label Bounds
	title.setBounds(22, 20, 500, 35);
	border.setBounds(25, 140, 350, 30);
	searchpatientID.setBounds(25, 185, 150, 25);
	observationillness.setBounds(25, 215, 150, 25);
	observationdisease.setBounds(25, 250, 150, 25);
	observationvalues.setBounds(25, 285, 150, 25);
	observationsocial.setBounds(25, 315, 150, 25);
	observationtemperature.setBounds(25, 350, 150, 25);
	observationpuls.setBounds(25, 385, 200, 25);
	observationrespiration.setBounds(25, 415, 200, 25);
	observationpressure.setBounds(25, 450, 200, 25);
	

	border.setEnabled(false);

	// Add Labels to Pane
	this.contentPane.add(title);
	contentPane.add(border);
	this.contentPane.add(searchpatientID);
	this.contentPane.add(observationillness);
	this.contentPane.add(observationdisease);
	this.contentPane.add(observationvalues);
	this.contentPane.add(observationsocial);
	this.contentPane.add(observationtemperature);
	this.contentPane.add(observationpuls);
	this.contentPane.add(observationrespiration);
	this.contentPane.add(observationpressure);
	
	}

	private void initTextFields() {
	// Set TextField Bounds
	searchpatientID.setBounds(165, 185, 200, 25);
	observationillness.setBounds(165, 215, 200, 25);
	observationdisease.setBounds(165, 250, 200, 25);
	observationvalues.setBounds(165, 285, 200, 25);
	observationsocial.setBounds(165, 315, 200, 25);
	observationtemperature.setBounds(165, 350, 200, 25);
	observationpuls.setBounds(165, 385, 200, 25);
	observationrespiration.setBounds(165, 415, 200, 25);
	observationpressure.setBounds(165, 450, 200, 25);
	
	// Add TextFields to Pane

	this.contentPane.add(searchpatientID);
	this.contentPane.add(observationillness);
	this.contentPane.add(observationdisease);
	this.contentPane.add(observationvalues);
	this.contentPane.add(observationsocial);
	this.contentPane.add(observationtemperature);
	this.contentPane.add(observationpuls);
	this.contentPane.add(observationrespiration);
	this.contentPane.add(observationpressure);
	

	}

	private void initButtons() {

	// Set button font
	saveButton.setFont(BUTTON_FONT);
	cancelButton.setFont(BUTTON_FONT);

	// Set button bounds
	saveButton.setBounds(165, 550, 95, 25);
	cancelButton.setBounds(270, 550, 95, 25);

	// Disable Button until customer search is completed
	// saveButton.setEnabled(false);
	this.contentPane.add(saveButton);
	this.contentPane.add(cancelButton);
	}

	public void setOperatingButtonsEnabled() {
	saveButton.setEnabled(true);
	}
	public JTextField searchpatientID() {
	return searchpatientID;
	}

	public JTextField observationillness() {
	return observationillness;
	}

	public JTextField observationdisease() {
	return observationdisease;
	}

	public JTextField observationvalues() {
	return observationvalues;
	}
	public JTextField observationsocial() {
	return observationsocial;
	}

	public JTextField observationtemperature() {
	return observationtemperature;
	}

	public JTextField observationrespiration() {
	return observationrespiration;
	}

	public JTextField observationpressure() {
	return observationpressure;
	}


	public JButton getSaveButton() {
	return saveButton;
	}

	public JButton getCancelButton() {
	return cancelButton;
	}

	}



