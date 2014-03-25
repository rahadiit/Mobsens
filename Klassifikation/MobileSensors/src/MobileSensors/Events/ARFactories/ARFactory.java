package MobileSensors.Events.ARFactories;

import java.util.Map;

import MobileSensors.Events.Labels.*;
import MobileSensors.Sensors.SensorRecord;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Super-type for Attribute Relation Factories.
 * 
 * Attribute Relation Factories specify a feature relation in
 * form of weka Attribute-FastVectors and supply factory methods
 * to create labeled and unlabeled feature vectors and training 
 * sets (collection of labeled vectors) of such relation.
 * 
 * @author henny, thomas, max
 *
 * @param <T> Subtype of EventLabel
 */
public interface ARFactory<T extends EventLabel> {

	/**
	 * 
	 * Creates an empty training set for the specified relation.
	 *  
	 * @param capacity int
	 * @return weka.core.Instances
	 */
	public Instances createTrainingSet ();
	
	/**
	 * 
	 * Creates training set for the specified relation filled with
	 * feature vectors from the given sensor collections
	 * 
	 * @param sensorCollections Map<SensorCollection, T>
	 * @return
	 */
	public Instances createTrainingSet (Map<SensorRecord, T> sensorCollections);
	
	/**
	 * 
	 * Creates an unlabeled feature vector of a given sensor collection
	 * 
	 * @param win SensorCollection
	 * @return weka.core.Instance
	 */
	public Instance createFeatureVector (SensorRecord sc);
	
	/**
	 * 
	 * Creates a labeled feature vector of given sensor collection
	 * 
	 * @param win SensorCollection
	 * @param label T 
	 * @return weka.core.Instance
	 */
	public Instance createFeatureVector (SensorRecord sc, T label);
	
}
