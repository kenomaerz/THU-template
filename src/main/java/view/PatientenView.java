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
setBounds(0, 0, 1000, 650);
setLocationRelativeTo(null);
this.initalizeUserLabelAndTextField();

this.initializesearchPatientButton();
this.initializeBackButton();
this.initializePatientIDButton();
this.initializeDescriptionTextArea();
this.initializeUserLabel(userName);

}


private void initalizeUserLabelAndTextField() {
JLabel patientenLabel = new JLabel("Patient ID:");
patientenLabel.setFont(LABEL_FONT);
patientenLabel.setBounds(65, 70, 100, 25);

patientenTextField = new JTextField();
patientenTextField.setFont(LABEL_FONT);
patientenTextField.setBounds(65, 95, 280, 25);

this.contentPane.add(patientenLabel);
this.contentPane.add(patientenTextField);


}

private void initializeDescriptionTextArea() {


observation = new JTextArea();

JScrollPane scroll = new JScrollPane();
scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

observation.setBounds(80, 155, 700, 200);
observation.setEditable(false);
scroll.setBounds(155, 210, 700, 200);

this.contentPane.add(observation);
this.contentPane.add(scroll);
scroll.setViewportView(observation);
}

private void initializesearchPatientButton() {
searchDataButton = new JButton("search");
searchDataButton.setFont(BUTTON_FONT);
searchDataButton.setBounds(65, 123, 89, 23);
this.contentPane.add(searchDataButton);
}

private void initializeBackButton() {
backButton = new JButton("Cancel");
backButton.setFont(BUTTON_FONT);
backButton.setBounds(50, 520, 89, 23);
this.contentPane.add(backButton);
}

private void initializeUserLabel(String userName) {
JLabel userLabel = new JLabel("User: " + userName);
userLabel.setFont(LABEL_FONT);
userLabel.setBounds(65, 35, 300, 23);
this.contentPane.add(userLabel);
}

private void initializePatientIDButton() {
PatientIDButton = new JButton("Patient IDs");
PatientIDButton.setFont(BUTTON_FONT);
PatientIDButton.setBounds(230, 123, 115, 23);
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

return observation;
}

public JButton getPatientIDButton() {
return PatientIDButton;
}

}