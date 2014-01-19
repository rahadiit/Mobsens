package MobileSensors.Storage.Sensors.Sensor;

import java.util.ArrayList;
import java.util.Collection;

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
