package sockergroda;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

import org.json.JSONObject;

import sockergroda.enums.Images;

public class MainWindow {
  private JFrame frmSockergroda;
  private JSONObject adData;
  private JLabel advertisement;
  private JLabel lblAdText;
  private boolean loadedAdLast = true;
  
  public static void display() {
    try {
      MainWindow window = new MainWindow();
      window.frmSockergroda.setVisible(true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public MainWindow() {
    initialize();
  }
  
  private void initialize() {
    this.frmSockergroda = new SGFrame();
    this.frmSockergroda.setResizable(false);
    this.frmSockergroda.setTitle("Sockergroda " + Main.versionName);
    boolean adsRemoved = Main.hasRemovedAds();
    this.frmSockergroda.setBounds(100, 100, 450, !adsRemoved ? 280 : 195);
    this.frmSockergroda.setLocationRelativeTo(null);
    this.frmSockergroda.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.frmSockergroda.setIconImage(Images.ICON_32x32.getImage());
    this.frmSockergroda.getContentPane().setLayout(null);

    JButton btnCreate = new JButton("Create");
    btnCreate.setToolTipText("Create a secret to be inspected by other people");
    btnCreate.setIcon(new ImageIcon(Images.CREATE_16x16.getImage()));
    btnCreate.setBounds(100, 76, 105, 24);
    btnCreate.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            MainWindow.this.frmSockergroda.dispose();
            CreateSecretWindow.display();
          }
    });
    this.frmSockergroda.getContentPane().add(btnCreate);
    
    
    JButton btnInspect = new JButton("Inspect");
    btnInspect.setToolTipText("Inspect a key that you have and see what it says");
    btnInspect.setIcon(new ImageIcon(Images.INSPECT_16x16.getImage()));
    btnInspect.setBounds(216, 76, 105, 24);
    btnInspect.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            MainWindow.this.frmSockergroda.dispose();
            InspectSecretWindow.display();
          }
    });
    this.frmSockergroda.getContentPane().add(btnInspect);

    JLabel lblTitle = new JLabel("Sockergroda");
    lblTitle.setBounds(10, 0, 237, 47);
    lblTitle.setIcon(new ImageIcon(Images.ICON_32x32.getImage()));
    lblTitle.setFont(new Font("Segoe UI Historic", Font.PLAIN, 35));
    this.frmSockergroda.getContentPane().add(lblTitle);
    
    JLabel lblVersion = new JLabel(Main.versionName);
    lblVersion.setBounds(245, 27, 85, 14);
    lblVersion.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
    frmSockergroda.getContentPane().add(lblVersion);
    
    JLabel lblCopyright = new JLabel("Copyright \u00A9 Sockergroda 2021. No rights reserved.");
    lblCopyright.setBounds(100, 110, 227, 14);
    lblCopyright.setFont(new Font("Segoe UI Historic", Font.PLAIN, 10));
    frmSockergroda.getContentPane().add(lblCopyright);
    
    JLabel lblAdvertisement = new JLabel("ADVERTISEMENT");
    lblAdvertisement.setBounds(5, 146, 75, 14);
    lblAdvertisement.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 8));
    frmSockergroda.getContentPane().add(lblAdvertisement);
    
    advertisement = new JLabel();
    advertisement.setBounds(5, 163, 424, 48);
    advertisement.setCursor(new Cursor(Cursor.HAND_CURSOR));
    advertisement.addMouseListener(new MouseListener() {
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mousePressed(MouseEvent e) {
			if(adData == null || OpenAdvertisementWindow.openingAdvertisement) {
				return;
			}
			
			OpenAdvertisementWindow.display(adData.getString("url"), adData.getString("id"));
		}
		public void mouseExited(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseClicked(MouseEvent e) {}
	});
    frmSockergroda.getContentPane().add(advertisement);
    
    JSeparator separator_2 = new JSeparator();
    separator_2.setBounds(10, 58, 411, 7);
    frmSockergroda.getContentPane().add(separator_2);
    
    JSeparator separator_2_1 = new JSeparator();
    separator_2_1.setBounds(10, 135, 411, 7);
    frmSockergroda.getContentPane().add(separator_2_1);
    
    lblAdText = new JLabel();
    lblAdText.setFont(new Font("Tahoma", Font.PLAIN, 10));
    lblAdText.setBounds(81, 146, 340, 14);
    frmSockergroda.getContentPane().add(lblAdText);
    
    JMenuBar menuBar = new JMenuBar();
    frmSockergroda.setJMenuBar(menuBar);
    
    JMenu mnConfig = new JMenu("Configuration");
    menuBar.add(mnConfig);
    
    JMenuItem mntmResetConfig = new JMenuItem("Reset configuration...");
    mntmResetConfig.setIcon(new ImageIcon(Images.RESET_CONFIG_16x16.getImage()));
    mntmResetConfig.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			int option = JOptionPane.showConfirmDialog(frmSockergroda, "Do you want to reset the current configuration?\nAll Sockergroda data will be lost.", "Reset Configuration", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if(option == 0) {
				StorageManager.resetFile();
				JOptionPane.showMessageDialog(frmSockergroda, "The configuration has been reset. Sockergroda will now restart.", "Configuration Reset", JOptionPane.INFORMATION_MESSAGE);
				frmSockergroda.dispose();
				Main.main(null);
			}
		}
	});
    
    JCheckBoxMenuItem chckbxmntmAutomaticUpdateChecker = new JCheckBoxMenuItem("Automatically check for updates");
    chckbxmntmAutomaticUpdateChecker.setSelected(StorageManager.getBoolean("automatic_update_check"));
    chckbxmntmAutomaticUpdateChecker.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			StorageManager.setAttribute("automatic_update_check", chckbxmntmAutomaticUpdateChecker.isSelected());
		}
	});
    
    JMenuItem mntmStoredSecrets = new JMenuItem("Saved secrets...");
    mntmStoredSecrets.setIcon(new ImageIcon(Images.SAVED_SECRETS_16x16.getImage()));
    mntmStoredSecrets.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			frmSockergroda.dispose();
			StoredSecretsWindow.display();
		}
	});
    mnConfig.add(mntmStoredSecrets);
    mnConfig.add(chckbxmntmAutomaticUpdateChecker);
    
    JMenuItem mntmRemoveAds = new JMenuItem("Remove advertisements...");
    mntmRemoveAds.setEnabled(!adsRemoved);
    mntmRemoveAds.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			frmSockergroda.dispose();
			RemoveAdvertisements.display();
		}
	});
    mnConfig.add(mntmRemoveAds);
    mnConfig.add(mntmResetConfig);
    
    JMenu mnHelp = new JMenu("Help");
    menuBar.add(mnHelp);
    
    JMenuItem mntmGitHub = new JMenuItem("GitHub repository");
    mntmGitHub.setIcon(new ImageIcon(Images.REPOSITORY_16x16.getImage()));
    mntmGitHub.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	Main.openURL(Main.gitHubUrl);
        }
    });
    
    JMenuItem mntmTroubleshoot = new JMenuItem("Troubleshoot connection...");
    mntmTroubleshoot.setIcon(new ImageIcon(Images.TROUBLESHOOT_CONNECTION_16x16.getImage()));
    mntmTroubleshoot.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	TroubleshootWindow.display();
        	frmSockergroda.dispose();
        }
    });
    mnHelp.add(mntmTroubleshoot);
    mnHelp.add(mntmGitHub);
    
    JMenuItem mntmCheckUpdate = new JMenuItem("Check for update...");
    mntmCheckUpdate.setIcon(new ImageIcon(Images.UPDATE_16x16.getImage()));
    mntmCheckUpdate.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			Main.displayUpdate(false);
		}
	});
    
    JMenuItem mntmReportIssue = new JMenuItem("Report an issue");
    mntmReportIssue.setIcon(new ImageIcon(Images.ISSUE_16x16.getImage()));
    mntmReportIssue.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	Main.openURL(Main.reportIssueUrl);
        }
    });
    mnHelp.add(mntmReportIssue);
    mnHelp.add(mntmCheckUpdate);
    
    JMenuItem mntmAbout = new JMenuItem("About Sockergroda...");
    mntmAbout.setIcon(new ImageIcon(Images.ABOUT_16x16.getImage()));
    mntmAbout.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			frmSockergroda.dispose();
			AboutWindow.display();
		}
	});
    mnHelp.add(mntmAbout);
    
    if(!Main.hasRemovedAds()) {
	    final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
	    executorService.scheduleAtFixedRate(new Runnable() {
	        @Override
	        public void run() {
	        	if(frmSockergroda.isFocused()) {
	        		loadAdvertisement();
	        		loadedAdLast = true;
	        	} else {
	        		loadedAdLast = false;
	        	}
	        }
	    }, 100, 10000, TimeUnit.MILLISECONDS);
	}
    
    final ScheduledExecutorService executorService1 = Executors.newSingleThreadScheduledExecutor();
    executorService1.scheduleAtFixedRate(new Runnable() {
        @Override
        public void run() {
        	if(StorageManager.getBoolean("automatic_update_check") && System.currentTimeMillis() > (StorageManager.getLong("last_update_check") + (24 * 60 * 60 * 1000))) {
        		StorageManager.setAttribute("last_update_check", System.currentTimeMillis());
        		Main.displayUpdate(true);
        	}
            executorService1.shutdown();
        }
    }, 1, 1, TimeUnit.SECONDS);
    
    frmSockergroda.addWindowFocusListener(new WindowFocusListener() {
		@Override
		public void windowLostFocus(WindowEvent e) {}
		@Override
		public void windowGainedFocus(WindowEvent e) {
			if(!loadedAdLast) {
				loadAdvertisement();
				loadedAdLast = true;
			}
		}
	});
  }
  
	private void loadAdvertisement() {
		try {
			this.adData = APIManager.grabAdvertisement();
		} catch (IOException e2) {}
		
		lblAdText.setText(this.adData.getString("text"));
		
		byte[] imageBytes = null;
		if(this.adData != null) {
			imageBytes = Base64.getDecoder().decode(this.adData.getString("banner"));
		}
		Image adBanner = null;
		try {
			adBanner = ImageIO.read(new ByteArrayInputStream(imageBytes));
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		if(this.adData != null) {
		    advertisement.setToolTipText("Click to open the web page for the advertisement");
		    advertisement.setIcon(new ImageIcon(adBanner));
		} else {
			advertisement.setText("Cannot load advertisement.");
		}
	}
}
