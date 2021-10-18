package sockergroda;

import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import sockergroda.enums.Images;
import utils.TimeStrings;

public class DisplaySecretWindow {

	private JFrame frmSockergrodaInspecting;
	private JCheckBox chckbxMaskMode;

	/**
	 * Launch the application.
	 */
	public static void display(String freeText, String title, long createdAt) {
		try {
			DisplaySecretWindow window = new DisplaySecretWindow(freeText, title, createdAt);
			window.frmSockergrodaInspecting.setVisible(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public DisplaySecretWindow(String freeText, String title, long createdAt) {
		initialize(freeText, title, createdAt);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String freeText, String title, long createdAt) {
		frmSockergrodaInspecting = new SGFrame();
		frmSockergrodaInspecting.setTitle("Inspecting Secret");
		frmSockergrodaInspecting.setResizable(false);
		frmSockergrodaInspecting.setIconImage(Images.ICON_32x32.getImage());
		frmSockergrodaInspecting.setBounds(100, 100, 450, 340);
		frmSockergrodaInspecting.setLocationRelativeTo(null);
		frmSockergrodaInspecting.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmSockergrodaInspecting.getContentPane().setLayout(null);
		
		TextArea textArea = new TextArea();
		textArea.setFont(new Font("Monospaced", Font.BOLD, 12));
		textArea.setEditable(false);
		textArea.setBounds(10, 75, 380, 160);
		frmSockergrodaInspecting.getContentPane().add(textArea);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSockergrodaInspecting.dispose();
			}
		});
		btnClose.setBounds(342, 265, 70, 23);
		frmSockergrodaInspecting.getContentPane().add(btnClose);

		JLabel lblCreatedAt = new JLabel(TimeStrings.getTimeString(System.currentTimeMillis() / 1000 - createdAt) + " ago");
		lblCreatedAt.setBounds(10, 241, 134, 14);
		frmSockergrodaInspecting.getContentPane().add(lblCreatedAt);
		
		JLabel lblTitle = new JLabel(title);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitle.setIcon(new ImageIcon(Images.EYE_16x16.getImage()));
		lblTitle.setBounds(10, 11, 380, 23);
		frmSockergrodaInspecting.getContentPane().add(lblTitle);
		
		ChangeListener maskChange = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				textArea.setText(!chckbxMaskMode.isSelected() ? freeText : freeText.replaceAll(".", "*"));
				StorageManager.setAttribute("mask_inspection", chckbxMaskMode.isSelected());
			}
		};
		
		chckbxMaskMode = new JCheckBox("Mask text");
		chckbxMaskMode.addChangeListener(maskChange);
		chckbxMaskMode.setBounds(10, 41, 134, 23);
		frmSockergrodaInspecting.getContentPane().add(chckbxMaskMode);
		
		chckbxMaskMode.setSelected(StorageManager.getBoolean("mask_inspection"));
		
		JButton btnCopy = new JButton("Copy content");
		btnCopy.setIcon(new ImageIcon(Images.COPY_16x16.getImage()));
		btnCopy.setBounds(262, 45, 128, 23);
		btnCopy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.copyToClipboard(freeText);
			}
		});
		frmSockergrodaInspecting.getContentPane().add(btnCopy);

		maskChange.stateChanged(null);
		
		chckbxMaskMode.grabFocus();
	}
}
