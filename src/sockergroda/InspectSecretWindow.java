package sockergroda;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.json.JSONObject;

import sockergroda.enums.Images;

public class InspectSecretWindow {

	private JFrame frmSockergrodaInspect;
	private JTextField txtId;
	private JPasswordField passwordField;
	private JLabel validationIcon;
	private int typeCount;
	private String clipboard;

	/**
	 * Launch the application.
	 */
	public static void display() {
		try {
			InspectSecretWindow window = new InspectSecretWindow();
			window.frmSockergrodaInspect.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public InspectSecretWindow() {
		this.typeCount = 0;
		try {
			this.clipboard = ((String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor)).trim();
		} catch(Exception e) {
			this.clipboard = "";
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSockergrodaInspect = new JFrame();
		frmSockergrodaInspect.setResizable(false);
		frmSockergrodaInspect.setTitle("Sockergroda - Inspect Secret");
		frmSockergrodaInspect.setBounds(100, 100, 350, 180);
		frmSockergrodaInspect.setLocationRelativeTo(null);
		frmSockergrodaInspect.setIconImage(Images.ICON_1024x1024.getImage());
		frmSockergrodaInspect.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSockergrodaInspect.getContentPane().setLayout(null);
		
		JLabel lblId = new JLabel("ID (formatted or raw):");
		lblId.setBounds(10, 11, 174, 14);
		frmSockergrodaInspect.getContentPane().add(lblId);
		
		txtId = new JTextField();
		txtId.setBounds(10, 26, 120, 20);
		txtId.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
				validationTimeout();
			}
			public void keyReleased(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {}
		});
		frmSockergrodaInspect.getContentPane().add(txtId);
		txtId.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(10, 56, 174, 14);
		frmSockergrodaInspect.getContentPane().add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(10, 71, 120, 20);
		frmSockergrodaInspect.getContentPane().add(passwordField);
		
		JButton btnInspect = new JButton("Inspect");
		btnInspect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String rawSecretId = "";
				String secretId = txtId.getText();
				for(int i = 0; i < secretId.length(); i++) {
					if(Character.isDigit(secretId.charAt(i))) {
						rawSecretId += secretId.charAt(i);
					}
				}

				if(rawSecretId.length() == 0) {
					JOptionPane.showMessageDialog(frmSockergrodaInspect, "You must give a valid ID to inspect! It must be a plain number or a formatted ID.", "Bad ID", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				String password = new String(passwordField.getPassword());
				int rawSecretIdInt = Integer.parseInt(rawSecretId);
				try {
					JSONObject jsonResponse = APIManager.inspectSecret(rawSecretIdInt, password);
					int code = jsonResponse.getInt("code");
					if(code != 2) {
						// Code 0 is invalid ID and 1 is bad password
						JOptionPane.showMessageDialog(frmSockergrodaInspect, code == 0 ? "The secret you are trying to find has expired or never existed." : "Incorrect password for secret.", code == 0 ? "Bad ID" : "Bad Password", JOptionPane.ERROR_MESSAGE);
						return;
					}
					String freeText = jsonResponse.getString("freetext");
					long createdAt = jsonResponse.getLong("created_at");
					frmSockergrodaInspect.setVisible(false);
					DisplaySecretWindow.display(freeText, createdAt);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(frmSockergrodaInspect, "An error occured whilst trying to connect to the server. Please try again later.", "Server Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnInspect.setBounds(10, 102, 89, 23);
		frmSockergrodaInspect.getContentPane().add(btnInspect);
		
		passwordField.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == 10) {
					// Pressed enter
					btnInspect.doClick();
				}
			}
		});
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSockergrodaInspect.setVisible(false);
				MainWindow.display();
			}
		});
		btnCancel.setBounds(231, 102, 89, 23);
		frmSockergrodaInspect.getContentPane().add(btnCancel);
		
		this.validationIcon = new JLabel();
		this.validationIcon.setBounds(140, 26, 180, 20);
		frmSockergrodaInspect.getContentPane().add(this.validationIcon);
		
		if(this.clipboard.startsWith("<sg?") && this.clipboard.endsWith(">")) {
			txtId.setText(clipboard);
			this.validationTimeout();
		}
	}
	
	private void updateIdValidation() {
		boolean validMode = false;
		String rawSecretId = "";
		String secretId = txtId.getText();

		for(int i = 0; i < secretId.length(); i++) {
			if(Character.isDigit(secretId.charAt(i))) {
				rawSecretId += secretId.charAt(i);
			}
		}

		if(rawSecretId.length() != 0) {
			int rawSecretIdInt = Integer.parseInt(rawSecretId);
			try {
				JSONObject jsonResponse = APIManager.checkSecret(rawSecretIdInt);
				validMode = jsonResponse.getBoolean("valid");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		this.validationIcon.setIcon(new ImageIcon(validMode ? Images.VALID_ICON_16x16.getImage() : Images.INVALID_ICON_16x16.getImage()));
		this.validationIcon.setText((validMode ? "Valid" : "Invalid") + (this.clipboard.length() > 0 && secretId.equals(this.clipboard) ? " (from clipboard)" : ""));
		if(validMode) {
			this.passwordField.grabFocus();
		}
	}
	
	private void validationTimeout() {
		this.typeCount++;
		int currentTypeCount = this.typeCount;
		validationIcon.setIcon(null);
		validationIcon.setText("Validating...");
	    final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
	    executorService.schedule(new Runnable() {
	        @Override
	        public void run() {
	        	if(currentTypeCount == typeCount) {
	        		updateIdValidation();
	        	}
	        }
	    }, 500, TimeUnit.MILLISECONDS);
	}
}
