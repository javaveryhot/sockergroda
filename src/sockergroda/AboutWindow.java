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

	private JFrame frmSockergrodaAbout;

	/**
	 * Launch the application.
	 */
	public static void display() {
		try {
			AboutWindow window = new AboutWindow();
			window.frmSockergrodaAbout.setVisible(true);
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
		frmSockergrodaAbout = new SGFrame();
		frmSockergrodaAbout.setResizable(false);
		frmSockergrodaAbout.setTitle("Sockergroda - About");
		frmSockergrodaAbout.setBounds(100, 100, 380, 210);
		frmSockergrodaAbout.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frmSockergrodaAbout.setIconImage(Images.ICON_32x32.getImage());
	    frmSockergrodaAbout.setLocationRelativeTo(null);
	    frmSockergrodaAbout.getContentPane().setLayout(null);
	    
	    JButton btnBack = new JButton("Back");
	    btnBack.setBounds(10, 136, 89, 23);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSockergrodaAbout.dispose();
				MainWindow.display();
			}
		});
	    frmSockergrodaAbout.getContentPane().add(btnBack);
	    
	    JSeparator separator_2 = new JSeparator();
	    separator_2.setBounds(10, 69, 355, 6);
	    frmSockergrodaAbout.getContentPane().add(separator_2);
	    
	    JLabel lblVersion = new JLabel(Main.versionName);
	    lblVersion.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
	    lblVersion.setBounds(245, 38, 85, 14);
	    frmSockergrodaAbout.getContentPane().add(lblVersion);
	    
	    JLabel lblTitle = new JLabel("Sockergroda");
	    lblTitle.setBounds(10, 11, 237, 47);
	    lblTitle.setIcon(new ImageIcon(Images.ICON_32x32.getImage()));
	    lblTitle.setFont(new Font("Segoe UI Historic", Font.PLAIN, 35));
	    frmSockergrodaAbout.getContentPane().add(lblTitle);
	    
	    JLabel lblInfo1 = new JLabel("Version " + Main.version + ". Created by crunchyfrog in 2021.");
	    lblInfo1.setBounds(20, 86, 310, 14);
	    frmSockergrodaAbout.getContentPane().add(lblInfo1);
	    
	    JLabel lblInfo2 = new JLabel("No rights reserved. Iconset: RR (icons16x16.com/SET-RR)");
	    lblInfo2.setBounds(20, 111, 310, 14);
	    frmSockergrodaAbout.getContentPane().add(lblInfo2);
	    
	    JButton btnWebsite = new JButton("Website");
	    btnWebsite.setBounds(245, 136, 105, 23);
	    btnWebsite.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.openURL("https://sockergroda.repl.co");
			}
		});
	    frmSockergrodaAbout.getContentPane().add(btnWebsite);
	    
	    JButton btnLicense = new JButton("License");
	    btnLicense.setBounds(130, 136, 89, 23);
	    btnLicense.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frmSockergrodaAbout.dispose();
				License.display();
			}
		});
	    frmSockergrodaAbout.getContentPane().add(btnLicense);
	}
}
