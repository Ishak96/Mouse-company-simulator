package stat;

import java.awt.Dimension;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class StatPane extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JFreeChart chart;
	private ChartPanel chartPanel;
	private DefaultCategoryDataset dataset;	
	
	
	public StatPane() {
		initChart();
		add(chartPanel);		
	}
	
	public void initChart() {
		dataset = new DefaultCategoryDataset();
		chart = ChartFactory.createBarChart("Grid evolution","", "Values", dataset,
				PlotOrientation.VERTICAL, true, true, false);
		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(390,250));
	}
	
	public void updateChart() {
		
		String category = "";
		String series1 = "Mice";
		String series2 = "Deaths";
		String series3 = "Births";
		String series4 = "Food";
		String series6 = "Pregnant";
		
		dataset.addValue(Statistics.getInstance().getAliveMice(), series1, category);
		dataset.addValue(Statistics.getInstance().getDeadMice(), series2, category);
		dataset.addValue(Statistics.getInstance().getBornedMice(), series3, category);
		dataset.addValue(Statistics.getInstance().getNbrFoodSources(), series4, category);
		dataset.addValue(Statistics.getInstance().getPregnantMice(), series6, category);
		
	}
	
}

