package mobsens.collector;

import java.sql.Date;

import java.util.ArrayList;
import mobsens.collector.communications.ConnectingActivity;
import mobsens.collector.drivers.messaging.CollectorStatusDriver;
import mobsens.collector.drivers.messaging.LocationUpdateDriver;
import mobsens.collector.intents.IntentStopCollector;
import mobsens.collector.util.Logging;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.PathOverlay;

import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Map extends ConnectingActivity
{
	private final LocationUpdateDriver locationUpdateDriver;

	private final CollectorStatusDriver collectorStatusDriver;

	private CollectorIPC collectorIPC;

	private MapView mapViewMapPosition;
	
	private MapController mapController;
	
	private Overlay myLocationOverlay;
	
    private ArrayList<OverlayItem> overlays = new ArrayList<OverlayItem>();
    
    //private PathOverlay myPath = new PathOverlay(Color.RED, this);

	private TextView textViewMapSpeed;

	private Button buttonMapStop;
	
    private ResourceProxy mResourceProxy;
    
    private GeoPoint yourLocation = new GeoPoint(53554070, -2959520); 

	public Map()
	{
		super(Collector.class);

		locationUpdateDriver = new LocationUpdateDriver(this)
		{
			@Override
			protected void onLocationUpdate(Date time, double latitude, double longitude, Float accuracy, Double altitude, Float bearing, Float speed)
			{
				if (mapViewMapPosition != null)
				{
					mapViewMapPosition.getController().animateTo(new GeoPoint(latitude, longitude));
					
					//das hier sollte Theoretisch deine momentane position anzeigen
					yourLocation.setLatitudeE6((int) latitude);
					yourLocation.setLongitudeE6((int) longitude);
					//yourLocation = new GeoPoint(latitude, longitude);
					
					//path
					//myPath.addPoint(new GeoPoint(latitude, longitude));
					
					
					//update current location marker
					//overlays.clear();
			        //overlays.add(new OverlayItem("New Overlay", "Your Location", yourLocation));
			        //this.mapViewMapPosition.getOverlays().add(this.myLocationOverlay);
			       
					//update the map?
					mapViewMapPosition.invalidate();

			        
					if (bearing != null)
					{
						mapViewMapPosition.setMapOrientation(bearing);
					}
				}

				if (textViewMapSpeed != null)
				{
					if (speed == null)
					{
						textViewMapSpeed.setText("Unknown speed");

					}
					else
					{
						textViewMapSpeed.setText(speed + "m/s, " + (speed * 3600.0f / 1000.0f) + "km/h");
					}
				}
			}
		};

		collectorStatusDriver = new CollectorStatusDriver(this)
		{

			@Override
			protected void onCollectorStatus(boolean status)
			{
				if (status)
				{
					buttonMapStop.setEnabled(true);
				}
				else
				{
					buttonMapStop.setEnabled(false);
				}
			}
		};

		collectorIPC = null;
	}


	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		//old
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		//get a handle on the XML element
		mapViewMapPosition = (MapView) findViewById(R.id.map_position);
		//Map parameters
		textViewMapSpeed = (TextView) findViewById(R.id.map_speed);
		buttonMapStop = (Button) findViewById(R.id.map_stop);
		mapViewMapPosition.setClickable(true);
		mapViewMapPosition.setBuiltInZoomControls(true);
		mapViewMapPosition.getController().setZoom(16);

		
		//new
        mapController = (MapController) this.mapViewMapPosition.getController();
        GeoPoint mapCenter = new GeoPoint(53554070, -2959520); 
        GeoPoint overlayPoint = new GeoPoint(53554070 + 1000, -2959520 + 1000); 
        //GeoPoint yourLocation = new GeoPoint(53554070, -2959520); 
        mapController.setCenter(mapCenter);
		
        //create an ArrayList of 'OverlayItems'. To this list, we will add the overlays we wish to add to the OSM. 
        //ArrayList<OverlayItem> overlays = new ArrayList<OverlayItem>();
        overlays.add(new OverlayItem("New Overlay", "Overlay Description", overlayPoint));
        overlays.add(new OverlayItem("New Overlay", "Your Location", yourLocation));
        //myPath.addPoint(mapCenter);
        
        //PathOverlay myPath = new PathOverlay(Color.RED, this);
        
        
		
        // instantiate the ItemizedIconOverlay class
        mResourceProxy = new DefaultResourceProxyImpl(getApplicationContext());
        this.myLocationOverlay = new ItemizedIconOverlay<OverlayItem>(overlays, null, mResourceProxy);
        //mapViewMapPosition.getOverlays().add(myPath);
        this.mapViewMapPosition.getOverlays().add(this.myLocationOverlay);
        
        mapViewMapPosition.invalidate();
        
        /*
		//new
		overlayItemArray = new ArrayList<OverlayItem>();
		DefaultResourceProxyImpl defaultResourceProxyImpl 
        = new DefaultResourceProxyImpl(this);
		MyItemizedIconOverlay myItemizedIconOverlay = new MyItemizedIconOverlay(
          overlayItemArray, null, defaultResourceProxyImpl);
		mapViewMapPosition.getOverlays().add(myItemizedIconOverlay);
		
		
		//new
		//Default Ortsmarker
		OverlayItem newMyLocationItem = new OverlayItem(
			       "My Location", "My Location", new GeoPoint(50.363566, 7.559343));
	    overlayItemArray.add(newMyLocationItem);
		*/
	    

		buttonMapStop.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (collectorIPC != null)
				{
					try
					{
						if (collectorIPC.isCollecting())
						{
							IntentStopCollector.sendBroadcast(Map.this);
						}
					}
					catch (RemoteException e)
					{
						Logging.log(Map.this, e);
					}
				}
			}
		});

		locationUpdateDriver.start();
		collectorStatusDriver.start();
	}

	@Override
	protected void onDestroy()
	{
		locationUpdateDriver.stop();
		collectorStatusDriver.stop();

		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	@Override
	protected void onConnected(IBinder service)
	{
		collectorIPC = CollectorIPC.Stub.asInterface(service);

		try
		{
			if (collectorIPC.isCollecting())
			{
				buttonMapStop.setEnabled(true);
			}
			else
			{
				buttonMapStop.setEnabled(false);
			}
		}
		catch (RemoteException e)
		{
			Logging.log(this, e);
		}

		Logging.log(this, "Controller", "Connected", null);
	}

	@Override
	protected void onDisconnected()
	{
		collectorIPC = null;

		Logging.log(this, "Controller", "Disconnected", null);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);

		outState.putDouble(R.id.map_position + "latitude", mapViewMapPosition.getMapCenter().getLatitude());
		outState.putDouble(R.id.map_position + "longitude", mapViewMapPosition.getMapCenter().getLongitude());
		outState.putFloat(R.id.map_position + "orientation", mapViewMapPosition.getMapOrientation());
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);

		mapViewMapPosition.getController().setCenter(
				new GeoPoint(savedInstanceState.getDouble(R.id.map_position + "latitude"), savedInstanceState.getDouble(R.id.map_position + "longitude")));
		mapViewMapPosition.setMapOrientation(savedInstanceState.getFloat(R.id.map_position + "orientation"));
	}


	

	
	}



