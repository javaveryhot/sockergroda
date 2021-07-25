package sockergroda;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import sockergroda.enums.Images;

public class SecretCreatedWindow {

	private JFrame frmSockergrodaSecret;
	private JTextField txtSecretId;
	private JTextField textField;
	private boolean showingPassword;

	/**
	 * Launch the application.
	 */
	public static void display(int secretId, String password, long expiration) {
		try {
			SecretCreatedWindow window = new SecretCreatedWindow(secretId, password, expiration);
			window.frmSockergrodaSecret.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public SecretCreatedWindow(int secretId, String password, long expiration) {
		this.showingPassword = true;
		initialize(secretId, password, expiration);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(int secretId, String password, long expiration) {
		frmSockergrodaSecret = new JFrame();
		frmSockergrodaSecret.setTitle("Sockergroda - Secret Created");
		frmSockergrodaSecret.setResizable(false);
		frmSockergrodaSecret.setBounds(100, 100, 500, 160);
		frmSockergrodaSecret.setLocationRelativeTo(null);
		frmSockergrodaSecret.setIconImage(Images.ICON_1024x1024.getImage());
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
		
		JLabel lblNewLabel = new JLabel("Password:");
		lblNewLabel.setBounds(25, 75, 95, 14);
		frmSockergrodaSecret.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setText(password);
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBounds(25, 89, 125, 20);
		frmSockergrodaSecret.getContentPane().add(textField);
		
		JButton btnToggleShowPassword = new JButton();
		btnToggleShowPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showingPassword = !showingPassword;
				textField.setText(showingPassword ? password : password.replaceAll(".", "*"));
				btnToggleShowPassword.setText(showingPassword ? "Hide" : "Show");
			}
		});
		btnToggleShowPassword.doClick();
		btnToggleShowPassword.setBounds(160, 88, 77, 23);
		frmSockergrodaSecret.getContentPane().add(btnToggleShowPassword);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSockergrodaSecret.setVisible(false);
				System.exit(1);
			}
		});
		btnClose.setBounds(397, 86, 70, 23);
		frmSockergrodaSecret.getContentPane().add(btnClose);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSockergrodaSecret.setVisible(false);
				MainWindow.display();
			}
		});
		btnBack.setBounds(317, 86, 70, 23);
		frmSockergrodaSecret.getContentPane().add(btnBack);
	}
}
