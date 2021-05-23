package view;

import javax.swing.JButton;
import javax.swing.JLabel;

import view.ViewStyle;

public class UserView extends ViewStyle {

	private static final long serialVersionUID = 1L;
	private JButton PatientenakteButton;  
	private JButton logoutButton;
	
	
	public UserView(String userName) {
		super();
		setLocationRelativeTo(null);  
		setTitle("Willkommen " + userName);
		this.initializePatientenakteButton();
		this.initializeLogoutButton();

	}

	private void initializePatientenakteButton() {
		PatientenakteButton = new JButton("Patientenakten");
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
	

	public JButton getLogoutButton() {
		return logoutButton;
	}

	
	public JButton getPatientenakteButton() {
		return PatientenakteButton;
	}
}
