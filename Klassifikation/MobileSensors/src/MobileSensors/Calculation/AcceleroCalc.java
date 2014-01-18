package MobileSensors.Calculation;

import java.util.ArrayList;
import java.util.Collection;

import MobileSensors.Storage.Sensors.Accelerometer;

public class AcceleroCalc {

	public static void removeFalseValues(Collection<Accelerometer> list) {

		Accelerometer old = null;

		ArrayList<Accelerometer> falseValues = new ArrayList<>();

		boolean oldDeleted =false;
		for (Accelerometer obj : list) {
			
			
			if (old != null) {
				double difference = old.getX() - obj.getX();
				if(oldDeleted){
					//wenn zu einem geloeschten messwert keine hohe differenz besteht, so ist dieser wert auch falsch.
					if(difference <1 && difference >-1){
						falseValues.add(obj);
					}
					else
						oldDeleted=false;
				}
				
				else{
					if (difference > 4 || difference < -4){
						falseValues.add(obj);
						oldDeleted=true;
					}
					else
						oldDeleted=false;
				}
				
			}
			
			if(obj.getX()<-10 || obj.getX()>10){
				falseValues.add(obj);
				oldDeleted=true;
			}
				
			old = obj;
		}

		for (Accelerometer obj : falseValues) {
			list.remove(obj);
		}
	}

}
