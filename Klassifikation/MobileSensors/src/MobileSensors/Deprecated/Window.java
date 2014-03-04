package MobileSensors.Deprecated;

import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Window {
	private final NavigableMap<Long, double[]> values = new TreeMap<>();

	private long innerStart;

	private long innerLength;

	public long getInnerStart() {
		return innerStart;
	}

	public void setInnerStart(long innerStart) {
		this.innerStart = innerStart;
	}

	public long getInnerLength() {
		return innerLength;
	}

	public void setInnerLength(long innerLength) {
		this.innerLength = innerLength;
	}

	public NavigableMap<Long, double[]> getValues() {
		return values;
	}

	public FixedWindow sampleNonLossy() {
		final long[] distances = new long[values.size()];

		int i = 0;
		for (long e : values.keySet()) {
			distances[i++] = Math.abs(e - innerStart);
		}

		long candiate = values.lastKey() - values.firstKey();
		o: while (candiate > 1L) {
			for (long d : distances) {
				if (d % candiate != 0L) {
					candiate--;
					continue o;
				}
			}

			break;
		}

		return sample(candiate);
	}

	public FixedWindow sampleN(long n) {
		return sample(innerLength / n);
	}

	public FixedWindow sample(long valueDistance) {
		assert innerLength % valueDistance == 0;

		final int vcount = (int) (innerLength / valueDistance);

		final FixedWindow result = new FixedWindow();
		result.setInnerStart(innerStart);
		result.setInnerLength(innerLength);
		result.setValueDistance(valueDistance);

		final double[][] values = new double[vcount][];

		for (int i = 0; i < vcount; i++) {

			// Zeit des interpolierten Wertes
			final long toff = innerStart + i * valueDistance;

			// Einträge vor und nach
			Entry<Long, double[]> fss = getValues().floorEntry(toff);
			Entry<Long, double[]> css = getValues().ceilingEntry(toff);

			// Nicht existente Einträge davor und danach korrigieren
			if (fss == null)
				fss = css;
			if (css == null)
				css = fss;

			// Timestamps vorher und nachher sowie Werte vorher und nachher
			// auslesen
			final long fssts = fss.getKey();
			final long cssts = css.getKey();

			final double[] fssv = fss.getValue();
			final double[] cssv = css.getValue();

			// Anzahl der zu interpolierenden Werte bestimmen
			final int fssc = fssv.length;
			final int cssc = cssv.length;
			final int ossc = Math.min(fssc, cssc);

			// Wertearray erstellen
			final double[] cvs = values[i] = new double[ossc];

			// Interpolationswert bestimmen
			final double frac = cssts == fssts ? 0.0 : (double) (toff - fssts)
					/ (double) (cssts - fssts);

			// Interpolieren
			for (int j = 0; j < ossc; j++) {
				cvs[j] = fssv[j] * (1.0 - frac) + cssv[j] * frac;
			}
		}

		result.setValues(values);

		return result;
	}

	@Override
	public String toString() {
		return "Window [values=" + values + ", innerStart=" + innerStart
				+ ", innerLength=" + innerLength + "]";
	}

	public static void main(String[] args) {
		final Window w = new Window();
		w.setInnerStart(15L);
		w.setInnerLength(20L);
		w.getValues().put(13L, new double[] { 1, 4 });
		w.getValues().put(16L, new double[] { 4, 2 });
		w.getValues().put(23L, new double[] { 2, 1 });
		w.getValues().put(31L, new double[] { 5, 1 });
		w.getValues().put(39L, new double[] { 1, 6 });

		System.out.println(w);

		final FixedWindow fw = w.sampleN(3);

		System.out.println(fw);
	}
}
