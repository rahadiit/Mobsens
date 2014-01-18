package MobileSensors.Calculation;

import java.util.ArrayList;
import java.util.Collection;

import MobileSensors.Storage.Sensors.Accelerometer;

public class AcceleroCalc {

	public static void accelerometerCalc(
			Collection<Accelerometer> accelerometers, boolean removeFalse) {

		if(removeFalse){
			removeFalseValues(accelerometers);
		}
		
		Accelerometer prevAccel = null;

		for (Accelerometer accel : accelerometers) {
			if (prevAccel != null) {
				accel.setTimeDifference(prevAccel);
				
				setJerk(prevAccel,accel);
			}
			prevAccel = accel;
		}
		
		

	}

	private static void setJerk(Accelerometer prevAcc, Accelerometer accel) {

		accel.setJerkX(getJerk(prevAcc, accel, 0));
		accel.setJerkY(getJerk(prevAcc, accel, 1));
		accel.setJerkZ(getJerk(prevAcc, accel, 2));
		
	}

	/**
	 * 
	 * @param prevAcc
	 * @param accel
	 * @param axis
	 *            0==x 1==y 2==z
	 * @return
	 */
	private static double getJerk(Accelerometer prevAcc, Accelerometer accel,
			int axis) {
		if (axis == 1)
			return Calc.jerk(prevAcc.getY(), accel.getY(),
					accel.getTimeDifference());
		else if (axis == 2)
			return Calc.jerk(prevAcc.getZ(), accel.getZ(),
					accel.getTimeDifference());
		else
			return Calc.jerk(prevAcc.getX(), accel.getX(),
					accel.getTimeDifference());
	}

	private static void removeFalseValues(Collection<Accelerometer> list) {

		Accelerometer old = null;

		ArrayList<Accelerometer> falseValues = new ArrayList<>();

		boolean oldDeleted = false;
		for (Accelerometer obj : list) {

			if (old != null) {
				double difference = old.getX() - obj.getX();
				if (oldDeleted) {
					// wenn zu einem geloeschten messwert keine hohe differenz
					// besteht, so ist dieser wert auch falsch.
					if (difference < 1 && difference > -1) {
						falseValues.add(obj);
					} else
						oldDeleted = false;
				}

				else {
					if (difference > 4 || difference < -4) {
						falseValues.add(obj);
						oldDeleted = true;
					} else
						oldDeleted = false;
				}

			}

			if (obj.getX() < -10 || obj.getX() > 10) {
				falseValues.add(obj);
				oldDeleted = true;
			}

			old = obj;
		}

		for (Accelerometer obj : falseValues) {
			list.remove(obj);
		}
	}

}
