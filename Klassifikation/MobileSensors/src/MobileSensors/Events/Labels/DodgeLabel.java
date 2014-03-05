package MobileSensors.Events.Labels;

/**
 * Type for dodge event training labels
 * 
 * @author henny, thomas, max
 *
 */
public enum DodgeLabel implements EventLabel {

	/**
	 * Label for feature vectors which are dodge events
	 */
	DODGE,
	
	/**
	 * Label for feature vectors which are no dodge events
	 */
	NODODGE
	
}
