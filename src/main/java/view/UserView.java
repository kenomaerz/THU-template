package view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import view.ViewStyle;

public class UserView extends ViewStyle {

private static final long serialVersionUID = 1L;
private JButton PatientenakteButton;
private JButton logoutButton;
private JButton createPatient;

public UserView(String userName) {
super();
setLocationRelativeTo(null);
setTitle("Welcome " + userName);
this.initializePatientenakteButton();
this.initializeLogoutButton();
this.initializecreatePatient();
}

private void initializePatientenakteButton() {
PatientenakteButton = new JButton("Patient Documents");
PatientenakteButton.setFont(BUTTON_FONT);
PatientenakteButton.setBounds(110, 100, 160, 30);
this.contentPane.add(PatientenakteButton);
}

private void initializeLogoutButton() {
logoutButton = new JButton("Logout");
logoutButton.setFont(BUTTON_FONT);
logoutButton.setBounds(40, 230, 89, 23);
this.contentPane.add(logoutButton);
}

private void initializecreatePatient() {
createPatient = new JButton("Create Patient");
createPatient.setFont(BUTTON_FONT);
createPatient.setBounds(110, 150, 160, 30);
this.contentPane.add(createPatient);
}

public JButton getLogoutButton() {
return logoutButton;
}

public JButton getPatientenakteButton() {
return PatientenakteButton;
}

public JButton getcreatePatient() {
return createPatient;
}
}