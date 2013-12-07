package mobsens.collector.drivers.locations;

import java.util.Date;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import mobsens.collector.pipeline.BasicGenerator;
import mobsens.collector.pipeline.Driver;

public abstract class LocationDriver extends BasicGenerator<LocationOutput> implements Driver<LocationOutput>
{


}