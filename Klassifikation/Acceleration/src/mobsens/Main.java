package mobsens;

import java.io.*;
import java.util.*;

import mobsens.physics.Acceleration;
import mobsens.physics.Jerk;
import mobsens.sensors.AccelerometerValue;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.ChartUtilities; 
import org.jfree.chart.util.*;

public class Main {
	
	public static void main(String[] args) throws IOException {
		
		Reader r = new FileReader("./data/accelerometers.csv");
		
		
		@SuppressWarnings("resource")
		CSVParser parser = new CSVParser(r, CSVFormat.DEFAULT);
		
		List<AccelerometerValue> lala = new ArrayList<AccelerometerValue>();
		
		List<CSVRecord> records = parser.getRecords();
		
		for (int i=1; i < records.size(); i++) {
			
			CSVRecord record = records.get(i);
			
			
			AccelerometerValue av = new AccelerometerValue(
					Long.valueOf(record.get(0)).longValue(),
					Double.valueOf(record.get(1)).doubleValue(),
					Double.valueOf(record.get(2)).doubleValue(),
					Double.valueOf(record.get(3)).doubleValue());
			
			
			lala.add(av);
			
		}
		
		XYSeries series = new XYSeries("lala");
//		series.add(1, 1);
//		series.add(2, 2);
//		series.add(3, 3);
//		series.add(4, 2);
		
		for (int i=1; i < lala.size(); i++) {
			
			AccelerometerValue a1 = lala.get(i-1);
			AccelerometerValue a2 = lala.get(i);
			
			Jerk j = a2.getXAcceleration().jerk(a1.getXAcceleration());
//			
//			double rc = 0.25;
//			double dt = x1.getTime() - x2.getTime();
//			double a  = rc / (rc + dt);
//			
//			double y = a * x1.getValue() + a * (x2.getValue() - x1.getValue());
//			
			
			
			if (!Double.isInfinite(j.getValue()))
			
				series.add(i, j.getValue());
			
			System.out.println(j.getValue());
			
//			if (x1.getValue() > 4 || x1.getValue() < -4) {
//				
//				series.add(i, x1.getValue());
//				
//			}
//			else {
//				
//				series.add(i, 0);
//				
//			}
		
			
		}
		
		
		
		XYSeriesCollection data = new XYSeriesCollection();
		data.addSeries(series);
	
		
	
		
		JFreeChart chart = ChartFactory.createXYLineChart(
				"asdf", "x", "y", data, PlotOrientation.VERTICAL, true, true, false);
		
		
		
		try {
			
			ChartUtilities.saveChartAsPNG(new File("./charts/jerk.png"), chart, series.getItemCount(), 1200 );
			
		}
		catch (IOException e) {
			
			System.err.println("Problem occurred creating chart.");
			
		}
		
	}
	
/*
	public static void main(String[] args) throws IOException {
		
		Reader r = new FileReader("./data/accelerometers.csv");
		
		
		@SuppressWarnings("resource")
		CSVParser parser = new CSVParser(r, CSVFormat.DEFAULT);
		
		List<AccelerometerValue> lala = new ArrayList<AccelerometerValue>();
		
		List<CSVRecord> records = parser.getRecords();
		
		for (int i=1; i < records.size(); i++) {
			
			CSVRecord record = records.get(i);
			
			
			AccelerometerValue av = new AccelerometerValue(
					Long.valueOf(record.get(0)).longValue(),
					Double.valueOf(record.get(1)).doubleValue(),
					Double.valueOf(record.get(2)).doubleValue(),
					Double.valueOf(record.get(3)).doubleValue());
			
			
			lala.add(av);
			
		}
		
		for (int i=1; i < lala.size(); i++) {
			
			AccelerometerValue a1 = lala.get(i-1);
			AccelerometerValue a2 = lala.get(i);
			
			Jerk j = a2.getXAcceleration().jerk(a1.getXAcceleration());
			
//			if (j.getValue() > 1 || j.getValue() < -1)
			
			System.out.println(j.getTime() + ", " + j.getValue());
			
		}
		
	}
	
*/
}
