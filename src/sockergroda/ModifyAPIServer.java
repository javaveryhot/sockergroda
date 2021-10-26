package sockergroda;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import sockergroda.enums.Images;

public class ModifyAPIServer {

	private JFrame frmSockergrodaModifyAPI;
	private JTextField txtURL;

	/**
	 * Launch the application.
	 */
	public static void display() {
		try {
			ModifyAPIServer window = new ModifyAPIServer();
			window.frmSockergrodaModifyAPI.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public ModifyAPIServer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSockergrodaModifyAPI = new SGFrame();
		frmSockergrodaModifyAPI.setResizable(false);
		frmSockergrodaModifyAPI.setTitle("Modify API Server Host");
		frmSockergrodaModifyAPI.setBounds(100, 100, 385, 140);
		frmSockergrodaModifyAPI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frmSockergrodaModifyAPI.setIconImage(Images.ICON_32x32.getImage());
	    frmSockergrodaModifyAPI.setLocationRelativeTo(null);
	    frmSockergrodaModifyAPI.getContentPane().setLayout(null);
	    
	    JButton btnBack = new JButton("Close");
	    btnBack.setBounds(270, 68, 89, 23);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSockergrodaModifyAPI.dispose();
			}
		});
	    frmSockergrodaModifyAPI.getContentPane().add(btnBack);
	    
	    txtURL = new JTextField();
	    txtURL.setText(Main.hostNameAPI);
	    txtURL.setFont(new Font("Monospaced", Font.PLAIN, 11));
	    txtURL.setBounds(10, 37, 349, 20);
	    frmSockergrodaModifyAPI.getContentPane().add(txtURL);
	    txtURL.setColumns(10);
	    
	    JLabel lblText = new JLabel("API server host:");
	    lblText.setBounds(10, 12, 362, 14);
	    frmSockergrodaModifyAPI.getContentPane().add(lblText);
	    
	    JButton btnConfirm = new JButton("Confirm");
	    btnConfirm.setBounds(10, 68, 89, 23);
	    btnConfirm.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		StorageManager.setAttribute("override_api_host", txtURL.getText());
	    		JOptionPane.showMessageDialog(frmSockergrodaModifyAPI, "The API host has been changed.", "API Host Changed", JOptionPane.INFORMATION_MESSAGE);
				Main.updateAPIHost();
	    		frmSockergrodaModifyAPI.dispose();
	    	}
	    });
	    frmSockergrodaModifyAPI.getContentPane().add(btnConfirm);
	    
	    KeyListener hostAPIInputChange = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
				btnConfirm.setEnabled(!txtURL.getText().equalsIgnoreCase(Main.hostNameAPI) && txtURL.getText().length() > 0);
			}
			@Override
			public void keyPressed(KeyEvent e) {
			}
	    };
	    
	    txtURL.addKeyListener(hostAPIInputChange);
	    hostAPIInputChange.keyReleased(null);
	    
	    JButton btnReset = new JButton("Reset");
	    btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(StorageManager.hasAttribute("override_api_host")) {
					StorageManager.deleteAttribute("override_api_host");
					JOptionPane.showMessageDialog(frmSockergrodaModifyAPI, "The API host has been reset to default.", "API Host Reset", JOptionPane.INFORMATION_MESSAGE);
					Main.updateAPIHost();
					frmSockergrodaModifyAPI.dispose();
				} else {
					JOptionPane.showMessageDialog(frmSockergrodaModifyAPI, "The API host is already set to default!", "No Custom Host", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	    btnReset.setBounds(111, 68, 89, 23);
	    frmSockergrodaModifyAPI.getContentPane().add(btnReset);
	}
}
