package sockergroda;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import sockergroda.enums.Images;
import utils.VersionConverter;

import javax.swing.JSeparator;

public class UpdateWindow {

	private JFrame frmSockergrodaUpdate;

	/**
	 * Launch the application.
	 */
	public static void display(int thisVersion, int latestVersion) {
		try {
			UpdateWindow window = new UpdateWindow(thisVersion, latestVersion);
			window.frmSockergrodaUpdate.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public UpdateWindow(int thisVersion, int latestVersion) {
		initialize(thisVersion, latestVersion);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(int thisVersion, int latestVersion) {
		frmSockergrodaUpdate = new SGFrame();
		frmSockergrodaUpdate.setResizable(false);
		frmSockergrodaUpdate.setTitle("Update");
		frmSockergrodaUpdate.setBounds(100, 100, 500, 250);
		frmSockergrodaUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frmSockergrodaUpdate.setIconImage(Images.ICON_32x32.getImage());
	    frmSockergrodaUpdate.setLocationRelativeTo(null);
	    frmSockergrodaUpdate.getContentPane().setLayout(null);
	    
	    JLabel lblTitle = new JLabel("Sockergroda Update");
	    lblTitle.setBounds(10, 0, 445, 47);
	    lblTitle.setIcon(new ImageIcon(Images.ICON_32x32.getImage()));
	    lblTitle.setFont(new Font("Segoe UI Historic", Font.PLAIN, 35));
	    frmSockergrodaUpdate.getContentPane().add(lblTitle);
	    
	    JButton btnClose = new JButton("Close");
	    btnClose.setBounds(10, 177, 89, 23);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSockergrodaUpdate.dispose();
			}
		});
	    frmSockergrodaUpdate.getContentPane().add(btnClose);
	    
	    JButton btnUpdate = new JButton("Update");
	    btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frmSockergrodaUpdate.dispose();
				PerformUpdateWindow.display();
			}
		});
	    btnUpdate.setBounds(360, 177, 110, 23);
	    frmSockergrodaUpdate.getContentPane().add(btnUpdate);
	    
	    JLabel lblStatus = new JLabel();
	    lblStatus.setFont(new Font("Tahoma", Font.BOLD, 14));
	    lblStatus.setBounds(20, 58, 435, 23);
	    frmSockergrodaUpdate.getContentPane().add(lblStatus);
	    
	    JLabel lblDescription = new JLabel();
	    lblDescription.setBounds(30, 92, 425, 14);
	    frmSockergrodaUpdate.getContentPane().add(lblDescription);
	    
	    JCheckBox chckbxAutomaticUpdateCheck = new JCheckBox("Automatic update checks");
	    chckbxAutomaticUpdateCheck.setToolTipText("Occasionally looks for a newer version and prompts you if one exists");
	    chckbxAutomaticUpdateCheck.setSelected(StorageManager.getBoolean("automatic_update_check"));
	    chckbxAutomaticUpdateCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StorageManager.setAttribute("automatic_update_check", chckbxAutomaticUpdateCheck.isSelected());
			}
		});
	    chckbxAutomaticUpdateCheck.setBounds(105, 177, 200, 23);
	    frmSockergrodaUpdate.getContentPane().add(chckbxAutomaticUpdateCheck);
	    
	    JSeparator separator = new JSeparator();
	    separator.setBounds(10, 48, 445, 14);
	    frmSockergrodaUpdate.getContentPane().add(separator);
	    
	    
	    if(latestVersion > thisVersion) {
	    	lblStatus.setText("A new version is available");
	    	lblStatus.setIcon(new ImageIcon(Images.BELL_16x16.getImage()));
	    	lblDescription.setText("Update to version " + VersionConverter.intToString(latestVersion, false) + " of Sockergroda for better stability and functionality.");
	    	btnUpdate.requestFocus(true);
	    } else {
	    	lblStatus.setText("No update available");
	    	lblDescription.setText("You are running the latest version!");
	    	btnUpdate.setEnabled(false);
	    }
	}
}
