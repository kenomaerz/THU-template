package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import view.Login;

public class Controller {

private UserController userCo;
private Login login;
// Model model;
//
// public Controller(Model model){
// this.model = model;
// }
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


String user=login.getUserTextField().getText();
char[] password=login.getPasswordField().getPassword();
String sPassword = new String(password);
System.out.println(user+", "+sPassword);
if((user.equals("admin") && sPassword.equals("thu123"))) {

login.getPasswordField().setText(null);
login.getUserTextField().setText(null);
login.setVisible(false);

userCo = new UserController(user);



}else {
JOptionPane.showMessageDialog(null, "invalid login/password", "login error", JOptionPane.ERROR_MESSAGE);
login.getPasswordField().setText(null);
login.getUserTextField().setText(null);

}



}
});
}

}