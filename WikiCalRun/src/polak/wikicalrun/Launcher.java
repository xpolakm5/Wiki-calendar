package polak.wikicalrun;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintStream;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;

import polak.textoutputlib.TextAreaOutputStream;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JList;


public class Launcher {

	private JFrame frmWikicalByXpolakm;
	private JLabel lblSelectedSource;
	
	private File selectedFile = new File("C:\\Users\\LenovoTunerX\\Desktop\\skwiki-latest-pages-articles.xml");
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		changeLookAndFeel();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Launcher window = new Launcher();
					window.frmWikicalByXpolakm.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Launcher() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmWikicalByXpolakm = new JFrame();
		frmWikicalByXpolakm.setBackground(Color.WHITE);
		frmWikicalByXpolakm.setForeground(Color.WHITE);
		frmWikicalByXpolakm.setTitle("WikiCal by xpolakm5");
		frmWikicalByXpolakm.setBounds(100, 100, 880, 624);
		frmWikicalByXpolakm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), "Wikipedia source file", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setLayout(null);
		
		lblSelectedSource = new JLabel("File is not selected");
		lblSelectedSource.setBounds(12, 27, 387, 16);
		lblSelectedSource.setEnabled(false);
		panel.add(lblSelectedSource);
		
		lblSelectedSource.setText(selectedFile.getAbsolutePath());
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Parsed file", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Searching", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Search output", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout groupLayout = new GroupLayout(frmWikicalByXpolakm.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(panel_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
						.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(13)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
						.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE))
					.addContainerGap())
		);
		panel_2.setLayout(null);
		
		JLabel lblDate = new JLabel("Date (DD.MM.YYYY):");
		lblDate.setBounds(12, 25, 126, 16);
		panel_2.add(lblDate);
		
		textField = new JTextField();
		textField.setBounds(293, 25, 116, 22);
		panel_2.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Serach");
		btnNewButton.setToolTipText("Write just in one box! Other must be empty.");
		btnNewButton.setBounds(293, 112, 116, 46);
		panel_2.add(btnNewButton);
		
		JButton btnClearAllFields = new JButton("Clear all fields");
		btnClearAllFields.setBounds(165, 112, 116, 46);
		panel_2.add(btnClearAllFields);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(12, 54, 56, 16);
		panel_2.add(lblName);
		
		textField_1 = new JTextField();
		textField_1.setBounds(293, 54, 116, 22);
		panel_2.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblEvent = new JLabel("Historic moment / thing / institution:");
		lblEvent.setBounds(12, 83, 221, 16);
		panel_2.add(lblEvent);
		
		textField_2 = new JTextField();
		textField_2.setBounds(293, 83, 116, 22);
		panel_2.add(textField_2);
		textField_2.setColumns(10);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(12, 173, 397, 231);
		panel_2.add(panel_4);
		
		
		DefaultListModel listModel = new DefaultListModel();
		listModel.addElement("Jane Doe");
		listModel.addElement("John Smith");
		JList list = new JList(listModel);
		
		JScrollPane scrollPane_1 = new JScrollPane(list);
		scrollPane_1.setPreferredSize(new Dimension(400,250));  
		panel_4.add(scrollPane_1);
		
		
		//panel_4.add(list);
		
		JTextArea txtrGafda = new JTextArea();
		

		
		/* Output from System.out.println is displayed in GUI of application */
		//TODO
//        TextAreaOutputStream taos = new TextAreaOutputStream( txtrGafda, 60 );
//        PrintStream ps = new PrintStream( taos );
//        System.setOut( ps );
//        System.setErr( ps );
		
		JScrollPane scrollPane = new JScrollPane(txtrGafda);
		scrollPane.setPreferredSize(new Dimension(400,380));  
		panel_3.add(scrollPane);
		
		txtrGafda.setText("gafda");
		//panel_3.add(txtrGafda);
		
		JButton btnGenerate = new JButton("Generate");
		btnGenerate.setBounds(324, 68, 85, 46);
		panel.add(btnGenerate);
		
		JButton btnSelectSource = new JButton("Select source");
		btnSelectSource.setBounds(203, 68, 109, 46);
		panel.add(btnSelectSource);
		btnSelectSource.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectFile();
			}
		});
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnGenerate();
			}
		});
		panel_1.setLayout(null);
		
		JLabel lblPathNotSelected = new JLabel("FILE NOT SELECTED");
		lblPathNotSelected.setEnabled(false);
		lblPathNotSelected.setBounds(12, 26, 132, 16);
		panel_1.add(lblPathNotSelected);
		
		JButton btnSelectParsedFile = new JButton("Select parsed file");
		btnSelectParsedFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("asf asdf");
				
			}
		});
		btnSelectParsedFile.setBounds(266, 68, 132, 46);
		panel_1.add(btnSelectParsedFile);
		frmWikicalByXpolakm.getContentPane().setLayout(groupLayout);
	}

	
	private static void changeLookAndFeel() {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
	}
	
	private void selectFile() {
		
		final JFileChooser fc = new JFileChooser();

		FileNameExtensionFilter filter = new FileNameExtensionFilter("xml", "XML");
		fc.setFileFilter(filter);
		
		int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            //This is where a real application would open the file.
            System.out.println("Opening: " + file.getName() + ".");
            lblSelectedSource.setText(file.getAbsolutePath());
            selectedFile = file;
        } else {
        	System.out.println("Open command cancelled by user.");
        }
	}
	
	private void btnGenerate() {
		WikiParser wikiParser = new WikiParser(selectedFile);
	}
}
