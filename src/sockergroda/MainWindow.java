package sockergroda;

import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;

import sockergroda.enums.Images;

public class MainWindow {
  private JFrame frmSockergroda;
  
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
    this.frmSockergroda.setBounds(100, 100, 450, 220);
    this.frmSockergroda.setLocationRelativeTo(null);
    this.frmSockergroda.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.frmSockergroda.getContentPane().setLayout((LayoutManager)null);
    this.frmSockergroda.setIconImage(Images.ICON_1024x1024.getImage());
    JButton btnCreate = new JButton("Create");
    btnCreate.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            MainWindow.this.frmSockergroda.setVisible(false);
            CreateSecretWindow.display();
          }
        });
    frmSockergroda.getContentPane().setLayout(null);
    frmSockergroda.getContentPane().setLayout(null);
    frmSockergroda.getContentPane().setLayout(null);
    frmSockergroda.getContentPane().setLayout(null);
    frmSockergroda.getContentPane().setLayout(null);
    frmSockergroda.getContentPane().setLayout(null);
    frmSockergroda.getContentPane().setLayout(null);
    frmSockergroda.getContentPane().setLayout(null);
    frmSockergroda.getContentPane().setLayout(null);
    btnCreate.setBounds(100, 111, 105, 24);
    this.frmSockergroda.getContentPane().add(btnCreate);
    JButton btnInspect = new JButton("Inspect");
    btnInspect.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            MainWindow.this.frmSockergroda.setVisible(false);
            InspectSecretWindow.display();
          }
        });
    btnInspect.setBounds(216, 111, 105, 24);
    this.frmSockergroda.getContentPane().add(btnInspect);
    JLabel lblNewLabel = new JLabel("Sockergroda");
    lblNewLabel.setIcon(new ImageIcon(Images.ICON_32x32.getImage()));
    lblNewLabel.setFont(new Font("Segoe UI Historic", Font.PLAIN, 35));
    lblNewLabel.setBounds(10, 0, 237, 47);
    this.frmSockergroda.getContentPane().add(lblNewLabel);
    JSeparator separator = new JSeparator();
    separator.setForeground(Color.LIGHT_GRAY);
    separator.setBackground(Color.GRAY);
    separator.setBounds(0, 47, 444, 7);
    this.frmSockergroda.getContentPane().add(separator);
    JLabel lblNewLabel_1 = new JLabel("This is a free tool that lets you share free-text that is encrypted into a key,");
    lblNewLabel_1.setFont(new Font("Tahoma", 0, 9));
    lblNewLabel_1.setBounds(31, 61, 384, 14);
    this.frmSockergroda.getContentPane().add(lblNewLabel_1);
    JLabel lblNewLabel_1_1 = new JLabel("which can be decrypted by the recipient with a password.");
    lblNewLabel_1_1.setFont(new Font("Tahoma", 0, 9));
    lblNewLabel_1_1.setBounds(31, 75, 384, 14);
    this.frmSockergroda.getContentPane().add(lblNewLabel_1_1);
    
    JLabel lblNewLabel_2_1 = new JLabel("License");
    lblNewLabel_2_1.setForeground(SystemColor.desktop);
    lblNewLabel_2_1.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 9));
    lblNewLabel_2_1.setBounds(10, 155, 54, 25);
    lblNewLabel_2_1.addMouseListener(new MouseListener() {
		public void mouseReleased(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseClicked(MouseEvent e) {
			frmSockergroda.setVisible(false);
			License.display();
		}
	});
    frmSockergroda.getContentPane().add(lblNewLabel_2_1);
    
    JLabel lblNewLabel_3 = new JLabel(Main.version);
    lblNewLabel_3.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
    lblNewLabel_3.setBounds(245, 27, 85, 14);
    frmSockergroda.getContentPane().add(lblNewLabel_3);
    
    JLabel lblNewLabel_2 = new JLabel("Copyright \u00A9 Sockergroda 2021. No rights reserved.");
    lblNewLabel_2.setFont(new Font("Segoe UI Historic", Font.PLAIN, 10));
    lblNewLabel_2.setBounds(207, 155, 227, 14);
    frmSockergroda.getContentPane().add(lblNewLabel_2);
  }
}
