package controller;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import view.PatientenView;
import view.UserView;

public class UserController extends Controller {

	public UserController() {
		setUserView();
		setPatientenView();
	}

	public void setUserView() {
		UserView userView = new UserView("UK DSM | Abteilung: KIM");
		userView.setVisible(true);
		setUserViewActionListener(userView);
	}

	public void setPatientenView() {
		PatientenView patientenView = new PatientenView("Patient");
		patientenView.setVisible(true);
		setPatientenViewActionListener(patientenView);

	}


	private void setPatientenViewActionListener(PatientenView patientenView) {
		JButton BackButton = patientenView.getBackButton();

		BackButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				patientenView.dispose();
				setUserView();
			}
			
		});
		
	}
		
		private void setUserViewActionListener(UserView userView) {
			JButton PatientenakteButton = userView.getPatientenakteButton();

			PatientenakteButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					userView.dispose();
					setPatientenView();
					
				}
		});
			
			JButton LogoutButton = userView.getLogoutButton();

			LogoutButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					userView.dispose();
					setLogin();
					
				}
			});
			
			
			
		}
}