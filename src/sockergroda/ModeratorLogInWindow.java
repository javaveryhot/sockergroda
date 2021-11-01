package sockergroda;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import sockergroda.enums.Images;
import utils.APIResponse;

public class ModeratorLogInWindow {

	private JFrame frmSockergrodaModLogIn;
	private JPasswordField pwdKey;

	/**
	 * Launch the application.
	 */
	public static void display() {
		try {
			ModeratorLogInWindow window = new ModeratorLogInWindow();
			window.frmSockergrodaModLogIn.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public ModeratorLogInWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSockergrodaModLogIn = new SGFrame();
		frmSockergrodaModLogIn.setResizable(false);
		frmSockergrodaModLogIn.setTitle("Moderator Dashboard Log In");
		frmSockergrodaModLogIn.setBounds(100, 100, 385, 140);
		frmSockergrodaModLogIn.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frmSockergrodaModLogIn.setIconImage(Images.ICON_32x32.getImage());
	    frmSockergrodaModLogIn.setLocationRelativeTo(null);
	    frmSockergrodaModLogIn.getContentPane().setLayout(null);
	    
	    JButton btnBack = new JButton("Close");
	    btnBack.setBounds(270, 68, 89, 23);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSockergrodaModLogIn.dispose();
			}
		});
	    frmSockergrodaModLogIn.getContentPane().add(btnBack);
	    
	    JLabel lblText = new JLabel("Moderator key:");
	    lblText.setBounds(10, 12, 362, 14);
	    frmSockergrodaModLogIn.getContentPane().add(lblText);
	    
	    JButton btnContinue = new JButton("Log in");
	    btnContinue.setBounds(10, 68, 89, 23);
	    btnContinue.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		String modKey = new String(pwdKey.getPassword());
	    		
				APIResponse response = APIManager.moderatorLogin(modKey);
	    		
	    		if(response.getCode() == 1) {
	    			ModeratorDashboardWindow.display(modKey);
	    			frmSockergrodaModLogIn.dispose();
	    		} else {
	    			JOptionPane.showMessageDialog(frmSockergrodaModLogIn, "The key you provided was incorrect.", "Bad Moderator Key", JOptionPane.ERROR_MESSAGE);
	    		}
	    	}
	    });
	    frmSockergrodaModLogIn.getContentPane().add(btnContinue);
	    
	    pwdKey = new JPasswordField();
	    pwdKey.setBounds(10, 37, 118, 20);
	    frmSockergrodaModLogIn.getContentPane().add(pwdKey);
	}
}
