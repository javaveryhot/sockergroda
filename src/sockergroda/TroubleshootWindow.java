package sockergroda;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import sockergroda.enums.Images;

public class TroubleshootWindow {

	private JFrame frmSockergrodaTroubleshoot;
	private Map<String, Boolean> testsMap;
	private JList<String> list;

	/**
	 * Launch the application.
	 */
	public static void display() {
		try {
			TroubleshootWindow window = new TroubleshootWindow();
			window.frmSockergrodaTroubleshoot.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public TroubleshootWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSockergrodaTroubleshoot = new SGFrame();
		frmSockergrodaTroubleshoot.setResizable(false);
		frmSockergrodaTroubleshoot.setTitle("Troubleshoot Connection");
		frmSockergrodaTroubleshoot.setBounds(100, 100, 360, 200);
		frmSockergrodaTroubleshoot.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frmSockergrodaTroubleshoot.setIconImage(Images.ICON_32x32.getImage());
	    frmSockergrodaTroubleshoot.setLocationRelativeTo(null);
	    frmSockergrodaTroubleshoot.getContentPane().setLayout(null);
	    
	    JButton btnBack = new JButton("Close");
	    btnBack.setBounds(10, 126, 89, 23);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSockergrodaTroubleshoot.dispose();
			}
		});
	    frmSockergrodaTroubleshoot.getContentPane().add(btnBack);
	    
	    JButton btnBegin = new JButton("Begin");
	    btnBegin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnBegin.setEnabled(false);
				performTests();
			}
		});
	    btnBegin.setBounds(240, 126, 89, 23);
	    frmSockergrodaTroubleshoot.getContentPane().add(btnBegin);
	    
	    testsMap = new HashMap<String, Boolean>();
	    
	    list = new JList<String>();
	    
	    JScrollPane scrollPane = new JScrollPane(list);
	    scrollPane.setBounds(10, 11, 319, 104);
	    frmSockergrodaTroubleshoot.getContentPane().add(scrollPane);
	}
	
	private void performTests() {
		addToTestList("Internet connection", Main.hasInternetConnection());
		addToTestList("API server connection test", APIManager.testSockergrodaAPI().getCode() == 1);
	}
	
	private void addToTestList(String testContext, boolean successful) {
		testsMap.put(testContext, successful);
		updateListModel();
	}
	
	private void updateListModel() {
	    list.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			public int getSize() {
	    		return testsMap.size();
	    	}
	    	public String getElementAt(int index) {
	    		return testsMap.keySet().toArray()[index].toString() + " | " + ((boolean)testsMap.values().toArray()[index] ? "SUCCESS" : "FAIL");
	    	}
	    });
	}
}
