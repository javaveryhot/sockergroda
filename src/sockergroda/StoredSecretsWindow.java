package sockergroda;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

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

public class StoredSecretsWindow {

	private JFrame frmSockergrodaStored;
	private JButton btnDeleteSecret;
	private JButton btnAbandon;
	private JSONArray metaDataArray;
	private JButton btnOpenSecret;
	private JTable table;
	private JButton btnCopyId;

	/**
	 * Launch the application.
	 */
	public static void display() {
		try {
			StoredSecretsWindow window = new StoredSecretsWindow();
			window.frmSockergrodaStored.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public StoredSecretsWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSockergrodaStored = new SGFrame();
		frmSockergrodaStored.setResizable(false);
		frmSockergrodaStored.setTitle("Saved Secrets");
		frmSockergrodaStored.setBounds(100, 100, 550, 300);
		frmSockergrodaStored.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frmSockergrodaStored.setIconImage(Images.ICON_32x32.getImage());
	    frmSockergrodaStored.setLocationRelativeTo(null);
	    
		Map<String, Object> storedSecrets = StorageManager.getJSOMap("owned_secrets");

		if(storedSecrets.size() > 0) {
			APIResponse response = APIManager.grabStackedMetadata(storedSecrets);
			if(!response.isRequestSuccessful()) {
				JOptionPane.showMessageDialog(frmSockergrodaStored, "Cannot load your stored secret metadata!", "Cannot Load Metadata", JOptionPane.ERROR_MESSAGE);
				frmSockergrodaStored.dispose();
				return;
			}
			metaDataArray = response.getJSONArrayValue("meta_result");
			
			int deadSecrets = 0;
			
			for(int i = 0; i < metaDataArray.length(); i++) {
				JSONObject checkObject = metaDataArray.getJSONObject(i);
				if(checkObject.getBoolean("dead")) {
					storedSecrets.remove(String.valueOf(checkObject.getInt("id")));
					StorageManager.setAttribute("owned_secrets", storedSecrets);
					metaDataArray.remove(i);
					deadSecrets++;
				}
			}
			
			if(deadSecrets > 0) {
				JOptionPane.showMessageDialog(frmSockergrodaStored, deadSecrets + " expired secret(s) were detected and removed.", "Dead Secret(s) Found", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
		frmSockergrodaStored.setTitle(frmSockergrodaStored.getTitle() + " (" + storedSecrets.size() + ")");
		
	    JButton btnBack = new JButton("Close");
	    btnBack.setMnemonic('C');
	    btnBack.setBounds(10, 226, 89, 23);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSockergrodaStored.dispose();
			}
		});
	    frmSockergrodaStored.getContentPane().setLayout(null);
	    frmSockergrodaStored.getContentPane().add(btnBack);

	    JList<String> list = new JList<String>();
	    list.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			public int getSize() {
	    		return storedSecrets.size();
	    	}
	    	public String getElementAt(int index) {
	    		JSONObject elementMeta = metaDataArray.getJSONObject(index);
	    		return elementMeta.getInt("id") + "> " + elementMeta.getString("title");
	    	}
	    });
	    list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				btnDeleteSecret.setEnabled(list.getSelectedIndex() != -1);
				btnAbandon.setEnabled(list.getSelectedIndex() != -1);
				btnOpenSecret.setEnabled(list.getSelectedIndex() != -1);
				btnCopyId.setEnabled(list.getSelectedIndex() != -1);
				
				if(list.getSelectedIndex() != -1) {
					JSONObject currentElementMeta = metaDataArray.getJSONObject(list.getSelectedIndex());
					table.setValueAt(currentElementMeta.getInt("id"), 0, 1);
					table.setValueAt(currentElementMeta.getString("title"), 1, 1);
					table.setValueAt(TimeStrings.getTimeString((System.currentTimeMillis() / 1000) - currentElementMeta.getLong("created_at")) + " ago", 2, 1);
					table.setValueAt(TimeStrings.getTimeString(currentElementMeta.getLong("expire_date") - (System.currentTimeMillis() / 1000)), 3, 1);
					table.setValueAt(currentElementMeta.getInt("inspections") + " / " + currentElementMeta.getInt("max_uses"), 4, 1);
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
	    frmSockergrodaStored.getContentPane().add(scrollPane);
	    
	    btnDeleteSecret = new JButton("Delete");
	    btnDeleteSecret.setMnemonic('D');
	    btnDeleteSecret.setIcon(new ImageIcon(Images.DESTROY_16x16.getImage()));
	    btnDeleteSecret.setEnabled(false);
	    btnDeleteSecret.setBounds(214, 226, 95, 23);
	    btnDeleteSecret.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedSecretId = (String)storedSecrets.keySet().toArray()[list.getSelectedIndex()];
				String selectedSecretOwnerKey = (String)storedSecrets.values().toArray()[list.getSelectedIndex()];
				
				int confirmDelete = JOptionPane.showConfirmDialog(frmSockergrodaStored, "Do you want to delete this secret?\nIt will be gone forever.", "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(confirmDelete == 0) {
					APIResponse response = APIManager.deleteSecret(Integer.parseInt(selectedSecretId), selectedSecretOwnerKey);
					if(response.isRequestSuccessful()) {
						storedSecrets.remove(selectedSecretId);
						StorageManager.setAttribute("owned_secrets", storedSecrets);
	
						frmSockergrodaStored.dispose();
						StoredSecretsWindow.display();
					} else {
						JOptionPane.showMessageDialog(frmSockergrodaStored, "Cannot delete the secret!", "Cannot Delete", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	    frmSockergrodaStored.getContentPane().add(btnDeleteSecret);
	    
	    btnAbandon = new JButton("Abandon");
	    btnAbandon.setMnemonic('A');
	    btnAbandon.setIcon(new ImageIcon(Images.DELETE_LOCALLY_16x16.getImage()));
	    btnAbandon.setEnabled(false);
	    btnAbandon.setBounds(109, 226, 95, 23);
	    btnAbandon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedSecretId = (String)storedSecrets.keySet().toArray()[list.getSelectedIndex()];
				
				int confirmAbandon = JOptionPane.showConfirmDialog(frmSockergrodaStored, "Do you want to abandon this secret?\nIt will be removed from your list of secrets and you will no longer be able to delete it.\nThe data will still remain!", "Confirm Abandon", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(confirmAbandon == 0) {
					storedSecrets.remove(selectedSecretId);
					StorageManager.setAttribute("owned_secrets", storedSecrets);

					frmSockergrodaStored.dispose();
					StoredSecretsWindow.display();
				}
			}
		});
	    frmSockergrodaStored.getContentPane().add(btnAbandon);
	    
	    btnOpenSecret = new JButton("Inspect");
	    btnOpenSecret.setMnemonic('I');
	    btnOpenSecret.setIcon(new ImageIcon(Images.KEY_16x16.getImage()));
	    btnOpenSecret.setEnabled(false);
	    btnOpenSecret.setBounds(319, 226, 95, 23);
	    btnOpenSecret.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedSecretId = (String)storedSecrets.keySet().toArray()[list.getSelectedIndex()];
				
				frmSockergrodaStored.dispose();
				InspectSecretWindow.display(selectedSecretId, false);
			}
		});
	    frmSockergrodaStored.getContentPane().add(btnOpenSecret);
	    
	    table = new JTable();
	    table.setRowSelectionAllowed(false);
	    table.setBackground(SystemColor.menu);
	    table.setModel(new DefaultTableModel(
	    	new Object[][] {
	    		{"ID", ""},
	    		{"Title", ""},
	    		{"Created", ""},
	    		{"Expires in", ""},
	    		{"Inspections", ""},
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
	    frmSockergrodaStored.getContentPane().add(table);
	    
	    btnCopyId = new JButton("Copy ID");
	    btnCopyId.setMnemonic('C');
	    btnCopyId.setIcon(new ImageIcon(Images.COPY_16x16.getImage()));
	    btnCopyId.setEnabled(false);
	    btnCopyId.setBounds(424, 226, 95, 23);
	    btnCopyId.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.copyToClipboard("<sg?" + (String)storedSecrets.keySet().toArray()[list.getSelectedIndex()] + ">");
			}
		});
	    frmSockergrodaStored.getContentPane().add(btnCopyId);
	}
}
