package tasks.visualization.charts;

import functionality.*;
import functionality.ChartSolver;
import model.ChartObject;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by Philipo on 24.09.2017.
 */
public class AuthorTask extends SwingWorker<Void, Integer> {

        JProgressBar jpb;


public AuthorTask(JProgressBar jpb) {
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

    List<String> listAuthorsReferences = ChartSolver.getAuthorListReferences();
    List<String> listAuthorsPDFS = ChartSolver.getAuthorListPDF();

    List<ChartObject> listChartObjectAuthorReferences = ChartSolver.getAuthorsChartObjectReferences(listAuthorsReferences);
    List<ChartObject> listChartObjectAuthorPDF = ChartSolver.getAuthorsChartObjectPDF(listAuthorsPDFS);

    final DefaultPieDataset datasetReferences = new DefaultPieDataset();
    final DefaultPieDataset datasetPDFs = new DefaultPieDataset();

    for (ChartObject chartObject : listChartObjectAuthorReferences) {

        double percent = (((double) chartObject.getAuthorsFrequency())/listChartObjectAuthorReferences.size())*100;

        if(percent > 1.00)
        {
            datasetReferences.setValue(chartObject.getAuthors(), chartObject.getAuthorsFrequency());
        }
    }
    if(datasetReferences.getItemCount() == 0)
    {
        for (ChartObject chartObject : listChartObjectAuthorReferences) {

            if(chartObject.getAuthorsFrequency() > 1)
            {
                datasetReferences.setValue(chartObject.getAuthors(), chartObject.getAuthorsFrequency());
            }
        }
    }

    JFreeChart chartReferences = ChartFactory.createPieChart("Backward citation set",datasetReferences,
            false, true, false);


    PiePlot plot = (PiePlot) chartReferences.getPlot();
    plot.setLabelGenerator(null);
    plot.setNoDataMessage("No proposed authors");
    plot.setLabelFont(new Font("SanSerif", Font.PLAIN,12));

    ChartPanel chartPanelReferences = new ChartPanel(chartReferences);


    for (ChartObject chartObject : listChartObjectAuthorPDF) {


        datasetPDFs.setValue(chartObject.getAuthors(), chartObject.getAuthorsFrequency());

    }

    JFreeChart chartPDFs= ChartFactory.createPieChart("Current literature set",datasetPDFs,
            false, true, false);

    PiePlot plotPDF = (PiePlot) chartPDFs.getPlot();
    plotPDF.setLabelGenerator(null);
    plotPDF.setNoDataMessage("No data available");
    plotPDF.setLabelFont(new Font("SanSerif", Font.PLAIN,12));

    ChartPanel chartPanelPDFS= new ChartPanel(chartPDFs);

    JScrollPane scrollPaneBackward = ChartSolver.getScrollPaneAuthors(listChartObjectAuthorReferences) ;
    JScrollPane scrollPanePDF = ChartSolver.getScrollPaneAuthors(listChartObjectAuthorPDF);


    JSplitPane jsplitPaneBackward = new JSplitPane(JSplitPane.VERTICAL_SPLIT, chartPanelReferences, scrollPaneBackward);
    jsplitPaneBackward.setDividerSize(0);
    jsplitPaneBackward.setResizeWeight(0.5);

    JSplitPane jsplitPanePDF = new JSplitPane(JSplitPane.VERTICAL_SPLIT, chartPanelPDFS, scrollPanePDF);
    jsplitPanePDF.setDividerSize(0);
    jsplitPanePDF.setResizeWeight(0.5);


    JSplitPane jsplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jsplitPanePDF, jsplitPaneBackward);
    jsplitPane.setDividerSize(0);
    jsplitPane.setResizeWeight(0.5);


    MainWindow.chartPanelAuthors = jsplitPane;


    jpb.setIndeterminate(false);

    MainWindow.tabbedPaneChart.setComponentAt(2, MainWindow.chartPanelAuthors);


    return null;
        }
@Override
protected void done() {

    MainWindow.frame.invalidate();
    MainWindow.frame.validate();
    MainWindow. frame.repaint();

        }


        }

