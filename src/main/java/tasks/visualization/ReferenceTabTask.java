package tasks.visualization;

import functionality.MainWindow;
import functionality.DataHolder;
import model.PDFPaper;
import model.ReferencedPaper;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.MultiplePiePlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.TableOrder;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

/**
 * Created by Philipo on 24.09.2017.
 */
public class ReferenceTabTask extends SwingWorker<Void, Integer> {

    JProgressBar jpb;
    int max;


    public ReferenceTabTask(JProgressBar jpb) {
        this.jpb = jpb;

        max = DataHolder.getListReferencedPaperAll().size()*3;
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

        Vector<String> columnNames = new Vector<String>();
        columnNames.addElement("title");
        columnNames.addElement("authors");
        columnNames.addElement("year");
        columnNames.addElement("journal");
        columnNames.addElement("volume");
        columnNames.addElement("issue");
        columnNames.addElement("page");
        columnNames.addElement("score");
        columnNames.addElement("pdf");
        columnNames.addElement("ref_no.");
        columnNames.addElement("isIn");


        Vector<Vector> rowData = new Vector<Vector>();

        for (int i = 0; i < DataHolder.listReferencedPaperAll.size(); i++) {

            ReferencedPaper paper = DataHolder.listReferencedPaperAll.get(i);

            if ((!paper.getTitle().equals("-")) && (paper.getMergeID() == null)) {
                Vector<String> row = new Vector<String>();


                String[] pdfID = paper.parseReferenceID();
                PDFPaper pdf = paper.findPDF(pdfID[0]);
                row.addElement(paper.getTitle());
                row.addElement(paper.getAuthorsString());
                row.addElement(paper.getYear());
                row.addElement(paper.getJournal());
                row.addElement(paper.getVolume());
                row.addElement(paper.getIssue());
                row.addElement(paper.getPage());
                row.addElement(String.valueOf(paper.getScore()));
                row.addElement(String.valueOf(pdf.getFilename()));
                row.addElement(String.valueOf(pdfID[1]));
                row.addElement(String.valueOf(paper.isInLiteratureSet()));

                rowData.add(row);
            }
        }


        JTable table = new JTable(rowData, columnNames);
        table.setAutoCreateRowSorter(true);
        TableRowSorter<DefaultTableModel> rowSorter = (TableRowSorter<DefaultTableModel>)table.getRowSorter();
        rowSorter.setComparator(7, new Comparator<String>() {

            @Override
            public int compare(String o1, String o2)
            {
                return Integer.parseInt(o1) - Integer.parseInt(o2);
            }

        });
        rowSorter.setComparator(9, new Comparator<String>() {

            @Override
            public int compare(String o1, String o2)
            {
                return Integer.parseInt(o1) - Integer.parseInt(o2);
            }

        });
        table.getColumnModel().getColumn(10).setMinWidth(0);
        table.getColumnModel().getColumn(10).setMaxWidth(0);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int col){

                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

                int modelRow = table.getRowSorter().convertRowIndexToModel(row);

                String isInLit = (String)table.getModel().getValueAt(modelRow, 10);
                if("true".equals(isInLit))
                {
                    setBackground(Color.LIGHT_GRAY);
                }
                else
                {
                    int mod = row%2;

                    if(mod == 0)
                    {
                        setBackground(new Color(255,255,255));
                    }
                    else
                    {
                        setBackground(new Color(240,240,240));
                    }

                }
                return this;
            }
        });

        table.setEnabled(false);
        MainWindow.foundPanel = new JScrollPane(table);


        //###################################################

        Vector<String> columnNames2 = new Vector<String>();

        columnNames2.addElement("title");
        columnNames2.addElement("author");
        columnNames2.addElement("year");
        columnNames2.addElement("journal");
        columnNames2.addElement("volume");
        columnNames2.addElement("issue");
        columnNames2.addElement("page");
        columnNames2.addElement("pdf");
        columnNames2.addElement("reference_no.");

        Vector<Vector> rowData2 = new Vector<Vector>();

        int countJournals =0;
        int countAuthors = 0;
        int countYear = 0;
        int countTitle = 0;
        for (int i = 0; i < DataHolder.listReferencedPaperAll.size(); i++) {

            ReferencedPaper paper = DataHolder.listReferencedPaperAll.get(i);


            if(paper.getYear().equals("-"))
            {
                countYear += 1;
            }
            if(paper.getJournal().equals("-") && (
                    (!paper.getVolume().equals("-")) 	||
                            (!paper.getPage().equals("-")) 		||
                            (!paper.getIssue().equals("-"))))
            {
                countJournals += 1;
            }
            if(paper.getAuthors() == null)
            {
                countAuthors += 1;
            }
            if (paper.getTitle().equals("-")) {

                countTitle += 1;
                Vector<String> row = new Vector<String>();

                String[] id = paper.parseReferenceID();

                PDFPaper pdf = paper.findPDF(id[0]);

                row.addElement(paper.getTitle());
                row.addElement(paper.getAuthorsString());
                row.addElement(paper.getYear());
                row.addElement(paper.getJournal());
                row.addElement(paper.getVolume());
                row.addElement(paper.getIssue());
                row.addElement(paper.getPage());
                row.addElement(pdf.getFilename());
                row.addElement(id[1]);

                rowData2.add(row);
            }

        }

        JTable table2 = new JTable(rowData2, columnNames2);
        table2.setAutoCreateRowSorter(true);


        UIDefaults defaults = UIManager.getLookAndFeelDefaults();
        if (defaults.get("Table.alternateRowColor") == null) {
            defaults.put("Table.alternateRowColor", new Color(240, 240, 240));
        }
        table2.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(table2);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(countTitle, "Null value", "Title");
        dataset.addValue(DataHolder.listReferencedPaperAll.size()-countTitle, "References", "Title");

        dataset.addValue(countAuthors, "Null value", "Author");
        dataset.addValue(DataHolder.listReferencedPaperAll.size()-countAuthors, "References", "Author");


        dataset.addValue(countYear, "Null value", "Year");
        dataset.addValue(DataHolder.listReferencedPaperAll.size()-countYear, "References", "Year");

        dataset.addValue(countJournals, "Null value", "Journal");
        dataset.addValue(DataHolder.listReferencedPaperAll.size()-countJournals, "References", "Journal");


        JFreeChart chart = ChartFactory.createMultiplePieChart("Null values within backward citation set",
                dataset, TableOrder.BY_COLUMN, true, true, false);

        MultiplePiePlot multiple = (MultiplePiePlot) chart.getPlot();
        PiePlot plot = (PiePlot) multiple.getPieChart().getPlot();
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        plot.setNoDataMessage("No data available");
        plot.setCircular(false);
        plot.setSectionPaint("Null value", new Color(105, 105, 105));
        plot.setSectionPaint("References", new Color(169, 169, 169));
        plot.setLabelGenerator(null);

        ChartPanel chartPanel = new ChartPanel(chart, true);
        chartPanel.setMinimumDrawWidth(0);
        chartPanel.setMaximumDrawWidth(Integer.MAX_VALUE);
        chartPanel.setMinimumDrawHeight(0);
        chartPanel.setMaximumDrawHeight(Integer.MAX_VALUE);
        JSplitPane jsplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,scrollPane,chartPanel);
        jsplitPane.setResizeWeight(0.66);

        MainWindow.notFoundPanel = jsplitPane;
        //################################################################################################

        Vector<String> columnNames3 = new Vector<String>();
        columnNames3.addElement("parent");
        columnNames3.addElement("title");
        columnNames3.addElement("authors");
        columnNames3.addElement("year");
        columnNames3.addElement("journal");
        columnNames3.addElement("volume");
        columnNames3.addElement("issue");
        columnNames3.addElement("page");

        Vector<Vector> rowData3 = new Vector<Vector>();

        List<Integer> values = new ArrayList<Integer>();
        int duplicates = 0;
        int nonRedundant = 0;
        ReferencedPaper parent = null;

        for (int i = 0; i < DataHolder.listReferencedPaperAll.size(); i++) {

            ReferencedPaper paper = DataHolder.listReferencedPaperAll.get(i);

            if ((paper.getMergeID() == null)) {
                nonRedundant += 1;
            }


            if ((paper.getMergeID() != null)) {


                if(!paper.getTitle().equals("-")){

                    duplicates +=1;

                    Vector<String> row = new Vector<String>();
                    if(parent != null && parent.getReferenceID().equals(paper.getMergeID()))
                    {
                        row.addElement("");
                    }
                    else
                    {
                        values.add(duplicates);
                        parent = paper.findParent();
                        row.addElement(parent.getTitle());
                    }
                    row.addElement(paper.getTitle());
                    row.addElement(paper.getAuthorsString());
                    row.addElement(paper.getYear());
                    row.addElement(paper.getJournal());
                    row.addElement(paper.getVolume());
                    row.addElement(paper.getIssue());
                    row.addElement(paper.getPage());

                    rowData3.add(row);
                }
            }


        }

        JTable table3 = new JTable(rowData3, columnNames3);

        UIDefaults defaults2 = UIManager.getLookAndFeelDefaults();
        if (defaults2.get("Table.alternateRowColor") == null) {
            defaults2.put("Table.alternateRowColor", new Color(240, 240, 240));
        }

        table3.setEnabled(false);
        JScrollPane scrollPane2 = new JScrollPane(table3);


        DefaultPieDataset dataset2 = new DefaultPieDataset();
        dataset2.setValue("Duplicates", duplicates);
        dataset2.setValue("Non redundant references", nonRedundant);

        JFreeChart chart3 = ChartFactory.createPieChart("Deduplication rate", dataset2, true, true, false);
        PiePlot plot2 = (PiePlot) chart3.getPlot();
        plot2.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        plot2.setNoDataMessage("No data available");
        plot2.setCircular(false);
        plot2.setSectionPaint("Duplicates", new Color(105, 105, 105));
        plot2.setSectionPaint("Non redundant references", new Color(169, 169, 169));
        plot2.setLabelGenerator(null);

        ChartPanel chartPanel2 = new ChartPanel(chart3, true);
        chartPanel2.setMinimumDrawWidth(0);
        chartPanel2.setMaximumDrawWidth(Integer.MAX_VALUE);
        chartPanel2.setMinimumDrawHeight(0);
        chartPanel2.setMaximumDrawHeight(Integer.MAX_VALUE);
        JSplitPane jsplitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane2, chartPanel2);
        jsplitPane2.setResizeWeight(0.66);

        MainWindow.mergedPanel = jsplitPane2;


        jpb.setIndeterminate(false);

        MainWindow.tabbedPaneReferences.addTab("Non-redundant", MainWindow.foundPanel);
        MainWindow.tabbedPaneReferences.addTab("Error", MainWindow.notFoundPanel);
        MainWindow.tabbedPaneReferences.addTab("Merged duplicates", MainWindow.mergedPanel);
        MainWindow.tabbedPane.setComponentAt(0, MainWindow.tabbedPaneReferences);


        return null;
    }
    @Override
    protected void done() {

        MainWindow.frame.invalidate();
        MainWindow.frame.validate();
        MainWindow. frame.repaint();
    }


}