package sockergroda;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import sockergroda.enums.Images;

public class FeedbackWindow {

	private JFrame frmSockergrodaFeedback;

	/**
	 * Launch the application.
	 */
	public static void display() {
		try {
			FeedbackWindow window = new FeedbackWindow();
			window.frmSockergrodaFeedback.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the application.
	 */
	public FeedbackWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSockergrodaFeedback = new SGFrame();
		frmSockergrodaFeedback.setResizable(false);
		frmSockergrodaFeedback.setTitle("Feedback");
		frmSockergrodaFeedback.setBounds(100, 100, 391, 150);
		frmSockergrodaFeedback.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frmSockergrodaFeedback.setIconImage(Images.ICON_32x32.getImage());
	    frmSockergrodaFeedback.setLocationRelativeTo(null);
	    frmSockergrodaFeedback.getContentPane().setLayout(null);
	    
	    JButton btnIgnore = new JButton("I'm good, thanks");
	    btnIgnore.setIcon(new ImageIcon(Images.THUMBS_UP_16x16.getImage()));
	    btnIgnore.setBounds(10, 80, 168, 23);
		btnIgnore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSockergrodaFeedback.dispose();
			}
		});
	    frmSockergrodaFeedback.getContentPane().add(btnIgnore);
	    
	    JSeparator separator_2 = new JSeparator();
	    separator_2.setBounds(10, 69, 355, 6);
	    frmSockergrodaFeedback.getContentPane().add(separator_2);
	    
	    JButton btnReportIssue = new JButton("I've found an issue");
	    btnReportIssue.setIcon(new ImageIcon(Images.EYE_16x16.getImage()));
	    btnReportIssue.setBounds(188, 80, 177, 23);
	    btnReportIssue.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.openURL(Main.reportIssueUrl);
				frmSockergrodaFeedback.dispose();
			}
		});
	    frmSockergrodaFeedback.getContentPane().add(btnReportIssue);
	    
	    JLabel lblNewLabel = new JLabel("Have you discovered a bug or error within Sockergroda?");
	    lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    lblNewLabel.setBounds(10, 11, 355, 14);
	    frmSockergrodaFeedback.getContentPane().add(lblNewLabel);
	    
	    JLabel lblText2 = new JLabel("If you have, please let us know so that we can improve the program.");
	    lblText2.setHorizontalAlignment(SwingConstants.CENTER);
	    lblText2.setBounds(10, 36, 355, 14);
	    frmSockergrodaFeedback.getContentPane().add(lblText2);
	}
}
