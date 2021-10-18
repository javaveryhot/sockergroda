package sockergroda;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import sockergroda.enums.Images;

public class SecretCreatedWindow {

	private JFrame frmSockergrodaSecret;
	private JTextField txtSecretId;

	/**
	 * Launch the application.
	 */
	public static void display(int secretId, String password, long expiration, int expirationType) {
		try {
			SecretCreatedWindow window = new SecretCreatedWindow(secretId, password, expiration, expirationType);
			window.frmSockergrodaSecret.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public SecretCreatedWindow(int secretId, String password, long expiration, int expirationType) {
		initialize(secretId, password, expiration, expirationType);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(int secretId, String password, long expiration, int expirationType) {
		frmSockergrodaSecret = new SGFrame();
		frmSockergrodaSecret.setTitle("Secret Created");
		frmSockergrodaSecret.setResizable(false);
		frmSockergrodaSecret.setBounds(100, 100, 500, 160);
		frmSockergrodaSecret.setLocationRelativeTo(null);
		frmSockergrodaSecret.setIconImage(Images.ICON_32x32.getImage());
		frmSockergrodaSecret.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmSockergrodaSecret.getContentPane().setLayout(null);
		
		JLabel lblSecretCreated = new JLabel("Your secret has been created:");
		lblSecretCreated.setBounds(25, 24, 212, 14);
		frmSockergrodaSecret.getContentPane().add(lblSecretCreated);
		
		txtSecretId = new JTextField();
		txtSecretId.setText("<sg?" + secretId + ">");
		txtSecretId.setBounds(25, 41, 125, 20);
		txtSecretId.setEditable(false);
		frmSockergrodaSecret.getContentPane().add(txtSecretId);
		txtSecretId.setColumns(10);
		
		JButton btnCopy = new JButton("Copy");
		btnCopy.setIcon(new ImageIcon(Images.COPY_16x16.getImage()));
		btnCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.copyToClipboard(txtSecretId.getText());
			}
		});
		btnCopy.setBounds(160, 40, 77, 23);
		frmSockergrodaSecret.getContentPane().add(btnCopy);
		
		txtSecretId.grabFocus();
		txtSecretId.setSelectionStart(0);
		txtSecretId.setSelectionEnd(txtSecretId.getText().length());
		
		if(password != null) {
			JLabel passwordLabel = new JLabel("Password:");
			passwordLabel.setBounds(25, 75, 95, 14);
			frmSockergrodaSecret.getContentPane().add(passwordLabel);
			
			JTextField textField = new JTextField();
			textField.setText(password);
			textField.setEditable(false);
			textField.setColumns(10);
			textField.setBounds(25, 89, 125, 20);
			frmSockergrodaSecret.getContentPane().add(textField);
			
			JCheckBox toggleShowPasswordCheckBox = new JCheckBox("Show password");
			toggleShowPasswordCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					textField.setText(toggleShowPasswordCheckBox.isSelected() ? password : password.replaceAll(".", "*"));
				}
			});
			toggleShowPasswordCheckBox.doClick();
			toggleShowPasswordCheckBox.doClick();
			toggleShowPasswordCheckBox.setBounds(160, 88, 100, 23);
			frmSockergrodaSecret.getContentPane().add(toggleShowPasswordCheckBox);
		}
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSockergrodaSecret.dispose();
			}
		});
		btnClose.setBounds(397, 86, 70, 23);
		frmSockergrodaSecret.getContentPane().add(btnClose);
		
		JLabel lblSaved = new JLabel();
		if(StorageManager.getBoolean("save_secrets")) {
			lblSaved.setText("Saved to your secrets");
			lblSaved.setIcon(new ImageIcon(Images.CORRECT_16x16.getImage()));
		}
		lblSaved.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSaved.setBounds(317, 65, 150, 14);
		frmSockergrodaSecret.getContentPane().add(lblSaved);
	}
}
