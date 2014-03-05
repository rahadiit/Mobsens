package MobileSensors.Events.Labels;

/**
 * Type for braking event training labels
 * 
 * @author henny, thomas, max
 *
 */
public enum BrakingLabel implements EventLabel {
	
	/**
	 * Label for feature vectors which are braking events
	 */
	BRAKE,
	
	/**
	 * Label for feature vectors which are no braking events
	 */
	NOBRAKE
	
}
