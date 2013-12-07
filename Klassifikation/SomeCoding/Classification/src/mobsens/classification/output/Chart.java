package mobsens.classification.output;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import mobsens.classification.data.Annotation;
import mobsens.classification.data.Location;

import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.event.AnnotationChangeListener;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Chart {
	
	public static void addAnnotations(ArrayList<Annotation> annotations, XYPlot plot){
		for(Annotation annotation:annotations){
			plot.addAnnotation(new XYTextAnnotation(annotation.getTag(), annotation.getTime(), 0.5));
		}
	}
	
	public static XYSeries speedData(String name,ArrayList<Location> values){
		XYSeries result = new XYSeries(name);
		
		for(int i=0;i<values.size();i++){
			result.add(values.get(i).getTime(),values.get(i).getSpeed());
		}
		
		return result;
	}
	
	public static XYSeriesCollection dataset(ArrayList<XYSeries> series){
		XYSeriesCollection result = new XYSeriesCollection();
	
		for(XYSeries serie:series){
			result.addSeries(serie);
		}
		
		return result;
	}
	
	public static XYPlot speedPlot(ArrayList<Location> values){
		
		XYSeries serie = speedData("1", values);
		ArrayList<XYSeries> series= new ArrayList<>();
		series.add(serie);
		XYSeriesCollection dataset = dataset(series);
		XYPlot result = plot(dataset,"time", "speed");
		
		NumberAxis domain  = (NumberAxis)result.getDomainAxis();
		domain.setRange(values.get(0).getTime(), values.get(values.size()-1).getTime());
		
		return result;
		
	}
	
	public static XYPlot plot(XYSeriesCollection dataset, String xAxis, String yAxis){
		//XYDotRenderer dot = new XYDotRenderer();
		//dot.setDotHeight(5);
		//dot.setDotWidth(5);
		
		return new XYPlot(dataset,new NumberAxis(xAxis), new NumberAxis(yAxis), new XYSplineRenderer());
		
	}
	
	
}
