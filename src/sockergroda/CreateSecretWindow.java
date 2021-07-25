package sockergroda;

import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import sockergroda.enums.Images;

public class CreateSecretWindow {

	private JFrame frmSockergrodaCreate;
	private JPasswordField passwordField;

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
		frmSockergrodaCreate.setBounds(100, 100, 450, 400);
		frmSockergrodaCreate.setLocationRelativeTo(null);
		frmSockergrodaCreate.setIconImage(Images.ICON_1024x1024.getImage());
		frmSockergrodaCreate.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSockergrodaCreate.getContentPane().setLayout(null);
		
		JLabel lblFreeText = new JLabel("Free-text:");
		lblFreeText.setBounds(10, 11, 326, 14);
		frmSockergrodaCreate.getContentPane().add(lblFreeText);
		
		TextArea textArea = new TextArea();
		textArea.setBounds(10, 31, 380, 122);
		textArea.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(textArea.getText().length() >= 3000) {
					e.consume();
				} else {
					if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V) {
						System.out.println("pasted!");
					}
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
		
		passwordField = new JPasswordField();
		passwordField.setBounds(10, 192, 164, 20);
		frmSockergrodaCreate.getContentPane().add(passwordField);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(10, 172, 326, 14);
		frmSockergrodaCreate.getContentPane().add(lblPassword);
		
		JLabel lblExpires = new JLabel("Expiration:");
		lblExpires.setBounds(10, 231, 326, 14);
		frmSockergrodaCreate.getContentPane().add(lblExpires);
		
		JSpinner spinner_minutes = new JSpinner();
		spinner_minutes.setModel(new SpinnerNumberModel(0, 0, 59, 1));
		spinner_minutes.setBounds(10, 256, 39, 20);
		frmSockergrodaCreate.getContentPane().add(spinner_minutes);
		
		JSpinner spinner_hours = new JSpinner();
		spinner_hours.setModel(new SpinnerNumberModel(0, 0, 23, 1));
		spinner_hours.setBounds(59, 256, 39, 20);
		frmSockergrodaCreate.getContentPane().add(spinner_hours);
		
		JSpinner spinner_days = new JSpinner();
		spinner_days.setModel(new SpinnerNumberModel(3, 0, 364, 1));
		spinner_days.setBounds(108, 256, 39, 20);
		frmSockergrodaCreate.getContentPane().add(spinner_days);
		
		JSpinner spinner_weeks = new JSpinner();
		spinner_weeks.setModel(new SpinnerNumberModel(0, 0, 51, 1));
		spinner_weeks.setBounds(157, 256, 39, 20);
		frmSockergrodaCreate.getContentPane().add(spinner_weeks);
		
		JSpinner spinner_months = new JSpinner();
		spinner_months.setModel(new SpinnerNumberModel(0, 0, 11, 1));
		spinner_months.setBounds(206, 256, 39, 20);
		frmSockergrodaCreate.getContentPane().add(spinner_months);
		
		JSpinner spinner_years = new JSpinner();
		spinner_years.setModel(new SpinnerNumberModel(0, 0, 3, 1));
		spinner_years.setBounds(255, 256, 39, 20);
		frmSockergrodaCreate.getContentPane().add(spinner_years);
		
		JLabel lblMinutes = new JLabel("Min.");
		lblMinutes.setBounds(10, 280, 46, 14);
		frmSockergrodaCreate.getContentPane().add(lblMinutes);
		
		JLabel lblHours = new JLabel("Hours");
		lblHours.setBounds(59, 280, 46, 14);
		frmSockergrodaCreate.getContentPane().add(lblHours);
		
		JLabel lblDays = new JLabel("Days");
		lblDays.setBounds(108, 280, 46, 14);
		frmSockergrodaCreate.getContentPane().add(lblDays);
		
		JLabel lblWeeks = new JLabel("Weeks");
		lblWeeks.setBounds(157, 280, 46, 14);
		frmSockergrodaCreate.getContentPane().add(lblWeeks);
		
		JLabel lblMonths = new JLabel("Months");
		lblMonths.setBounds(206, 280, 46, 14);
		frmSockergrodaCreate.getContentPane().add(lblMonths);
		
		JLabel lblYears = new JLabel("Years");
		lblYears.setBounds(255, 280, 46, 14);
		frmSockergrodaCreate.getContentPane().add(lblYears);
		
		JButton btnReturn = new JButton("Cancel");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSockergrodaCreate.setVisible(false);
				MainWindow.display();
			}
		});
		btnReturn.setBounds(232, 324, 89, 23);
		frmSockergrodaCreate.getContentPane().add(btnReturn);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long expiresAfter = 0;
				int minutes = (int)spinner_minutes.getValue();
				int hours = (int)spinner_hours.getValue();
				int days = (int)spinner_days.getValue();
				int weeks = (int)spinner_weeks.getValue();
				int months = (int)spinner_months.getValue();
				int years = (int)spinner_years.getValue();
				expiresAfter = (minutes * 60) + (hours * 60 * 60) + (days * 60 * 60 * 24) + (weeks * 60 * 60 * 24 * 7) + (months * 60 * 60 * 24 * 30) + (years * 60 * 60 * 24 * 365);
				String freeText = textArea.getText();
				String password = new String(passwordField.getPassword());
				
				try {
					int secretId = APIManager.createSecret(freeText, password, expiresAfter);
					if(secretId != 0) {
						frmSockergrodaCreate.setVisible(false);
						SecretCreatedWindow.display(secretId, password, expiresAfter);
					} else {
						JOptionPane.showMessageDialog(frmSockergrodaCreate, "You're input is only valid if the following criterias are met:\n- Free-text must be 1-3,000 characters\n- Password must be 1-32 characters\n- Must expire in at least 1 minute and at most 3 years", "Invalid Input", JOptionPane.ERROR_MESSAGE);
					}
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(frmSockergrodaCreate, "An error occured whilst trying to connect to the server. Please try again later.", "Server Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnCreate.setBounds(330, 324, 89, 23);
		frmSockergrodaCreate.getContentPane().add(btnCreate);
	}
}
