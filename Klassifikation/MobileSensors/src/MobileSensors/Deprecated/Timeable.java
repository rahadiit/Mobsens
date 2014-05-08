package MobileSensors.Deprecated;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public abstract class Timeable {

	private long time;

	private long difference;

	public long getTimeDifference() {
		return difference;
	}

	public void setTimeDifference(long difference) {
		this.difference = difference;
	}

	public void setTimeDifference(Timeable t) {
		this.difference = getTimeDifference(t);
	}

	public long getTime() {
		return this.time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public long getTimeDifference(Timeable t) {
		long time = this.getTime() - t.getTime();
		time = Math.abs(time);

		return time;
	}

	public static <T extends Timeable> Collection<Collection<T>> window(
			Collection<T> list, long span) {
		Collection<Collection<T>> result = new ArrayList<>();

		if (!list.isEmpty()) {
			ArrayList<T> alist = new ArrayList<>(list);

			long timeFrom = alist.get(0).getTime();
			Collection<T> window;
			do {
				window = window(list, timeFrom, timeFrom + span);
				if (!window.isEmpty())
					result.add(window);
				timeFrom += span;

			} while (!window.isEmpty());
		}
		return result;

	}
	
	public static <T extends Timeable> Collection<Collection<T>> window(long span, long shift,
			Collection<T> list ) {
		Collection<Collection<T>> result = new ArrayList<>();

		if (!list.isEmpty()) {
			ArrayList<T> alist = new ArrayList<>(list);

			long timeFrom = alist.get(0).getTime();
			Collection<T> window;
			do {
				window = window(list, timeFrom, timeFrom + span);
				if (!window.isEmpty())
					result.add(window);
				timeFrom += shift;

			} while (!window.isEmpty());
		}
		return result;

	}
	
	
	public static <T extends Timeable> Collection<T> window(
			Collection<T> list, int startIndex, int endIndex) {
		Collection<T> result = new ArrayList<>();

		if (!list.isEmpty()) {
			ArrayList<T> alist = new ArrayList<>(list);
			
			for(int i=startIndex; i<endIndex; i++){
				result.add(alist.get(i));
			}
		}
		
		return result;
	}

	/**
	 * 
	 * @param list
	 * @param index
	 * @return samling-rate in HZ. abhaengig vom parameter index. der index
	 *         befindet sich in der mitte des beobachteten zeitraumes
	 */
	public static <T extends Timeable> double samplingRateBetween(
			Collection<T> list, int index) {
		long span = 1000; // 1sek
		double result = -1;
		long spanHalf = span / 2;

		ArrayList<T> holeList = (ArrayList<T>) list;
		ArrayList<T> window = new ArrayList<>();

		long leastTime = -1;
		long lastTime = -1;
		int i = index;
		// alle werte kleiner einsammeln
		while (i > 0
				&& Math.abs(holeList.get(i).getTime()
						- holeList.get(index).getTime()) < spanHalf) {
			window.add(holeList.get(i));
			leastTime = holeList.get(i).getTime();
			i--;
		}
		i = index;
		// alle werte grosser einsammeln
		while (i < holeList.size()
				&& Math.abs(holeList.get(i).getTime()
						- holeList.get(index).getTime()) < spanHalf) {
			window.add(holeList.get(i));
			lastTime = holeList.get(i).getTime();
			i++;
		}
		
		double divisor = (double) Math.abs(lastTime - leastTime) / 1000;

		try {
			result = ((double) window.size()) / divisor;
		} catch (ArithmeticException ae) {
			result = -1;
		}
		return result;
	}

	/**
	 * 
	 * @param list
	 *            List containing Objects implementing Timeable
	 * @param timeFrom
	 *            start of timeframe in ms since 1970
	 * @param timeTo
	 *            end of timeframe in ms since 1970
	 * @return every object from the list in specific timeframe
	 * @return empty collcetion if no objects in timespan
	 */
	public static <T extends Timeable> Collection<T> window(Collection<T> list,
			long timeFrom, long timeTo) {
		Collection<T> window = new ArrayList<>();

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
	public static <T extends Timeable> T to(Collection<T> list, T from,
			long span) {

		ArrayList<T> times = new ArrayList<>(window(list, from, span));

		if (!times.isEmpty())
			return times.get(times.size() - 1);

		return null;
	}

	/**
	 * 
	 * @param list
	 *            objects
	 * @param from
	 *            object
	 * @param span
	 *            timeframe in ms
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
