package sockergroda;

import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;

import sockergroda.enums.Images;

public class ChangeLogWindow {

	private JFrame frmSockergrodaLicense;

	/**
	 * Launch the application.
	 */
	public static void display() {
		try {
			ChangeLogWindow window = new ChangeLogWindow();
			window.frmSockergrodaLicense.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public ChangeLogWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSockergrodaLicense = new SGFrame();
		frmSockergrodaLicense.setResizable(false);
		frmSockergrodaLicense.setTitle("Changelog " + Main.versionName);
		frmSockergrodaLicense.setBounds(100, 100, 850, 385);
		frmSockergrodaLicense.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frmSockergrodaLicense.setIconImage(Images.ICON_32x32.getImage());
	    frmSockergrodaLicense.setLocationRelativeTo(null);
	    frmSockergrodaLicense.getContentPane().setLayout(null);
	    
	    JButton btnBack = new JButton("Close");
	    btnBack.setBounds(10, 312, 89, 23);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSockergrodaLicense.dispose();
			}
		});
	    frmSockergrodaLicense.getContentPane().add(btnBack);
	    
	    TextArea textArea = new TextArea();
	    textArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
	    textArea.setEditable(false);
	    textArea.setBounds(10, 22, 814, 270);
	    String fileContent = "";
	    StringBuffer stringBuffer = new StringBuffer();
    	try {
    		InputStream inputStream = getClass().getResource("/resources/changelog.txt").openStream();

    		if(inputStream != null) {
    			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    			String currentLine = "";
    			while((currentLine = reader.readLine()) != null) {
    				stringBuffer.append(currentLine + "\n");
    			}
    			fileContent = stringBuffer.toString();
    		} else {
    			fileContent = "Could not read the changelog file.";
    		}

    	    inputStream.close();
	    } catch(IOException e) {
	    	fileContent = "Couldn't read the changelog file;\n" + e.toString();
	    }
	    textArea.setText(fileContent);
	    frmSockergrodaLicense.getContentPane().add(textArea);
	}
}
