package mobsens.collector;

import java.util.Date;

import mobsens.collector.communications.ConnectedService;
import mobsens.collector.config.Config;
import mobsens.collector.consumers.LogConsumer;
import mobsens.collector.consumers.WFJStreamingConsumer;
import mobsens.collector.drivers.annotations.AnnotationDriver;
import mobsens.collector.drivers.connectivity.ConnectivityDriver;
import mobsens.collector.drivers.locations.LocationDriver;
import mobsens.collector.drivers.locations.LocationOutput;
import mobsens.collector.drivers.locations.LocationPSDriver;
import mobsens.collector.drivers.locations.LocationSysDriver;
import mobsens.collector.drivers.messaging.StartCollectorDriver;
import mobsens.collector.drivers.messaging.StartCollectorOutput;
import mobsens.collector.drivers.messaging.StopCollectorDriver;
import mobsens.collector.drivers.messaging.StopCollectorOutput;
import mobsens.collector.drivers.sensors.SensorDriver;
import mobsens.collector.intents.IntentCollectorStatus;
import mobsens.collector.pipeline.Consumer;
import mobsens.collector.pipeline.basics.Dispatcher;
import mobsens.collector.pipeline.basics.Filter;
import mobsens.collector.pipeline.basics.WorkerCache;
import mobsens.collector.util.Calculations;
import mobsens.collector.util.Logging;
import mobsens.collector.util.Pipeline;
import mobsens.collector.wfj.WFJ;
import mobsens.collector.wfj.basics.BasicWFJ;

import org.json.JSONException;
import org.json.JSONStringer;

import android.content.Intent;
import android.hardware.Sensor;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings.Secure;
import android.util.Log;

public class Collector extends ConnectedService
{
	private final int sid = Float.floatToIntBits((float) Math.random()) % 10000;

	private String did;

	public final CollectorIPC IPC_ENDPOINT = new CollectorIPC.Stub()
	{
		@Override
		public boolean isCollecting() throws RemoteException
		{
			return collecting;
		}
	};

	public final Consumer<StartCollectorOutput> START_COLLECTOR_ENDPOINT = new Consumer<StartCollectorOutput>()
	{
		@Override
		public void consume(final StartCollectorOutput item)
		{
			collecting = true;
			IntentCollectorStatus.sendBroadcast(Collector.this, true);

			startTime = new Date();
			title = item.title;

			Logging.log(Collector.this, "Collector:" + sid, "Starting collection", null);

			wfjStreamer.setLocation("wfj/" + item.title + new Date().getTime() + ".wfj");

			wfjStreamer.start();

			wfjDetacher.start();

			for (SensorDriver sensorDriver : sensorDrivers)
			{
				sensorDriver.start();
			}

			locationDriver.start();

			connectivityDriver.start();

			annotationDriver.start();
		}
	};

	public final Consumer<StopCollectorOutput> STOP_COLLECTOR_ENDPOINT = new Consumer<StopCollectorOutput>()
	{
		@Override
		public void consume(StopCollectorOutput item)
		{
			collecting = false;
			IntentCollectorStatus.sendBroadcast(Collector.this, false);

			final Date endTime = new Date();

			Logging.log(Collector.this, new Date(), "Collector", "Stopping collection", null);

			// Rec-Token schreiben
			wfjStreamer.consume(new BasicWFJ()
			{
				@Override
				public void generateTo(JSONStringer stringer) throws JSONException
				{
					stringer.object().key("rec").object().key("title").value(title).key("did").value(did).key("time_start").value(startTime.getTime()).key("time_stop")
							.value(endTime.getTime()).endObject().endObject();
				}
			});

			for (SensorDriver sensorDriver : sensorDrivers)
			{
				sensorDriver.stop();
			}

			locationDriver.stop();

			connectivityDriver.stop();

			annotationDriver.stop();

			wfjStreamer.stop();

			wfjDetacher.stop();
			wfjDetacher.releaseAllItems();
		}
	};

	private final StartCollectorDriver startCollectorDriver;

	private final StopCollectorDriver stopCollectorDriver;

	private SensorDriver[] sensorDrivers;

	private LocationDriver locationDriver;

	private ConnectivityDriver connectivityDriver;

	private AnnotationDriver annotationDriver;

	private WFJStreamingConsumer wfjStreamer;

	private LogConsumer wfjLogger;

	private Dispatcher<WFJ> wfjDispatcher;

	private WorkerCache<WFJ> wfjDetacher;

	private Filter<WFJ, Object> wfjFilter;

	private boolean collecting;

	private Date startTime;

	private String title;

	public Collector()
	{
		startCollectorDriver = new StartCollectorDriver(this);
		startCollectorDriver.setConsumer(START_COLLECTOR_ENDPOINT);

		stopCollectorDriver = new StopCollectorDriver(this);
		stopCollectorDriver.setConsumer(STOP_COLLECTOR_ENDPOINT);

		collecting = false;
	}

	@Override
	public void onCreate()
	{
		super.onCreate();

		did = "DEV" + Integer.toHexString((Secure.getString(getContentResolver(), Secure.ANDROID_ID)).hashCode());

		sensorDrivers = new SensorDriver[] { new SensorDriver(this, Sensor.TYPE_ACCELEROMETER, Calculations.msFromFrequency(Config.FREQUENCY_ACCELEROMETER)),
				new SensorDriver(this, Sensor.TYPE_GYROSCOPE, Calculations.msFromFrequency(Config.FREQUENCY_GYROSCOPE)),
				new SensorDriver(this, Sensor.TYPE_MAGNETIC_FIELD, Calculations.msFromFrequency(Config.FREQUENCY_MAGNETIC_FIELD)),
				new SensorDriver(this, Sensor.TYPE_LINEAR_ACCELERATION, Calculations.msFromFrequency(Config.FREQUENCY_LINEAR_ACCELEROMETER)),
				new SensorDriver(this, Sensor.TYPE_GRAVITY, Calculations.msFromFrequency(Config.FREQUENCY_GRAVITY)) };

		locationDriver = LocationPSDriver.isAvailable(this) ? new LocationPSDriver(this, Calculations.msFromFrequency(Config.FREQUENCY_LOCATION), 0.0f) : new LocationSysDriver(this,
				Calculations.msFromFrequency(Config.FREQUENCY_LOCATION), 0.0f);

		connectivityDriver = new ConnectivityDriver(this);

		annotationDriver = new AnnotationDriver(this);

		wfjStreamer = new WFJStreamingConsumer(this);

		wfjLogger = new LogConsumer(Log.INFO, "WFJ");

		wfjDispatcher = new Dispatcher<WFJ>();
		wfjDispatcher.addConsumer(wfjStreamer);
		wfjDispatcher.addConsumer(Pipeline.with(new Filter<LocationOutput, Object>(LocationOutput.class), wfjLogger));

		wfjDetacher = new WorkerCache<WFJ>(true);
		wfjDetacher.setConsumer(wfjDispatcher);

		wfjFilter = new Filter<WFJ, Object>(WFJ.class);
		wfjFilter.setConsumer(wfjDetacher);

		for (SensorDriver sensorDriver : sensorDrivers)
		{
			sensorDriver.setConsumer(wfjFilter);
		}

		locationDriver.setConsumer(wfjFilter);

		connectivityDriver.setConsumer(wfjFilter);

		annotationDriver.setConsumer(wfjFilter);

		startCollectorDriver.start();

		stopCollectorDriver.start();

		Logging.log(this, "Collector:" + sid, "Created", null);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		return START_STICKY;
	}

	@Override
	public void onDestroy()
	{
		startCollectorDriver.stop();

		stopCollectorDriver.stop();

		Logging.log(this, "Collector:" + sid, "Destroyed", null);

		super.onDestroy();
	}

	@Override
	protected void onConnected()
	{
		Logging.log(this, "Collector:" + sid, "Connected", null);
	}

	@Override
	protected void onDisconnected()
	{
		Logging.log(this, "Collector:" + sid, "Disconnected", null);

		if (!collecting)
		{
			stopSelf();
		}
	}

	@Override
	protected IBinder getBinder()
	{
		return IPC_ENDPOINT.asBinder();
	}
}
