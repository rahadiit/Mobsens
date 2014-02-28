/*
 * === Run information ===

Scheme:weka.classifiers.trees.J48 -C 0.25 -M 2
Relation:     Dodge.arff
Instances:    12863
Attributes:   13
              xArithMean
              yArithMean
              zArithMean
              xHarmMean
              yHarmMean
              zHarmMean
              xVariance
              yVariance
              zVariance
              xKurtosis
              yKurtosis
              zKurtosis
              label
Test mode:10-fold cross-validation

=== Classifier model (full training set) ===

J48 pruned tree
------------------

xVariance <= 3.884184
|   xArithMean <= 0.156937
|   |   yArithMean <= 2.733901
|   |   |   xVariance <= 3.372636: NODODGE (705.0/2.0)
|   |   |   xVariance > 3.372636
|   |   |   |   xHarmMean <= -0.360153: NODODGE (41.0)
|   |   |   |   xHarmMean > -0.360153: DODGE (11.0)
|   |   yArithMean > 2.733901
|   |   |   yHarmMean <= 0.208444
|   |   |   |   zHarmMean <= 0.158935: NODODGE (100.0/1.0)
|   |   |   |   zHarmMean > 0.158935: DODGE (6.0)
|   |   |   yHarmMean > 0.208444
|   |   |   |   yVariance <= 10.486294
|   |   |   |   |   xVariance <= 1.930867
|   |   |   |   |   |   zHarmMean <= 0.111814: NODODGE (8.0)
|   |   |   |   |   |   zHarmMean > 0.111814: DODGE (4.0)
|   |   |   |   |   xVariance > 1.930867: DODGE (226.0/1.0)
|   |   |   |   yVariance > 10.486294: NODODGE (30.0)
|   xArithMean > 0.156937
|   |   zHarmMean <= -2.647547
|   |   |   zHarmMean <= -16.574305: NODODGE (100.0)
|   |   |   zHarmMean > -16.574305: DODGE (60.0)
|   |   zHarmMean > -2.647547
|   |   |   xHarmMean <= -3.434439
|   |   |   |   xHarmMean <= -4.037997: NODODGE (465.0)
|   |   |   |   xHarmMean > -4.037997
|   |   |   |   |   xVariance <= 1.770007: NODODGE (105.0)
|   |   |   |   |   xVariance > 1.770007
|   |   |   |   |   |   zHarmMean <= 0.187239: DODGE (107.0)
|   |   |   |   |   |   zHarmMean > 0.187239: NODODGE (26.0)
|   |   |   xHarmMean > -3.434439
|   |   |   |   yVariance <= 7.008231
|   |   |   |   |   xVariance <= 2.617034
|   |   |   |   |   |   zArithMean <= 9.102529
|   |   |   |   |   |   |   yArithMean <= 3.108797
|   |   |   |   |   |   |   |   zVariance <= 7.85324
|   |   |   |   |   |   |   |   |   yHarmMean <= 0.449129
|   |   |   |   |   |   |   |   |   |   xArithMean <= 0.375437
|   |   |   |   |   |   |   |   |   |   |   xArithMean <= 0.274995: DODGE (5.0)
|   |   |   |   |   |   |   |   |   |   |   xArithMean > 0.274995: NODODGE (7.0)
|   |   |   |   |   |   |   |   |   |   xArithMean > 0.375437: NODODGE (226.0/2.0)
|   |   |   |   |   |   |   |   |   yHarmMean > 0.449129
|   |   |   |   |   |   |   |   |   |   xKurtosis <= -0.055317: DODGE (29.0/1.0)
|   |   |   |   |   |   |   |   |   |   xKurtosis > -0.055317: NODODGE (22.0)
|   |   |   |   |   |   |   |   zVariance > 7.85324: NODODGE (645.0)
|   |   |   |   |   |   |   yArithMean > 3.108797
|   |   |   |   |   |   |   |   xVariance <= 1.618357: NODODGE (52.0)
|   |   |   |   |   |   |   |   xVariance > 1.618357
|   |   |   |   |   |   |   |   |   xVariance <= 2.247466: DODGE (42.0/1.0)
|   |   |   |   |   |   |   |   |   xVariance > 2.247466: NODODGE (17.0)
|   |   |   |   |   |   zArithMean > 9.102529
|   |   |   |   |   |   |   zHarmMean <= 0.12682: NODODGE (2309.0)
|   |   |   |   |   |   |   zHarmMean > 0.12682
|   |   |   |   |   |   |   |   yKurtosis <= 0.372405
|   |   |   |   |   |   |   |   |   zHarmMean <= 0.244939: DODGE (19.0)
|   |   |   |   |   |   |   |   |   zHarmMean > 0.244939: NODODGE (3.0)
|   |   |   |   |   |   |   |   yKurtosis > 0.372405: NODODGE (267.0)
|   |   |   |   |   xVariance > 2.617034
|   |   |   |   |   |   xKurtosis <= 0.763142
|   |   |   |   |   |   |   xArithMean <= 0.592678
|   |   |   |   |   |   |   |   xVariance <= 2.644319
|   |   |   |   |   |   |   |   |   xArithMean <= 0.24199: DODGE (7.0)
|   |   |   |   |   |   |   |   |   xArithMean > 0.24199: NODODGE (5.0)
|   |   |   |   |   |   |   |   xVariance > 2.644319: DODGE (77.0)
|   |   |   |   |   |   |   xArithMean > 0.592678: NODODGE (30.0/1.0)
|   |   |   |   |   |   xKurtosis > 0.763142: NODODGE (69.0)
|   |   |   |   yVariance > 7.008231
|   |   |   |   |   xVariance <= 3.618993: NODODGE (5003.0)
|   |   |   |   |   xVariance > 3.618993
|   |   |   |   |   |   yVariance <= 8.652347
|   |   |   |   |   |   |   xKurtosis <= 0.682811: DODGE (5.0)
|   |   |   |   |   |   |   xKurtosis > 0.682811: NODODGE (15.0)
|   |   |   |   |   |   yVariance > 8.652347: NODODGE (284.0)
xVariance > 3.884184
|   xKurtosis <= 0.370088
|   |   zVariance <= 18.722858
|   |   |   yHarmMean <= -0.979068
|   |   |   |   xArithMean <= 0.520598: NODODGE (15.0)
|   |   |   |   xArithMean > 0.520598: DODGE (10.0)
|   |   |   yHarmMean > -0.979068: DODGE (481.0/1.0)
|   |   zVariance > 18.722858
|   |   |   xVariance <= 5.072036: NODODGE (72.0)
|   |   |   xVariance > 5.072036: DODGE (27.0)
|   xKurtosis > 0.370088
|   |   zHarmMean <= -0.073168: DODGE (97.0)
|   |   zHarmMean > -0.073168
|   |   |   yVariance <= 7.139025
|   |   |   |   xHarmMean <= -0.482153: NODODGE (59.0)
|   |   |   |   xHarmMean > -0.482153: DODGE (85.0)
|   |   |   yVariance > 7.139025
|   |   |   |   zKurtosis <= 4.281835
|   |   |   |   |   xVariance <= 8.091138
|   |   |   |   |   |   xKurtosis <= 0.44971
|   |   |   |   |   |   |   xHarmMean <= 0.584613: NODODGE (21.0)
|   |   |   |   |   |   |   xHarmMean > 0.584613: DODGE (4.0)
|   |   |   |   |   |   xKurtosis > 0.44971: NODODGE (791.0)
|   |   |   |   |   xVariance > 8.091138
|   |   |   |   |   |   yArithMean <= 2.748506: NODODGE (18.0)
|   |   |   |   |   |   yArithMean > 2.748506: DODGE (16.0)
|   |   |   |   zKurtosis > 4.281835
|   |   |   |   |   zVariance <= 16.963371: NODODGE (11.0)
|   |   |   |   |   zVariance > 16.963371: DODGE (24.0/1.0)

Number of Leaves  : 	52

Size of the tree : 	103


Time taken to build model: 7.81 seconds

=== Stratified cross-validation ===
=== Summary ===

Correctly Classified Instances       12816               99.6346 %
Incorrectly Classified Instances        47                0.3654 %
Kappa statistic                          0.9804
Mean absolute error                      0.0044
Root mean squared error                  0.0605
Relative absolute error                  2.3427 %
Root relative squared error             19.7919 %
Total Number of Instances            12863     

=== Detailed Accuracy By Class ===

               TP Rate   FP Rate   Precision   Recall  F-Measure   ROC Area  Class
                 0.978     0.002      0.986     0.978     0.982      0.992    DODGE
                 0.998     0.022      0.997     0.998     0.998      0.992    NODODGE
Weighted Avg.    0.996     0.02       0.996     0.996     0.996      0.992

=== Confusion Matrix ===

     a     b   <-- classified as
  1314    29 |     a = DODGE
    18 11502 |     b = NODODGE
 */
package MobileSensors.Wekas;

//Generated with Weka 3.6.9
//
//This code is public domain and comes with no warranty.
//
//Timestamp: Tue Feb 25 15:57:26 CET 2014

import weka.core.Capabilities;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.RevisionUtils;
import weka.classifiers.Classifier;

public class DodgeJ48 extends Classifier {

	/**
	 * Returns only the toString() method.
	 * 
	 * @return a string describing the classifier
	 */
	public String globalInfo() {
		return toString();
	}

	/**
	 * Returns the capabilities of this classifier.
	 * 
	 * @return the capabilities
	 */
	public Capabilities getCapabilities() {
		weka.core.Capabilities result = new weka.core.Capabilities(this);

		result.enable(weka.core.Capabilities.Capability.NOMINAL_ATTRIBUTES);
		result.enable(weka.core.Capabilities.Capability.NUMERIC_ATTRIBUTES);
		result.enable(weka.core.Capabilities.Capability.DATE_ATTRIBUTES);
		result.enable(weka.core.Capabilities.Capability.MISSING_VALUES);
		result.enable(weka.core.Capabilities.Capability.NOMINAL_CLASS);
		result.enable(weka.core.Capabilities.Capability.MISSING_CLASS_VALUES);

		result.setMinimumNumberInstances(0);

		return result;
	}

	/**
	 * only checks the data against its capabilities.
	 * 
	 * @param i
	 *            the training data
	 */
	public void buildClassifier(Instances i) throws Exception {
		// can classifier handle the data?
		getCapabilities().testWithFail(i);
	}

	/**
	 * Classifies the given instance.
	 * 
	 * @param i
	 *            the instance to classify
	 * @return the classification result
	 */
	public double classifyInstance(Instance i) throws Exception {
		Object[] s = new Object[i.numAttributes()];

		for (int j = 0; j < s.length; j++) {
			if (!i.isMissing(j)) {
				if (i.attribute(j).isNominal())
					s[j] = new String(i.stringValue(j));
				else if (i.attribute(j).isNumeric())
					s[j] = new Double(i.value(j));
			}
		}

		// set class value to missing
		s[i.classIndex()] = null;

		return WekaClassifier.classify(s);
	}

	/**
	 * Returns the revision string.
	 * 
	 * @return the revision
	 */
	public String getRevision() {
		return RevisionUtils.extract("1.0");
	}

	/**
	 * Returns only the classnames and what classifier it is based on.
	 * 
	 * @return a short description
	 */
	public String toString() {
		return "Auto-generated classifier wrapper, based on weka.classifiers.trees.J48 (generated with Weka 3.6.9).\n"
				+ this.getClass().getName() + "/WekaClassifier";
	}

	/**
	 * Runs the classfier from commandline.
	 * 
	 * @param args
	 *            the commandline arguments
	 */
	public static void main(String args[]) {
		runClassifier(new DodgeJ48(), args);
	}

}