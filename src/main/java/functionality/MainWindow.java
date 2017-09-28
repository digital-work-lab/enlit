package functionality;

import ExportImport.BibTexFile;
import ExportImport.CSVFile;
import ExportImport.ExcelFile;
import org.jfree.chart.ChartPanel;
import tasks.*;
import tasks.VisualizationTaskMain;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import java.util.prefs.Preferences;
import javax.imageio.ImageIO;

public class MainWindow implements ActionListener {

	public static JPanel panelNotFound;
	public static JPanel panelMerged;
	public static JPanel panelPDF2;
	private SwingWorker<Void, Void> swingWorker;
	public static JFrame frame;
	public static JProgressBar progressBar,progressBarRedundant, progressBarPDF,  progressBarAuthor, progressBarTitle, progressBarJournal, progressBarYear ;
	public static JPanel panelRedundant, panelPDF, panelAuthor, panelTitle, panelJournal, panelYear ;
	public static JDialog progressDialog, configDialog;
	public static JLabel progressLabel;
	public static JMenuBar menuBar;
	public static JMenu menu;
	public static JMenu submenu;
	public static JMenuItem menuAbout;
	public static JFileChooser chooser;
	public static JMenuItem menuItemImport, menuItemExportExcel, menuItemExportCSV, menuItemExportBibTex, menuItemSave, menuItemSearch;
	public static ChartPanel chartPanelTitle, chartPanelTime;
	public static JSplitPane chartPanelJournal, chartPanelAuthors, mergedPanel, notFoundPanel;

	public static JTabbedPane tabbedPane, tabbedPaneReferences, tabbedPanePDF, tabbedPaneChart;
	public static JScrollPane foundPanel, pdfPanel, errorPanel;

	public static MainWindow crw;

	public static void main(String[] argv) {
		frame = new JFrame();
		crw = new MainWindow();
	}

	public MainWindow() {
		JFrame.setDefaultLookAndFeelDecorated(true);

		try {
			frame.setIconImage(ImageIO.read(new File("src\\main\\logo.png")));
		}
		catch (IOException exc) {
			exc.printStackTrace();
		}

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		createMenu();
		frame.setTitle("ENLIT");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
	}

	private void createMenu() {

		menuBar = new JMenuBar();

		menu = new JMenu("File");
		menu.getAccessibleContext().setAccessibleDescription("file menu");
		menuBar.add(menu);

		menuItemSearch = new JMenuItem("Open PDFs");
		menuItemSearch.getAccessibleContext().setAccessibleDescription(
				"create a new search within a directory");
		menuItemSearch.addActionListener(this);
		menu.add(menuItemSearch);

		menu.addSeparator();
		
		menuItemImport = new JMenuItem("Import");
		menuItemImport.getAccessibleContext().setAccessibleDescription(
				"imports an existing exported ct file");
		menuItemImport.addActionListener(this);
		menu.add(menuItemImport);
		
		menuItemSave = new JMenuItem("Save");
		menuItemSave.getAccessibleContext().setAccessibleDescription(
				"saves the data");
		menuItemSave.addActionListener(this);
		menuItemSave.setEnabled(false);
		menu.add(menuItemSave);
		
		menu.addSeparator();

		submenu = new JMenu("Export as");

		menuItemExportCSV = new JMenuItem("csv file");
		menuItemExportCSV.getAccessibleContext().setAccessibleDescription(
				"exports the csv file");
		menuItemExportCSV.addActionListener(this);
		menuItemExportCSV.setEnabled(false);
		submenu.add(menuItemExportCSV);
	
		menuItemExportBibTex = new JMenuItem("bibTex file");
		menuItemExportBibTex.getAccessibleContext().setAccessibleDescription(
				"exports the bibtex");
		menuItemExportBibTex.addActionListener(this);
		menuItemExportBibTex.setEnabled(false);
		submenu.add(menuItemExportBibTex);
		
		menuItemExportExcel = new JMenuItem("excel file");
		menuItemExportExcel.getAccessibleContext().setAccessibleDescription(
				"exports the excel");
		menuItemExportExcel.addActionListener(this);
		menuItemExportExcel.setEnabled(false);
		submenu.add(menuItemExportExcel);
		
		menuAbout = new JMenuItem("Settings");
		menuAbout.addActionListener(this);

		menu.add(submenu);
		menu.addSeparator();
		menu.add(menuAbout);
		menuBar.add(menu);

		frame.setJMenuBar(menuBar);

	}


	private void cleanEnvironment() {

		if(DataHolder.listReferencedPaperAll != null) {
			DataHolder.listReferencedPaperAll.clear();
		}
		if(DataHolder.listEmptyOrError != null) {
			DataHolder.listEmptyOrError.clear();
		}
		if(DataHolder.listPDFPaper != null) {
			DataHolder.listPDFPaper.clear();
		}

		if(tabbedPane != null)
		{
		frame.remove(tabbedPane);
		frame.invalidate();
		frame.validate();
		frame.repaint();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == menuAbout) {

				Preferences preferences = Preferences.userNodeForPackage(MainWindow.class);

				JTextField home = new JTextField(50);
				JTextField properties = new JTextField(50);

					home.setText(preferences.get("pGrobidHome", ""));
					properties.setText(preferences.get("pGrobidProperties", ""));

					if(!preferences.get("pGrobidHome", "").equals(""))
					{
						home.setText(preferences.get("pGrobidHome", ""));
					}
					else
					{
						home.setText("C:/Users/Username/.../grobid/grobid-home");
					}
				if(!preferences.get("pGrobidProperties", "").equals(""))
				{
				properties.setText(preferences.get("pGrobidProperties", ""));
				}
				else
				{
				properties.setText("C:/Users/Username/.../grobid/grobid-home/config/grobid.properties");
				}
					JPanel config = new JPanel();
					config.setLayout(new GridLayout(0, 1));
					JLabel labelHeadline = new JLabel("GROBID");
					labelHeadline.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
							if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
								try {
									desktop.browse(new URI("https://github.com/kermitt2/grobid"));
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}
							super.mouseClicked(e);
						}
					});
					labelHeadline.setToolTipText("<html>https://github.com/kermitt2/grobid</html>");
					config.add(labelHeadline);
					config.add(Box.createHorizontalStrut(15)); // a spacer

					JLabel labelHome = new JLabel("GROBID home:");
					config.add(labelHome);
					config.add(home);
					config.add(Box.createHorizontalStrut(15)); // a spacer

					JLabel labelProperties = new JLabel("GROBID properties:");
					labelProperties.setToolTipText("C:/Users/Username/.../grobid/grobid-home/config/grobid.properties");
					config.add(labelProperties);
					config.add(properties);

				int result = JOptionPane.showConfirmDialog(frame, config,
						"Please set the GROBID path", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {

					preferences.put("pGrobidHome", home.getText());
					preferences.put("pGrobidProperties", properties.getText());

				}

		}
		if (e.getSource() == menuItemSearch) {

			chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setDialogTitle("Select a Directory");
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			chooser.setAcceptAllFileFilterUsed(false);

			if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {

				cleanEnvironment();
				progressDialog = new JDialog(frame, "Processing literature corpus...", Dialog.ModalityType.APPLICATION_MODAL);
				progressDialog.setLayout(new BorderLayout());
				progressBar = new JProgressBar();
				progressDialog.add(progressBar, BorderLayout.NORTH);
				progressLabel = new JLabel();

				JPanel contentPane = new JPanel();
				contentPane.add(progressLabel);
				progressDialog.add(contentPane, BorderLayout.CENTER);
				contentPane.setPreferredSize(new Dimension(300, 100));
				contentPane.add(progressBar);
				contentPane.add(progressLabel);
				progressDialog.setContentPane(contentPane);
				progressDialog.pack();
				progressDialog.setLocationRelativeTo(null);

				RunGrobidTask runGrobidTask= new RunGrobidTask(progressBar, progressLabel, chooser.getSelectedFile().toString());
				runGrobidTask.execute();

				progressDialog.setVisible(true);
			}
			}
			if (e.getSource() == menuItemExportCSV) {
				chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Export CSV File");
				FileFilter filter = new FileNameExtensionFilter("CSV File", "csv");
				chooser.setFileFilter(filter);
				chooser.setAcceptAllFileFilterUsed(false);

				if (chooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
					CSVFile csv = new CSVFile();
					try {
						csv.export(chooser.getSelectedFile().getAbsolutePath());
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				}
			}

			if (e.getSource() == menuItemSave) {
				chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Save File");
				FileFilter filter = new FileNameExtensionFilter("Cobat File", "ct");
				chooser.setFileFilter(filter);
				chooser.setAcceptAllFileFilterUsed(false);

				if (chooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
					CSVFile csv = new CSVFile();
					try {
						csv.saveData(chooser.getSelectedFile().getAbsolutePath());
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				}
			}
			if (e.getSource() == menuItemExportBibTex) {
				chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Export BibTex File");
				FileFilter filter = new FileNameExtensionFilter("BibTex File", "bib");
				chooser.setFileFilter(filter);
				chooser.setAcceptAllFileFilterUsed(false);

				if (chooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
					try {
						BibTexFile bib = new BibTexFile(chooser.getSelectedFile().getAbsolutePath());
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}
			}
			if (e.getSource() == menuItemExportExcel) {
				chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Export Excel File");
				FileFilter filter = new FileNameExtensionFilter("Excel File", "xls");
				chooser.setFileFilter(filter);
				chooser.setAcceptAllFileFilterUsed(false);

				if (chooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
					try {
						ExcelFile bib = new ExcelFile(chooser.getSelectedFile().getAbsolutePath());
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}
			}
			if (e.getSource() == menuItemImport) {

				chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Select a Cobat File");
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);

				if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {

					cleanEnvironment();
					try {
						cleanEnvironment();
						CSVFile csv = new CSVFile();
						csv.loadData(chooser.getSelectedFile().toString());
						menuBar.setEnabled(false);
						menuItemExportBibTex.setEnabled(true);
						menuItemExportCSV.setEnabled(true);
						menuItemSave.setEnabled(true);
						menuItemExportExcel.setEnabled(true);

						VisualizationTaskMain visualizationTaskMain = new VisualizationTaskMain();
						visualizationTaskMain.execute();

					} catch (Exception e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(MainWindow.frame, "File is incorrect");
					}
				}


		}
	}
}
