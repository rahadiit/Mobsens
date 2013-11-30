package mobsens.collector;

interface CollectorIPC
{
	/**
	 * True, wenn der Collector noch am Aufnehmen ist
	 */
	boolean isCollecting();
}