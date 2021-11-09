package sockergroda;

import java.awt.Cursor;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import sockergroda.enums.Images;
import utils.ImageComponent;

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
		frmSockergrodaInit.setResizable(false);
		frmSockergrodaInit.setBounds(100, 100, 380, 210);
		frmSockergrodaInit.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    frmSockergrodaInit.setIconImage(Images.ICON_32x32.getImage());
	    frmSockergrodaInit.setLocationRelativeTo(null);
	    frmSockergrodaInit.getContentPane().setLayout(null);
	    frmSockergrodaInit.setCursor(new Cursor(Cursor.WAIT_CURSOR));
	    frmSockergrodaInit.setUndecorated(true);
	    
	    lblStatus = new JLabel();
	    lblStatus.setBounds(20, 11, 360, 33);
	    frmSockergrodaInit.getContentPane().add(lblStatus);
	    lblStatus.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 16));
	    
	    progressBar = new JProgressBar();
	    progressBar.setBounds(10, 16, 360, 22);
	    frmSockergrodaInit.getContentPane().add(progressBar);
	    progressBar.setIndeterminate(true);
	    
	    ImageComponent panel = new ImageComponent(Images.INIT_BACKGROUND.getImage().getScaledInstance(frmSockergrodaInit.getWidth(), frmSockergrodaInit.getHeight(), 0));
	    panel.setBounds(0, 0, 380, 210);
	    frmSockergrodaInit.getContentPane().add(panel);
	    panel.setLayout(null);
	    
	    JLabel lblBanner = new JLabel();
	    lblBanner.setBounds(170, 38, 298, 256);
	    panel.add(lblBanner);
	    lblBanner.setIcon(new ImageIcon(Images.ICON_256x256.getImage()));
	    
	    setStatus("Please wait", 0);
	    
	    frmSockergrodaInit.setVisible(true);
	}
	
	public void setStatus(String text, int progress) {
		this.lblStatus.setText(text + " ...");
		this.progressBar.setValue(progress);
	}
	
	public void dispose() {
		frmSockergrodaInit.dispose();
	}
}
