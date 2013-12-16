package MobileSensors.Test.Output;

import java.util.ArrayList;

import org.jfree.data.xy.XYSeries;

import MobileSensors.Storage.Sensors.Accelerometer;
import MobileSensors.Storage.Sensors.Location;

public class ChartData {

	public static XYSeries accelData(String name,
			ArrayList<Accelerometer> values, int axis) {
		XYSeries result = new XYSeries(name);

		for (int i = 0; i < values.size(); i++) {
			double axisValue = 0;

			if (axis == 0)
				axisValue = values.get(i).getX();
			else if (axis == 1)
				axisValue = values.get(i).getY();
			else if (axis == 2)
				axisValue = values.get(i).getZ();

			result.add(values.get(i).getTime(), axisValue);
		}

		return result;
	}
	
	public static XYSeries getSpeed(String name, ArrayList<Location> values) {
		return getSpeed(name,values,0);
	}
	
	public static XYSeries getSpeed(String name, ArrayList<Location> values, int method) {
		XYSeries result = new XYSeries(name);
		
		for (int i = 0; i < values.size(); i++) {
			double value = 0;

			if (method==0)
				value=values.get(i).getSpeed();
			else if (method == 1)
				value=values.get(i).getSpeedCalcCo();
			else if (method == 2)
				value=values.get(i).getSpeedFusion();
			else if (method == 3)
				value=values.get(i).getJerkFusion();
			
			
			result.add(values.get(i).getTime(), value);
		}
		
		return result;
	}
	
	public static XYSeries getDistance(String name, ArrayList<Location> values, int method) {
		XYSeries result = new XYSeries(name);
		
		for (int i = 0; i < values.size(); i++) {
			double value = 0;

			if (method==0)
				value=values.get(i).getDistanceSumCalcCo();
			else if (method == 1)
				value=values.get(i).getDistanceSumCalcGs();
			else if (method == 2)
				value=values.get(i).getDistanceFusionSum();
			
			result.add(values.get(i).getTime(), value);
		}
		
		return result;
	}
	
	
	
	
	
}
