package sockergroda;

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
import javax.swing.SpinnerNumberModel;

import sockergroda.enums.Images;

public class CreateSecretWindow {

	private JFrame frmSockergrodaCreate;
	private JPasswordField passwordField;
	private boolean passwordEnabled = true;

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
		frmSockergrodaCreate = new JFrame();
		frmSockergrodaCreate.setResizable(false);
		frmSockergrodaCreate.setTitle("Sockergroda - Create Secret");
		frmSockergrodaCreate.setBounds(100, 100, 450, 450);
		frmSockergrodaCreate.setLocationRelativeTo(null);
		frmSockergrodaCreate.setIconImage(Images.ICON_1024x1024.getImage());
		frmSockergrodaCreate.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmSockergrodaCreate.getContentPane().setLayout(null);
		
		JLabel lblFreeText = new JLabel("Free-text:");
		lblFreeText.setIcon(new ImageIcon(Images.FREETEXT_16x16.getImage()));
		lblFreeText.setBounds(10, 11, 326, 14);
		frmSockergrodaCreate.getContentPane().add(lblFreeText);
		
		TextArea textArea = new TextArea();
		textArea.setBounds(10, 31, 380, 122);
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
					int option = JOptionPane.showConfirmDialog(frmSockergrodaCreate, "You have started writing a message.\nDo you want to discard it?", "Discard Message?", JOptionPane.YES_NO_OPTION);
					if(option == 0) {
						frmSockergrodaCreate.dispose();
						System.exit(1);
					}
				} else {
					frmSockergrodaCreate.dispose();
					System.exit(1);
				}
			}
		});
		
		passwordField = new JPasswordField();
		passwordField.setBounds(10, 192, 164, 20);
		frmSockergrodaCreate.getContentPane().add(passwordField);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setIcon(new ImageIcon(Images.KEY_16x16.getImage()));
		lblPassword.setBounds(10, 172, 326, 14);
		frmSockergrodaCreate.getContentPane().add(lblPassword);
		
		JLabel lblExpires = new JLabel("Expiration:");
		lblExpires.setIcon(new ImageIcon(Images.EXPIRATION_16x16.getImage()));
		lblExpires.setBounds(10, 231, 326, 14);
		frmSockergrodaCreate.getContentPane().add(lblExpires);
		
		JLabel lblMinutes = new JLabel("Min.");
		lblMinutes.setBounds(31, 275, 46, 14);
		frmSockergrodaCreate.getContentPane().add(lblMinutes);
		
		JLabel lblHours = new JLabel("Hours");
		lblHours.setBounds(75, 275, 46, 14);
		frmSockergrodaCreate.getContentPane().add(lblHours);
		
		JLabel lblDays = new JLabel("Days");
		lblDays.setBounds(120, 275, 46, 14);
		frmSockergrodaCreate.getContentPane().add(lblDays);
		
		JLabel lblWeeks = new JLabel("Weeks");
		lblWeeks.setBounds(170, 275, 46, 14);
		frmSockergrodaCreate.getContentPane().add(lblWeeks);
		
		JLabel lblMonths = new JLabel("Months");
		lblMonths.setBounds(209, 275, 46, 14);
		frmSockergrodaCreate.getContentPane().add(lblMonths);
		
		JLabel lblYears = new JLabel("Years");
		lblYears.setBounds(254, 275, 46, 14);
		frmSockergrodaCreate.getContentPane().add(lblYears);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 248, 291, 38);
		frmSockergrodaCreate.getContentPane().add(panel);
		
		JSpinner spinner_minutes = new JSpinner();
		panel.add(spinner_minutes);
		spinner_minutes.setModel(new SpinnerNumberModel(0, 0, 59, 1));
		
		JSpinner spinner_hours = new JSpinner();
		panel.add(spinner_hours);
		spinner_hours.setModel(new SpinnerNumberModel(0, 0, 23, 1));
		
		JSpinner spinner_days = new JSpinner();
		panel.add(spinner_days);
		spinner_days.setModel(new SpinnerNumberModel(3, 0, 364, 1));
		
		JSpinner spinner_weeks = new JSpinner();
		panel.add(spinner_weeks);
		spinner_weeks.setModel(new SpinnerNumberModel(0, 0, 51, 1));
		
		JSpinner spinner_months = new JSpinner();
		panel.add(spinner_months);
		spinner_months.setModel(new SpinnerNumberModel(0, 0, 11, 1));
		
		JSpinner spinner_years = new JSpinner();
		panel.add(spinner_years);
		spinner_years.setModel(new SpinnerNumberModel(0, 0, 3, 1));
		
		JCheckBox usePasswordCheckBox = new JCheckBox("Require password");
		usePasswordCheckBox.setSelected(true);
		usePasswordCheckBox.setBounds(180, 191, 111, 23);
		usePasswordCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				passwordField.setEnabled(passwordEnabled = usePasswordCheckBox.isSelected());
			}
		});
		frmSockergrodaCreate.getContentPane().add(usePasswordCheckBox);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 159, 409, 14);
		frmSockergrodaCreate.getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 223, 409, 14);
		frmSockergrodaCreate.getContentPane().add(separator_1);
		
		JRadioButton rdbtnExpireTypeTime = new JRadioButton("Expire after time");
		rdbtnExpireTypeTime.setBounds(310, 255, 128, 23);
		rdbtnExpireTypeTime.setSelected(true);
		frmSockergrodaCreate.getContentPane().add(rdbtnExpireTypeTime);
		
		JRadioButton rdbtnExpireTypeUses = new JRadioButton("Expire after usage");
		rdbtnExpireTypeUses.setBounds(310, 313, 128, 23);
		frmSockergrodaCreate.getContentPane().add(rdbtnExpireTypeUses);
		
		ButtonGroup expirationType = new ButtonGroup();
		
		expirationType.add(rdbtnExpireTypeTime);
		expirationType.add(rdbtnExpireTypeUses);
		
		JSpinner spinner_uses = new JSpinner();
		spinner_uses.setModel(new SpinnerNumberModel(1, 1, 100, 1));
		spinner_uses.setBounds(223, 313, 63, 20);
		frmSockergrodaCreate.getContentPane().add(spinner_uses);
		
		JLabel lblUses = new JLabel("Uses");
		lblUses.setBounds(223, 339, 46, 14);
		frmSockergrodaCreate.getContentPane().add(lblUses);
		
		JButton btnReturn = new JButton("Back");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textArea.getText().length() > 0) {
					int option = JOptionPane.showConfirmDialog(frmSockergrodaCreate, "You have started writing a message.\nDo you want to discard it?", "Discard Message?", JOptionPane.YES_NO_OPTION);
					if(option == 0) {
						frmSockergrodaCreate.setVisible(false);
						MainWindow.display();
					}
				} else {
					frmSockergrodaCreate.setVisible(false);
					MainWindow.display();
				}
			}
		});
		btnReturn.setBounds(232, 376, 89, 23);
		frmSockergrodaCreate.getContentPane().add(btnReturn);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int expirationType = rdbtnExpireTypeTime.isSelected() ? 0 : 1;

				long expiresAfter = 0;

				if(expirationType == 0) {
					int minutes = (int)spinner_minutes.getValue();
					int hours = (int)spinner_hours.getValue();
					int days = (int)spinner_days.getValue();
					int weeks = (int)spinner_weeks.getValue();
					int months = (int)spinner_months.getValue();
					int years = (int)spinner_years.getValue();
					expiresAfter = (minutes * 60) + (hours * 60 * 60) + (days * 60 * 60 * 24) + (weeks * 60 * 60 * 24 * 7) + (months * 60 * 60 * 24 * 30) + (years * 60 * 60 * 24 * 365);
				} else {
					expiresAfter = (int)spinner_uses.getValue();
				}

				String freeText = textArea.getText();
				String password = passwordEnabled ? new String(passwordField.getPassword()) : null;
				
				try {
					int secretId = APIManager.createSecret(freeText, password, expiresAfter, expirationType);
					if(secretId != 0) {
						frmSockergrodaCreate.setVisible(false);
						SecretCreatedWindow.display(secretId, password, expiresAfter, expirationType);
					} else {
						JOptionPane.showMessageDialog(frmSockergrodaCreate, "You're input is only valid if the following criterias are met:\n- Free-text must be 1-3,000 characters\n- Password must be 1-32 characters\n- Must expire in at least 1 minute and at most 3 years", "Invalid Input", JOptionPane.ERROR_MESSAGE);
					}
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(frmSockergrodaCreate, "An error occured whilst trying to connect to the server. Please try again later.", "Server Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnCreate.setBounds(330, 376, 89, 23);
		frmSockergrodaCreate.getContentPane().add(btnCreate);
	}
}
