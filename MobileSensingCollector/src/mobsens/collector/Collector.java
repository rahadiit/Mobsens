package mobsens.collector;

import java.util.Date;

import mobsens.collector.communications.ConnectedService;
import mobsens.collector.config.Config;
import mobsens.collector.consumers.LocationPublisher;
import mobsens.collector.consumers.WFJStreamingConsumer;
import mobsens.collector.drivers.annotations.AnnotationDriver;
import mobsens.collector.drivers.connectivity.ConnectivityDriver;
import mobsens.collector.drivers.locations.LocationDriver;
import mobsens.collector.drivers.locations.LocationOutput;
import mobsens.collector.drivers.locations.LocationPSDriver;
import mobsens.collector.drivers.locations.LocationNoPSDriver;
import mobsens.collector.drivers.messaging.StartCollectorDriver;
import mobsens.collector.drivers.messaging.StopCollectorDriver;
import mobsens.collector.drivers.sensors.SensorDriver;
import mobsens.collector.intents.IntentCollectorStatus;
import mobsens.collector.pipeline.basics.ClassFilter;
import mobsens.collector.pipeline.basics.HostDriver;
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

	private final HostDriver messagingDriver;

	private final StartCollectorDriver startCollectorDriver;

	private final StopCollectorDriver stopCollectorDriver;

	private final HostDriver sensorDriver;

	private final SensorDriver accelerometerDriver, gyroscopeDriver, magneticFieldDriver, linearAccelerationDriver, gravityDriver;

	private final LocationDriver locationPSDriver, locationSysDriver;

	private final ConnectivityDriver connectivityDriver;

	private final AnnotationDriver annotationDriver;

	private final LocationPublisher locationPublisher;

	private final WFJStreamingConsumer wfjStreamer;

	private final WorkerCache<WFJ> wfjDetacher;

	private final ClassFilter<WFJ, Object> wfjFilter;

	private boolean collecting;

	private Date startTime;

	private String title;

	public Collector()
	{
		messagingDriver = new HostDriver();

		// Messaging: Collector starten
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

				sensorDriver.start();
			}
		};
		
		messagingDriver.addDriver(startCollectorDriver);

		// Messaging: Collector stoppen
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
						stringer.key("os").value(Deviceinfo.getOS());
						stringer.key("sdk").value(Deviceinfo.getSDK());
						stringer.key("device").value(Deviceinfo.getDevice());
						stringer.key("model").value(Deviceinfo.getModel());
						stringer.key("manufacturer").value(Deviceinfo.getManufacturer());
						stringer.key("product").value(Deviceinfo.getProduct());
						stringer.endObject();

						stringer.endObject().endObject();
					}
				});

				sensorDriver.stop();

				wfjDetacher.releaseAllItems();
			}
		};
		messagingDriver.addDriver(stopCollectorDriver);

		// Sensor Hosttreiber
		sensorDriver = new HostDriver();

		// Dateiausgabe
		wfjStreamer = new WFJStreamingConsumer(this);
		sensorDriver.addDriver(wfjStreamer);

		// Locationintent-Ausgabe
		locationPublisher = new LocationPublisher(this);

		// Detachment-Cache
		wfjDetacher = new WorkerCache<WFJ>(true);
		wfjDetacher.addConsumer(wfjStreamer);
		wfjDetacher.addConsumer(Pipeline.with(new ClassFilter<LocationOutput, Object>(LocationOutput.class), locationPublisher));
		sensorDriver.addDriver(wfjDetacher);

		// Filter für WFJ Objekte
		wfjFilter = new ClassFilter<WFJ, Object>(WFJ.class);
		wfjFilter.setConsumer(wfjDetacher);

		// Accelerometer
		accelerometerDriver = new SensorDriver(this, Sensor.TYPE_ACCELEROMETER, Calculations.msFromFrequency(Config.FREQUENCY_ACCELEROMETER));
		accelerometerDriver.addConsumer(wfjFilter);
		sensorDriver.addDriver(accelerometerDriver);

		// Gyroskop
		gyroscopeDriver = new SensorDriver(this, Sensor.TYPE_GYROSCOPE, Calculations.msFromFrequency(Config.FREQUENCY_GYROSCOPE));
		gyroscopeDriver.addConsumer(wfjFilter);
		sensorDriver.addDriver(gyroscopeDriver);

		// Magnetfeld
		magneticFieldDriver = new SensorDriver(this, Sensor.TYPE_MAGNETIC_FIELD, Calculations.msFromFrequency(Config.FREQUENCY_MAGNETIC_FIELD));
		magneticFieldDriver.addConsumer(wfjFilter);
		sensorDriver.addDriver(magneticFieldDriver);

		// Lineare Beschleunigung
		linearAccelerationDriver = new SensorDriver(this, Sensor.TYPE_LINEAR_ACCELERATION, Calculations.msFromFrequency(Config.FREQUENCY_LINEAR_ACCELEROMETER));
		linearAccelerationDriver.addConsumer(wfjFilter);
		sensorDriver.addDriver(linearAccelerationDriver);

		// Gravitation
		gravityDriver = new SensorDriver(this, Sensor.TYPE_GRAVITY, Calculations.msFromFrequency(Config.FREQUENCY_GRAVITY));
		gravityDriver.addConsumer(wfjFilter);
		sensorDriver.addDriver(gravityDriver);

		// Position mit Playservices
		locationPSDriver = new LocationPSDriver(this, Calculations.msFromFrequency(Config.FREQUENCY_LOCATION), 0.0f);
		locationPSDriver.addConsumer(wfjFilter);
		sensorDriver.addDriver(locationPSDriver);

		// Position ohne Playservices
		locationSysDriver = new LocationNoPSDriver(this, Calculations.msFromFrequency(Config.FREQUENCY_LOCATION), 0.0f);
		locationSysDriver.addConsumer(wfjFilter);
		sensorDriver.addDriver(locationSysDriver);

		// Verbindungsstatus
		connectivityDriver = new ConnectivityDriver(this);
		connectivityDriver.addConsumer(wfjFilter);
		sensorDriver.addDriver(connectivityDriver);

		// Annotationen
		annotationDriver = new AnnotationDriver(this);
		annotationDriver.addConsumer(wfjFilter);
		sensorDriver.addDriver(annotationDriver);

		collecting = false;
	}

	@Override
	public void onCreate()
	{
		super.onCreate();

		// Messaging Listener anschalten
		messagingDriver.start();

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
		Logging.log(this, "Collector:" + sid, "Destroyed", null);

		// Messaging Listener abschalten
		messagingDriver.stop();

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
