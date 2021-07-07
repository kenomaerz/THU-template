package view;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class createPatientView extends ViewStyle {
private static final long serialVersionUID = 1L;

public createPatientView() {
super();
setBounds(0, 0, 400, 625);
setLocationRelativeTo(null);
setTitle("Patient statement");

this.initTextFields();
this.initlabels();
this.initButtons();
this.Logo();
}
private void Logo() {
Icon imgIcon = new ImageIcon (this.getClass().getResource("pb1.png"));
JLabel imglabel = new JLabel(imgIcon);
imglabel.setBounds(280, 40, 95, 95);
this.getContentPane().add(imglabel);
setVisible(true);
}
private JButton saveButton = new JButton("Save");
private JButton cancelButton = new JButton("Cancel");

private JTextField patientIDField = new JTextField("");
private JTextField patientDateField = new JTextField("");
private JTextField patientinoutField = new JTextField("");
private JTextField patientLastNameField = new JTextField("");
private JTextField patientFirstNameField = new JTextField("");
private JTextField patientGenderField = new JTextField("");
private JTextField patientBirthdateField = new JTextField("");
private JTextField patientAdressOneField = new JTextField("");
private JTextField patientPhoneField = new JTextField("");
private JTextField patientPostalCodeField = new JTextField("");
private JTextField patientCityField = new JTextField("");

private void initlabels() {
JLabel title = new JLabel("Patient statement");
JLabel border = new JLabel("______________________________________");
JLabel patientIDLabel = new JLabel("PatientID: ");
JLabel patientDateLabel = new JLabel("Date:");
JLabel patientinoutLabel = new JLabel("Inpatient/Outpatient:");
JLabel patientLastNameLabel = new JLabel("Last Name:");
JLabel patientFirstNameLabel = new JLabel("First Name:");
JLabel patientGenderLabel = new JLabel("Gender:");
JLabel patientBirthdateLabel = new JLabel("Birth date:");
JLabel patientAdressOneLabel = new JLabel("Address:");
JLabel patientPhoneLabel = new JLabel("Phone Number:");
JLabel patientPostalCodeLabel = new JLabel("Postal Code:");
JLabel patientCityLabel = new JLabel("City:");



// Set Label Bounds
title.setBounds(22, 20, 500, 35);
border.setBounds(25, 140, 350, 30);
patientIDLabel.setBounds(25, 185, 150, 25);
patientDateLabel.setBounds(25, 215, 150, 25);
patientFirstNameLabel.setBounds(25, 250, 150, 25);
patientLastNameLabel.setBounds(25, 285, 150, 25);
patientBirthdateLabel.setBounds(25, 315, 150, 25);
patientGenderLabel.setBounds(25, 350, 150, 25);
patientinoutLabel.setBounds(25, 385, 200, 25);
patientAdressOneLabel.setBounds(25, 415, 200, 25);
patientPostalCodeLabel.setBounds(25, 450, 200, 25);
patientCityLabel.setBounds(25, 485, 200, 25);
patientPhoneLabel.setBounds(25, 515, 200, 25);

border.setEnabled(false);

// Add Labels to Pane
this.contentPane.add(title);
contentPane.add(border);
this.contentPane.add(patientIDLabel);
this.contentPane.add(patientinoutLabel);
this.contentPane.add(patientDateLabel);
this.contentPane.add(patientBirthdateLabel);
this.contentPane.add(patientGenderLabel);
this.contentPane.add(patientLastNameLabel);
this.contentPane.add(patientFirstNameLabel);
this.contentPane.add(patientAdressOneLabel);
this.contentPane.add(patientPhoneLabel);
this.contentPane.add(patientPostalCodeLabel);
this.contentPane.add(patientCityLabel);
}

private void initTextFields() {
// Set TextField Bounds
patientIDField.setBounds(165, 185, 200, 25);
patientDateField.setBounds(165, 215, 200, 25);
patientFirstNameField.setBounds(165, 250, 200, 25);
patientLastNameField.setBounds(165, 285, 200, 25);
patientBirthdateField.setBounds(165, 315, 200, 25);
patientGenderField.setBounds(165, 350, 200, 25);
patientinoutField.setBounds(165, 385, 200, 25);
patientAdressOneField.setBounds(165, 415, 200, 25);
patientPostalCodeField.setBounds(165, 450, 200, 25);
patientCityField.setBounds(165, 485, 200, 25);
patientPhoneField.setBounds(165, 515, 200, 25);

// Add TextFields to Pane
this.contentPane.add(patientIDField);
this.contentPane.add(patientDateField);
this.contentPane.add(patientFirstNameField);
this.contentPane.add(patientLastNameField);
this.contentPane.add(patientBirthdateField);
this.contentPane.add(patientGenderField);
this.contentPane.add(patientinoutField);
this.contentPane.add(patientAdressOneField);
this.contentPane.add(patientPostalCodeField);
this.contentPane.add(patientCityField);
this.contentPane.add(patientPhoneField);

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

public JTextField patientIDField() {
return patientIDField;
}
public JTextField getpatientinoutField() {
return patientinoutField;
}

public JTextField getpatientGenderField() {
return patientGenderField;
}

public JTextField getpatientBirthdateField() {
return patientBirthdateField;
}

public JTextField getpatientDateField() {
return patientDateField;
}
public JTextField getpatientLastNameField() {
return patientLastNameField;
}

public JTextField getpatientFirstNameField() {
return patientFirstNameField;
}

public JTextField getpatientAdressOneField() {
return patientAdressOneField;
}

public JTextField getpatientPhoneField() {
return patientPhoneField;
}

public JTextField getpatientPostalCodeField() {
return patientPostalCodeField;

}

public JButton getSaveButton() {
return saveButton;
}

public JButton getCancelButton() {
return cancelButton;
}

}

