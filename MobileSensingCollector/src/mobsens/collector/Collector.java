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
import mobsens.collector.drivers.messaging.StopCollectorDriver;
import mobsens.collector.drivers.sensors.SensorDriver;
import mobsens.collector.intents.IntentCollectorStatus;
import mobsens.collector.pipeline.basics.ClassFilter;
import mobsens.collector.pipeline.basics.Dispatcher;
import mobsens.collector.pipeline.basics.WorkerCache;
import mobsens.collector.util.Calculations;
import mobsens.collector.util.Deviceinfo;
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
import android.util.Log;

public class Collector extends ConnectedService
{
	private final int sid = Float.floatToIntBits((float) Math.random()) % 10000;

	public final CollectorIPC IPC_ENDPOINT = new CollectorIPC.Stub()
	{
		@Override
		public boolean isCollecting() throws RemoteException
		{
			return collecting;
		}
	};

	private final StartCollectorDriver startCollectorDriver;

	private final StopCollectorDriver stopCollectorDriver;

	private SensorDriver[] sensorDrivers;

	private SensorDriver accelerometerDriver, gyroscopeDriver, magneticFieldDriver, linearAccelerationDriver, gravityDriver;

	private LocationDriver locationDriver;

	private ConnectivityDriver connectivityDriver;

	private AnnotationDriver annotationDriver;

	private WFJStreamingConsumer wfjStreamer;

	private LogConsumer wfjLogger;

	private Dispatcher<WFJ> wfjDispatcher;

	private WorkerCache<WFJ> wfjDetacher;

	private ClassFilter<WFJ, Object> wfjFilter;

	private boolean collecting;

	private Date startTime;

	private String title;

	public Collector()
	{
		startCollectorDriver = new StartCollectorDriver(this)
		{
			@Override
			protected void onStartCollector(String title)
			{
				Collector.this.title = title;

				collecting = true;
				IntentCollectorStatus.sendBroadcast(Collector.this, true);

				startTime = new Date();

				Logging.log(Collector.this, "Collector:" + sid, "Starting collection", null);

				wfjStreamer.setLocation("wfj/" + title + new Date().getTime() + ".wfj");

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

		stopCollectorDriver = new StopCollectorDriver(this)
		{
			@Override
			protected void onStopCollector()
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
						stringer.object().key("rec").object();

						stringer.key("title").value(title);
						stringer.key("time_start").value(startTime.getTime());
						stringer.key("time_stop").value(endTime.getTime());

						// Legacy-Feld
						stringer.key("did").value(Deviceinfo.getID(Collector.this));

						// Neues deviceinfo-feld
						stringer.key("deviceinfo").object();
						stringer.key("id").value(Deviceinfo.getID(Collector.this));
						stringer.key("device").value(Deviceinfo.getDevice());
						stringer.key("model").value(Deviceinfo.getModel());
						stringer.key("manufacturer").value(Deviceinfo.getManufacturer());
						stringer.key("product").value(Deviceinfo.getProduct());
						stringer.endObject();

						stringer.endObject().endObject();
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

		collecting = false;
	}

	@Override
	public void onCreate()
	{
		super.onCreate();

		// Alle Sensoren
		sensorDrivers = new SensorDriver[] {
				// Accelerometer
				accelerometerDriver = new SensorDriver(this, Sensor.TYPE_ACCELEROMETER, Calculations.msFromFrequency(Config.FREQUENCY_ACCELEROMETER)),
				// Gyroskop
				gyroscopeDriver = new SensorDriver(this, Sensor.TYPE_GYROSCOPE, Calculations.msFromFrequency(Config.FREQUENCY_GYROSCOPE)),
				// Magnetfeld
				magneticFieldDriver = new SensorDriver(this, Sensor.TYPE_MAGNETIC_FIELD, Calculations.msFromFrequency(Config.FREQUENCY_MAGNETIC_FIELD)),
				// Lineare Beschleunigung
				linearAccelerationDriver = new SensorDriver(this, Sensor.TYPE_LINEAR_ACCELERATION, Calculations.msFromFrequency(Config.FREQUENCY_LINEAR_ACCELEROMETER)),
				// Gravitation
				gravityDriver = new SensorDriver(this, Sensor.TYPE_GRAVITY, Calculations.msFromFrequency(Config.FREQUENCY_GRAVITY))
		};

		// Positionstreiber
		if (LocationPSDriver.isAvailable(this))
		{
			// Mit Playservices
			locationDriver = new LocationPSDriver(this, Calculations.msFromFrequency(Config.FREQUENCY_LOCATION), 0.0f);
		}
		else
		{
			// Ohne Playservices
			locationDriver = new LocationSysDriver(this, Calculations.msFromFrequency(Config.FREQUENCY_LOCATION), 0.0f);
		}

		// Verbindungsstatus
		connectivityDriver = new ConnectivityDriver(this);

		// Annotationen
		annotationDriver = new AnnotationDriver(this);

		wfjStreamer = new WFJStreamingConsumer(this);

		wfjLogger = new LogConsumer(Log.INFO, "WFJ");

		wfjDispatcher = new Dispatcher<WFJ>();
		wfjDispatcher.addConsumer(wfjStreamer);
		wfjDispatcher.addConsumer(Pipeline.with(new ClassFilter<LocationOutput, Object>(LocationOutput.class), wfjLogger));

		wfjDetacher = new WorkerCache<WFJ>(true);
		wfjDetacher.setConsumer(wfjDispatcher);

		wfjFilter = new ClassFilter<WFJ, Object>(WFJ.class);
		wfjFilter.setConsumer(wfjDetacher);

		for (SensorDriver sensorDriver : sensorDrivers)
		{
			sensorDriver.addConsumer(wfjFilter);
		}

		locationDriver.addConsumer(wfjFilter);

		connectivityDriver.addConsumer(wfjFilter);

		annotationDriver.addConsumer(wfjFilter);

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
