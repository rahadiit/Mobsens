package mobsens.collector.drivers.locations;

import java.util.Date;

import mobsens.collector.wfj.basics.BasicWFJ;

import org.json.JSONException;
import org.json.JSONStringer;

public class LocationOutput extends BasicWFJ
{
	/**
	 * Zeit
	 */
	public final Date time;

	/**
	 * Latitude
	 */
	public final double latitude;

	/**
	 * Longitude
	 */
	public final double longitude;

	/**
	 * Optionale Genauigkeit
	 */
	public final Float accuracy;

	/**
	 * Optionale Höhe
	 */
	public final Double altitude;

	/**
	 * Optionale Ausrichtung
	 */
	public final Float bearing;

	/**
	 * Optionale Geschwindigkeit
	 */
	public final Float speed;

	public LocationOutput(Date time, double latitude, double longitude, Float accuracy, Double altitude, Float bearing, Float speed)
	{
		this.time = time;
		this.latitude = latitude;
		this.longitude = longitude;
		this.accuracy = accuracy;
		this.altitude = altitude;
		this.bearing = bearing;
		this.speed = speed;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accuracy == null) ? 0 : accuracy.hashCode());
		result = prime * result + ((altitude == null) ? 0 : altitude.hashCode());
		result = prime * result + ((bearing == null) ? 0 : bearing.hashCode());
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((speed == null) ? 0 : speed.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		LocationOutput other = (LocationOutput) obj;
		if (accuracy == null)
		{
			if (other.accuracy != null) return false;
		}
		else if (!accuracy.equals(other.accuracy)) return false;
		if (altitude == null)
		{
			if (other.altitude != null) return false;
		}
		else if (!altitude.equals(other.altitude)) return false;
		if (bearing == null)
		{
			if (other.bearing != null) return false;
		}
		else if (!bearing.equals(other.bearing)) return false;
		if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude)) return false;
		if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude)) return false;
		if (speed == null)
		{
			if (other.speed != null) return false;
		}
		else if (!speed.equals(other.speed)) return false;
		if (time == null)
		{
			if (other.time != null) return false;
		}
		else if (!time.equals(other.time)) return false;
		return true;
	}

	@Override
	protected void generateTo(JSONStringer jsonStringer) throws JSONException
	{
		jsonStringer.object();
		jsonStringer.key("location");
		jsonStringer.object();

		jsonStringer.key("time");
		jsonStringer.value(time.getTime());

		jsonStringer.key("latitude");
		jsonStringer.value(latitude);

		jsonStringer.key("longitude");
		jsonStringer.value(longitude);

		if (accuracy != null)
		{
			jsonStringer.key("accuracy");
			jsonStringer.value((double) (float) accuracy);
		}

		if (altitude != null)
		{
			jsonStringer.key("altitude");
			jsonStringer.value((double) altitude);
		}

		if (bearing != null)
		{
			jsonStringer.key("bearing");
			jsonStringer.value((double) (float) bearing);
		}

		if (speed != null)
		{
			jsonStringer.key("speed");
			jsonStringer.value((double) (float) speed);
		}

		jsonStringer.endObject();
		jsonStringer.endObject();
	}
}
