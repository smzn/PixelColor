package pixelcolor;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Graph extends Frame implements ActionListener,WindowListener{
	String[] color_name = {"white","olive","yellow","fuchsia","silver","aqua","lime","red","gray","bule","green","purple","black","navy","teal","maroon","madder","dawn"};
	
	public Graph(int [] r_range) {
		addWindowListener(this);
		this.setTitle("Color Distribution");
		
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		for(int i = 0; i < color_name.length; i++) {
			data.addValue(r_range[i], "", color_name[i]+"");
			
			//data.addValue(timequeue[4].get(i), node_index[4]+"", i+"");
			//data.addValue(timequeue[5].get(i), node_index[5]+"", i+"");
		}
	    JFreeChart chart = ChartFactory.createBarChart("Color Distribution","Color","Frequency",data,PlotOrientation.VERTICAL,true,false,false);
	    ChartPanel cpanel = new ChartPanel(chart);
	    add(cpanel, BorderLayout.CENTER);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
