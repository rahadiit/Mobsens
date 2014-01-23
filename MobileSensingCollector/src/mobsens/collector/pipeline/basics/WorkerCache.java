package mobsens.collector.pipeline.basics;

import mobsens.collector.pipeline.Driver;
import mobsens.collector.util.threading.Worker;

/**
 * Automatisiert Releasender Cache
 * 
 * @author Pizza
 * 
 * @param <Item>
 *            Klasse der gecacheten Items
 */
public class WorkerCache<Item> extends Cache<Item> implements Driver
{
	/**
	 * Initiale Einstellung der Releaserate in Millisekunden
	 */
	public static final int INITIAL_RELEASE_DELAY = 100;

	/**
	 * Worker, das Detachment ausführt
	 */
	private final Worker worker;

	/**
	 * True, wenn der Worker die gespeicherten Objekte weiterleiten soll
	 */
	private boolean releasing;

	/**
	 * Releaserate in Millisekunden
	 */
	private int releaseDelay;

	/**
	 * True, wenn im Worker noch ein Release ansteht
	 */
	private boolean releasePending;

	/**
	 * Erstellt den automatisch entleerenden Cache
	 * 
	 * @param allowConcurrentWrite
	 *            True, wenn konkurrentes Schreiben des Caches gewünscht ist
	 */
	public WorkerCache(boolean allowConcurrentWrite)
	{
		super(allowConcurrentWrite);

		worker = new Worker();
		releaseDelay = INITIAL_RELEASE_DELAY;
	}

	/**
	 * True, wenn der Worker die gespeicherten Objekte weiterleiten soll
	 */
	public boolean isReleasing()
	{
		return releasing;
	}
	/**
	 * Startet den automatiesierten Releasevorgang
	 */
	@Override
	public void start()
	{
		if (!releasing)
		{
			releasing = true;

			if (!releasePending)
			{
				releasePending = worker.getHandler().postDelayed(workerEndpoint, releaseDelay);
			}
		}
	}

	/**
	 * Beendet den automatiesierten Releasevorgang
	 */
	@Override
	public void stop()
	{
		if (releasing)
		{
			releasing = false;
		}
	}

	/**
	 * Gibt die Releaserate in Millisekunden aus
	 */
	public int getReleaseRate()
	{
		return releaseDelay;
	}

	/**
	 * Setzt die Releaserate in Millisekunden
	 * 
	 * @param releaseRate
	 *            Die neue Rate
	 */
	public void setReleaseRate(int releaseRate)
	{
		this.releaseDelay = releaseRate;
	}

	/**
	 * Endpunkt für den Worker
	 */
	private final Runnable workerEndpoint = new Runnable()
	{
		@Override
		public void run()
		{
			releasePending = false;

			if (releasing)
			{
				releaseAllItems();

				releasePending = worker.getHandler().postDelayed(workerEndpoint, releaseDelay);
			}
		}
	};
}
