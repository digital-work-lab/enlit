package tasks.visualization.charts;

import functionality.MainWindow;
import functionality.ChartSolver;
import model.ChartObject;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.util.List;

/**
 * Created by Philipo on 24.09.2017.
 */
public class TimeTask extends SwingWorker<Void, Integer> {


    JProgressBar jpb;
        public TimeTask(JProgressBar jpb) {

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
            List<ChartObject> listChartObjectReferences = ChartSolver.createChartObjectsReferences();
            List<ChartObject> listChartObjectPDFS = ChartSolver.createChartObjectsPDFS();

            jpb.setIndeterminate(false);

            TimeSeries seriesPDFs = new TimeSeries("Current literature set");
            TimeSeries seriesReferences = new TimeSeries("Backward citation set");

            for (ChartObject co : listChartObjectReferences) {
                Year year = new Year(co.getYear());
                seriesReferences.add(year, co.getYearFrequency());
            }

            for (ChartObject co : listChartObjectPDFS) {
                Year year = new Year(co.getYear());
                seriesPDFs.add(year, co.getYearFrequency());
            }
            seriesReferences.setDescription("Backward citation set");
            seriesPDFs.setDescription("Current literature set");
            TimeSeriesCollection time = new TimeSeriesCollection();
            time.addSeries(seriesReferences);
            time.addSeries(seriesPDFs);

            XYDataset xyDataset = time;
            JFreeChart chart = ChartFactory.createTimeSeriesChart(
                    "Coverage of literature over time", "Year", "Relative frequency",
                    xyDataset, true, true, false);


            ChartPanel chartPanel = new ChartPanel(chart);

            MainWindow.chartPanelTime = chartPanel;

            jpb.setIndeterminate(false);

            MainWindow.tabbedPaneChart.setComponentAt(0, MainWindow.chartPanelTime);


            return null;
        }
        @Override
        protected void done() {

            MainWindow.frame.invalidate();
            MainWindow.frame.validate();
            MainWindow. frame.repaint();

        }


    }

