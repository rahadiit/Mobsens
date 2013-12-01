package mobsens.classification.util;

import org.apache.commons.lang3.time.DurationFormatUtils;

public class Time {

	public static String duration(long time1, long time2, String format){
		return DurationFormatUtils.formatDuration((time1-time2),format);
	}
}
