package sockergroda;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import sockergroda.enums.Images;

public class OpenAdvertisementWindow {

	private JFrame frmSockergrodaOpenAd;
	private boolean isCanceled = false;
	private JLabel lblWarning;
	private String url;
	private String id;
	private int waitSeconds;
	
	public static boolean openingAdvertisement = false;

	/**
	 * Launch the application.
	 */
	public static void display(String url, String id) {
		try {
			OpenAdvertisementWindow window = new OpenAdvertisementWindow(url, id);
			window.frmSockergrodaOpenAd.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public OpenAdvertisementWindow(String url, String id) {
		openingAdvertisement = true;
		this.url = url;
		this.id = id;
		this.waitSeconds = 3;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSockergrodaOpenAd = new SGFrame();
		frmSockergrodaOpenAd.setResizable(false);
		frmSockergrodaOpenAd.setTitle("Opening Webpage");
		frmSockergrodaOpenAd.setBounds(100, 100, 240, 135);
	    frmSockergrodaOpenAd.setIconImage(Images.ICON_32x32.getImage());
	    frmSockergrodaOpenAd.setLocationRelativeTo(null);
	    frmSockergrodaOpenAd.getContentPane().setLayout(null);
	    frmSockergrodaOpenAd.setAlwaysOnTop(true);
	    
	    JButton btnCancel = new JButton("Cancel");
	    btnCancel.setBounds(10, 63, 89, 23);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isCanceled = true;
				frmSockergrodaOpenAd.dispose();
			}
		});
	    frmSockergrodaOpenAd.getContentPane().add(btnCancel);
	    
	    lblWarning = new JLabel();
	    lblWarning.setFont(new Font("Segoe UI", Font.PLAIN, 11));
	    lblWarning.setBounds(10, 11, 204, 14);
	    frmSockergrodaOpenAd.getContentPane().add(lblWarning);
	    
	    JLabel lblUrl = new JLabel(url);
	    lblUrl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
	    lblUrl.setHorizontalAlignment(SwingConstants.CENTER);
	    lblUrl.setBounds(10, 29, 204, 23);
	    frmSockergrodaOpenAd.getContentPane().add(lblUrl);
	    
	    frmSockergrodaOpenAd.addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}

			@Override
			public void windowClosing(WindowEvent e) {
				isCanceled = true;
			}
			
			public void windowClosed(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
		});
	    
    	this.countdownCaller(this.waitSeconds);
	}
	
	private void countdownCaller(int secondsLeft) {
		if(this.isCanceled) {
			openingAdvertisement = false;
			return;
		}
		
    	if(secondsLeft == 0) {
    		frmSockergrodaOpenAd.dispose();
			Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
			if(desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
				try {
					desktop.browse(new URI(url));
				} catch (IOException | URISyntaxException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(frmSockergrodaOpenAd, "Could not open the web page.", "Browser Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			openingAdvertisement = false;
			return;
    	}
    	lblWarning.setText("Opening webpage in " + secondsLeft + " seconds...");
    	if(secondsLeft != 1) {
    	    final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    	    executorService.scheduleAtFixedRate(new Runnable() {
    	        @Override
    	        public void run() {
    	            countdownCaller(secondsLeft - 1);
    	            executorService.shutdown();
    	        }
    	    }, 1, 1, TimeUnit.SECONDS);
    	} else {
    		// On the last second, instead of just waiting 1 second, it sends the click registration to the server and then waits the remaining time
    		long prevTime = System.currentTimeMillis();

    		APIManager.registerAdvertisementClicked(id);

    		int remTime = (int)Math.max(1000 - (System.currentTimeMillis() - prevTime), 0);
    	    final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    	    executorService.scheduleAtFixedRate(new Runnable() {
    	        @Override
    	        public void run() {
    	            countdownCaller(secondsLeft - 1);
    	            executorService.shutdown();
    	        }
    	    }, remTime, 1, TimeUnit.MILLISECONDS);
    	}
	}
}
