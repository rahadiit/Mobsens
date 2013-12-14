package mobsens.collector.consumers;

import android.content.ContextWrapper;
import mobsens.collector.drivers.locations.LocationOutput;
import mobsens.collector.intents.IntentLocationUpdate;
import mobsens.collector.pipeline.Consumer;

public class LocationPublisher implements Consumer<LocationOutput>
{
	public final ContextWrapper contextWrapper;

	public LocationPublisher(ContextWrapper contextWrapper)
	{
		this.contextWrapper = contextWrapper;
	}

	@Override
	public void consume(LocationOutput item)
	{
		IntentLocationUpdate.sendBroadcast(contextWrapper, item.time, item.latitude, item.longitude, item.accuracy, item.altitude, item.bearing, item.speed);
	}

}
