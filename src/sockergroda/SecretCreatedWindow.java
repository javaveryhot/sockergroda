package sockergroda;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
		frmSockergrodaSecret.setBounds(100, 100, 500, 150);
		frmSockergrodaSecret.setLocationRelativeTo(null);
		frmSockergrodaSecret.setIconImage(Images.ICON_32x32.getImage());
		frmSockergrodaSecret.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmSockergrodaSecret.getContentPane().setLayout(null);
		
		JLabel lblSecretCreated = new JLabel("Secret ID:");
		lblSecretCreated.setBounds(25, 11, 212, 14);
		frmSockergrodaSecret.getContentPane().add(lblSecretCreated);
		
		txtSecretId = new JTextField();
		txtSecretId.setText("<sg?" + secretId + ">");
		txtSecretId.setBounds(25, 28, 125, 20);
		txtSecretId.setEditable(false);
		frmSockergrodaSecret.getContentPane().add(txtSecretId);
		txtSecretId.setColumns(10);
		
		JButton btnCopy = new JButton("Copy");
		btnCopy.setBounds(160, 27, 77, 23);
		btnCopy.setIcon(new ImageIcon(Images.COPY_16x16.getImage()));
		btnCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.copyToClipboard(txtSecretId.getText());
			}
		});
		frmSockergrodaSecret.getContentPane().add(btnCopy);
		
		txtSecretId.grabFocus();
		txtSecretId.setSelectionStart(0);
		txtSecretId.setSelectionEnd(txtSecretId.getText().length());
		
		if(password != null) {
			JLabel passwordLabel = new JLabel("Password:");
			passwordLabel.setBounds(25, 59, 95, 14);
			frmSockergrodaSecret.getContentPane().add(passwordLabel);
			
			JPasswordField passwordField = new JPasswordField();
			passwordField.setText(password);
			passwordField.setEditable(false);
			passwordField.setColumns(10);
			passwordField.setBounds(25, 76, 125, 20);
			frmSockergrodaSecret.getContentPane().add(passwordField);
			
			JCheckBox toggleShowPasswordCheckBox = new JCheckBox("Show");
			toggleShowPasswordCheckBox.setBounds(80, 59, 105, 14);
			ChangeListener togglePasswordShow = new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					passwordField.setEchoChar(toggleShowPasswordCheckBox.isSelected() ? (char)0 : (char)UIManager.get("PasswordField.echoChar"));
				}
			};
			toggleShowPasswordCheckBox.addChangeListener(togglePasswordShow);
			togglePasswordShow.stateChanged(null);
			frmSockergrodaSecret.getContentPane().add(toggleShowPasswordCheckBox);
			
			JButton btnCopyPassword = new JButton("Copy Password");
			btnCopyPassword.setBounds(160, 76, 141, 23);
			btnCopyPassword.setIcon(new ImageIcon(Images.COPY_16x16.getImage()));
			btnCopyPassword.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Main.copyToClipboard(new String(passwordField.getPassword()));
				}
			});
			frmSockergrodaSecret.getContentPane().add(btnCopyPassword);
		}
		
		JButton btnClose = new JButton("Close");
		btnClose.setMnemonic('C');
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSockergrodaSecret.dispose();
			}
		});
		btnClose.setBounds(397, 76, 70, 23);
		frmSockergrodaSecret.getContentPane().add(btnClose);
		
		JLabel lblSaved = new JLabel();
		if(StorageManager.getBoolean("save_secrets")) {
			lblSaved.setText("Saved to your secrets");
			lblSaved.setIcon(new ImageIcon(Images.CORRECT_16x16.getImage()));
		}
		lblSaved.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSaved.setBounds(317, 45, 150, 14);
		frmSockergrodaSecret.getContentPane().add(lblSaved);
	}
}
