package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ViewStyle extends JFrame {
	
	private static final long serialVersionUID = 1L;
	protected JPanel contentPane;
	
	protected final Font TITLE_FONT = new Font("Tahoma", Font.PLAIN, 24);
	protected final Font LABEL_FONT = new Font("Tahoma", Font.PLAIN, 16);
	protected final Font BUTTON_FONT = new Font("Tahoma", Font.PLAIN, 14);
	protected final Font BIG_BUTTON_FONT = new Font("Tahoma", Font.PLAIN, 18);
	
	
	public ViewStyle() {
		this.initializeFrame();
		this.initializeContentPane();
	}
	
	private void initializeFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 400, 300);
        setResizable(false);
        setLocationRelativeTo(null);  
		this.contentPane = new JPanel();
		setContentPane(contentPane);
	}
	
	private void initializeContentPane() {
		this.contentPane.setLayout(null);
		this.contentPane.setBackground(Color.WHITE);
	}
}
