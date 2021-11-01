package sockergroda;

import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import sockergroda.enums.Images;
import utils.APIResponse;
import utils.TimeStrings;

public class DisplaySecretWindow {

	private JFrame frmSockergrodaInspecting;
	private JCheckBox chckbxMaskMode;

	/**
	 * Launch the application.
	 */
	public static void display(int secretId, String password, APIResponse response) {
		try {
			DisplaySecretWindow window = new DisplaySecretWindow(secretId, password, response);
			window.frmSockergrodaInspecting.setVisible(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public DisplaySecretWindow(int secretId, String password, APIResponse response) {
		initialize(secretId, password, response);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(int secretId, String password, APIResponse response) {
		frmSockergrodaInspecting = new SGFrame();
		frmSockergrodaInspecting.setTitle("Inspecting Secret");
		frmSockergrodaInspecting.setResizable(false);
		frmSockergrodaInspecting.setIconImage(Images.ICON_32x32.getImage());
		frmSockergrodaInspecting.setBounds(100, 100, 450, 340);
		frmSockergrodaInspecting.setLocationRelativeTo(null);
		frmSockergrodaInspecting.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmSockergrodaInspecting.getContentPane().setLayout(null);
		
		String freeText = response.getStringValue("freetext");
		boolean isSecretExpiredFromUsage = response.getIntValue("inspections") == response.getIntValue("max_uses");
		
		TextArea textArea = new TextArea();
		textArea.setFont(new Font("Monospaced", Font.BOLD, 12));
		textArea.setEditable(false);
		textArea.setBounds(10, 75, 380, 160);
		frmSockergrodaInspecting.getContentPane().add(textArea);
		
		JButton btnClose = new JButton("Close");
		btnClose.setMnemonic('C');
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSockergrodaInspecting.dispose();
			}
		});
		btnClose.setBounds(342, 265, 70, 23);
		frmSockergrodaInspecting.getContentPane().add(btnClose);

		JLabel lblCreatedAt = new JLabel(TimeStrings.getTimeString(System.currentTimeMillis() / 1000 - response.getLongValue("created_at")) + " ago");
		lblCreatedAt.setBounds(10, 241, 134, 14);
		frmSockergrodaInspecting.getContentPane().add(lblCreatedAt);
		
		JLabel lblTitle = new JLabel(response.getStringValue("title"));
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitle.setIcon(new ImageIcon(Images.EYE_16x16.getImage()));
		lblTitle.setBounds(10, 11, 380, 23);
		frmSockergrodaInspecting.getContentPane().add(lblTitle);
		
		ChangeListener maskChange = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				textArea.setText(!chckbxMaskMode.isSelected() ? freeText : freeText.replaceAll(".", "*"));
				StorageManager.setAttribute("mask_inspection", chckbxMaskMode.isSelected());
			}
		};
		
		chckbxMaskMode = new JCheckBox("Mask text");
		chckbxMaskMode.setMnemonic('M');
		chckbxMaskMode.addChangeListener(maskChange);
		chckbxMaskMode.setBounds(10, 41, 134, 23);
		frmSockergrodaInspecting.getContentPane().add(chckbxMaskMode);
		
		chckbxMaskMode.setSelected(StorageManager.getBoolean("mask_inspection"));
		
		JButton btnCopy = new JButton("Copy content");
		btnCopy.setIcon(new ImageIcon(Images.COPY_16x16.getImage()));
		btnCopy.setBounds(262, 45, 128, 23);
		btnCopy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.copyToClipboard(freeText);
			}
		});
		frmSockergrodaInspecting.getContentPane().add(btnCopy);
		
		JButton btnReport = new JButton("Report malicious");
		btnReport.setEnabled(!isSecretExpiredFromUsage);
		btnReport.setToolTipText("Report abusive content");
		btnReport.setIcon(new ImageIcon(Images.MALICIOUS_16x16.getImage()));
		btnReport.setBounds(10, 265, 160, 23);
		btnReport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(frmSockergrodaInspecting, "The report will be made anonymously.\nDo you want to report the content of this secret?", "Report Content?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				
				if(confirm == 0) {
					int result = 0;
					
					APIResponse response = APIManager.reportSecret(secretId, password);
					
					if(response.isRequestSuccessful()) {
						result = response.getCode();
					}
					
					switch(result) {
					case 0:
						JOptionPane.showMessageDialog(frmSockergrodaInspecting, "Something went wrong while trying to report the content. It may have disappeared.", "Error", JOptionPane.ERROR_MESSAGE);
						break;
					case 1:
						JOptionPane.showMessageDialog(frmSockergrodaInspecting, "This should not happen.\nSomehow the password is incorrect.", "Bad Password", JOptionPane.ERROR_MESSAGE);
						break;
					case 2:
						JOptionPane.showMessageDialog(frmSockergrodaInspecting, "This content has already been reported!", "Already Reported", JOptionPane.ERROR_MESSAGE);
						break;
					case 3:
						JOptionPane.showMessageDialog(frmSockergrodaInspecting, "The content has been reported.\nYou will not get a response from this.", "Content Reported", JOptionPane.INFORMATION_MESSAGE);
						break;
					}
				}
			}
		});
		frmSockergrodaInspecting.getContentPane().add(btnReport);
		
		JLabel lblExpiration = new JLabel();
		lblExpiration.setHorizontalAlignment(SwingConstants.RIGHT);
		switch(response.getIntValue("expire_type")) {
		case 0:
			lblExpiration.setText("Expires in " + TimeStrings.getTimeString(response.getLongValue("expire_date") - (System.currentTimeMillis() / 1000)));
			break;
		case 1:
			lblExpiration.setText(response.getIntValue("inspections") + "/" + response.getIntValue("max_uses") + " uses");
			break;
		}
		lblExpiration.setBounds(230, 241, 160, 14);
		frmSockergrodaInspecting.getContentPane().add(lblExpiration);

		maskChange.stateChanged(null);
		
		chckbxMaskMode.grabFocus();
	}
}
