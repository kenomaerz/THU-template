package view;
import view.PatientenView;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.UserController;
import view.ViewStyle;

public class Login extends ViewStyle {

	private static final long serialVersionUID = 1L;
	private JTextField userTextField;
	private JPasswordField passwordField;
	private JButton loginButton;

	
			
		
	public Login() {
		super();
		setTitle("Welcome | Login"); 
		this.initalizeUserLabelAndTextField();
		this.initializePasswordLabelAndTextField();
		this.initializeLoginButton();
//		this.Logo();
	}
	
//	private void Logo() {
//	Icon imgIcon = new ImageIcon (this.getClass().getResource("test2.gif"));
//	JLabel imglabel = new JLabel(imgIcon);
//	imglabel.setBounds(300, 188, 75, 75);
//	this.getContentPane().add(imglabel);
//	setVisible(true);
//}

	
	
	private void initalizeUserLabelAndTextField() {
		JLabel userLabel = new JLabel("User ID:");
		userLabel.setFont(LABEL_FONT);
		userLabel.setBounds(65, 61, 100, 23);
		
		userTextField = new JTextField();
		userTextField.setFont(LABEL_FONT);
		userTextField.setBounds(65, 90, 250, 23);

		this.contentPane.add(userLabel);
		this.contentPane.add(userTextField);
	}
	
	private void initializePasswordLabelAndTextField() {	
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(LABEL_FONT);
		passwordLabel.setBounds(65, 124, 73, 23);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(65, 153, 250, 23);
		this.contentPane.add(passwordLabel);
		this.contentPane.add(passwordField);
	}
	
	private void initializeLoginButton() {
		loginButton = new JButton("Login");
		loginButton.setFont(BUTTON_FONT);
		loginButton.setBounds(65, 188, 89, 23);
		this.contentPane.add(loginButton);	
		
		
	}

	public JTextField getUserTextField() {
		return userTextField;
	}

	public JPasswordField getPasswordField() {
		return passwordField;
	}

	public JButton getLoginButton() {
		return loginButton;
	}

	public static void main(String[]args) {
		EventQueue.invokeLater(new Runnable(){
			public void run() {
				try {
					Login window = new Login();
					window.contentPane.setVisible(true);
				}catch(Exception e) {
					e.printStackTrace();
				}}
		});
	}
	public void actionPerformed(ActionEvent e) {
		String user=userTextField.getText();
		String password=passwordField.getText();
		System.out.println(user+", "+password);
		if(user.equals("admin")&&password.equals("thu123")) {
			
		passwordField.setText(null);	
		userTextField.setText(null);
		//PatientenView info= new PatientenView(password);
		//PatientenView.main(null);
		UserController userCo = new UserController();
    	
        userCo.setPatientenView();
        
	}else {
		JOptionPane.showMessageDialog(null, "invalid login details", "login error", JOptionPane.ERROR_MESSAGE);
		passwordField.setText(null);
		userTextField.setText(null);
		
		return ;
		
	}
		
		}
				
}
	
			
