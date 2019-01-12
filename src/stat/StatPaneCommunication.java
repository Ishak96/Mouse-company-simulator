package stat;

import java.awt.Dimension;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class StatPaneCommunication extends JPanel{

	private static final long serialVersionUID = 1L;
	private XYSeriesCollection dataset = new XYSeriesCollection();
	private JFreeChart chart;
	private ChartPanel chartPanel = new ChartPanel(chart);
	private XYSeries series1 = new XYSeries("Truths");
	private XYSeries series2 = new XYSeries("Lies");
	
	public StatPaneCommunication() {
		initChart();
		add(chartPanel);		
	}
	
	public void initChart() {
		dataset.addSeries(series1);
		dataset.addSeries(series2);
		chart = ChartFactory.createXYLineChart("Exchanged-Infos","Simulation-Turn", "Exchange", dataset,PlotOrientation.VERTICAL, true, true, false);
		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(350,250));
	}
	
	public void updateChart(int simulationTurn) {
		
		series1.add(simulationTurn, Statistics.getInstance().getExchangedTrueInfo());

		series2.add(simulationTurn, Statistics.getInstance().getExchangedflaseInfo());
		
	}
}
