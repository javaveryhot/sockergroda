package sockergroda;

import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import sockergroda.enums.Images;

public class DisplaySecretWindow {

	private JFrame frmSockergrodaInspecting;

	/**
	 * Launch the application.
	 */
	public static void display(String freeText, long createdAt) {
		try {
			DisplaySecretWindow window = new DisplaySecretWindow(freeText, createdAt);
			window.frmSockergrodaInspecting.setVisible(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public DisplaySecretWindow(String freeText, long createdAt) {
		initialize(freeText, createdAt);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String freeText, long createdAt) {
		frmSockergrodaInspecting = new JFrame();
		frmSockergrodaInspecting.setTitle("Sockergroda - Inspecting Secret");
		frmSockergrodaInspecting.setResizable(false);
		frmSockergrodaInspecting.setIconImage(Images.ICON_1024x1024.getImage());
		frmSockergrodaInspecting.setBounds(100, 100, 450, 300);
		frmSockergrodaInspecting.setLocationRelativeTo(null);
		frmSockergrodaInspecting.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSockergrodaInspecting.getContentPane().setLayout(null);
		
		TextArea textArea = new TextArea();
		textArea.setEditable(false);
		textArea.setText(freeText);
		textArea.setBounds(10, 32, 380, 160);
		frmSockergrodaInspecting.getContentPane().add(textArea);
		
		JLabel lblContent = new JLabel("Secret free-text:");
		lblContent.setBounds(10, 12, 114, 14);
		frmSockergrodaInspecting.getContentPane().add(lblContent);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSockergrodaInspecting.setVisible(false);
				MainWindow.display();
			}
		});
		btnBack.setBounds(262, 222, 70, 23);
		frmSockergrodaInspecting.getContentPane().add(btnBack);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSockergrodaInspecting.setVisible(false);
				System.exit(1);
			}
		});
		btnClose.setBounds(342, 222, 70, 23);
		frmSockergrodaInspecting.getContentPane().add(btnClose);
		
		String timeCreated = null;
		long timeAgoCreated = (System.currentTimeMillis() / 1000) - createdAt;
		int usingTimeUnit = 0; // 0 = seconds, 1 = minutes, 2 = hours, 3 = days, 4 = months, 5 = years
		boolean isMultiple = false;
		
		if(timeAgoCreated < 60) { // Seconds
			usingTimeUnit = 0;
			isMultiple = Math.floor(timeAgoCreated) != 1;
		} else if(timeAgoCreated < 60 * 60) { // Minutes
			usingTimeUnit = 1;
			isMultiple = Math.floor(timeAgoCreated / 60) != 1;
		} else if(timeAgoCreated < 60 * 60 * 24) { // Hours
			usingTimeUnit = 2;
			isMultiple = Math.floor(timeAgoCreated / 60 / 60) != 1;
		} else if(timeAgoCreated < 60 * 60 * 24 * 30) { // Days
			usingTimeUnit = 3;
			isMultiple = Math.floor(timeAgoCreated / 60 / 60 / 24) != 1;
		} else if(timeAgoCreated < 60 * 60 * 24 * 30 * 365) { // Months
			usingTimeUnit = 4;
			isMultiple = Math.floor(timeAgoCreated / 60 / 60 / 24 / 30) != 1;
		} else { // Years
			usingTimeUnit = 5;
			isMultiple = Math.floor(timeAgoCreated / 60 / 60 / 24 / 365) != 1;
		}
		
		switch(usingTimeUnit) {
		case 0:
			timeCreated = (int)Math.floor(timeAgoCreated) + " second";
			break;
		case 1:
			timeCreated = (int)Math.floor(timeAgoCreated / 60) + " minute";
			break;
		case 2:
			timeCreated = (int)Math.floor(timeAgoCreated / 60 / 60) + " hour";
			break;
		case 3:
			timeCreated = (int)Math.floor(timeAgoCreated / 60 / 60 / 24) + " day";
			break;
		case 4:
			timeCreated = (int)Math.floor(timeAgoCreated / 60 / 60 / 24 / 30) + " month";
			break;
		case 5:
			timeCreated = (int)Math.floor(timeAgoCreated / 60 / 60 / 24 / 365) + " year";
			break;
		}
		
		timeCreated += (isMultiple ? "s" : "") + " ago";
		
		JLabel lblCreatedAt = new JLabel(timeCreated);
		lblCreatedAt.setBounds(10, 198, 134, 14);
		frmSockergrodaInspecting.getContentPane().add(lblCreatedAt);
	}
}
