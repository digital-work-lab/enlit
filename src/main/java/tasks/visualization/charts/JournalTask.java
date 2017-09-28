package tasks.visualization.charts;

import functionality.MainWindow;
import functionality.ChartSolver;
import model.ChartObject;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;

/**
 * Created by Philipo on 24.09.2017.
 */
public class JournalTask extends SwingWorker<Void, Integer> {

    JProgressBar jpb;


    public JournalTask(JProgressBar jpb) {
        this.jpb = jpb;

    }
    @Override
    protected void process(List<Integer> chunks) {
        int i = chunks.get(chunks.size()-1);
        jpb.setValue(i); // The last value in this array is all we care about.
    }
    @Override
    protected Void doInBackground() throws Exception {

        jpb.setIndeterminate(true);
        List<String> listJournal = ChartSolver.getJournalList();
        List<ChartObject> listChartObjectJournalReferences = ChartSolver.getJournalChartObjectReferences(listJournal);
        List<ChartObject> listChartObjectJournalPDFs = ChartSolver.getJournalChartObjectPDF(listJournal);

        final DefaultPieDataset datasetReferences = new DefaultPieDataset();
        final DefaultPieDataset datasetPDFs = new DefaultPieDataset();

        for (ChartObject chartObject : listChartObjectJournalReferences) {

            double percent = (((double) chartObject.getJournalFrequency())/listChartObjectJournalReferences.size())*100	;

            if(percent > 1.00)
            {
                datasetReferences.setValue(chartObject.getJournal(), chartObject.getJournalFrequency());
            }
        }

        JFreeChart chartReferences = ChartFactory.createPieChart("Backward citation set",datasetReferences,
                false, true, false);


        PiePlot plot = (PiePlot) chartReferences.getPlot();
        plot.setLabelGenerator(null);
        plot.setNoDataMessage("No proposed journals");
        plot.setLabelFont(new Font("SanSerif", Font.PLAIN,12));

        ChartPanel chartPanelReferences = new ChartPanel(chartReferences);


        for (ChartObject chartObject : listChartObjectJournalPDFs) {

            datasetPDFs.setValue(chartObject.getJournal(), chartObject.getJournalFrequency());

        }

        JFreeChart chartPDFs= ChartFactory.createPieChart("Current literature set",datasetPDFs,
                false, true, false);

        PiePlot plotPDF = (PiePlot) chartPDFs.getPlot();
        plotPDF.setLabelGenerator(null);
        plotPDF.setNoDataMessage("No data available");
        plotPDF.setLabelFont(new Font("SanSerif", Font.PLAIN,12));

        ChartPanel chartPanelPDFS= new ChartPanel(chartPDFs);

        JTable backwardTable = ChartSolver.getScrollPaneBackward(listChartObjectJournalReferences);
        JTable pdfTable = ChartSolver.getScrollPaneBackward(listChartObjectJournalPDFs);

        ListSelectionModel cellSelectionModelBack = backwardTable.getSelectionModel();
        cellSelectionModelBack.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cellSelectionModelBack.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {

            }
        });

        JScrollPane scrollPaneBackward = new JScrollPane(backwardTable);
        JScrollPane scrollPanePDF = new JScrollPane(pdfTable);

        JSplitPane jsplitPaneBackward = new JSplitPane(JSplitPane.VERTICAL_SPLIT, chartPanelReferences, scrollPaneBackward);
        jsplitPaneBackward.setDividerSize(0);
        jsplitPaneBackward.setResizeWeight(0.5);

        JSplitPane jsplitPanePDF = new JSplitPane(JSplitPane.VERTICAL_SPLIT, chartPanelPDFS, scrollPanePDF);
        jsplitPanePDF.setDividerSize(0);
        jsplitPanePDF.setResizeWeight(0.5);


        JSplitPane jsplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jsplitPanePDF, jsplitPaneBackward);
        jsplitPane.setDividerSize(0);
        jsplitPane.setResizeWeight(0.5);


        jpb.setIndeterminate(false);

        MainWindow.chartPanelJournal = jsplitPane;

        MainWindow.tabbedPaneChart.setComponentAt(1, MainWindow.chartPanelJournal);

        return null;
    }
    @Override
    protected void done() {

        MainWindow.frame.invalidate();
        MainWindow.frame.validate();
        MainWindow. frame.repaint();

    }


}
