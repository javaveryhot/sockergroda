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
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import java.awt.SystemColor;

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
		frmSockergrodaAbout.setTitle("About");
		frmSockergrodaAbout.setBounds(100, 100, 380, 210);
		frmSockergrodaAbout.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frmSockergrodaAbout.setIconImage(Images.ICON_32x32.getImage());
	    frmSockergrodaAbout.setLocationRelativeTo(null);
	    frmSockergrodaAbout.getContentPane().setLayout(null);
	    
	    JButton btnBack = new JButton("Close");
	    btnBack.setBounds(10, 136, 89, 23);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSockergrodaAbout.dispose();
			}
		});
	    frmSockergrodaAbout.getContentPane().add(btnBack);
	    
	    JSeparator separator_2 = new JSeparator();
	    separator_2.setBounds(10, 69, 355, 6);
	    frmSockergrodaAbout.getContentPane().add(separator_2);
	    
	    JLabel lblTitle = new JLabel(Main.versionName);
	    lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
	    lblTitle.setBounds(10, 5, 355, 64);
	    lblTitle.setIcon(new ImageIcon(Images.ICON_64x64.getImage()));
	    lblTitle.setFont(new Font("Lucida Bright", Font.PLAIN, 40));
	    frmSockergrodaAbout.getContentPane().add(lblTitle);
	    
	    JButton btnWebsite = new JButton("Website");
	    btnWebsite.setBounds(245, 136, 105, 23);
	    btnWebsite.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.openURL(Main.websiteUrl);
			}
		});
	    frmSockergrodaAbout.getContentPane().add(btnWebsite);
	    
	    JButton btnLicense = new JButton("License");
	    btnLicense.setBounds(130, 136, 89, 23);
	    btnLicense.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LicenseWindow.display();
			}
		});
	    frmSockergrodaAbout.getContentPane().add(btnLicense);
	    
	    JTextPane txtpnVersioninsertVersion = new JTextPane();
	    txtpnVersioninsertVersion.setEditable(false);
	    txtpnVersioninsertVersion.setBackground(SystemColor.menu);
	    txtpnVersioninsertVersion.setText("Version " + Main.version + ". Created by crunchyfrog in Jul-Nov.\r\nCopyright Sockergroda 2021. No rights reserved.\r\nIconset: RR (https://icons16x16.com/SET-RR)");
	    txtpnVersioninsertVersion.setBounds(20, 80, 330, 45);
	    frmSockergrodaAbout.getContentPane().add(txtpnVersioninsertVersion);
	}
}
