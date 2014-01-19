package MobileSensors.Classifiers;

import java.util.ArrayList;
import java.util.Collection;

import MobileSensors.Storage.Event.Event;
import MobileSensors.Storage.Sensors.Accelerometer;

public class DetectDodge implements EventClassifier {

	private ArrayList<Accelerometer> data;

	public DetectDodge(ArrayList<Accelerometer> data) {
		this.data = data;
	}

	@Override
	public ArrayList<Event> getEvents() {
		ArrayList<Event> events = new ArrayList<Event>();

		long span = 600;// 0,5sek
		for (int i = 0; i < data.size() - 1; i++) {

			long time = data.get(i).getTime();
			ArrayList<Accelerometer> accel = new ArrayList<>();
			accel = (ArrayList<Accelerometer>) Accelerometer.window(data, time,
					time + span);

			if (accel.size() > 2) {
				double value = accel.get(0).getX();
				// initial boolean values <=,prevent undefined behavior
				boolean allLower = accel.get(1).getX() <= value ? true : false;
				boolean allHigher = !allLower;

				// kontrolliert, ob alle Werte in diesem Zeitfenster kleiner
				// sind
				for (int j = 2; j < accel.size(); j++) {
					double compareV = accel.get(j).getX();

					if (compareV < value) {
						if (allHigher) {
							allHigher = false;
							break;
						}

					} else if (compareV > value) {
						if (allLower) {
							allLower = false;
							break;
						}
					}
				}

				// falls alle werte kleiner oder groesser sind, als erster wert
				// finde maximale differenz

				if (allLower || allHigher) {
					double maximalDiff = 0;

					for (int j = 1; j < accel.size(); j++) {

						double diff = value - accel.get(j).getX();

						if (allLower) {
							if (diff > maximalDiff) {
								maximalDiff = diff;
								//System.out.println("v "+value+ " - ac"+accel.get(j).getX()+ "mD "+maximalDiff);
							}

						} else if (allHigher) {
							if (diff < maximalDiff) {
								maximalDiff = diff;
								//System.out.println("v "+value+ " - ac"+accel.get(j).getX()+ "mD "+maximalDiff);
							}
						}
					}
					// wenn alle werte kleiner||groesser sind && die differenz
					// zum anfangs-punkt sehr hoch ist

					if (Math.abs(maximalDiff) >= 7.5) {
						
						// neues Intervall erstellen welches nach den getesten
						// werten anfaeng und auch eine halbe sekunde dauert
						// in diesem Intervall muessen Messwerte existieren,
						// welche Nahe dem des Anfangs-Punktes sind

						// Intervall nach dem letzten Messwert des vorherigen
						// Intervalls
						long span2 = 400;
						long time2 = accel.get(accel.size() - 1).getTime();
						ArrayList<Accelerometer> accel2 = new ArrayList<>();
						accel2 = (ArrayList<Accelerometer>) Accelerometer
								.window(data, time2 + 1, time2 + span2);

						boolean foundValue = false;
						// laufe von hinten. erhofft schnellere berechnung
						for (int j = accel2.size() - 1; j > 0; j--) {

							double compValue = accel2.get(j).getX();

							if ((compValue > value - 0.8 && compValue < value + 0.8)
									|| (allLower && compValue > value)
									|| (allHigher && compValue < value)) {
								foundValue = true;
								break;
							}
						}

						if (foundValue) {
							// i kann auf letzten Punkt des Intervalls gesetzt
							// werden
							i = data.indexOf(accel.get(accel.size() - 1)) + 1;
							events.add(new Event(data.get(i).getTime(),
									MobileSensors.Storage.Event.EventType.DODGE));
						}
					}
				}
			}
		}
		System.out.println(events.size() + " dodges found");
		return events;
	}
}
