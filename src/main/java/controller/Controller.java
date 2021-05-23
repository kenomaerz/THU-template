package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import view.Login;

public class Controller{
	
//	Model model;
//	
//	public Controller(Model model){
//		this.model = model;
//	}
//	
//
	public void start() {
		this.setLogin();
	}
//	
//
//	
	protected void setLogin(){
		Login login = new Login();
		login.setVisible(true);
		this.setLoginViewActionListener(login);
	}

	protected void setLoginViewActionListener(Login login) {
		JButton loginButton = login.getLoginButton(); 
		login.getRootPane().setDefaultButton(loginButton);
		loginButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)  {
//                String userName = login.getUserTextField().getText();
//                String password = String.valueOf(login.getPasswordField().getPassword());
               
            	UserController userCo = new UserController();
            	
                userCo.setPatientenView();
                
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
		
	
			
	
