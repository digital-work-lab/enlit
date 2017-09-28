package tasks.visualization;

import functionality.MainWindow;
import functionality.DataHolder;
import model.PDFPaper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Vector;

/**
 * Created by Philipo on 24.09.2017.
 */
public class PDFTabTask extends SwingWorker<Void, Integer> {

    JProgressBar jpb;
    int max;


    public PDFTabTask(JProgressBar jpb) {
        this.jpb = jpb;
        int max = DataHolder.listEmptyOrError.size() + DataHolder.listPDFPaper.size();
        jpb.setMaximum(max);

    }
    @Override
    protected void process(List<Integer> chunks) {
        int i = chunks.get(chunks.size()-1);
        jpb.setValue(i); // The last value in this array is all we care about.
    }
    @Override
    protected Void doInBackground() throws Exception {

        jpb.setIndeterminate(true);


        Vector<String> columnPDFPanel = new Vector<String>();
        columnPDFPanel.addElement("PDF");
        columnPDFPanel.addElement("title");
        columnPDFPanel.addElement("authors");
        columnPDFPanel.addElement("year");
        columnPDFPanel.addElement("journal");
        columnPDFPanel.addElement("volume");
        columnPDFPanel.addElement("issue");
        columnPDFPanel.addElement("page");


        Vector<Vector> rowDataPDFPanel = new Vector<Vector>();

        for (PDFPaper paper : DataHolder.listPDFPaper) {


            Vector<String> rowPDFPanel = new Vector<String>();

            rowPDFPanel.addElement(paper.getFilename());
            rowPDFPanel.addElement(paper.getTitle());
            rowPDFPanel.addElement(paper.getAuthorsString());
            rowPDFPanel.addElement(paper.getYear());
            rowPDFPanel.addElement(paper.getJournal());
            rowPDFPanel.addElement(paper.getVolume());
            rowPDFPanel.addElement(paper.getIssue());
            rowPDFPanel.addElement(paper.getPage());

            rowDataPDFPanel.add(rowPDFPanel);

        }

        JTable tablePDFPanel = new JTable(rowDataPDFPanel, columnPDFPanel);
        DefaultTableModel dmPDFPanel = (DefaultTableModel) tablePDFPanel.getModel();

        UIDefaults defaultsPDFPanel = UIManager.getLookAndFeelDefaults();
        if (defaultsPDFPanel.get("Table.alternateRowColor") == null) {
            defaultsPDFPanel.put("Table.alternateRowColor", new Color(240, 240, 240));
        }
        tablePDFPanel.setEnabled(false);
        MainWindow.pdfPanel  = new JScrollPane(tablePDFPanel);

        //#############################################################################

        List<String> emptyOrError = DataHolder.listEmptyOrError;

        Vector<String> columnNames = new Vector<String>();
        columnNames.addElement("Please check missing OCR regognition or empty reference section");


        Vector<Vector> rowData = new Vector<Vector>();

        for (String file : emptyOrError) {

            int count = 0;

            Vector<String> row = new Vector<String>();

            row.addElement(file);

            rowData.add(row);

            count += 1;
        }

        JTable table = new JTable(rowData, columnNames);

        UIDefaults defaults = UIManager.getLookAndFeelDefaults();
        if (defaults.get("Table.alternateRowColor") == null) {
            defaults.put("Table.alternateRowColor", new Color(240, 240, 240));
        }
        table.setEnabled(false);
        MainWindow.errorPanel  = new JScrollPane(table);


        jpb.setIndeterminate(false);
        MainWindow.tabbedPanePDF = new JTabbedPane();
        MainWindow.tabbedPanePDF.addTab("Imported", MainWindow.pdfPanel);
        MainWindow.tabbedPanePDF.addTab("Error", MainWindow.errorPanel);
        MainWindow.tabbedPane.setComponentAt(1, MainWindow.tabbedPanePDF);

        return null;
    }
    @Override
    protected void done() {

        MainWindow.frame.invalidate();
        MainWindow.frame.validate();
        MainWindow.frame.repaint();
    }


}