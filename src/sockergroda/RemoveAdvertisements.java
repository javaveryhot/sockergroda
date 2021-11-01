package sockergroda;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import sockergroda.enums.Images;

public class RemoveAdvertisements {

	private JFrame frmSockergrodaRemoveAds;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void display() {
		try {
			RemoveAdvertisements window = new RemoveAdvertisements();
			window.frmSockergrodaRemoveAds.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public RemoveAdvertisements() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSockergrodaRemoveAds = new SGFrame();
		frmSockergrodaRemoveAds.setResizable(false);
		frmSockergrodaRemoveAds.setTitle("Remove Advertisements");
		frmSockergrodaRemoveAds.setBounds(100, 100, 385, 140);
		frmSockergrodaRemoveAds.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frmSockergrodaRemoveAds.setIconImage(Images.ICON_32x32.getImage());
	    frmSockergrodaRemoveAds.setLocationRelativeTo(null);
	    frmSockergrodaRemoveAds.getContentPane().setLayout(null);
	    
	    JButton btnBack = new JButton("Close");
	    btnBack.setBounds(270, 68, 89, 23);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSockergrodaRemoveAds.dispose();
			}
		});
	    frmSockergrodaRemoveAds.getContentPane().add(btnBack);
	    
	    textField = new JTextField();
	    textField.setBounds(10, 37, 86, 20);
	    frmSockergrodaRemoveAds.getContentPane().add(textField);
	    textField.setColumns(10);
	    
	    JLabel lblText = new JLabel("Key:");
	    lblText.setBounds(10, 12, 362, 14);
	    frmSockergrodaRemoveAds.getContentPane().add(lblText);
	    
	    JButton btnRemoveAds = new JButton("Remove Ads");
	    btnRemoveAds.setBounds(10, 68, 110, 23);
	    btnRemoveAds.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if(Main.hasRemovedAds()) {
	    			JOptionPane.showMessageDialog(frmSockergrodaRemoveAds, "Advertisements have already been removed.", "Cannot Remove Advertisements", JOptionPane.ERROR_MESSAGE);
	    			return;
	    		}
	    		
	    		String removeAdsKey = textField.getText();
	    		int raKey = 0;
	    		try {
	    			raKey = Integer.parseInt(removeAdsKey);
	    		} catch(NumberFormatException e1) {}
	    		
				if(APIManager.testRAKey(raKey)) {
					Main.removeAds();
					JOptionPane.showMessageDialog(frmSockergrodaRemoveAds, "Success! Advertisements have been removed.", "Advertisements Removed", JOptionPane.INFORMATION_MESSAGE);
					frmSockergrodaRemoveAds.dispose();
				} else {
					JOptionPane.showMessageDialog(frmSockergrodaRemoveAds, "An invalid RA key was provided. Make sure that you wrote it correctly.", "Invalid Key", JOptionPane.ERROR_MESSAGE);
				}
	    	}
	    });
	    frmSockergrodaRemoveAds.getContentPane().add(btnRemoveAds);
	}
}
