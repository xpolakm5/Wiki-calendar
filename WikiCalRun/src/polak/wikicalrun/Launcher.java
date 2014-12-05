package polak.wikicalrun;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.List;

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

import polak.dataclasses.SearchData;
import polak.lucene.CreateIndex;
import polak.parser.WikiParser;
import polak.search.SearchParsed;
import polak.settings.Settings;
import polak.tester.PeopleOutputTest;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JList;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import com.toedter.calendar.JCalendar;


public class Launcher {

	private JFrame frmWikicalByXpolakm;
	private JLabel lblSelectedSource;
	
	private File selectedFile = new File(Settings.defaultSelectedFile);
	private File selectedFolder = new File(Settings.defaultSelectedFolder);
	private JTextField txtDate;
	private JTextField txtName;
	private JTextField txtEvent;
	private JTextArea txtConsole;
	@SuppressWarnings("rawtypes")
	private JList listOutput;
	@SuppressWarnings("rawtypes")
	private DefaultListModel listModel;

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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize() {
		frmWikicalByXpolakm = new JFrame();
		frmWikicalByXpolakm.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\work\\GIT\\Wiki-calendar\\WikiCalRun\\images\\Wikipedia-icon.png"));
		frmWikicalByXpolakm.setBackground(Color.WHITE);
		frmWikicalByXpolakm.setForeground(Color.WHITE);
		frmWikicalByXpolakm.setTitle("WikiCal by xpolakm5");
		frmWikicalByXpolakm.setBounds(100, 100, 880, 850);
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
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Folder of parsed files", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
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
		lblDate.setBounds(12, 253, 126, 16);
		panel_2.add(lblDate);
		
		txtDate = new JTextField();
		txtDate.setBounds(293, 253, 116, 22);
		panel_2.add(txtDate);
		txtDate.setColumns(10);
		
		JButton btnNewButton = new JButton("Serach");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchParsed();
			}
		});
		btnNewButton.setToolTipText("Write just in one box! Other must be empty.");
		btnNewButton.setBounds(293, 340, 116, 46);
		panel_2.add(btnNewButton);
		
		JButton btnClearAllFields = new JButton("Clear all fields");
		btnClearAllFields.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearAllFields();
			}
		});
		btnClearAllFields.setBounds(165, 340, 116, 46);
		panel_2.add(btnClearAllFields);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(12, 282, 56, 16);
		panel_2.add(lblName);
		
		txtName = new JTextField();
		txtName.setBounds(293, 282, 116, 22);
		panel_2.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblEvent = new JLabel("Historic moment / thing / institution:");
		lblEvent.setBounds(12, 311, 221, 16);
		panel_2.add(lblEvent);
		
		txtEvent = new JTextField();
		txtEvent.setBounds(293, 311, 116, 22);
		panel_2.add(txtEvent);
		txtEvent.setColumns(10);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(12, 399, 397, 231);
		panel_2.add(panel_4);
		
		listModel = new DefaultListModel();
		listOutput = new JList(listModel);
		
		
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				if(evt.getClickCount() == 2) {
					mouseClickedOutputList(listOutput.getSelectedIndex());
				}
			}
		};
		listOutput.addMouseListener(mouseListener);
		
		JScrollPane scrollPane_1 = new JScrollPane(listOutput);
		scrollPane_1.setPreferredSize(new Dimension(400,250));  
		panel_4.add(scrollPane_1);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBounds(12, 27, 397, 213);
		panel_2.add(panel_5);
		
		JCalendar calendar = new JCalendar();
		panel_5.add(calendar);
		
		
		calendar.addPropertyChangeListener("calendar", new PropertyChangeListener() {

			   // @Override
			    public void propertyChange(PropertyChangeEvent e) {
			        final Calendar c = (Calendar) e.getNewValue();   
			        System.out.println(c.get(Calendar.DAY_OF_MONTH));   
			    }
			});
		
		
		txtConsole = new JTextArea();
		
		/* Output from System.out.println is displayed in GUI of application */
		//TODO
//        TextAreaOutputStream taos = new TextAreaOutputStream( txtConsole, 9999999 );
//        PrintStream ps;
//		try {
//			ps = new PrintStream( taos , true, "UTF-8");
//	        System.setOut( ps );
//	        System.setErr( ps );
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		}
		
		JScrollPane scrollPane = new JScrollPane(txtConsole);
		scrollPane.setPreferredSize(new Dimension(400,380));  
		panel_3.add(scrollPane);
		
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
		
		final JLabel lblSelectedFolder = new JLabel(selectedFolder.getAbsolutePath());
		lblSelectedFolder.setEnabled(false);
		lblSelectedFolder.setBounds(12, 26, 386, 16);
		panel_1.add(lblSelectedFolder);
		
		JButton btnSelectParsedFile = new JButton("Select parsed folder");
		btnSelectParsedFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				final JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				int returnVal = fc.showOpenDialog(null);
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            lblSelectedFolder.setText(file.getAbsolutePath());
		            selectedFolder = file;
		        } else {
		        	System.out.println("Open command cancelled by user.");
		        }
				
			}
		});
		btnSelectParsedFile.setBounds(251, 68, 147, 46);
		panel_1.add(btnSelectParsedFile);
		
		JButton btnRunUnityTest = new JButton("Run unity test");
		btnRunUnityTest.setBounds(12, 68, 111, 46);
		panel_1.add(btnRunUnityTest);
		
		JButton btnCreateIndex = new JButton("Create index");
		btnCreateIndex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCreateIndex();
			}
		});
		btnCreateIndex.setBounds(135, 68, 104, 46);
		panel_1.add(btnCreateIndex);
		btnRunUnityTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnTest();
			}
		});
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
		    e.printStackTrace();
		}
	}
	
	/**
	 * Button Select source - xml file will be selected
	 */
	private void selectFile() {
		
		final JFileChooser fc = new JFileChooser();

		FileNameExtensionFilter filter = new FileNameExtensionFilter("xml", "XML");
		fc.setFileFilter(filter);
		
		int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            lblSelectedSource.setText(file.getAbsolutePath());
            selectedFile = file;
        } else {
        	System.out.println("Open command cancelled by user.");
        }
	}
	
	/**
	 * Button which generate 2 file output from big SK wiki page
	 */
	private void btnGenerate() {
		new WikiParser(selectedFile);
	}
	
	/**
	 * Button Run unity test - check if the output file people is generated properly
	 */
	private void btnTest() {
		new PeopleOutputTest(selectedFolder.getAbsolutePath());
	}
	
	/**
	 * Button Create index - already parsed files will be indexed
	 */
	private void btnCreateIndex() {
		try {
			new CreateIndex(selectedFolder);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Button Search - searching from indexed files
	 */
	@SuppressWarnings("unchecked")
	private void searchParsed() {

		SearchParsed searchParsed = new SearchParsed(txtDate.getText(), txtName.getText(), txtEvent.getText(), selectedFolder);
		try {

			List<SearchData> foundStrings = searchParsed.searchParsedIndexed("");
			listModel.clear();

			if(foundStrings != null) {
				for(SearchData str : foundStrings) {
					listModel.addElement(str.getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void clearAllFields() {
		txtDate.setText("");
		txtEvent.setText("");
		txtName.setText("");
		txtConsole.setText("");
		listModel.clear();
	}
	
	/**
	 * Action after selecting one row from list of names (calendar)
	 */
	private void mouseClickedOutputList(int selectedIndex) {
		System.out.println("selected index " + selectedIndex);
	}
}
