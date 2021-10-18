package sockergroda;

import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import sockergroda.enums.Images;

public class PreUsageWarningWindow {

	private JFrame frmSockergrodaPreUsage;

	/**
	 * Launch the application.
	 */
	public static void display() {
		try {
			PreUsageWarningWindow window = new PreUsageWarningWindow();
			window.frmSockergrodaPreUsage.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public PreUsageWarningWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSockergrodaPreUsage = new SGFrame();
		frmSockergrodaPreUsage.setTitle("Warning");
		frmSockergrodaPreUsage.setResizable(false);
		frmSockergrodaPreUsage.setBounds(100, 100, 500, 220);
		frmSockergrodaPreUsage.setLocationRelativeTo(null);
		frmSockergrodaPreUsage.setIconImage(Images.ICON_32x32.getImage());
		frmSockergrodaPreUsage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSockergrodaPreUsage.getContentPane().setLayout(null);
		
		JButton btnContinue = new JButton("Continue");
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSockergrodaPreUsage.dispose();
				StorageManager.setAttribute("pre_usage_warning_confirmed", true);
				MainWindow.display();
			}
		});
		btnContinue.setBounds(380, 146, 87, 23);
		frmSockergrodaPreUsage.getContentPane().add(btnContinue);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		btnCancel.setBounds(300, 146, 70, 23);
		frmSockergrodaPreUsage.getContentPane().add(btnCancel);
		
		JLabel lblWarning = new JLabel("WARNING!");
		lblWarning.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblWarning.setBounds(10, 11, 457, 14);
		frmSockergrodaPreUsage.getContentPane().add(lblWarning);
		
		TextArea textArea = new TextArea();
		textArea.setEditable(false);
		textArea.setText("Sockergroda is a free text sharing utility, and some content of this application is user generated.\r\nExplicit text content may occur. The creators of Sockergroda is in no way affiliated with the content served.\r\nSecurity, data management, privacy and other information about this program are specified at https://sockergroda.repl.co/help.\r\nUse this program at your own risk of your information shared.\r\nIf you believe that this service is being abused, please contact the creator (https://sockergroda.repl.co/help#contact).\r\nThis service may at any time be shut down by the owners.\r\nYou are responsible for your own actions using this service and in event of a data leak, the owners have no responsibility for your information.");
		textArea.setBounds(10, 31, 457, 109);
		frmSockergrodaPreUsage.getContentPane().add(textArea);
				
		JCheckBox chckbxConfirm = new JCheckBox("I have read and understood what is stated above");
		chckbxConfirm.setBounds(10, 146, 284, 23);
		frmSockergrodaPreUsage.getContentPane().add(chckbxConfirm);
		
		ChangeListener confirmation = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				btnContinue.setEnabled(chckbxConfirm.isSelected());
				btnContinue.setToolTipText(chckbxConfirm.isSelected() ? null : "You must check the box to use this program");
			}
		};
		
		chckbxConfirm.addChangeListener(confirmation);
		
		confirmation.stateChanged(null);
	}
}
