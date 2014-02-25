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
package MobileSensors.Classifiers.Weka;

//Generated with Weka 3.6.9
//
//This code is public domain and comes with no warranty.
//
//Timestamp: Tue Feb 25 15:57:26 CET 2014


import weka.core.Attribute;
import weka.core.Capabilities;
import weka.core.Capabilities.Capability;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.RevisionUtils;
import weka.classifiers.Classifier;

public class DodgeJ48
extends Classifier {

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
* @param i the training data
*/
public void buildClassifier(Instances i) throws Exception {
 // can classifier handle the data?
 getCapabilities().testWithFail(i);
}

/**
* Classifies the given instance.
*
* @param i the instance to classify
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
* @return        the revision
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
 return "Auto-generated classifier wrapper, based on weka.classifiers.trees.J48 (generated with Weka 3.6.9).\n" + this.getClass().getName() + "/WekaClassifier";
}

/**
* Runs the classfier from commandline.
*
* @param args the commandline arguments
*/
public static void main(String args[]) {
 runClassifier(new DodgeJ48(), args);
}
}

class WekaClassifier {

public static double classify(Object[] i)
 throws Exception {

 double p = Double.NaN;
 p = WekaClassifier.N5e84c30(i);
 return p;
}
static double N5e84c30(Object []i) {
 double p = Double.NaN;
 if (i[6] == null) {
   p = 1;
 } else if (((Double) i[6]).doubleValue() <= 3.8841837794840797) {
 p = WekaClassifier.N15e5cf81(i);
 } else if (((Double) i[6]).doubleValue() > 3.8841837794840797) {
 p = WekaClassifier.Nfa463f37(i);
 } 
 return p;
}
static double N15e5cf81(Object []i) {
 double p = Double.NaN;
 if (i[0] == null) {
   p = 1;
 } else if (((Double) i[0]).doubleValue() <= 0.1569366482691841) {
 p = WekaClassifier.N29c88a2(i);
 } else if (((Double) i[0]).doubleValue() > 0.1569366482691841) {
 p = WekaClassifier.Ncda74b10(i);
 } 
 return p;
}
static double N29c88a2(Object []i) {
 double p = Double.NaN;
 if (i[1] == null) {
   p = 1;
 } else if (((Double) i[1]).doubleValue() <= 2.7339013329148285) {
 p = WekaClassifier.N12d093e3(i);
 } else if (((Double) i[1]).doubleValue() > 2.7339013329148285) {
 p = WekaClassifier.N16b8e2c5(i);
 } 
 return p;
}
static double N12d093e3(Object []i) {
 double p = Double.NaN;
 if (i[6] == null) {
   p = 1;
 } else if (((Double) i[6]).doubleValue() <= 3.3726364641442466) {
   p = 1;
 } else if (((Double) i[6]).doubleValue() > 3.3726364641442466) {
 p = WekaClassifier.N1b9d56f4(i);
 } 
 return p;
}
static double N1b9d56f4(Object []i) {
 double p = Double.NaN;
 if (i[3] == null) {
   p = 1;
 } else if (((Double) i[3]).doubleValue() <= -0.36015329497411913) {
   p = 1;
 } else if (((Double) i[3]).doubleValue() > -0.36015329497411913) {
   p = 0;
 } 
 return p;
}
static double N16b8e2c5(Object []i) {
 double p = Double.NaN;
 if (i[4] == null) {
   p = 1;
 } else if (((Double) i[4]).doubleValue() <= 0.20844372690919977) {
 p = WekaClassifier.N4300f96(i);
 } else if (((Double) i[4]).doubleValue() > 0.20844372690919977) {
 p = WekaClassifier.Nf4ed017(i);
 } 
 return p;
}
static double N4300f96(Object []i) {
 double p = Double.NaN;
 if (i[5] == null) {
   p = 1;
 } else if (((Double) i[5]).doubleValue() <= 0.1589349048155099) {
   p = 1;
 } else if (((Double) i[5]).doubleValue() > 0.1589349048155099) {
   p = 0;
 } 
 return p;
}
static double Nf4ed017(Object []i) {
 double p = Double.NaN;
 if (i[7] == null) {
   p = 0;
 } else if (((Double) i[7]).doubleValue() <= 10.486294091010434) {
 p = WekaClassifier.N1f40c548(i);
 } else if (((Double) i[7]).doubleValue() > 10.486294091010434) {
   p = 1;
 } 
 return p;
}
static double N1f40c548(Object []i) {
 double p = Double.NaN;
 if (i[6] == null) {
   p = 1;
 } else if (((Double) i[6]).doubleValue() <= 1.9308665339861084) {
 p = WekaClassifier.N15589db9(i);
 } else if (((Double) i[6]).doubleValue() > 1.9308665339861084) {
   p = 0;
 } 
 return p;
}
static double N15589db9(Object []i) {
 double p = Double.NaN;
 if (i[5] == null) {
   p = 1;
 } else if (((Double) i[5]).doubleValue() <= 0.11181360276752668) {
   p = 1;
 } else if (((Double) i[5]).doubleValue() > 0.11181360276752668) {
   p = 0;
 } 
 return p;
}
static double Ncda74b10(Object []i) {
 double p = Double.NaN;
 if (i[5] == null) {
   p = 1;
 } else if (((Double) i[5]).doubleValue() <= -2.64754686368254) {
 p = WekaClassifier.N1a264bb11(i);
 } else if (((Double) i[5]).doubleValue() > -2.64754686368254) {
 p = WekaClassifier.N834a7212(i);
 } 
 return p;
}
static double N1a264bb11(Object []i) {
 double p = Double.NaN;
 if (i[5] == null) {
   p = 1;
 } else if (((Double) i[5]).doubleValue() <= -16.574304622068354) {
   p = 1;
 } else if (((Double) i[5]).doubleValue() > -16.574304622068354) {
   p = 0;
 } 
 return p;
}
static double N834a7212(Object []i) {
 double p = Double.NaN;
 if (i[3] == null) {
   p = 1;
 } else if (((Double) i[3]).doubleValue() <= -3.434438696707811) {
 p = WekaClassifier.N18c90fd13(i);
 } else if (((Double) i[3]).doubleValue() > -3.434438696707811) {
 p = WekaClassifier.Nb560cc16(i);
 } 
 return p;
}
static double N18c90fd13(Object []i) {
 double p = Double.NaN;
 if (i[3] == null) {
   p = 1;
 } else if (((Double) i[3]).doubleValue() <= -4.037997339589058) {
   p = 1;
 } else if (((Double) i[3]).doubleValue() > -4.037997339589058) {
 p = WekaClassifier.N1830b4114(i);
 } 
 return p;
}
static double N1830b4114(Object []i) {
 double p = Double.NaN;
 if (i[6] == null) {
   p = 1;
 } else if (((Double) i[6]).doubleValue() <= 1.7700074069224019) {
   p = 1;
 } else if (((Double) i[6]).doubleValue() > 1.7700074069224019) {
 p = WekaClassifier.N5814ac15(i);
 } 
 return p;
}
static double N5814ac15(Object []i) {
 double p = Double.NaN;
 if (i[5] == null) {
   p = 0;
 } else if (((Double) i[5]).doubleValue() <= 0.18723852285764217) {
   p = 0;
 } else if (((Double) i[5]).doubleValue() > 0.18723852285764217) {
   p = 1;
 } 
 return p;
}
static double Nb560cc16(Object []i) {
 double p = Double.NaN;
 if (i[7] == null) {
   p = 1;
 } else if (((Double) i[7]).doubleValue() <= 7.0082306304747055) {
 p = WekaClassifier.N1e61e9317(i);
 } else if (((Double) i[7]).doubleValue() > 7.0082306304747055) {
 p = WekaClassifier.N11e0bba34(i);
 } 
 return p;
}
static double N1e61e9317(Object []i) {
 double p = Double.NaN;
 if (i[6] == null) {
   p = 1;
 } else if (((Double) i[6]).doubleValue() <= 2.617033622076744) {
 p = WekaClassifier.Ne17c3c18(i);
 } else if (((Double) i[6]).doubleValue() > 2.617033622076744) {
 p = WekaClassifier.N10f148b30(i);
 } 
 return p;
}
static double Ne17c3c18(Object []i) {
 double p = Double.NaN;
 if (i[2] == null) {
   p = 1;
 } else if (((Double) i[2]).doubleValue() <= 9.102529179453851) {
 p = WekaClassifier.N1a370a019(i);
 } else if (((Double) i[2]).doubleValue() > 9.102529179453851) {
 p = WekaClassifier.Nb7b11b27(i);
 } 
 return p;
}
static double N1a370a019(Object []i) {
 double p = Double.NaN;
 if (i[1] == null) {
   p = 1;
 } else if (((Double) i[1]).doubleValue() <= 3.108796729333699) {
 p = WekaClassifier.N1371a3d20(i);
 } else if (((Double) i[1]).doubleValue() > 3.108796729333699) {
 p = WekaClassifier.N242f8425(i);
 } 
 return p;
}
static double N1371a3d20(Object []i) {
 double p = Double.NaN;
 if (i[8] == null) {
   p = 1;
 } else if (((Double) i[8]).doubleValue() <= 7.853240076452458) {
 p = WekaClassifier.N9bcba121(i);
 } else if (((Double) i[8]).doubleValue() > 7.853240076452458) {
   p = 1;
 } 
 return p;
}
static double N9bcba121(Object []i) {
 double p = Double.NaN;
 if (i[4] == null) {
   p = 1;
 } else if (((Double) i[4]).doubleValue() <= 0.4491291281749063) {
 p = WekaClassifier.N55c4ac22(i);
 } else if (((Double) i[4]).doubleValue() > 0.4491291281749063) {
 p = WekaClassifier.N111cedd24(i);
 } 
 return p;
}
static double N55c4ac22(Object []i) {
 double p = Double.NaN;
 if (i[0] == null) {
   p = 1;
 } else if (((Double) i[0]).doubleValue() <= 0.3754372757766394) {
 p = WekaClassifier.Ne32ebe23(i);
 } else if (((Double) i[0]).doubleValue() > 0.3754372757766394) {
   p = 1;
 } 
 return p;
}
static double Ne32ebe23(Object []i) {
 double p = Double.NaN;
 if (i[0] == null) {
   p = 0;
 } else if (((Double) i[0]).doubleValue() <= 0.27499464728403855) {
   p = 0;
 } else if (((Double) i[0]).doubleValue() > 0.27499464728403855) {
   p = 1;
 } 
 return p;
}
static double N111cedd24(Object []i) {
 double p = Double.NaN;
 if (i[9] == null) {
   p = 0;
 } else if (((Double) i[9]).doubleValue() <= -0.055317250898663684) {
   p = 0;
 } else if (((Double) i[9]).doubleValue() > -0.055317250898663684) {
   p = 1;
 } 
 return p;
}
static double N242f8425(Object []i) {
 double p = Double.NaN;
 if (i[6] == null) {
   p = 1;
 } else if (((Double) i[6]).doubleValue() <= 1.6183566234380249) {
   p = 1;
 } else if (((Double) i[6]).doubleValue() > 1.6183566234380249) {
 p = WekaClassifier.N1ab98b926(i);
 } 
 return p;
}
static double N1ab98b926(Object []i) {
 double p = Double.NaN;
 if (i[6] == null) {
   p = 0;
 } else if (((Double) i[6]).doubleValue() <= 2.24746556978075) {
   p = 0;
 } else if (((Double) i[6]).doubleValue() > 2.24746556978075) {
   p = 1;
 } 
 return p;
}
static double Nb7b11b27(Object []i) {
 double p = Double.NaN;
 if (i[5] == null) {
   p = 1;
 } else if (((Double) i[5]).doubleValue() <= 0.126820417957119) {
   p = 1;
 } else if (((Double) i[5]).doubleValue() > 0.126820417957119) {
 p = WekaClassifier.N1cc932b28(i);
 } 
 return p;
}
static double N1cc932b28(Object []i) {
 double p = Double.NaN;
 if (i[10] == null) {
   p = 0;
 } else if (((Double) i[10]).doubleValue() <= 0.37240526920997885) {
 p = WekaClassifier.N1d1fc5c29(i);
 } else if (((Double) i[10]).doubleValue() > 0.37240526920997885) {
   p = 1;
 } 
 return p;
}
static double N1d1fc5c29(Object []i) {
 double p = Double.NaN;
 if (i[5] == null) {
   p = 0;
 } else if (((Double) i[5]).doubleValue() <= 0.24493891869466425) {
   p = 0;
 } else if (((Double) i[5]).doubleValue() > 0.24493891869466425) {
   p = 1;
 } 
 return p;
}
static double N10f148b30(Object []i) {
 double p = Double.NaN;
 if (i[9] == null) {
   p = 0;
 } else if (((Double) i[9]).doubleValue() <= 0.7631422799679717) {
 p = WekaClassifier.N10db33e31(i);
 } else if (((Double) i[9]).doubleValue() > 0.7631422799679717) {
   p = 1;
 } 
 return p;
}
static double N10db33e31(Object []i) {
 double p = Double.NaN;
 if (i[0] == null) {
   p = 0;
 } else if (((Double) i[0]).doubleValue() <= 0.5926775232329966) {
 p = WekaClassifier.N72d60b32(i);
 } else if (((Double) i[0]).doubleValue() > 0.5926775232329966) {
   p = 1;
 } 
 return p;
}
static double N72d60b32(Object []i) {
 double p = Double.NaN;
 if (i[6] == null) {
   p = 0;
 } else if (((Double) i[6]).doubleValue() <= 2.6443192068376336) {
 p = WekaClassifier.N14280ec33(i);
 } else if (((Double) i[6]).doubleValue() > 2.6443192068376336) {
   p = 0;
 } 
 return p;
}
static double N14280ec33(Object []i) {
 double p = Double.NaN;
 if (i[0] == null) {
   p = 0;
 } else if (((Double) i[0]).doubleValue() <= 0.2419897786993534) {
   p = 0;
 } else if (((Double) i[0]).doubleValue() > 0.2419897786993534) {
   p = 1;
 } 
 return p;
}
static double N11e0bba34(Object []i) {
 double p = Double.NaN;
 if (i[6] == null) {
   p = 1;
 } else if (((Double) i[6]).doubleValue() <= 3.6189930734782223) {
   p = 1;
 } else if (((Double) i[6]).doubleValue() > 3.6189930734782223) {
 p = WekaClassifier.N193fea835(i);
 } 
 return p;
}
static double N193fea835(Object []i) {
 double p = Double.NaN;
 if (i[7] == null) {
   p = 1;
 } else if (((Double) i[7]).doubleValue() <= 8.652347274588532) {
 p = WekaClassifier.N133e10536(i);
 } else if (((Double) i[7]).doubleValue() > 8.652347274588532) {
   p = 1;
 } 
 return p;
}
static double N133e10536(Object []i) {
 double p = Double.NaN;
 if (i[9] == null) {
   p = 0;
 } else if (((Double) i[9]).doubleValue() <= 0.6828105443217947) {
   p = 0;
 } else if (((Double) i[9]).doubleValue() > 0.6828105443217947) {
   p = 1;
 } 
 return p;
}
static double Nfa463f37(Object []i) {
 double p = Double.NaN;
 if (i[9] == null) {
   p = 0;
 } else if (((Double) i[9]).doubleValue() <= 0.37008772804604106) {
 p = WekaClassifier.N119dfef38(i);
 } else if (((Double) i[9]).doubleValue() > 0.37008772804604106) {
 p = WekaClassifier.N1bd24cb42(i);
 } 
 return p;
}
static double N119dfef38(Object []i) {
 double p = Double.NaN;
 if (i[8] == null) {
   p = 0;
 } else if (((Double) i[8]).doubleValue() <= 18.72285838023828) {
 p = WekaClassifier.N1bcd6f039(i);
 } else if (((Double) i[8]).doubleValue() > 18.72285838023828) {
 p = WekaClassifier.N1450aa241(i);
 } 
 return p;
}
static double N1bcd6f039(Object []i) {
 double p = Double.NaN;
 if (i[4] == null) {
   p = 1;
 } else if (((Double) i[4]).doubleValue() <= -0.9790678800758558) {
 p = WekaClassifier.Nc34a5440(i);
 } else if (((Double) i[4]).doubleValue() > -0.9790678800758558) {
   p = 0;
 } 
 return p;
}
static double Nc34a5440(Object []i) {
 double p = Double.NaN;
 if (i[0] == null) {
   p = 1;
 } else if (((Double) i[0]).doubleValue() <= 0.5205979112838396) {
   p = 1;
 } else if (((Double) i[0]).doubleValue() > 0.5205979112838396) {
   p = 0;
 } 
 return p;
}
static double N1450aa241(Object []i) {
 double p = Double.NaN;
 if (i[6] == null) {
   p = 1;
 } else if (((Double) i[6]).doubleValue() <= 5.072036235138872) {
   p = 1;
 } else if (((Double) i[6]).doubleValue() > 5.072036235138872) {
   p = 0;
 } 
 return p;
}
static double N1bd24cb42(Object []i) {
 double p = Double.NaN;
 if (i[5] == null) {
   p = 0;
 } else if (((Double) i[5]).doubleValue() <= -0.07316777755265041) {
   p = 0;
 } else if (((Double) i[5]).doubleValue() > -0.07316777755265041) {
 p = WekaClassifier.Nbab86d43(i);
 } 
 return p;
}
static double Nbab86d43(Object []i) {
 double p = Double.NaN;
 if (i[7] == null) {
   p = 0;
 } else if (((Double) i[7]).doubleValue() <= 7.139024652264432) {
 p = WekaClassifier.Na21e5444(i);
 } else if (((Double) i[7]).doubleValue() > 7.139024652264432) {
 p = WekaClassifier.N175569145(i);
 } 
 return p;
}
static double Na21e5444(Object []i) {
 double p = Double.NaN;
 if (i[3] == null) {
   p = 1;
 } else if (((Double) i[3]).doubleValue() <= -0.4821530374605669) {
   p = 1;
 } else if (((Double) i[3]).doubleValue() > -0.4821530374605669) {
   p = 0;
 } 
 return p;
}
static double N175569145(Object []i) {
 double p = Double.NaN;
 if (i[11] == null) {
   p = 1;
 } else if (((Double) i[11]).doubleValue() <= 4.281835088276322) {
 p = WekaClassifier.N865bc646(i);
 } else if (((Double) i[11]).doubleValue() > 4.281835088276322) {
 p = WekaClassifier.N11cb86050(i);
 } 
 return p;
}
static double N865bc646(Object []i) {
 double p = Double.NaN;
 if (i[6] == null) {
   p = 1;
 } else if (((Double) i[6]).doubleValue() <= 8.091137736346182) {
 p = WekaClassifier.Nf35bd347(i);
 } else if (((Double) i[6]).doubleValue() > 8.091137736346182) {
 p = WekaClassifier.N17524e49(i);
 } 
 return p;
}
static double Nf35bd347(Object []i) {
 double p = Double.NaN;
 if (i[9] == null) {
   p = 1;
 } else if (((Double) i[9]).doubleValue() <= 0.4497103455624738) {
 p = WekaClassifier.N111af7948(i);
 } else if (((Double) i[9]).doubleValue() > 0.4497103455624738) {
   p = 1;
 } 
 return p;
}
static double N111af7948(Object []i) {
 double p = Double.NaN;
 if (i[3] == null) {
   p = 1;
 } else if (((Double) i[3]).doubleValue() <= 0.5846132886092136) {
   p = 1;
 } else if (((Double) i[3]).doubleValue() > 0.5846132886092136) {
   p = 0;
 } 
 return p;
}
static double N17524e49(Object []i) {
 double p = Double.NaN;
 if (i[1] == null) {
   p = 1;
 } else if (((Double) i[1]).doubleValue() <= 2.748506455169991) {
   p = 1;
 } else if (((Double) i[1]).doubleValue() > 2.748506455169991) {
   p = 0;
 } 
 return p;
}
static double N11cb86050(Object []i) {
 double p = Double.NaN;
 if (i[8] == null) {
   p = 1;
 } else if (((Double) i[8]).doubleValue() <= 16.96337127881466) {
   p = 1;
 } else if (((Double) i[8]).doubleValue() > 16.96337127881466) {
   p = 0;
 } 
 return p;
}
}