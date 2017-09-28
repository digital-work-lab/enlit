package functionality;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


public class Chart {

	static ArrayList<String[]> strArray = new ArrayList<String[]>();
	static JFrame frame;
	public static void main(String[] argv)
	{
		readCSV();
		createUI();
		
	}

	private static void createUI() {
		frame = new JFrame();
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(getNewChartPane(), BorderLayout.CENTER);		
		frame.setSize(1000, 1000);
		frame.setVisible(true);
		
	}

	private int[][] getAverageLine()
	{
		int[][] intAverage = new int[strArray.size()][2];
		
		for(int i = 0; i < strArray.size(); i++)
		{
			intAverage[i][0]= 139 - Integer.valueOf(strArray.get(i)[0].substring(1,strArray.get(i)[0].length()-1 ));
			intAverage[i][1] = Integer.valueOf(strArray.get(i)[1].substring(1,strArray.get(i)[1].length()-1 ));

		}
		Arrays.sort(intAverage, new Comparator<int[]>() {
			
			public int compare(int[] a, int[] b)
			{
				return Integer.compare(a[0], b[0]);
			}
		} );
			
		int[][] average = new int[intAverage.length][2];
		
		for(int i = 0; i < intAverage.length; i++)
		{
			int averageValue = intAverage[i][0];
				while(intAverage[i][0] == intAverage[i+1][0])
				{
					
				}
			}
		

	return null;
}
	private static Component getNewChartPane() {
XYSeriesCollection result = new XYSeriesCollection();
XYSeries series = new XYSeries("Effectiveness");

for(String[] string : strArray)
{
	int x = 139 - Integer.valueOf(string[0].substring(1,string[0].length()-1 ));
	int y = Integer.valueOf(string[1].substring(1,string[1].length()-1 ));
	series.add(y, x);
	
}
 result.addSeries(series);
JFreeChart chart = ChartFactory.createScatterPlot("Effectiveness of Literature Review Reconstruction"
		, "Reconstruction", "Excluded References",result,PlotOrientation.HORIZONTAL, true, true, false);

ChartPanel chartPanel = new ChartPanel(chart);
return chartPanel;

	}

	private static void readCSV() {

		String csvFile = "/home/grobid/Development/AvgerouEVA/results.csv";
		BufferedReader br = null;
		String line = "";
		String csvSplit = ",";
		
		try
		{
			br = new BufferedReader(new FileReader(csvFile));
			line = br.readLine();
			
			while((line = br.readLine()) != null)
					{
					strArray.add(line.split(csvSplit));
					}
		}
		catch(Exception e)
		{
			
		}
	}
}
