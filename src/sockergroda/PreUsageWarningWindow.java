package sockergroda;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import sockergroda.enums.Images;

public class PreUsageWarningWindow {

	private JFrame frmSockergrodaPreUsage;
	private boolean scrolledToBottom = false;

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
		
		JButton btnContinue = new JButton("Continue");
		btnContinue.setBounds(380, 146, 87, 23);
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSockergrodaPreUsage.dispose();
				StorageManager.setAttribute("pre_usage_warning_confirmed", true);
				Main.main(null);
			}
		});
		frmSockergrodaPreUsage.getContentPane().setLayout(null);
		frmSockergrodaPreUsage.getContentPane().add(btnContinue);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(300, 146, 70, 23);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		frmSockergrodaPreUsage.getContentPane().add(btnCancel);
		
		JLabel lblWarning = new JLabel("WARNING!");
		lblWarning.setBounds(10, 11, 457, 14);
		lblWarning.setFont(new Font("Tahoma", Font.BOLD, 14));
		frmSockergrodaPreUsage.getContentPane().add(lblWarning);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textArea.setEditable(false);
		textArea.setText("Sockergroda is a free text sharing utility, and some content of this application is user generated.\r\nExplicit text content may occur. The creators of Sockergroda is in no way affiliated with the content served.\r\nSecurity, data management, privacy and other information about this program are specified at https://sockergroda.repl.co/help.\r\nUse this program at your very own risk.\r\nSockergroda serves no guarantee at all.\r\nIf you believe that this service is being abused, please contact the creator (https://sockergroda.repl.co/help#contact).\r\nThis service may at any time be shut down by the owners.\r\nYou are responsible for your own actions using this service and in event of a data leak, the owners have no responsibility for your information.");
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(10, 31, 457, 109);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		frmSockergrodaPreUsage.getContentPane().add(scrollPane);
				
		JCheckBox chckbxConfirm = new JCheckBox("I have read and understood what is stated above");
		chckbxConfirm.setBounds(10, 146, 284, 23);
		chckbxConfirm.setEnabled(false);
		chckbxConfirm.setToolTipText("Please read what you are agreeing to before checking!");
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
		
		scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				if(!scrolledToBottom && scrollPane.getVerticalScrollBar().getVisibleAmount() + e.getValue() == scrollPane.getVerticalScrollBar().getMaximum()) {
					scrolledToBottom = true;
					
				    final ScheduledExecutorService enableCheckBox = Executors.newSingleThreadScheduledExecutor();
				    enableCheckBox.scheduleAtFixedRate(new Runnable() {
				        @Override
				        public void run() {
				        	chckbxConfirm.setEnabled(true);
				        	chckbxConfirm.setToolTipText(null);
				        	enableCheckBox.shutdown();
				        }
				    }, 2, 1, TimeUnit.SECONDS);
				}
			}
		});
	}
}
