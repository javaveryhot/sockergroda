package sockergroda;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import sockergroda.enums.Images;

public class PerformUpdateWindow {

	private JFrame frmSockergrodaUpdate;
	private UpdatePerformer updatePerformer;

	/**
	 * Launch the application.
	 */
	public static void display() {
		try {
			PerformUpdateWindow window = new PerformUpdateWindow();
			window.frmSockergrodaUpdate.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public PerformUpdateWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSockergrodaUpdate = new SGFrame();
		frmSockergrodaUpdate.setResizable(false);
		frmSockergrodaUpdate.setTitle("Updating Sockergroda");
		frmSockergrodaUpdate.setBounds(100, 100, 500, 220);
		frmSockergrodaUpdate.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    frmSockergrodaUpdate.setIconImage(Images.ICON_32x32.getImage());
	    frmSockergrodaUpdate.setLocationRelativeTo(null);
	    frmSockergrodaUpdate.getContentPane().setLayout(null);
	    
	    JLabel lblTitle = new JLabel("Updating Sockergroda");
	    lblTitle.setBounds(10, 0, 445, 47);
	    lblTitle.setIcon(new ImageIcon(Images.ICON_32x32.getImage()));
	    lblTitle.setFont(new Font("Segoe UI Historic", Font.PLAIN, 35));
	    frmSockergrodaUpdate.getContentPane().add(lblTitle);
	    
	    JLabel lblStatus = new JLabel();
	    lblStatus.setText("A new version of Sockergroda is being downloaded");
	    lblStatus.setFont(new Font("Tahoma", Font.BOLD, 14));
	    lblStatus.setBounds(20, 58, 435, 23);
	    frmSockergrodaUpdate.getContentPane().add(lblStatus);
	    
	    JLabel lblDescription = new JLabel();
	    lblDescription.setText("Starting the update...");
	    lblDescription.setBounds(30, 92, 425, 14);
	    frmSockergrodaUpdate.getContentPane().add(lblDescription);
	    
	    JSeparator separator = new JSeparator();
	    separator.setBounds(10, 48, 445, 14);
	    frmSockergrodaUpdate.getContentPane().add(separator);
	    
	    JProgressBar downloadProgress = new JProgressBar();
	    downloadProgress.setBounds(70, 117, 340, 14);
	    frmSockergrodaUpdate.getContentPane().add(downloadProgress);
	    
	    JButton btnClose = new JButton("Close");
	    btnClose.setEnabled(false);
	    btnClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frmSockergrodaUpdate.dispose();
			}
		});
	    btnClose.setBounds(10, 142, 89, 23);
	    frmSockergrodaUpdate.getContentPane().add(btnClose);
	    
	    JButton btnRetry = new JButton("Retry");
	    btnRetry.setEnabled(false);
	    btnRetry.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frmSockergrodaUpdate.dispose();
				PerformUpdateWindow.display();
			}
		});
	    btnRetry.setBounds(109, 142, 89, 23);
	    frmSockergrodaUpdate.getContentPane().add(btnRetry);
	    
	    JCheckBox chckbxLaunch = new JCheckBox("Launch new version when finished");
	    chckbxLaunch.setSelected(true);
	    chckbxLaunch.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updatePerformer.setLaunchOnFinish(chckbxLaunch.isSelected());
			}
		});
	    chckbxLaunch.setBounds(204, 142, 251, 23);
	    frmSockergrodaUpdate.getContentPane().add(chckbxLaunch);
	    
	    String[] downloadOptions = {
	    		"Jar",
	    		"Exe"
	    };
	    
	    Object selectedFileType = JOptionPane.showInputDialog(frmSockergrodaUpdate, "What file type would you like to download?", "Choose File Type", JOptionPane.PLAIN_MESSAGE, new ImageIcon(Images.ICON_16x16.getImage()), downloadOptions, downloadOptions[0]);
	    
	    updatePerformer = new UpdatePerformer(selectedFileType.toString() == downloadOptions[0] ? "application/java-archive" : "application/x-msdownload");
	    updatePerformer.setLaunchOnFinish(true);
	    
	    updatePerformer.setDoneListener(new OperationListener() {
			@Override
			public void onPerformed() {
				downloadProgress.setValue(100);
				
	    	    if(updatePerformer.isSuccessful()) {
		    	    frmSockergrodaUpdate.setTitle("Update Finished");
		    	    lblTitle.setText("Update Finished");
		    	    lblStatus.setText("The newest version of Sockergroda has been downloaded");
		    	    lblStatus.setIcon(new ImageIcon(Images.UPDATE_SUCCESSFUL_16x16.getImage()));
		    	    lblDescription.setText("You can now close this window.");
	    	    } else {
		    	    frmSockergrodaUpdate.setTitle("Update Failed");
		    	    lblTitle.setText("Update Failed");
		    	    lblStatus.setText("Could not complete the update");
		    	    lblStatus.setIcon(new ImageIcon(Images.UPDATE_UNSUCCESSFUL_16x16.getImage()));
		    	    lblDescription.setText(updatePerformer.getAbortReason());
		    	    btnRetry.setEnabled(true);
	    	    }

	    	    btnClose.setEnabled(true);
			}
		});
	    
	    updatePerformer.setOperationChangeListener(new OperationListener() {
			@Override
			public void onPerformed() {
				lblDescription.setText(updatePerformer.getCurrentOperation());
				downloadProgress.setValue(updatePerformer.getCurrentOperationPercentage());
			}
		});
	    
	    
	    final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
	    executorService.scheduleAtFixedRate(new Runnable() {
	        @Override
	        public void run() {
	        	updatePerformer.beginUpdate();
	            executorService.shutdown();
	        }
	    }, 1, 1, TimeUnit.SECONDS);
	    
	    
	    frmSockergrodaUpdate.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(!updatePerformer.hasFinished()) {
					JOptionPane.showMessageDialog(frmSockergrodaUpdate, "An update is currently running! Please wait for it to finish before closing it.", "Cannot Stop Update", JOptionPane.ERROR_MESSAGE);
				} else {
					frmSockergrodaUpdate.dispose();
				}
			}
		});
	}
}
