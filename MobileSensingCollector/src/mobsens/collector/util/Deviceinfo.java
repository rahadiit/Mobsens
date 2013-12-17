package mobsens.collector.util;

import android.content.ContextWrapper;
import android.os.Build;
import android.provider.Settings.Secure;

public class Deviceinfo
{
	public static String getID(ContextWrapper wrapper)
	{
		final int abstractId = (Secure.getString(wrapper.getContentResolver(), Secure.ANDROID_ID)).hashCode();

		return Integer.toHexString(0xffff & (abstractId >> 16)) + " " + Integer.toHexString(0xffff & (abstractId >> 0));
	}

	public static String getOS()
	{
		return System.getProperty("os.version");
	}

	public static String getSDK()
	{
		return Build.VERSION.SDK;
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
