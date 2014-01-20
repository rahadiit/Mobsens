package MobileSensors.Classifiers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import MobileSensors.Enums.Axis;
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

		System.out.println(data.size());
		
		long span = 600;// 0,6sek
		for (int i = 0; i < data.size() - 1; i++) {

			
			long time = data.get(i).getTime();
			ArrayList<Accelerometer> accel = new ArrayList<>();
			accel = (ArrayList<Accelerometer>) Accelerometer.window(data, time,
					time + span);

			if (accel.size() <= 25)
				continue;

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

			// letzter Wert muss kleiner bzw groesser sein als mittlerer
			// Wert

			if (allLower) {
				if (accel.get(accel.size() - 1).getX() > accel.get(
						(accel.size() - 1) / 2).getX())
					allLower = false;
			}
			if (allHigher) {
				if (accel.get(accel.size() - 1).getX() < accel.get(
						(accel.size() - 1) / 2).getX())
					allHigher = false;
			}

			// falls alle werte kleiner oder groesser sind, als erster wert
			// finde maximale differenz

			if (allLower || allHigher) {
				// System.out.println(data.get(i).getTime() + " " +
				// allLower);
				double maximalDiff = 0;

				for (int j = 1; j < accel.size(); j++) {

					double diff = value - accel.get(j).getX();

					if (allLower) {
						if (diff > maximalDiff) {
							maximalDiff = diff;
							// System.out.println("v "+value+
							// " - ac"+accel.get(j).getX()+
							// "mD "+maximalDiff);
						}

					} else if (allHigher) {
						if (diff < maximalDiff) {
							maximalDiff = diff;
							// System.out.println("v "+value+
							// " - ac"+accel.get(j).getX()+
							// "mD "+maximalDiff);
						}
					}
					System.out.println("max diff " +data.get(i).getTime() + "  "+maximalDiff);
					
				}
						
				// wenn alle werte kleiner||groesser sind && die differenz
				// zum anfangs-punkt sehr hoch ist

				if (Math.abs(maximalDiff) >= 7) {
//					System.out.println("");
					

					// neues Intervall erstellen welches nach den getesten
					// werten anfaeng und auch eine halbe sekunde dauert
					// in diesem Intervall muessen Messwerte existieren,
					// welche Nahe dem des Anfangs-Punktes sind

					// Intervall nach dem letzten Messwert des vorherigen
					// Intervalls
					long span2 = 400;
					long time2 = accel.get(accel.size() - 1).getTime();
					ArrayList<Accelerometer> accel2 = new ArrayList<>();
					accel2 = (ArrayList<Accelerometer>) Accelerometer.window(
							data, time2 + 1, time2 + span2);

					boolean foundValue = false;
					// laufe von hinten. erhofft schnellere berechnung
					for (int j = accel2.size() - 1; j > 0; j--) {

						double compValue = accel2.get(j).getX();

						if ((compValue > value - 2 && compValue < value + 2)
								|| (allLower && compValue > value)
								|| (allHigher && compValue < value)) {
							foundValue = true;
							break;
						}
					}

//					if (foundValue) {
//						
//						System.out.println("End-wert wurde auch gefunden");
//						
//						ArrayList<Accelerometer> complete = new ArrayList<>();
//						complete.addAll(accel);
//						complete.addAll(accel2);
//						
//						System.out.println("complete" +complete.size());
//
//						for (Accelerometer a : complete) {
//							a.setInterestedAxis(Axis.X);
//						}
//
//						try {
//							@SuppressWarnings("unchecked")
//							double highestValue = Collections.max(complete)
//									.getX();
//							@SuppressWarnings("unchecked")
//							double lowestValue = Collections.min(complete)
//									.getX();
//
//							foundValue = Math.abs(highestValue - lowestValue) > 7;
//
//							if(!foundValue)
//								System.out.println("abstand nicht gross genug");
//							
//							if (foundValue) {
//								System.out.println("abschnitt1");
//								for (Accelerometer a : accel) {
//									System.out.println(a.getTime() + " :: "
//											+ a.getX());
//								}
//								System.out.println("abschnitt2");
//								for (Accelerometer a : accel2) {
//									System.out.println(a.getTime() + " :: "
//											+ a.getX());
//								}
//								System.out.println("");
//							}
//
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//					}else{
//						System.out.println("endwert wurde nicht gefunden");
//					}

					if (foundValue) {
						
						
						// i kann auf letzten Punkt des Intervalls
						// gesetzt
						// werden
						events.add(new Event(data.get(i).getTime(),
								MobileSensors.Storage.Event.EventType.DODGE));
						i = data.indexOf(accel.get(accel.size() - 1)) + 1;

					}
				}
			}

		}
		//System.out.println(events.size() + " dodges found");
		return events;
	}
}
