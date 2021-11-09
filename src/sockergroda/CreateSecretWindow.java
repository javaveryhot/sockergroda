package sockergroda;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.optionalusertools.DateTimeChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateTimeChangeEvent;

import sockergroda.enums.Images;
import utils.APIResponse;
import utils.TimeStrings;

public class CreateSecretWindow {
	private JFrame frmSockergrodaCreate;
	private JPasswordField passwordField;
	private boolean passwordEnabled = true;
	private JTextField txtTitle;
	private JLabel lblExpirationSummary;
	
	private JSpinner spinner_uses;
	
	private DateTimePicker datePicker;
	private JTabbedPane expirationTypeTabs;

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
		textArea.setBounds(10, 95, 380, 122);
		frmSockergrodaCreate.getContentPane().add(textArea);
		
		JLabel lblFreetextCharCount = new JLabel();
		lblFreetextCharCount.setForeground(SystemColor.textInactiveText);
		lblFreetextCharCount.setHorizontalAlignment(SwingConstants.TRAILING);
		lblFreetextCharCount.setBounds(301, 75, 89, 14);
		frmSockergrodaCreate.getContentPane().add(lblFreetextCharCount);
		
		textArea.addTextListener(new TextListener() {
			@Override
			public void textValueChanged(TextEvent e) {
				int freetextLength = textArea.getText().length();
				if(freetextLength > 0) {
					lblFreetextCharCount.setText(freetextLength + " chars");
					lblFreetextCharCount.setIcon(new ImageIcon(!(freetextLength > 3000) ? Images.CORRECT_16x16.getImage() : Images.INCORRECT_16x16.getImage()));
				} else {
					lblFreetextCharCount.setText(null);
					lblFreetextCharCount.setIcon(null);
				}
			}
		});
		
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
		
		expirationTypeTabs = new JTabbedPane(JTabbedPane.TOP);
		expirationTypeTabs.setBounds(10, 320, 409, 70);
		frmSockergrodaCreate.getContentPane().add(expirationTypeTabs);

		JPanel timeBased = new JPanel();
		expirationTypeTabs.addTab("Time-based", null, timeBased, null);
		
		datePicker = new DateTimePicker();
		datePicker.getDatePicker().setDate(LocalDate.now().plusDays(3));
		datePicker.getTimePicker().setTime(LocalTime.now().plusMinutes(1));
		timeBased.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		timeBased.add(datePicker);
		
		datePicker.addDateTimeChangeListener(new DateTimeChangeListener() {
			@Override
			public void dateOrTimeChanged(DateTimeChangeEvent arg0) {
				updateExpirationSummaryText();
			}
		});
		
		JPanel usageBased = new JPanel();
		expirationTypeTabs.addTab("Usage-based", null, usageBased, null);
		
		spinner_uses = new JSpinner();
		spinner_uses.setModel(new SpinnerNumberModel(1, 1, 100, 1));
		spinner_uses.setBounds(223, 369, 63, 20);
		usageBased.add(spinner_uses);
		
		spinner_uses.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateExpirationSummaryText();
			}
		});
		
		expirationTypeTabs.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateExpirationSummaryText();
			}
		});
		
		JLabel lblUses = new JLabel("Uses");
		lblUses.setBounds(223, 395, 46, 14);
		usageBased.add(lblUses);
		
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
					frmSockergrodaCreate.getWindowListeners()[0].windowClosing(null);
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
				
				// Do the request
				
				APIResponse response = APIManager.createSecret(freeText, password, title, expiresAfter, expirationType);

				if(response.isRequestSuccessful()) {
					if(response.getCode() != 0) {
						if(chckbxSaveSecret.isSelected()) {
							Map<String, Object> storedSecrets = StorageManager.getJSOMap("owned_secrets");
							storedSecrets.put(String.valueOf(response.getIntValue("secret_id")), response.getStringValue("owner_key"));
							StorageManager.setAttribute("owned_secrets", storedSecrets);
						}
						
						frmSockergrodaCreate.dispose();
						SecretCreatedWindow.display(response.getIntValue("secret_id"), password, expiresAfter, expirationType);
					} else {
						JOptionPane.showMessageDialog(frmSockergrodaCreate, "Oops! The server denied your request.\nYour Sockergroda version is probably outdated.\nIf you are running the latest version, please report this!", "Request Denied", JOptionPane.ERROR_MESSAGE);
						btnCreate.setEnabled(true);
					}
				} else {
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
		lblExpirationSummary.setHorizontalAlignment(SwingConstants.RIGHT);
		lblExpirationSummary.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblExpirationSummary.setBounds(233, 409, 186, 20);
		frmSockergrodaCreate.getContentPane().add(lblExpirationSummary);
		
		JLabel lblWndTitle = new JLabel("Create a secret");
		lblWndTitle.setFont(new Font("Segoe UI Historic", Font.PLAIN, 16));
	    lblWndTitle.setIcon(new ImageIcon(Images.ICON_32x32.getImage()));
		lblWndTitle.setBounds(264, 21, 155, 32);
		frmSockergrodaCreate.getContentPane().add(lblWndTitle);
		
		JSpinner fontSizeSpinner = new JSpinner();
		fontSizeSpinner.setToolTipText("Choose a font size (this won't change the appearance of the text for the receiver!)");
		fontSizeSpinner.setModel(new SpinnerNumberModel(12, 3, 30, 2));
		fontSizeSpinner.setBounds(243, 72, 48, 20);
		frmSockergrodaCreate.getContentPane().add(fontSizeSpinner);
		
		ChangeListener fontSizeChange = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				textArea.setFont(new Font("Monospaced", Font.BOLD, (int)fontSizeSpinner.getValue()));
			}
		};
		fontSizeSpinner.addChangeListener(fontSizeChange);
		fontSizeChange.stateChanged(null);
		
		JButton btnRandomPassword = new JButton("Generate");
		btnRandomPassword.setBounds(82, 232, 92, 20);
		btnRandomPassword.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String generatedPassword = "";
				String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
				for(int i = 0; i < 16; i++) {
					char addChar = characters.charAt((int)Math.floor(Math.random() * characters.length()));
					if(Character.isLetter(addChar) && Math.floor(Math.random() * 2) == 0) {
						addChar = Character.toUpperCase(addChar);
					}
					generatedPassword += addChar;
				}
				passwordField.setText(generatedPassword);
			}
		});
		frmSockergrodaCreate.getContentPane().add(btnRandomPassword);
		
		JCheckBox chckbxShowPassword = new JCheckBox("Show password");
		chckbxShowPassword.setBounds(180, 232, 156, 23);
		frmSockergrodaCreate.getContentPane().add(chckbxShowPassword);
		
		chckbxShowPassword.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if(chckbxShowPassword.isSelected()) {
					passwordField.setEchoChar((char)0);
				} else {
					passwordField.setEchoChar((char)UIManager.get("PasswordField.echoChar"));
				}
			}
		});
		
		updateExpirationSummaryText();
	}
	
	private long getTimeExpirationValue() {
		LocalDate theDate = datePicker.getDatePicker().getDate();
		LocalTime theTime = datePicker.getTimePicker().getTime();
		
		if(theDate == null) {
			theDate = LocalDate.now();
		}

		if(theTime == null) {
			theTime = LocalTime.now();
		}
		
		return LocalDateTime.of(theDate, theTime).toEpochSecond(OffsetDateTime.now().getOffset()) - System.currentTimeMillis() / 1000;
	}
	
	private int getUsesExpirationValue() {
		return (int)spinner_uses.getValue();
	}
	
	private int getExpirationType() {
		return expirationTypeTabs.getSelectedIndex();
	}
	
	private void updateExpirationSummaryText() {
		if(getExpirationType() == 0 && getTimeExpirationValue() <= 0) {
			lblExpirationSummary.setText("Invalid expiration time");
			lblExpirationSummary.setIcon(new ImageIcon(Images.INCORRECT_16x16.getImage()));
		} else {
			lblExpirationSummary.setText("Expires after " + (getExpirationType() == 0 ? TimeStrings.getTimeString(getTimeExpirationValue()) : getUsesExpirationValue() + " use" + (getUsesExpirationValue() != 1 ? "s" : "")));
			lblExpirationSummary.setIcon(new ImageIcon(getExpirationType() == 0 ? Images.EXPIRATION_16x16.getImage() : Images.ID_16x16.getImage()));
		}
	}
}
