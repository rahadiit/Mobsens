package mobsens.classification.util;

import mobsens.classification.data.Location;

import org.apache.commons.lang3.time.DurationFormatUtils;

public class Time {

	public static String duration(long time1, long time2, String format){
		return DurationFormatUtils.formatDuration((time2-time1),format);
	}
	
	public static String duration(Location loc1, Location loc2, String format){
		return duration((long)loc1.getTime(),(long)loc2.getTime(),format);
	}
}
