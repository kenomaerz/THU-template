package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import view.Login;

public class Controller {
	
	private UserController userCo;
	private Login login;
//	Model model;
//	
//	public Controller(Model model){
//		this.model = model;
//	}
//	
//
	public Controller() {
		
	}
	public void start() {
		this.setLogin();
	}

	public UserController getUserController() {
		return userCo;
	}
//
//	
	protected void setLogin() {
		login = new Login();
		login.setVisible(true);
		this.setLoginViewActionListener(login);
	}
	public Login getLogin() {
		return login;
	}
	
	protected void setLoginViewActionListener(Login login) {
		JButton loginButton = login.getLoginButton();
		login.getRootPane().setDefaultButton(loginButton);
		loginButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
//                String userName = login.getUserTextField().getText();
//                String password = String.valueOf(login.getPasswordField().getPassword());
				
				String user=login.getUserTextField().getText();
				char[] password=login.getPasswordField().getPassword();
				String sPassword = new String(password);
				System.out.println(user+", "+sPassword);
				if((user.equals("admin") && sPassword.equals("thu123"))) {
					
				login.getPasswordField().setText(null);	
				login.getUserTextField().setText(null);
				login.setVisible(false);
//				PatientenView.main(null);
				userCo = new UserController(user);
		    	
//		        userCo.setPatientenView(user);
		        
			}else {
				JOptionPane.showMessageDialog(null, "invalid login/password", "login error", JOptionPane.ERROR_MESSAGE);
				login.getPasswordField().setText(null);	
				login.getUserTextField().setText(null);
				
			}
				
				
				//UserController userCo = new UserController();

				// userCo.setPatientenView();

				// TODO: Login

//                try {
//                	model.logOn(userName, password);
//                    login.dispose();
//                    
////                    if(model.isAdmin())
////                    {
////						new AdminController(model);
////                    	model.setAdmin(false);
////                    }else if(model.isManager()); 
//                }
//                catch (NoEntryException noentry) {
//                	JOptionPane.showMessageDialog(loginButton, "You must enter a Username and a password");
//                	noentry.printStackTrace();
//                } 
//                catch(NullPointerException nex) {
//                	JOptionPane.showMessageDialog(loginButton, "Wrong Username");
//                	nex.printStackTrace();
//                }
//                catch(InvalidUserException userError)  {
//                    JOptionPane.showMessageDialog(loginButton, "Wrong Username");
//                    userError.printStackTrace();
//                }
//                catch(InvalidPasswordException passwordError)  {
//                    JOptionPane.showMessageDialog(loginButton, "Wrong Password");
//                    passwordError.printStackTrace();
//                }
//                catch(Exception error)  {
//                    JOptionPane.showMessageDialog(loginButton, "Error, something went wrong!");
//                    error.printStackTrace();
//                }
//                

			}
		});
	}

}
