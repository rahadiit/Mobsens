package mobsens.collector.maps;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.MapView.Projection;
import org.osmdroid.views.overlay.Overlay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class ResourceOverlay extends Overlay
{
	private final Context context;

	private final Point screenCoords = new Point();

	private final Paint paint = new Paint();

	public ResourceOverlay(Context context)
	{
		super(context);
		this.context = context;
	}

	private GeoPoint geoPoint;

	public GeoPoint getGeoPoint()
	{
		return geoPoint;
	}

	public void setGeoPoint(GeoPoint geoPoint)
	{
		this.geoPoint = geoPoint;
	}

	private Bitmap bitmap = null;

	private Integer resource = null;

	public void setResource(int i)
	{
		if (resource == null || resource != i)
		{
			resource = i;
			bitmap = null;
		}
	}

	@Override
	protected void draw(Canvas c, MapView parent, boolean arg2)
	{
		if (geoPoint == null) return;

		if (bitmap == null)
		{
			if (resource == null) return;

			bitmap = BitmapFactory.decodeResource(context.getResources(), resource);
		}

		Projection p = parent.getProjection();

		p.toMapPixels(geoPoint, screenCoords);

		c.drawBitmap(bitmap, screenCoords.x - (bitmap.getWidth() / 2), screenCoords.y - (bitmap.getHeight() / 2), paint);
	}
}
