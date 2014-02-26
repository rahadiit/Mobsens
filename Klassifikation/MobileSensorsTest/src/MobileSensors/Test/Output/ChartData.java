package MobileSensors.Test.Output;

import java.util.ArrayList;
import java.util.Collection;

import org.jfree.data.xy.XYSeries;

import MobileSensors.Deprecated.Accelerometer;
import MobileSensors.Deprecated.Location;
import MobileSensors.Enums.Axis;
import MobileSensors.Enums.AcceleroOption;

public class ChartData {

	public static XYSeries accelData(ArrayList<Accelerometer> values,
			Axis axis, AcceleroOption option) {

		XYSeries result = new XYSeries(option.toString().toLowerCase()+axis.toString());

		for (int i = 0; i < values.size(); i++) {

			double axisValue = 0;
			
			axisValue = values.get(i).getOptionValue(option, axis);
			result.add(values.get(i).getTime(), axisValue);
		}

		return result;
	}

	public static XYSeries getSpeed(String name, ArrayList<Location> values) {
		return getSpeed(name, values, 0);
	}

	public static XYSeries getSpeed(String name, ArrayList<Location> values,
			int method) {
		XYSeries result = new XYSeries(name);

		for (int i = 0; i < values.size(); i++) {
			double value = 0;

			if (method == 0)
				value = values.get(i).getSpeed();
			else if (method == 1)
				value = values.get(i).getSpeedCalcCo();
			else if (method == 2)
				value = values.get(i).getSpeedFusion();
			else if (method == 3)
				value = values.get(i).getJerk();

			result.add(values.get(i).getTime(), value);
		}

		return result;
	}

	public static XYSeries getDistance(String name, ArrayList<Location> values,
			int method) {
		XYSeries result = new XYSeries(name);

		for (int i = 0; i < values.size(); i++) {
			double value = 0;

			if (method == 0)
				value = values.get(i).getDistanceSumCalcCo();
			else if (method == 1)
				value = values.get(i).getDistanceSumCalcGs();
			else if (method == 2)
				value = values.get(i).getDistanceFusionSum();

			result.add(values.get(i).getTime(), value);
		}

		return result;
	}

}
