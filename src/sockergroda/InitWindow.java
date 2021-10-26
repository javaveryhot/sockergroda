package sockergroda;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import sockergroda.enums.Images;

public class InitWindow {

	private JFrame frmSockergrodaInit;
	private JLabel lblStatus;
	private JProgressBar progressBar;
	
	/**
	 * Launch the application.
	 */
	public static InitWindow display() {
		try {
			InitWindow window = new InitWindow();
			window.frmSockergrodaInit.setVisible(true);
			return window;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Create the application.
	 */
	public InitWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSockergrodaInit = new SGFrame();
		frmSockergrodaInit.getContentPane().setBackground(new Color(50, 150, 70));
		frmSockergrodaInit.setResizable(false);
		frmSockergrodaInit.setBounds(100, 100, 380, 210);
		frmSockergrodaInit.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    frmSockergrodaInit.setIconImage(Images.ICON_32x32.getImage());
	    frmSockergrodaInit.setLocationRelativeTo(null);
	    frmSockergrodaInit.getContentPane().setLayout(null);
	    frmSockergrodaInit.setCursor(new Cursor(Cursor.WAIT_CURSOR));
	    
	    JLabel lblBanner = new JLabel();
	    lblBanner.setIcon(new ImageIcon(Images.INIT_BANNER.getImage()));
	    lblBanner.setBounds(0, 115, 380, 95);
	    frmSockergrodaInit.getContentPane().add(lblBanner);
	    
	    lblStatus = new JLabel();
	    lblStatus.setForeground(new Color(255, 255, 255));
	    lblStatus.setFont(new Font("Rockwell", Font.PLAIN, 16));
	    lblStatus.setBounds(10, 11, 360, 33);
	    frmSockergrodaInit.getContentPane().add(lblStatus);
	    
	    progressBar = new JProgressBar();
	    progressBar.setBounds(20, 55, 340, 22);
	    frmSockergrodaInit.getContentPane().add(progressBar);
	    frmSockergrodaInit.setUndecorated(true);
	    
	    setStatus("Please wait", 0);
	}
	
	public void setStatus(String text, int progress) {
		this.lblStatus.setText(text + " ...");
		this.progressBar.setValue(progress);
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void dispose() {
		frmSockergrodaInit.dispose();
	}
}
