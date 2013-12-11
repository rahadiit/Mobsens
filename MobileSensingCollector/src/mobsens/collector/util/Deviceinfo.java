package mobsens.collector.util;

import org.json.JSONException;
import org.json.JSONStringer;

import mobsens.collector.wfj.WFJ;
import mobsens.collector.wfj.basics.BasicWFJ;
import android.content.ContextWrapper;
import android.os.Build;
import android.provider.Settings.Secure;

public class Deviceinfo
{
	public static String getID(ContextWrapper wrapper)
	{
		final int abstractId = (Secure.getString(wrapper.getContentResolver(), Secure.ANDROID_ID)).hashCode();

		return "Device " + Integer.toHexString(0xffff & (abstractId >> 16)) + " " + Integer.toHexString(0xffff & (abstractId >> 0));
	}

	public static String getDevice()
	{
		return Build.DEVICE;
	}

	public static String getModel()
	{
		return Build.MODEL;
	}

	public static String getManufacturer()
	{
		return Build.MANUFACTURER;
	}

	public static String getProduct()
	{
		return Build.PRODUCT;
	}
}
