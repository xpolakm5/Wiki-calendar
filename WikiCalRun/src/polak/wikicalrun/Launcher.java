package polak.wikicalrun;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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


public class Launcher {

	private JFrame frmWikicalByXpolakm;
	private JLabel lblSelectedSource;
	
	private File selectedFile = new File("C:\\Users\\LenovoTunerX\\Desktop\\skwiki-latest-pages-articles.xml");
	
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
		frmWikicalByXpolakm.setBounds(100, 100, 452, 233);
		frmWikicalByXpolakm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), "Wikipedia source file", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GroupLayout groupLayout = new GroupLayout(frmWikicalByXpolakm.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JButton btnGenerate = new JButton("Generate");
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnGenerate();
			}
		});
		btnGenerate.setBounds(318, 98, 85, 46);
		
		JButton btnSelectSource = new JButton("Select source");
		btnSelectSource.setBounds(197, 98, 109, 46);
		btnSelectSource.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectFile();
			}
		});
		panel.setLayout(null);
		
		lblSelectedSource = new JLabel("File is not selected");
		lblSelectedSource.setBounds(16, 46, 387, 16);
		lblSelectedSource.setEnabled(false);
		panel.add(lblSelectedSource);
		panel.add(btnSelectSource);
		panel.add(btnGenerate);
		frmWikicalByXpolakm.getContentPane().setLayout(groupLayout);
		
		//TODO
		lblSelectedSource.setText(selectedFile.getAbsolutePath());
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
