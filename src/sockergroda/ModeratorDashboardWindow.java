package sockergroda;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import sockergroda.enums.Images;
import utils.APIResponse;
import utils.TimeStrings;

public class ModeratorDashboardWindow {

	private JFrame frmSockergrodaModDashb;
	private JButton btnDeny;
	private JButton btnViewContent;
	private JButton btnConfirm;
	private JTable table;
	private JButton btnRefresh;

	private String moderatorKey;
	private JSONArray reports;

	/**
	 * Launch the application.
	 */
	public static void display(String modKey) {
		try {
			ModeratorDashboardWindow window = new ModeratorDashboardWindow(modKey);
			window.frmSockergrodaModDashb.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public ModeratorDashboardWindow(String modKey) {
		this.moderatorKey = modKey;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSockergrodaModDashb = new SGFrame();
		frmSockergrodaModDashb.setResizable(false);
		frmSockergrodaModDashb.setTitle("Moderator Dashboard");
		frmSockergrodaModDashb.setBounds(100, 100, 550, 300);
		frmSockergrodaModDashb.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frmSockergrodaModDashb.setIconImage(Images.ICON_32x32.getImage());
	    frmSockergrodaModDashb.setLocationRelativeTo(null);
	    
		reports = new JSONArray();
		
		APIResponse response = APIManager.moderatorGrabReports(moderatorKey);
		
		if(response.isRequestSuccessful() && response.getCode() == 1) {
			reports = response.getJSONArrayValue("reports");
		} else {
			JOptionPane.showMessageDialog(frmSockergrodaModDashb, "Cannot load reports. Failed access.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	    JButton btnBack = new JButton("Close");
	    btnBack.setMnemonic('C');
	    btnBack.setBounds(10, 226, 89, 23);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSockergrodaModDashb.dispose();
			}
		});
	    frmSockergrodaModDashb.getContentPane().setLayout(null);
	    frmSockergrodaModDashb.getContentPane().add(btnBack);

	    JList<String> list = new JList<String>();
	    list.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			public int getSize() {
	    		return reports.length();
	    	}
	    	public String getElementAt(int index) {
	    		JSONObject reportData = reports.getJSONObject(index);
	    		return reportData.getInt("id") + "> " + reportData.getString("title");
	    	}
	    });
	    list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				btnDeny.setEnabled(list.getSelectedIndex() != -1);
				btnViewContent.setEnabled(list.getSelectedIndex() != -1);
				btnConfirm.setEnabled(list.getSelectedIndex() != -1);
				
				if(list.getSelectedIndex() != -1) {
					JSONObject thisReportData = reports.getJSONObject(list.getSelectedIndex());
					table.setValueAt(thisReportData.getInt("id"), 0, 1);
					table.setValueAt(thisReportData.getString("title"), 1, 1);
					table.setValueAt(thisReportData.getString("address"), 2, 1);
					table.setValueAt(TimeStrings.getTimeString((System.currentTimeMillis() / 1000) - thisReportData.getLong("report_date")) + " ago", 3, 1);
				} else {
					for(int i = 0; i < table.getRowCount(); i++) {
						table.setValueAt("", i, 1);
					}
				}
			}
		});
	    list.setBounds(10, 11, 320, 112);
	    
	    JScrollPane scrollPane = new JScrollPane(list);
	    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    scrollPane.setBounds(10, 11, 509, 114);
	    frmSockergrodaModDashb.getContentPane().add(scrollPane);
	    
	    btnDeny = new JButton("Deny");
	    btnDeny.setToolTipText("Ignore and remove the report");
	    btnDeny.setMnemonic('D');
	    btnDeny.setIcon(new ImageIcon(Images.INCORRECT_16x16.getImage()));
	    btnDeny.setEnabled(false);
	    btnDeny.setBounds(214, 226, 95, 23);
	    btnDeny.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JSONObject selectedReportData = reports.getJSONObject(list.getSelectedIndex());
				
				int confirmAction = JOptionPane.showConfirmDialog(frmSockergrodaModDashb, "Do you want to deny this report as the content is not malicious?", "Deny Report", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(confirmAction == 0) {
					APIResponse response = APIManager.moderatorDenyReport(moderatorKey, selectedReportData.getInt("id"));
					if(response.isRequestSuccessful()) {
						if(response.getCode() == 0) {
							JOptionPane.showMessageDialog(frmSockergrodaModDashb, "The report could not be denied.", "Cannot Deny Report", JOptionPane.ERROR_MESSAGE);
						}
						refreshWindow();
					} else {
						JOptionPane.showMessageDialog(frmSockergrodaModDashb, "Cannot connect to the server.", "Cannot Deny Report", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	    frmSockergrodaModDashb.getContentPane().add(btnDeny);
	    
	    btnViewContent = new JButton("View");
	    btnViewContent.setToolTipText("See the reported content");
	    btnViewContent.setMnemonic('V');
	    btnViewContent.setIcon(new ImageIcon(Images.EYE_16x16.getImage()));
	    btnViewContent.setEnabled(false);
	    btnViewContent.setBounds(109, 226, 95, 23);
	    btnViewContent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JSONObject selectedReportData = reports.getJSONObject(list.getSelectedIndex());
				
				RandomOutputTextField.display("Reported Content", selectedReportData.getString("freetext"));
			}
		});
	    frmSockergrodaModDashb.getContentPane().add(btnViewContent);
	    
	    btnConfirm = new JButton("Confirm");
	    btnConfirm.setToolTipText("Accept this report and ban the reported author");
	    btnConfirm.setMnemonic('m');
	    btnConfirm.setIcon(new ImageIcon(Images.CORRECT_16x16.getImage()));
	    btnConfirm.setEnabled(false);
	    btnConfirm.setBounds(319, 226, 95, 23);
	    btnConfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JSONObject selectedReportData = reports.getJSONObject(list.getSelectedIndex());
				
				String[] reasonOptions = {
						"Abusive content",
						"Prohibited content",
						"Unspecified"
				};
				
				Object input = JOptionPane.showInputDialog(frmSockergrodaModDashb, "Specify a ban reason:", "Specify Reason", JOptionPane.PLAIN_MESSAGE, new ImageIcon(Images.MALICIOUS_16x16.getImage()), reasonOptions, reasonOptions[0]);
				if(input != null) {
					APIResponse response = APIManager.moderatorConfirmReport(moderatorKey, selectedReportData.getInt("id"), input.toString());
					if(response.isRequestSuccessful()) {
						if(response.getCode() == 0) {
							JOptionPane.showMessageDialog(frmSockergrodaModDashb, "The report could not be confirmed.", "Cannot Confirm Report", JOptionPane.ERROR_MESSAGE);
						}
						refreshWindow();
					} else {
						JOptionPane.showMessageDialog(frmSockergrodaModDashb, "Cannot connect to the server.", "Cannot Confirm Report", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	    frmSockergrodaModDashb.getContentPane().add(btnConfirm);
	    
	    table = new JTable();
	    table.setRowSelectionAllowed(false);
	    table.setBackground(SystemColor.menu);
	    table.setModel(new DefaultTableModel(
	    	new Object[][] {
	    		{"ID", ""},
	    		{"Title", ""},
	    		{"IP Address", null},
	    		{"Reported", null},
	    	},
	    	new String[] {
	    		"", ""
	    	}
	    ){
			private static final long serialVersionUID = 1L;

			@Override
	    	public boolean isCellEditable(int row, int column) {
	    		return false;
	    	}
	    });
	    table.setBounds(10, 136, 509, 79);
	    frmSockergrodaModDashb.getContentPane().add(table);
	    
	    btnRefresh = new JButton("Refresh");
	    btnRefresh.setMnemonic('R');
	    btnRefresh.setIcon(new ImageIcon(Images.REFRESH_16x16.getImage()));
	    btnRefresh.setBounds(424, 226, 95, 23);
	    btnRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refreshWindow();
			}
		});
	    frmSockergrodaModDashb.getContentPane().add(btnRefresh);
	}
	
	private void refreshWindow() {
		frmSockergrodaModDashb.dispose();
		ModeratorDashboardWindow.display(moderatorKey);
	}
}
