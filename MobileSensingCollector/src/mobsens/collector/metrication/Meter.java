package mobsens.collector.metrication;

import mobsens.collector.util.Meters;

public interface Meter
{
	/**
	 * Indikator für erstes Feld, gültige Eingabe für {@link #getNextField(int)}
	 */
	public static final int FIELD_INIT = Integer.MIN_VALUE;

	/**
	 * Indikator für kein Feld, gültige Ausgabe für {@link #getNextField(int)}
	 */
	public static final int FIELD_NONE = Integer.MAX_VALUE;

	/**
	 * Metertyp binärer Status (Stopped/Started)
	 */
	public static final int TYPE_BINARY_STATUS = 1;

	/**
	 * Metertyp ternärer Status (Stopped/Pending/Started)
	 */
	public static final int TYPE_TERNARY_STATUS = 2;

	/**
	 * Metertyp Anzahl
	 */
	public static final int TYPE_AMOUNT = 3;

	/**
	 * Metertyp Anteil
	 */
	public static final int TYPE_FRACTION = 4;

	/**
	 * <p>
	 * Gibt das Nächste Feld nach dem gegebenen aus
	 * </p>
	 * <p>
	 * Iteration über Felder mit {@link Meters#fields(Meter)} oder:
	 * 
	 * <pre>
	 * for (int field = meter.getNextField(Meter.FIELD_INIT); field != Meter.FIELD_NONE; field = meter.getNextField(field))
	 * {
	 * }
	 * </pre>
	 * 
	 * </p>
	 * 
	 */
	public int getNextField(int field);

	public int getType(int field);

	public String getName(int field);

	public String getValue(int field);

	public boolean addMeterableListener(MeterListener listener);

	public boolean removeMeterableListener(MeterListener listener);
}
