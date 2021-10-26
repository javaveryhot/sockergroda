package sockergroda;

import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import sockergroda.enums.Images;

public class RandomOutputTextField {

	private JFrame frmSockergrodaInspecting;
	
	private String wndTitle;
	private String output;

	/**
	 * Launch the application.
	 */
	public static void display(String wndTitle, String output) {
		try {
			RandomOutputTextField window = new RandomOutputTextField(wndTitle, output);
			window.frmSockergrodaInspecting.setVisible(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public RandomOutputTextField(String wndTitle, String output) {
		this.wndTitle = wndTitle;
		this.output = output;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSockergrodaInspecting = new SGFrame();
		frmSockergrodaInspecting.setTitle(wndTitle);
		frmSockergrodaInspecting.setResizable(false);
		frmSockergrodaInspecting.setIconImage(Images.ICON_32x32.getImage());
		frmSockergrodaInspecting.setBounds(100, 100, 450, 340);
		frmSockergrodaInspecting.setLocationRelativeTo(null);
		frmSockergrodaInspecting.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmSockergrodaInspecting.getContentPane().setLayout(null);
		
		TextArea textArea = new TextArea(output);
		textArea.setFont(new Font("Monospaced", Font.BOLD, 12));
		textArea.setEditable(false);
		textArea.setBounds(10, 10, 380, 225);
		frmSockergrodaInspecting.getContentPane().add(textArea);
		
		JButton btnClose = new JButton("Close");
		btnClose.setMnemonic('C');
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSockergrodaInspecting.dispose();
			}
		});
		btnClose.setBounds(342, 265, 70, 23);
		frmSockergrodaInspecting.getContentPane().add(btnClose);
		
		JButton btnCopy = new JButton("Copy content");
		btnCopy.setIcon(new ImageIcon(Images.COPY_16x16.getImage()));
		btnCopy.setBounds(10, 265, 128, 23);
		btnCopy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.copyToClipboard(output);
			}
		});
		frmSockergrodaInspecting.getContentPane().add(btnCopy);
	}
}
