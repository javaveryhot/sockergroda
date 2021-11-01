package sockergroda;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

import sockergroda.enums.Images;
import utils.APIResponse;

public class InspectSecretWindow {

	private JFrame frmSockergrodaInspect;
	private JTextField txtId;
	private JPasswordField passwordField;
	private JLabel validationIcon;
	private JButton btnInspect;
	private JLabel lblPasswordNote;
	private int typeCount;
	private String clipboard;
	private boolean autoInspect;

	/**
	 * Launch the application.
	 */
	public static void display(String presetInput, boolean autoInspect) {
		try {
			InspectSecretWindow window = new InspectSecretWindow(presetInput, autoInspect);
			window.frmSockergrodaInspect.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the application.
	 */
	public InspectSecretWindow(String presetInput, boolean autoInspect) {
		this.typeCount = 0;
		try {
			this.clipboard = ((String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor)).trim();
		} catch(Exception e) {
			this.clipboard = "";
		}
		this.autoInspect = autoInspect;
		initialize(presetInput);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String presetInput) {
		frmSockergrodaInspect = new SGFrame();
		frmSockergrodaInspect.setResizable(false);
		frmSockergrodaInspect.setTitle("Inspect Secret");
		frmSockergrodaInspect.setBounds(100, 100, 350, 180);
		frmSockergrodaInspect.setLocationRelativeTo(null);
		frmSockergrodaInspect.setIconImage(Images.ICON_32x32.getImage());
		frmSockergrodaInspect.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmSockergrodaInspect.getContentPane().setLayout(null);
		
		JLabel lblId = new JLabel("ID:");
		lblId.setIcon(new ImageIcon(Images.ID_16x16.getImage()));
		lblId.setBounds(10, 11, 174, 14);
		frmSockergrodaInspect.getContentPane().add(lblId);
		
		txtId = new JTextField();
		txtId.setBounds(10, 26, 120, 20);
		txtId.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				validationTimeout();
			}
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == 10) {
					// Pressed enter
					passwordField.grabFocus();
				}
			}
		});
		frmSockergrodaInspect.getContentPane().add(txtId);
		txtId.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setIcon(new ImageIcon(Images.KEY_16x16.getImage()));
		lblPassword.setBounds(10, 56, 174, 14);
		frmSockergrodaInspect.getContentPane().add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(10, 71, 120, 20);
		frmSockergrodaInspect.getContentPane().add(passwordField);
		
		btnInspect = new JButton("Inspect");
		btnInspect.setMnemonic('n');
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
					txtId.grabFocus();
					return;
				}
				
				String password = new String(passwordField.getPassword());
				long rawSecretIdLong = Long.parseLong(rawSecretId);

				// Do the request
				
				APIResponse response = APIManager.inspectSecret(rawSecretIdLong, password);
				
				if(response.isRequestSuccessful()) {
					int code = response.getCode();
	
					if(code != 2) {
						// Code 0 is invalid ID and 1 is bad password
						JOptionPane.showMessageDialog(frmSockergrodaInspect, code == 0 ? "There is no secret with such ID.\nIt has either expired or never existed." : "Incorrect password for secret.", code == 0 ? "Bad ID" : "Bad Password", JOptionPane.ERROR_MESSAGE);
						return;
					}
	
					frmSockergrodaInspect.dispose();
					DisplaySecretWindow.display(Math.toIntExact(rawSecretIdLong), password, response);
				} else {
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
		btnCancel.setMnemonic('e');
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSockergrodaInspect.dispose();
			}
		});
		btnCancel.setBounds(231, 102, 89, 23);
		frmSockergrodaInspect.getContentPane().add(btnCancel);
		
		validationIcon = new JLabel();
		validationIcon.setBounds(140, 26, 180, 20);
		frmSockergrodaInspect.getContentPane().add(validationIcon);
		
		lblPasswordNote = new JLabel();
		lblPasswordNote.setBounds(138, 74, 125, 14);
		frmSockergrodaInspect.getContentPane().add(lblPasswordNote);
		
		JLabel lblWndTitle = new JLabel("Inspect");
		lblWndTitle.setFont(new Font("Segoe UI Historic", Font.PLAIN, 16));
	    lblWndTitle.setIcon(new ImageIcon(Images.ICON_32x32.getImage()));
		lblWndTitle.setBounds(119, 98, 93, 32);
		frmSockergrodaInspect.getContentPane().add(lblWndTitle);
		
		if(presetInput != null) {
			txtId.setText(presetInput);
			this.validationTimeout();
		} else if(this.clipboard.startsWith("<sg?") && this.clipboard.endsWith(">")) {
			txtId.setText(clipboard);
			this.validationTimeout();
		}
	}
	
	private void updateIdValidation() {
		if(txtId.getText().length() == 0) {
			validationIcon.setIcon(null);
			validationIcon.setText(null);
			return;
		}
		
		boolean validMode = false;
		boolean passwordless = false;
		String rawSecretId = "";
		String secretId = txtId.getText();

		for(int i = 0; i < secretId.length(); i++) {
			if(Character.isDigit(secretId.charAt(i))) {
				rawSecretId += secretId.charAt(i);
			}
		}

		if(rawSecretId.length() != 0) {
			long rawSecretIdLong = Long.parseLong(rawSecretId);
			APIResponse response = APIManager.checkSecret(rawSecretIdLong);
			validMode = response.getBooleanValue("valid");
			passwordless = response.getBooleanValue("passwordless");
		}
		
		this.validationIcon.setIcon(new ImageIcon(validMode ? Images.CORRECT_16x16.getImage() : Images.INCORRECT_16x16.getImage()));
		this.validationIcon.setText((validMode ? "Valid" : "Invalid") + (this.clipboard.length() > 0 && secretId.equals(this.clipboard) ? " (from clipboard)" : ""));
		if(validMode && !passwordless) {
			// Secret exists and requires password
			
			this.passwordField.grabFocus();
			this.passwordField.setEnabled(true);
			this.lblPasswordNote.setText(null);
		} else if(!validMode) {
			// Secret does not exist
			
			this.passwordField.setEnabled(true);
			this.lblPasswordNote.setText(null);
		} else if(passwordless) {
			// Secret exists and does not require password
			
			this.btnInspect.grabFocus();
			this.passwordField.setEnabled(false);
			this.lblPasswordNote.setText("No password for secret");
			
			if(this.autoInspect) {
				this.btnInspect.doClick();
			}
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
