package sockergroda;

import java.awt.Component;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.json.JSONObject;

import sockergroda.enums.Images;
import utils.TimeStrings;
import java.awt.Font;

public class CreateSecretWindow {

	private JFrame frmSockergrodaCreate;
	private JPasswordField passwordField;
	private boolean passwordEnabled = true;
	private JTextField txtTitle;
	private JRadioButton rdbtnExpireTypeTime;
	private JRadioButton rdbtnExpireTypeUses;
	private JLabel lblExpirationSummary;
	
	private JSpinner spinner_minutes;
	private JSpinner spinner_hours;
	private JSpinner spinner_days;
	private JSpinner spinner_weeks;
	private JSpinner spinner_months;
	private JSpinner spinner_years;
	
	private JSpinner spinner_uses;

	/**
	 * Launch the application.
	 */
	public static void display() {
		try {
			CreateSecretWindow window = new CreateSecretWindow();
			window.frmSockergrodaCreate.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public CreateSecretWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSockergrodaCreate = new SGFrame();
		frmSockergrodaCreate.setResizable(false);
		frmSockergrodaCreate.setTitle("Create Secret");
		frmSockergrodaCreate.setBounds(100, 100, 450, 520);
		frmSockergrodaCreate.setLocationRelativeTo(null);
		frmSockergrodaCreate.setIconImage(Images.ICON_32x32.getImage());
		frmSockergrodaCreate.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmSockergrodaCreate.getContentPane().setLayout(null);
		
		txtTitle = new JTextField();
		txtTitle.setBounds(10, 31, 164, 20);
		frmSockergrodaCreate.getContentPane().add(txtTitle);
		txtTitle.setColumns(10);
		
		JLabel lblTitle = new JLabel("Title:");
		lblTitle.setIcon(new ImageIcon(Images.TAG_16x16.getImage()));
		lblTitle.setBounds(10, 11, 326, 14);
		frmSockergrodaCreate.getContentPane().add(lblTitle);
		
		JLabel lblFreeText = new JLabel("Free-text:");
		lblFreeText.setIcon(new ImageIcon(Images.FREETEXT_16x16.getImage()));
		lblFreeText.setBounds(10, 75, 326, 14);
		frmSockergrodaCreate.getContentPane().add(lblFreeText);
		
		TextArea textArea = new TextArea();
		textArea.setFont(new Font("Monospaced", Font.BOLD, 12));
		textArea.setBounds(10, 95, 380, 122);
		textArea.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(textArea.getText().length() >= 3000) {
					e.consume();
				}
			}
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V) {
					String clipboard = "";
					try {
						clipboard = ((String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor)).trim();
					} catch(Exception e1) {
						clipboard = "";
					}
					int totalLength = textArea.getText().length() + clipboard.length() - textArea.getSelectedText().length();
					if(totalLength > 3000) {
						e.consume();
					}
				}
			}
		});
		frmSockergrodaCreate.getContentPane().add(textArea);
		
		frmSockergrodaCreate.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(textArea.getText().length() > 0) {
					int option = JOptionPane.showConfirmDialog(frmSockergrodaCreate, "You have started writing a message.\nDo you want to discard it?", "Discard Message?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if(option == 0) {
						frmSockergrodaCreate.dispose();
					}
				} else {
					frmSockergrodaCreate.dispose();
				}
			}
		});
		
		passwordField = new JPasswordField();
		passwordField.setBounds(10, 256, 164, 20);
		frmSockergrodaCreate.getContentPane().add(passwordField);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setIcon(new ImageIcon(Images.KEY_16x16.getImage()));
		lblPassword.setBounds(10, 236, 326, 14);
		frmSockergrodaCreate.getContentPane().add(lblPassword);
		
		JLabel lblExpires = new JLabel("Expiration:");
		lblExpires.setIcon(new ImageIcon(Images.EXPIRATION_16x16.getImage()));
		lblExpires.setBounds(10, 300, 326, 14);
		frmSockergrodaCreate.getContentPane().add(lblExpires);
		
		JLabel lblMinutes = new JLabel("Min.");
		lblMinutes.setBounds(31, 344, 46, 14);
		frmSockergrodaCreate.getContentPane().add(lblMinutes);
		
		JLabel lblHours = new JLabel("Hours");
		lblHours.setBounds(75, 344, 46, 14);
		frmSockergrodaCreate.getContentPane().add(lblHours);
		
		JLabel lblDays = new JLabel("Days");
		lblDays.setBounds(120, 344, 46, 14);
		frmSockergrodaCreate.getContentPane().add(lblDays);
		
		JLabel lblWeeks = new JLabel("Weeks");
		lblWeeks.setBounds(170, 344, 46, 14);
		frmSockergrodaCreate.getContentPane().add(lblWeeks);
		
		JLabel lblMonths = new JLabel("Months");
		lblMonths.setBounds(209, 344, 46, 14);
		frmSockergrodaCreate.getContentPane().add(lblMonths);
		
		JLabel lblYears = new JLabel("Years");
		lblYears.setBounds(254, 344, 46, 14);
		frmSockergrodaCreate.getContentPane().add(lblYears);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 320, 291, 38);
		frmSockergrodaCreate.getContentPane().add(panel);
		
		spinner_minutes = new JSpinner();
		panel.add(spinner_minutes);
		spinner_minutes.setModel(new SpinnerNumberModel(0, 0, 59, 1));
		
		spinner_hours = new JSpinner();
		panel.add(spinner_hours);
		spinner_hours.setModel(new SpinnerNumberModel(0, 0, 23, 1));
		
		spinner_days = new JSpinner();
		panel.add(spinner_days);
		spinner_days.setModel(new SpinnerNumberModel(3, 0, 364, 1));
		
		spinner_weeks = new JSpinner();
		panel.add(spinner_weeks);
		spinner_weeks.setModel(new SpinnerNumberModel(0, 0, 51, 1));
		
		spinner_months = new JSpinner();
		panel.add(spinner_months);
		spinner_months.setModel(new SpinnerNumberModel(0, 0, 11, 1));
		
		spinner_years = new JSpinner();
		panel.add(spinner_years);
		spinner_years.setModel(new SpinnerNumberModel(0, 0, 3, 1));
		
		ChangeListener expirationChangeListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateExpirationSummaryText();
			}
		};
		
		spinner_minutes.addChangeListener(expirationChangeListener);
		spinner_hours.addChangeListener(expirationChangeListener);
		spinner_days.addChangeListener(expirationChangeListener);
		spinner_weeks.addChangeListener(expirationChangeListener);
		spinner_months.addChangeListener(expirationChangeListener);
		spinner_years.addChangeListener(expirationChangeListener);
		
		JCheckBox usePasswordCheckBox = new JCheckBox("Require password");
		usePasswordCheckBox.setSelected(true);
		usePasswordCheckBox.setBounds(180, 255, 156, 23);
		usePasswordCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				passwordField.setEnabled(passwordEnabled = usePasswordCheckBox.isSelected());
			}
		});
		frmSockergrodaCreate.getContentPane().add(usePasswordCheckBox);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 223, 409, 14);
		frmSockergrodaCreate.getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 287, 409, 14);
		frmSockergrodaCreate.getContentPane().add(separator_1);
		
		rdbtnExpireTypeTime = new JRadioButton("Time-based");
		rdbtnExpireTypeTime.setBounds(310, 327, 128, 23);
		rdbtnExpireTypeTime.setSelected(true);
		frmSockergrodaCreate.getContentPane().add(rdbtnExpireTypeTime);
		
		rdbtnExpireTypeUses = new JRadioButton("Usage-based");
		rdbtnExpireTypeUses.setBounds(310, 369, 128, 23);
		frmSockergrodaCreate.getContentPane().add(rdbtnExpireTypeUses);
		
		ButtonGroup expirationType = new ButtonGroup();
		
		expirationType.add(rdbtnExpireTypeTime);
		expirationType.add(rdbtnExpireTypeUses);
		
		rdbtnExpireTypeTime.addChangeListener(expirationChangeListener);
		rdbtnExpireTypeUses.addChangeListener(expirationChangeListener);
		
		ChangeListener expirationTypeChangeListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int whichType = getExpirationType();
				
				Component[] timeComponents = panel.getComponents();

				for(int i = 0; i < timeComponents.length; i++) {
					timeComponents[i].setEnabled(whichType == 0);
				}
				
				spinner_uses.setEnabled(whichType == 1);
			}
		};
		
		rdbtnExpireTypeTime.addChangeListener(expirationTypeChangeListener);
		rdbtnExpireTypeUses.addChangeListener(expirationTypeChangeListener);
		
		spinner_uses = new JSpinner();
		spinner_uses.setModel(new SpinnerNumberModel(1, 1, 100, 1));
		spinner_uses.setBounds(223, 369, 63, 20);
		frmSockergrodaCreate.getContentPane().add(spinner_uses);
		
		spinner_uses.addChangeListener(expirationChangeListener);
		
		JLabel lblUses = new JLabel("Uses");
		lblUses.setBounds(223, 395, 46, 14);
		frmSockergrodaCreate.getContentPane().add(lblUses);
		
		JCheckBox chckbxSaveSecret = new JCheckBox("Save this secret");
		chckbxSaveSecret.setSelected(StorageManager.getBoolean("save_secrets"));
		chckbxSaveSecret.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StorageManager.setAttribute("save_secrets", chckbxSaveSecret.isSelected());
				if(!chckbxSaveSecret.isSelected() && !StorageManager.getBoolean("ignore_dont_save_secret_warning")) {
					String[] options = {"Ignore", "Don't show this again"};
					int option = JOptionPane.showOptionDialog(frmSockergrodaCreate, "You will not be able to delete this secret in the future if you don't save it.", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
					if(option == 1) {
						StorageManager.setAttribute("ignore_dont_save_secret_warning", true);
					}
				}
			}
		});
		chckbxSaveSecret.setBounds(82, 440, 135, 23);
		frmSockergrodaCreate.getContentPane().add(chckbxSaveSecret);
		
		JButton btnBack = new JButton("Cancel");
		btnBack.setMnemonic('e');
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textArea.getText().length() > 0) {
					int option = JOptionPane.showConfirmDialog(frmSockergrodaCreate, "You have started writing a message.\nDo you want to discard it?", "Discard Message?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if(option == 0) {
						frmSockergrodaCreate.dispose();
					}
				} else {
					frmSockergrodaCreate.dispose();
				}
			}
		});
		btnBack.setBounds(232, 440, 89, 23);
		frmSockergrodaCreate.getContentPane().add(btnBack);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.setMnemonic('C');
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int expirationType = getExpirationType();

				long expiresAfter = 0;

				if(expirationType == 0) {
					expiresAfter = getTimeExpirationValue();
				} else {
					expiresAfter = getUsesExpirationValue();
				}

				String freeText = textArea.getText();
				String password = passwordEnabled ? new String(passwordField.getPassword()) : null;
				String title = txtTitle.getText();

				if(title.length() > 64) {
					JOptionPane.showMessageDialog(frmSockergrodaCreate, "Title must not be longer than 64 characters.\nYou used " + title.length() + " characters.", "Bad Title", JOptionPane.ERROR_MESSAGE);
					txtTitle.requestFocus();
					return;
				} else if(freeText.length() < 1 || freeText.length() > 3000) {
					JOptionPane.showMessageDialog(frmSockergrodaCreate, "Free-text must be of 1-3000 characters.\nYou used " + freeText.length() + " characters.", "Bad Free-text", JOptionPane.ERROR_MESSAGE);
					textArea.requestFocus();
					return;
				} else if(password != null && (password.length() < 1 || password.length() > 32)) {
					JOptionPane.showMessageDialog(frmSockergrodaCreate, "Password must be of 1-32 characters.\nYou used " + password.length() + " characters.", "Bad Password", JOptionPane.ERROR_MESSAGE);
					passwordField.requestFocus();
					return;
				} else if(expirationType == 0 && expiresAfter > 60 * 60 * 24 * 365 * 3) {
					JOptionPane.showMessageDialog(frmSockergrodaCreate, "Expiration for secret must be within 1 minute and 3 years.", "Bad Expiration", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				btnCreate.setEnabled(false);
				
				if(title.length() == 0) {
					title = null;
				}
				
				try {
					JSONObject secret = APIManager.createSecret(freeText, password, title, expiresAfter, expirationType);
					if(secret.getInt("code") != 0) {
						if(chckbxSaveSecret.isSelected()) {
							Map<String, Object> storedSecrets = StorageManager.getJSOMap("owned_secrets");
							storedSecrets.put(String.valueOf(secret.getInt("secret_id")), secret.getString("owner_key"));
							StorageManager.setAttribute("owned_secrets", storedSecrets);
						}
						
						frmSockergrodaCreate.dispose();
						SecretCreatedWindow.display(secret.getInt("secret_id"), password, expiresAfter, expirationType);
					} else {
						JOptionPane.showMessageDialog(frmSockergrodaCreate, "Oops! The server denied your request.\nYour Sockergroda version is probably outdated.\nIf you are running the latest version, please report this!", "Request Denied", JOptionPane.ERROR_MESSAGE);
						btnCreate.setEnabled(true);
					}
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(frmSockergrodaCreate, "An error occured whilst trying to connect to the server. Please try again later.", "Server Error", JOptionPane.ERROR_MESSAGE);
					btnCreate.setEnabled(true);
				}
			}
		});
		btnCreate.setBounds(330, 440, 89, 23);
		frmSockergrodaCreate.getContentPane().add(btnCreate);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 62, 409, 14);
		frmSockergrodaCreate.getContentPane().add(separator_2);
		
		lblExpirationSummary = new JLabel();
		lblExpirationSummary.setBounds(31, 373, 143, 14);
		frmSockergrodaCreate.getContentPane().add(lblExpirationSummary);
		
		JLabel lblWndTitle = new JLabel("Create a secret");
		lblWndTitle.setFont(new Font("Segoe UI Historic", Font.PLAIN, 16));
	    lblWndTitle.setIcon(new ImageIcon(Images.ICON_32x32.getImage()));
		lblWndTitle.setBounds(264, 21, 155, 32);
		frmSockergrodaCreate.getContentPane().add(lblWndTitle);
		
		expirationTypeChangeListener.stateChanged(null);
		updateExpirationSummaryText();
	}
	
	private long getTimeExpirationValue() {
		int minutes = (int)spinner_minutes.getValue();
		int hours = (int)spinner_hours.getValue();
		int days = (int)spinner_days.getValue();
		int weeks = (int)spinner_weeks.getValue();
		int months = (int)spinner_months.getValue();
		int years = (int)spinner_years.getValue();
		return (minutes * 60) + (hours * 60 * 60) + (days * 60 * 60 * 24) + (weeks * 60 * 60 * 24 * 7) + (months * 60 * 60 * 24 * 30) + (years * 60 * 60 * 24 * 365);
	}
	
	private int getUsesExpirationValue() {
		return (int)spinner_uses.getValue();
	}
	
	private int getExpirationType() {
		return rdbtnExpireTypeTime.isSelected() ? 0 : 1;
	}
	
	private void updateExpirationSummaryText() {
		lblExpirationSummary.setText("Expires in " + (getExpirationType() == 0 ? TimeStrings.getTimeString(getTimeExpirationValue()) : getUsesExpirationValue() + " uses"));
	}
}
