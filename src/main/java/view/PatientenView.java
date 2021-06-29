package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.ColorModel;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;

import view.ViewStyle;

public class PatientenView extends ViewStyle {

	private static final long serialVersionUID = 1L;
	private static JTextField patientenTextField;
	private JButton searchDataButton;
	private JButton backButton;
	private static JTextArea observation;
	private JButton PatientIDButton;

	public PatientenView(String userName) {
		super();
		setTitle("Universitätsklinikum DSM");
		setBounds(0, 0, 1000, 1000);
		setLocationRelativeTo(null);
		this.initalizeUserLabelAndTextField();
//		this.initalizeObservation();
		this.initializesearchPatientButton();
		this.initializeBackButton();
		this.initializePatientIDButton();
		this.initializeDescriptionTextArea();

	}
//
//	private void initalizeObservation() {
//
//		observation = new JTextArea(5, 20);
//		observation.setBounds(120, 150, 350, 300);
//		observation.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
//		observation.setEditable(false);
//		observation.setBackground(Color.LIGHT_GRAY);
//		observation.setEditable(false);
//		JScrollPane scroll = new JScrollPane();
//		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//		scroll.setBounds(138, 150, 400, 317);
//		scroll.setViewportView(observation);
//
//		this.contentPane.add(observation);
//		this.contentPane.add(scroll);

//	}

	private void initalizeUserLabelAndTextField() {
		JLabel patientenLabel = new JLabel("Patient ID:");
		patientenLabel.setFont(LABEL_FONT);
		patientenLabel.setBounds(65, 35, 100, 25);

		patientenTextField = new JTextField();
		patientenTextField.setFont(LABEL_FONT);
		patientenTextField.setBounds(65, 60, 280, 25);

		this.contentPane.add(patientenLabel);
		this.contentPane.add(patientenTextField);

// JLabel border = new JLabel("________________________________________________________________________________");
// border.setBounds(65, 100, 2000, 30);
// this.contentPane.add(border);
	}

	private void initializeDescriptionTextArea() {
//		JLabel descriptionLabel = new JLabel("Description");
//		descriptionLabel.setFont(LABEL_FONT);
//		descriptionLabel.setBounds(100, 90, 200, 23);

		

		JScrollPane scroll = new JScrollPane();
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		getObservation().setBounds(50, 120, 700, 200);
		getObservation().setEditable(false);
		scroll.setBounds(50, 120, 700, 200);
//		this.contentPane.add(descriptionLabel);
		this.contentPane.add(getObservation());
		this.contentPane.add(scroll);
		scroll.setViewportView(getObservation());
	}

	private void initializesearchPatientButton() {
		searchDataButton = new JButton("search");
		searchDataButton.setFont(BUTTON_FONT);
		searchDataButton.setBounds(65, 88, 89, 23);
		this.contentPane.add(searchDataButton);
	}

	private void initializeBackButton() {
		backButton = new JButton("back");
		backButton.setFont(BUTTON_FONT);
		backButton.setBounds(50, 520, 89, 23);
		this.contentPane.add(backButton);
	}

	private void initializeUserLabel(String userName) {
		JLabel userLabel = new JLabel("User: " + userName);
		userLabel.setFont(LABEL_FONT);
		userLabel.setBounds(75, 35, 300, 23);
		this.contentPane.add(userLabel);
	}

	private void initializePatientIDButton() {
		PatientIDButton = new JButton("Patient IDs");
		PatientIDButton.setFont(BUTTON_FONT);
		PatientIDButton.setBounds(230, 88, 115, 23);
		this.contentPane.add(PatientIDButton);
	}

	public static JTextField getPatientenTextField() {
		return patientenTextField;
	}

	public JButton getBackButton() {
		return backButton;
	}

	public JButton getsearchDataButton() {
		return searchDataButton;
	}

	public static JTextArea observation() {

		return getObservation();
	}

	public JButton getPatientIDButton() {
		return PatientIDButton;
	}

	public static JTextArea getObservation() {
		return observation;
	
	}

	public static void main(Object object) {
		// TODO Auto-generated method stub
		
	}

}