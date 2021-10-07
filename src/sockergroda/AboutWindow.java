package sockergroda;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;

import sockergroda.enums.Images;

public class AboutWindow {

	private JFrame frmSockergrodaUser;

	/**
	 * Launch the application.
	 */
	public static void display() {
		try {
			AboutWindow window = new AboutWindow();
			window.frmSockergrodaUser.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public AboutWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSockergrodaUser = new JFrame();
		frmSockergrodaUser.setResizable(false);
		frmSockergrodaUser.setTitle("Sockergroda - About");
		frmSockergrodaUser.setBounds(100, 100, 380, 210);
		frmSockergrodaUser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frmSockergrodaUser.setIconImage(Images.ICON_1024x1024.getImage());
	    frmSockergrodaUser.setLocationRelativeTo(null);
	    frmSockergrodaUser.getContentPane().setLayout(null);
	    
	    JButton btnBack = new JButton("Back");
	    btnBack.setBounds(10, 136, 89, 23);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSockergrodaUser.setVisible(false);
				MainWindow.display();
			}
		});
	    frmSockergrodaUser.getContentPane().add(btnBack);
	    
	    JSeparator separator_2 = new JSeparator();
	    separator_2.setBounds(10, 69, 355, 6);
	    frmSockergrodaUser.getContentPane().add(separator_2);
	    
	    JLabel lblVersion = new JLabel(Main.versionName);
	    lblVersion.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
	    lblVersion.setBounds(245, 38, 85, 14);
	    frmSockergrodaUser.getContentPane().add(lblVersion);
	    
	    JLabel lblTitle = new JLabel("Sockergroda");
	    lblTitle.setBounds(10, 11, 237, 47);
	    lblTitle.setIcon(new ImageIcon(Images.ICON_32x32.getImage()));
	    lblTitle.setFont(new Font("Segoe UI Historic", Font.PLAIN, 35));
	    frmSockergrodaUser.getContentPane().add(lblTitle);
	    
	    JLabel lblInfo1 = new JLabel("Version " + Main.version + ". Created by crunchyfrog in 2021.");
	    lblInfo1.setBounds(20, 86, 310, 14);
	    frmSockergrodaUser.getContentPane().add(lblInfo1);
	    
	    JLabel lblInfo2 = new JLabel("No rights reserved. Iconset: RR (icons16x16.com/SET-RR)");
	    lblInfo2.setBounds(20, 111, 310, 14);
	    frmSockergrodaUser.getContentPane().add(lblInfo2);
	    
	    JButton btnContact = new JButton("Contact me on Discord");
	    btnContact.setBounds(200, 136, 150, 23);
	    btnContact.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.openURL("https://discord.com/users/482459199213928459");
			}
		});
	    frmSockergrodaUser.getContentPane().add(btnContact);
	    
	    JButton btnLicense = new JButton("License");
	    btnLicense.setBounds(105, 136, 89, 23);
	    btnLicense.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frmSockergrodaUser.setVisible(false);
				License.display();
			}
		});
	    frmSockergrodaUser.getContentPane().add(btnLicense);
	}
}
