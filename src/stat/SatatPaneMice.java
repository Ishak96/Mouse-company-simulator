package stat;

import java.awt.Dimension;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class SatatPaneMice extends JPanel{

	private static final long serialVersionUID = 1L;
	private DefaultPieDataset dataset;
	private JFreeChart chart;
	private ChartPanel chartPanel;
	
	public SatatPaneMice() {
		initChart();
		add(chartPanel);
	}
	
	public void initChart() {
		dataset = new DefaultPieDataset();
		chart = ChartFactory.createPieChart("Mouse-Types", dataset, true, true, false);
		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(350,250));
	}

	public void updateChart() {
		
		String series1 = "Cooperative";
		String series2 = "Selfish";
		String series3 = "Receptive";
		String series4 = "Nihilist";
		
		dataset.setValue(series1, new Double(Statistics.getInstance().getCooperative()));
		dataset.setValue(series2, new Double(Statistics.getInstance().getSelfish()));
		dataset.setValue(series3, new Double(Statistics.getInstance().getReciptive()));
		dataset.setValue(series4, new Double(Statistics.getInstance().getNihilist()));
	}
}
