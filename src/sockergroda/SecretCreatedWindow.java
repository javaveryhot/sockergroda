package sockergroda;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import sockergroda.enums.Images;
import java.awt.Font;

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
		frmSockergrodaSecret.setTitle("Sockergroda - Secret Created");
		frmSockergrodaSecret.setResizable(false);
		frmSockergrodaSecret.setBounds(100, 100, 500, 160);
		frmSockergrodaSecret.setLocationRelativeTo(null);
		frmSockergrodaSecret.setIconImage(Images.ICON_32x32.getImage());
		frmSockergrodaSecret.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		btnCopy.setIcon(new ImageIcon(Images.SCISSORS_16x16.getImage()));
		btnCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StringSelection copyString = new StringSelection(txtSecretId.getText());
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(copyString, copyString);
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
				System.exit(1);
			}
		});
		btnClose.setBounds(397, 86, 70, 23);
		frmSockergrodaSecret.getContentPane().add(btnClose);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSockergrodaSecret.dispose();
				MainWindow.display();
			}
		});
		btnBack.setBounds(317, 86, 70, 23);
		frmSockergrodaSecret.getContentPane().add(btnBack);
		
		JLabel lblSaved = new JLabel(StorageManager.getBoolean("save_secrets") ? "Saved to your secrets" : "");
		lblSaved.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSaved.setBounds(317, 61, 150, 14);
		frmSockergrodaSecret.getContentPane().add(lblSaved);
	}
}
