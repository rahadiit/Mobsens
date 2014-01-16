package MobileSensors.Calculation;

import java.util.ArrayList;
import java.util.Collection;

import MobileSensors.Storage.Sensors.Sensor.Timeable;

public class TimeableCalc<T> {

	/**
	 * 
	 * @param list List containing Objects implementing Timeable
	 * @param timeFrom start of timeframe in ms since 1970
	 * @param timeTo end of timeframe in ms since 1970
	 * @return every object from the list in specific timeframe 
	 * @return empty collcetion if no objects in timespan
	 */
	public static <T extends Timeable> Collection<T> window(Collection<T> list,
			long timeFrom, long timeTo) {
		Collection<T> window = new ArrayList<>();

		System.out.println("from "+timeFrom+" to "+timeTo);
		for (T t : list)
			if (t.getTime() >= timeFrom && t.getTime() <= timeTo)
				window.add(t);

		return window;
	}
	
	/**
	 * 
	 * @param list
	 * @param from
	 * @param span
	 * @return last Element of a timespan
	 */
	public static <T extends Timeable> T to (Collection<T> list, T from, long span){
		
		ArrayList<T> times = new ArrayList<>(window(list,from,span));
		
		if(!times.isEmpty())
			return times.get(times.size()-1);
		
		return null;
		
	}
	
	
/**
 * 
 * @param list objects
 * @param from object
 * @param span timeframe in ms
 * @return
 */
	public static <T extends Timeable> Collection<T> window(Collection<T> list,
			T from, long span) {

		if (list.contains(from))
			return window(list, from.getTime(), from.getTime() + span);
		
		return new ArrayList<T>();

	}

	public static <T extends Timeable> Collection<T> window(Collection<T> list,
			T middle, long timeBefore, long timeAfter) {

		if (list.contains(middle))
			return window(list, middle.getTime() - timeBefore, middle.getTime()
					+ timeAfter);
		
		return new ArrayList<T>();

	}

}
