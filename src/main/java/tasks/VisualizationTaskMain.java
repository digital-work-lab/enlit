package tasks;

import functionality.MainWindow;
import tasks.visualization.ChartTabTask;
import tasks.visualization.PDFTabTask;
import tasks.visualization.ReferenceTabTask;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Philipo on 13.09.2017.
 */
public class VisualizationTaskMain extends SwingWorker<Void, Integer> {


    public VisualizationTaskMain() {

    }

    @Override
    protected Void doInBackground() throws Exception {

        MainWindow.tabbedPane = new JTabbedPane();

        MainWindow.tabbedPaneReferences = new JTabbedPane();
        MainWindow.tabbedPaneChart = new JTabbedPane();
        MainWindow.tabbedPanePDF = new JTabbedPane();

        MainWindow.progressBarRedundant = new JProgressBar();

        MainWindow.panelRedundant = new JPanel();
        MainWindow.panelRedundant.add(MainWindow.progressBarRedundant);

        //##############################################

        MainWindow.progressBarPDF = new JProgressBar();
        MainWindow.panelPDF = new JPanel();
        MainWindow.panelPDF.add(MainWindow.progressBarPDF);

        MainWindow.tabbedPanePDF.add(MainWindow.panelPDF);

        //##############################################
        MainWindow.progressBarAuthor = new JProgressBar();
        MainWindow.panelAuthor = new JPanel();
        MainWindow.panelAuthor.add(MainWindow.progressBarAuthor);

        MainWindow.progressBarTitle = new JProgressBar();
        MainWindow.panelTitle = new JPanel();
        MainWindow.panelTitle.add(MainWindow.progressBarTitle);

        MainWindow.progressBarJournal = new JProgressBar();
        MainWindow.panelJournal = new JPanel();
        MainWindow.panelJournal.add(MainWindow.progressBarJournal);

        MainWindow.progressBarYear = new JProgressBar();
        MainWindow.panelYear = new JPanel();
        MainWindow.panelYear.add(MainWindow.progressBarYear);

        //tabbedPaneChart.addTab("title", );
        MainWindow.tabbedPaneChart.addTab("Period", MainWindow.panelYear);
        MainWindow.tabbedPaneChart.addTab("Journal", MainWindow.panelJournal);
        MainWindow.tabbedPaneChart.addTab("Author", MainWindow.panelAuthor);

        //###########################################################

        MainWindow.tabbedPane.addTab("References", MainWindow.panelRedundant);
        MainWindow.tabbedPane.addTab("PDF", MainWindow.panelPDF);
        MainWindow.tabbedPane.addTab("Charts", MainWindow.tabbedPaneChart);

        MainWindow.tabbedPane.setTabPlacement(JTabbedPane.TOP);
        MainWindow.tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        MainWindow.tabbedPane.revalidate();
        MainWindow.tabbedPane.repaint();

        MainWindow.frame.add(MainWindow.tabbedPane, BorderLayout.CENTER);
        MainWindow.frame.invalidate();
        MainWindow.frame.validate();
        MainWindow.frame.repaint();


        ReferenceTabTask referenceTabTask = new ReferenceTabTask(MainWindow.progressBarRedundant);
        referenceTabTask.execute();

        PDFTabTask pdfTabTask = new PDFTabTask(MainWindow.progressBarPDF);
        pdfTabTask.execute();

        ChartTabTask chartTabTask = new ChartTabTask();
        chartTabTask.execute();




        return null;
    }
    @Override
    protected void done() {

        MainWindow.menuBar.setEnabled(false);
        MainWindow.menuItemExportBibTex.setEnabled(true);
        MainWindow.menuItemExportCSV.setEnabled(true);
        MainWindow.menuItemExportExcel.setEnabled(true);
        MainWindow.menuItemSave.setEnabled(true);

    }

}
