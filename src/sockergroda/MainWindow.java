package sockergroda;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

import org.json.JSONObject;

import sockergroda.enums.Images;
import utils.VersionConverter;

public class MainWindow {
  private JFrame frmSockergroda;
  private JSONObject adData;
  private JLabel advertisement;
  
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
    this.frmSockergroda = new JFrame();
    this.frmSockergroda.setResizable(false);
    this.frmSockergroda.setTitle("Sockergroda " + Main.version);
    this.frmSockergroda.setBounds(100, 100, 450, 300);
    this.frmSockergroda.setLocationRelativeTo(null);
    this.frmSockergroda.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.frmSockergroda.getContentPane().setLayout((LayoutManager)null);
    this.frmSockergroda.setIconImage(Images.ICON_1024x1024.getImage());
    this.frmSockergroda.setLayout(null);
    JButton btnCreate = new JButton("Create");
    btnCreate.setBounds(100, 111, 105, 24);
    btnCreate.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            MainWindow.this.frmSockergroda.setVisible(false);
            CreateSecretWindow.display();
          }
        });
    this.frmSockergroda.getContentPane().add(btnCreate);
    JButton btnInspect = new JButton("Inspect");
    btnInspect.setBounds(216, 111, 105, 24);
    btnInspect.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            MainWindow.this.frmSockergroda.setVisible(false);
            InspectSecretWindow.display();
          }
        });
    this.frmSockergroda.getContentPane().add(btnInspect);
    JLabel lblNewLabel = new JLabel("Sockergroda");
    lblNewLabel.setBounds(10, 0, 237, 47);
    lblNewLabel.setIcon(new ImageIcon(Images.ICON_32x32.getImage()));
    lblNewLabel.setFont(new Font("Segoe UI Historic", Font.PLAIN, 35));
    this.frmSockergroda.getContentPane().add(lblNewLabel);
    JSeparator separator = new JSeparator();
    separator.setBounds(0, 47, 444, 7);
    separator.setForeground(Color.LIGHT_GRAY);
    separator.setBackground(Color.GRAY);
    this.frmSockergroda.getContentPane().add(separator);
    JLabel lblNewLabel_1 = new JLabel("This is a free tool that lets you share free-text that is encrypted into a key,");
    lblNewLabel_1.setBounds(31, 61, 384, 14);
    lblNewLabel_1.setFont(new Font("Tahoma", 0, 9));
    this.frmSockergroda.getContentPane().add(lblNewLabel_1);
    JLabel lblNewLabel_1_1 = new JLabel("which can be decrypted by the recipient with a password.");
    lblNewLabel_1_1.setBounds(31, 75, 384, 14);
    lblNewLabel_1_1.setFont(new Font("Tahoma", 0, 9));
    this.frmSockergroda.getContentPane().add(lblNewLabel_1_1);
    
    JLabel lblNewLabel_2_1 = new JLabel("License");
    lblNewLabel_2_1.setBounds(10, 155, 54, 25);
    lblNewLabel_2_1.setForeground(SystemColor.desktop);
    lblNewLabel_2_1.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 9));
    lblNewLabel_2_1.addMouseListener(new MouseListener() {
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mousePressed(MouseEvent e) {
			frmSockergroda.setVisible(false);
			License.display();
		}
		public void mouseExited(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseClicked(MouseEvent e) {}
	});
    frmSockergroda.getContentPane().add(lblNewLabel_2_1);
    
    JLabel lblNewLabel_3 = new JLabel(Main.version);
    lblNewLabel_3.setBounds(245, 27, 85, 14);
    lblNewLabel_3.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
    frmSockergroda.getContentPane().add(lblNewLabel_3);
    
    JLabel lblNewLabel_2 = new JLabel("Copyright \u00A9 Sockergroda 2021. No rights reserved.");
    lblNewLabel_2.setBounds(207, 155, 227, 14);
    lblNewLabel_2.setFont(new Font("Segoe UI Historic", Font.PLAIN, 10));
    frmSockergroda.getContentPane().add(lblNewLabel_2);
    
    JSeparator separator_1 = new JSeparator();
    separator_1.setBounds(0, 184, 444, 7);
    separator_1.setForeground(Color.LIGHT_GRAY);
    separator_1.setBackground(Color.GRAY);
    frmSockergroda.getContentPane().add(separator_1);
    
    JLabel lblNewLabel_4 = new JLabel("ADVERTISEMENT");
    lblNewLabel_4.setBounds(5, 191, 60, 14);
    lblNewLabel_4.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 8));
    frmSockergroda.getContentPane().add(lblNewLabel_4);
    
    advertisement = new JLabel();
    advertisement.setBounds(5, 208, 424, 48);
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
    
    final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    executorService.scheduleAtFixedRate(new Runnable() {
        @Override
        public void run() {
        	loadAdvertisement();
            executorService.shutdown();
        }
    }, 100, 1, TimeUnit.MILLISECONDS);
    
    final ScheduledExecutorService executorService1 = Executors.newSingleThreadScheduledExecutor();
    executorService1.scheduleAtFixedRate(new Runnable() {
        @Override
        public void run() {
        	checkVersion();
            executorService1.shutdown();
        }
    }, 7, 1, TimeUnit.SECONDS);
  }
  
  private void loadAdvertisement() {
	try {
		this.adData = APIManager.grabAdvertisement();
	} catch (IOException e2) {}
	
	byte[] imageBytes = null;
	if(this.adData != null) {
		imageBytes = Base64.getDecoder().decode(this.adData.getString("banner"));
	}
	Image adBanner = Images.AD_SAMPLE.getImage();
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
  
  private void checkVersion() {
	  if(!frmSockergroda.isVisible()) {
		  return;
	  }
	  try {
		JSONObject globalVersionData = APIManager.grabVersionData();
		int latestVersion = globalVersionData.getInt("latest_version");
		if(latestVersion > Main.versionInt) {
			String[] options = {"Download", "Not now"};
			int updateChoice = JOptionPane.showOptionDialog(frmSockergroda, "A new version (" + VersionConverter.intToString(latestVersion) + ") is available.\nUpdate for new features, bug fixes and improvements.", "Please Update Sockergroda", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Images.ICON_32x32.getImage()), options, options[1]);
			if(updateChoice == 0) {
				Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
				if(desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
					try {
						desktop.browse(new URI(Main.downloadUrl));
					} catch (IOException | URISyntaxException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(frmSockergroda, "Could not open the web page.", "Browser Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	} catch (IOException e) {
		e.printStackTrace();
	}
  }
}
