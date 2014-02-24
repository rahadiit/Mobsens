/*
=== Stratified cross-validation ===
=== Summary ===

Correctly Classified Instances       12805               99.5491 %
Incorrectly Classified Instances        58                0.4509 %
Kappa statistic                          0.9758
Mean absolute error                      0.0054
Root mean squared error                  0.0667
Relative absolute error                  2.8975 %
Root relative squared error             21.8172 %
Total Number of Instances            12863     

=== Detailed Accuracy By Class ===

               TP Rate   FP Rate   Precision   Recall  F-Measure   ROC Area  Class
                 0.974     0.002      0.983     0.974     0.978      0.989    DODGE
                 0.998     0.026      0.997     0.998     0.997      0.989    NODODGE
Weighted Avg.    0.995     0.024      0.995     0.995     0.995      0.989

=== Confusion Matrix ===

     a     b   <-- classified as
  1308    35 |     a = DODGE
    23 11497 |     b = NODODGE
 */





//Generated with Weka 3.6.9
//
//This code is public domain and comes with no warranty.
//
//Timestamp: Mon Feb 24 16:14:21 CET 2014

package MobileSensors.Weka;

import weka.core.Attribute;
import weka.core.Capabilities;
import weka.core.Capabilities.Capability;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.RevisionUtils;
import weka.classifiers.Classifier;

public class J48
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
 runClassifier(new J48(), args);
}
}

class WekaClassifier {

public static double classify(Object[] i)
 throws Exception {

 double p = Double.NaN;
 p = WekaClassifier.N7b4f744d0(i);
 return p;
}
static double N7b4f744d0(Object []i) {
 double p = Double.NaN;
 if (i[6] == null) {
   p = 1;
 } else if (((Double) i[6]).doubleValue() <= 3.8841837794840797) {
 p = WekaClassifier.N1d5caa7a1(i);
 } else if (((Double) i[6]).doubleValue() > 3.8841837794840797) {
 p = WekaClassifier.N14270b1837(i);
 } 
 return p;
}
static double N1d5caa7a1(Object []i) {
 double p = Double.NaN;
 if (i[0] == null) {
   p = 1;
 } else if (((Double) i[0]).doubleValue() <= 0.1569366482691841) {
 p = WekaClassifier.N2ebc3ea52(i);
 } else if (((Double) i[0]).doubleValue() > 0.1569366482691841) {
 p = WekaClassifier.N4f22f4e611(i);
 } 
 return p;
}
static double N2ebc3ea52(Object []i) {
 double p = Double.NaN;
 if (i[1] == null) {
   p = 1;
 } else if (((Double) i[1]).doubleValue() <= 2.7339013329148285) {
 p = WekaClassifier.N48b4da9b3(i);
 } else if (((Double) i[1]).doubleValue() > 2.7339013329148285) {
 p = WekaClassifier.N62eb35165(i);
 } 
 return p;
}
static double N48b4da9b3(Object []i) {
 double p = Double.NaN;
 if (i[6] == null) {
   p = 1;
 } else if (((Double) i[6]).doubleValue() <= 3.3726364641442466) {
   p = 1;
 } else if (((Double) i[6]).doubleValue() > 3.3726364641442466) {
 p = WekaClassifier.N597c1b674(i);
 } 
 return p;
}
static double N597c1b674(Object []i) {
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
static double N62eb35165(Object []i) {
 double p = Double.NaN;
 if (i[4] == null) {
   p = 1;
 } else if (((Double) i[4]).doubleValue() <= 0.20844372690919977) {
 p = WekaClassifier.N3fea6a166(i);
 } else if (((Double) i[4]).doubleValue() > 0.20844372690919977) {
 p = WekaClassifier.N5e8321508(i);
 } 
 return p;
}
static double N3fea6a166(Object []i) {
 double p = Double.NaN;
 if (i[6] == null) {
   p = 1;
 } else if (((Double) i[6]).doubleValue() <= 2.8990135385713387) {
   p = 1;
 } else if (((Double) i[6]).doubleValue() > 2.8990135385713387) {
 p = WekaClassifier.N36daeb227(i);
 } 
 return p;
}
static double N36daeb227(Object []i) {
 double p = Double.NaN;
 if (i[0] == null) {
   p = 0;
 } else if (((Double) i[0]).doubleValue() <= 0.08046966148540366) {
   p = 0;
 } else if (((Double) i[0]).doubleValue() > 0.08046966148540366) {
   p = 1;
 } 
 return p;
}
static double N5e8321508(Object []i) {
 double p = Double.NaN;
 if (i[7] == null) {
   p = 0;
 } else if (((Double) i[7]).doubleValue() <= 10.486294091010434) {
 p = WekaClassifier.N73003ba99(i);
 } else if (((Double) i[7]).doubleValue() > 10.486294091010434) {
   p = 1;
 } 
 return p;
}
static double N73003ba99(Object []i) {
 double p = Double.NaN;
 if (i[6] == null) {
   p = 1;
 } else if (((Double) i[6]).doubleValue() <= 1.9308665339861084) {
 p = WekaClassifier.N144d0f3b10(i);
 } else if (((Double) i[6]).doubleValue() > 1.9308665339861084) {
   p = 0;
 } 
 return p;
}
static double N144d0f3b10(Object []i) {
 double p = Double.NaN;
 if (i[7] == null) {
   p = 0;
 } else if (((Double) i[7]).doubleValue() <= 4.381004918030684) {
   p = 0;
 } else if (((Double) i[7]).doubleValue() > 4.381004918030684) {
   p = 1;
 } 
 return p;
}
static double N4f22f4e611(Object []i) {
 double p = Double.NaN;
 if (i[3] == null) {
   p = 1;
 } else if (((Double) i[3]).doubleValue() <= -3.434438696707811) {
 p = WekaClassifier.N7ffc50a012(i);
 } else if (((Double) i[3]).doubleValue() > -3.434438696707811) {
 p = WekaClassifier.N620bba815(i);
 } 
 return p;
}
static double N7ffc50a012(Object []i) {
 double p = Double.NaN;
 if (i[3] == null) {
   p = 1;
 } else if (((Double) i[3]).doubleValue() <= -4.037997339589058) {
   p = 1;
 } else if (((Double) i[3]).doubleValue() > -4.037997339589058) {
 p = WekaClassifier.Ne117a0513(i);
 } 
 return p;
}
static double Ne117a0513(Object []i) {
 double p = Double.NaN;
 if (i[6] == null) {
   p = 1;
 } else if (((Double) i[6]).doubleValue() <= 1.7700074069224019) {
   p = 1;
 } else if (((Double) i[6]).doubleValue() > 1.7700074069224019) {
 p = WekaClassifier.N1d61e57a14(i);
 } 
 return p;
}
static double N1d61e57a14(Object []i) {
 double p = Double.NaN;
 if (i[7] == null) {
   p = 0;
 } else if (((Double) i[7]).doubleValue() <= 8.55094843074926) {
   p = 0;
 } else if (((Double) i[7]).doubleValue() > 8.55094843074926) {
   p = 1;
 } 
 return p;
}
static double N620bba815(Object []i) {
 double p = Double.NaN;
 if (i[7] == null) {
   p = 1;
 } else if (((Double) i[7]).doubleValue() <= 7.7947817344912895) {
 p = WekaClassifier.N4f0015bc16(i);
 } else if (((Double) i[7]).doubleValue() > 7.7947817344912895) {
 p = WekaClassifier.N2a31ddcf34(i);
 } 
 return p;
}
static double N4f0015bc16(Object []i) {
 double p = Double.NaN;
 if (i[6] == null) {
   p = 1;
 } else if (((Double) i[6]).doubleValue() <= 2.617033622076744) {
 p = WekaClassifier.Ne93122917(i);
 } else if (((Double) i[6]).doubleValue() > 2.617033622076744) {
 p = WekaClassifier.N34849e5229(i);
 } 
 return p;
}
static double Ne93122917(Object []i) {
 double p = Double.NaN;
 if (i[10] == null) {
   p = 1;
 } else if (((Double) i[10]).doubleValue() <= 0.9773505838776608) {
 p = WekaClassifier.N598d493818(i);
 } else if (((Double) i[10]).doubleValue() > 0.9773505838776608) {
   p = 1;
 } 
 return p;
}
static double N598d493818(Object []i) {
 double p = Double.NaN;
 if (i[1] == null) {
   p = 1;
 } else if (((Double) i[1]).doubleValue() <= 2.7189115111529825) {
   p = 1;
 } else if (((Double) i[1]).doubleValue() > 2.7189115111529825) {
 p = WekaClassifier.N4ac2297619(i);
 } 
 return p;
}
static double N4ac2297619(Object []i) {
 double p = Double.NaN;
 if (i[9] == null) {
   p = 1;
 } else if (((Double) i[9]).doubleValue() <= -0.47297772685519623) {
   p = 1;
 } else if (((Double) i[9]).doubleValue() > -0.47297772685519623) {
 p = WekaClassifier.N1130285220(i);
 } 
 return p;
}
static double N1130285220(Object []i) {
 double p = Double.NaN;
 if (i[7] == null) {
   p = 1;
 } else if (((Double) i[7]).doubleValue() <= 3.2255126790558273) {
   p = 1;
 } else if (((Double) i[7]).doubleValue() > 3.2255126790558273) {
 p = WekaClassifier.N70a7284e21(i);
 } 
 return p;
}
static double N70a7284e21(Object []i) {
 double p = Double.NaN;
 if (i[9] == null) {
   p = 1;
 } else if (((Double) i[9]).doubleValue() <= -0.09160586461953502) {
 p = WekaClassifier.N6e4752a922(i);
 } else if (((Double) i[9]).doubleValue() > -0.09160586461953502) {
 p = WekaClassifier.N30215a2325(i);
 } 
 return p;
}
static double N6e4752a922(Object []i) {
 double p = Double.NaN;
 if (i[7] == null) {
   p = 0;
 } else if (((Double) i[7]).doubleValue() <= 5.871515842682901) {
 p = WekaClassifier.Nc840dcf23(i);
 } else if (((Double) i[7]).doubleValue() > 5.871515842682901) {
   p = 1;
 } 
 return p;
}
static double Nc840dcf23(Object []i) {
 double p = Double.NaN;
 if (i[3] == null) {
   p = 1;
 } else if (((Double) i[3]).doubleValue() <= -1.1524884528619952) {
   p = 1;
 } else if (((Double) i[3]).doubleValue() > -1.1524884528619952) {
 p = WekaClassifier.N31a6977424(i);
 } 
 return p;
}
static double N31a6977424(Object []i) {
 double p = Double.NaN;
 if (i[4] == null) {
   p = 1;
 } else if (((Double) i[4]).doubleValue() <= -0.6441536293354524) {
   p = 1;
 } else if (((Double) i[4]).doubleValue() > -0.6441536293354524) {
   p = 0;
 } 
 return p;
}
static double N30215a2325(Object []i) {
 double p = Double.NaN;
 if (i[7] == null) {
   p = 1;
 } else if (((Double) i[7]).doubleValue() <= 6.832717773513765) {
 p = WekaClassifier.N5da4c88426(i);
 } else if (((Double) i[7]).doubleValue() > 6.832717773513765) {
 p = WekaClassifier.N6837fff228(i);
 } 
 return p;
}
static double N5da4c88426(Object []i) {
 double p = Double.NaN;
 if (i[4] == null) {
   p = 1;
 } else if (((Double) i[4]).doubleValue() <= 0.21306661467437016) {
 p = WekaClassifier.N6d68822327(i);
 } else if (((Double) i[4]).doubleValue() > 0.21306661467437016) {
   p = 1;
 } 
 return p;
}
static double N6d68822327(Object []i) {
 double p = Double.NaN;
 if (i[1] == null) {
   p = 1;
 } else if (((Double) i[1]).doubleValue() <= 3.1004905700497334) {
   p = 1;
 } else if (((Double) i[1]).doubleValue() > 3.1004905700497334) {
   p = 0;
 } 
 return p;
}
static double N6837fff228(Object []i) {
 double p = Double.NaN;
 if (i[4] == null) {
   p = 1;
 } else if (((Double) i[4]).doubleValue() <= 0.3393391957555126) {
   p = 1;
 } else if (((Double) i[4]).doubleValue() > 0.3393391957555126) {
   p = 0;
 } 
 return p;
}
static double N34849e5229(Object []i) {
 double p = Double.NaN;
 if (i[9] == null) {
   p = 0;
 } else if (((Double) i[9]).doubleValue() <= 1.0813591031914753) {
 p = WekaClassifier.N6eb6346d30(i);
 } else if (((Double) i[9]).doubleValue() > 1.0813591031914753) {
   p = 1;
 } 
 return p;
}
static double N6eb6346d30(Object []i) {
 double p = Double.NaN;
 if (i[1] == null) {
   p = 0;
 } else if (((Double) i[1]).doubleValue() <= 3.0517488437891003) {
 p = WekaClassifier.N7c2c18e331(i);
 } else if (((Double) i[1]).doubleValue() > 3.0517488437891003) {
   p = 1;
 } 
 return p;
}
static double N7c2c18e331(Object []i) {
 double p = Double.NaN;
 if (i[10] == null) {
   p = 0;
 } else if (((Double) i[10]).doubleValue() <= 6.568646387188185) {
 p = WekaClassifier.N33161ec532(i);
 } else if (((Double) i[10]).doubleValue() > 6.568646387188185) {
   p = 1;
 } 
 return p;
}
static double N33161ec532(Object []i) {
 double p = Double.NaN;
 if (i[6] == null) {
   p = 0;
 } else if (((Double) i[6]).doubleValue() <= 2.6443192068376336) {
 p = WekaClassifier.N713e31b633(i);
 } else if (((Double) i[6]).doubleValue() > 2.6443192068376336) {
   p = 0;
 } 
 return p;
}
static double N713e31b633(Object []i) {
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
static double N2a31ddcf34(Object []i) {
 double p = Double.NaN;
 if (i[10] == null) {
   p = 1;
 } else if (((Double) i[10]).doubleValue() <= 3.3427846753468757) {
   p = 1;
 } else if (((Double) i[10]).doubleValue() > 3.3427846753468757) {
 p = WekaClassifier.N2fd956ad35(i);
 } 
 return p;
}
static double N2fd956ad35(Object []i) {
 double p = Double.NaN;
 if (i[9] == null) {
   p = 1;
 } else if (((Double) i[9]).doubleValue() <= 3.430597925905374) {
   p = 1;
 } else if (((Double) i[9]).doubleValue() > 3.430597925905374) {
 p = WekaClassifier.N65c9906536(i);
 } 
 return p;
}
static double N65c9906536(Object []i) {
 double p = Double.NaN;
 if (i[10] == null) {
   p = 0;
 } else if (((Double) i[10]).doubleValue() <= 4.095094364382948) {
   p = 0;
 } else if (((Double) i[10]).doubleValue() > 4.095094364382948) {
   p = 1;
 } 
 return p;
}
static double N14270b1837(Object []i) {
 double p = Double.NaN;
 if (i[9] == null) {
   p = 0;
 } else if (((Double) i[9]).doubleValue() <= 0.37008772804604106) {
 p = WekaClassifier.Nf495efe38(i);
 } else if (((Double) i[9]).doubleValue() > 0.37008772804604106) {
 p = WekaClassifier.N4deebab240(i);
 } 
 return p;
}
static double Nf495efe38(Object []i) {
 double p = Double.NaN;
 if (i[1] == null) {
   p = 0;
 } else if (((Double) i[1]).doubleValue() <= 2.9012263679504393) {
   p = 0;
 } else if (((Double) i[1]).doubleValue() > 2.9012263679504393) {
 p = WekaClassifier.N19fb7d8939(i);
 } 
 return p;
}
static double N19fb7d8939(Object []i) {
 double p = Double.NaN;
 if (i[7] == null) {
   p = 0;
 } else if (((Double) i[7]).doubleValue() <= 9.990872601878399) {
   p = 0;
 } else if (((Double) i[7]).doubleValue() > 9.990872601878399) {
   p = 1;
 } 
 return p;
}
static double N4deebab240(Object []i) {
 double p = Double.NaN;
 if (i[6] == null) {
   p = 1;
 } else if (((Double) i[6]).doubleValue() <= 8.091137736346182) {
 p = WekaClassifier.N7423241641(i);
 } else if (((Double) i[6]).doubleValue() > 8.091137736346182) {
 p = WekaClassifier.N290bdbae59(i);
 } 
 return p;
}
static double N7423241641(Object []i) {
 double p = Double.NaN;
 if (i[7] == null) {
   p = 1;
 } else if (((Double) i[7]).doubleValue() <= 12.439122656903685) {
 p = WekaClassifier.N2f165beb42(i);
 } else if (((Double) i[7]).doubleValue() > 12.439122656903685) {
 p = WekaClassifier.N27a2536d57(i);
 } 
 return p;
}
static double N2f165beb42(Object []i) {
 double p = Double.NaN;
 if (i[4] == null) {
   p = 0;
 } else if (((Double) i[4]).doubleValue() <= -0.2590330243812398) {
 p = WekaClassifier.N64ecb97343(i);
 } else if (((Double) i[4]).doubleValue() > -0.2590330243812398) {
 p = WekaClassifier.N718360c844(i);
 } 
 return p;
}
static double N64ecb97343(Object []i) {
 double p = Double.NaN;
 if (i[6] == null) {
   p = 1;
 } else if (((Double) i[6]).doubleValue() <= 4.551112788255033) {
   p = 1;
 } else if (((Double) i[6]).doubleValue() > 4.551112788255033) {
   p = 0;
 } 
 return p;
}
static double N718360c844(Object []i) {
 double p = Double.NaN;
 if (i[10] == null) {
   p = 1;
 } else if (((Double) i[10]).doubleValue() <= 1.7126538288112765) {
 p = WekaClassifier.N684724b045(i);
 } else if (((Double) i[10]).doubleValue() > 1.7126538288112765) {
 p = WekaClassifier.N71a8000249(i);
 } 
 return p;
}
static double N684724b045(Object []i) {
 double p = Double.NaN;
 if (i[7] == null) {
   p = 0;
 } else if (((Double) i[7]).doubleValue() <= 6.362801143185457) {
 p = WekaClassifier.N16b9d44c46(i);
 } else if (((Double) i[7]).doubleValue() > 6.362801143185457) {
 p = WekaClassifier.N220d53c47(i);
 } 
 return p;
}
static double N16b9d44c46(Object []i) {
 double p = Double.NaN;
 if (i[1] == null) {
   p = 1;
 } else if (((Double) i[1]).doubleValue() <= 3.026984365582466) {
   p = 1;
 } else if (((Double) i[1]).doubleValue() > 3.026984365582466) {
   p = 0;
 } 
 return p;
}
static double N220d53c47(Object []i) {
 double p = Double.NaN;
 if (i[9] == null) {
   p = 1;
 } else if (((Double) i[9]).doubleValue() <= 0.4875739977358813) {
 p = WekaClassifier.N398f573b48(i);
 } else if (((Double) i[9]).doubleValue() > 0.4875739977358813) {
   p = 1;
 } 
 return p;
}
static double N398f573b48(Object []i) {
 double p = Double.NaN;
 if (i[6] == null) {
   p = 0;
 } else if (((Double) i[6]).doubleValue() <= 4.22840858642855) {
   p = 0;
 } else if (((Double) i[6]).doubleValue() > 4.22840858642855) {
   p = 1;
 } 
 return p;
}
static double N71a8000249(Object []i) {
 double p = Double.NaN;
 if (i[4] == null) {
   p = 0;
 } else if (((Double) i[4]).doubleValue() <= 0.17928010384896595) {
   p = 0;
 } else if (((Double) i[4]).doubleValue() > 0.17928010384896595) {
 p = WekaClassifier.N4c98bd9950(i);
 } 
 return p;
}
static double N4c98bd9950(Object []i) {
 double p = Double.NaN;
 if (i[1] == null) {
   p = 0;
 } else if (((Double) i[1]).doubleValue() <= 2.5179708746634426) {
   p = 0;
 } else if (((Double) i[1]).doubleValue() > 2.5179708746634426) {
 p = WekaClassifier.N3fc7af1851(i);
 } 
 return p;
}
static double N3fc7af1851(Object []i) {
 double p = Double.NaN;
 if (i[10] == null) {
   p = 1;
 } else if (((Double) i[10]).doubleValue() <= 3.953417712756964) {
 p = WekaClassifier.N4eb8715e52(i);
 } else if (((Double) i[10]).doubleValue() > 3.953417712756964) {
 p = WekaClassifier.N2c308bb455(i);
 } 
 return p;
}
static double N4eb8715e52(Object []i) {
 double p = Double.NaN;
 if (i[7] == null) {
   p = 0;
 } else if (((Double) i[7]).doubleValue() <= 9.802755246112689) {
 p = WekaClassifier.N2f1afab253(i);
 } else if (((Double) i[7]).doubleValue() > 9.802755246112689) {
 p = WekaClassifier.N1440d44754(i);
 } 
 return p;
}
static double N2f1afab253(Object []i) {
 double p = Double.NaN;
 if (i[6] == null) {
   p = 1;
 } else if (((Double) i[6]).doubleValue() <= 4.605274852394602) {
   p = 1;
 } else if (((Double) i[6]).doubleValue() > 4.605274852394602) {
   p = 0;
 } 
 return p;
}
static double N1440d44754(Object []i) {
 double p = Double.NaN;
 if (i[0] == null) {
   p = 0;
 } else if (((Double) i[0]).doubleValue() <= 0.25608147551305604) {
   p = 0;
 } else if (((Double) i[0]).doubleValue() > 0.25608147551305604) {
   p = 1;
 } 
 return p;
}
static double N2c308bb455(Object []i) {
 double p = Double.NaN;
 if (i[9] == null) {
   p = 1;
 } else if (((Double) i[9]).doubleValue() <= 1.1167735654742459) {
 p = WekaClassifier.N2723ed1656(i);
 } else if (((Double) i[9]).doubleValue() > 1.1167735654742459) {
   p = 1;
 } 
 return p;
}
static double N2723ed1656(Object []i) {
 double p = Double.NaN;
 if (i[0] == null) {
   p = 0;
 } else if (((Double) i[0]).doubleValue() <= 0.5848967050015927) {
   p = 0;
 } else if (((Double) i[0]).doubleValue() > 0.5848967050015927) {
   p = 1;
 } 
 return p;
}
static double N27a2536d57(Object []i) {
 double p = Double.NaN;
 if (i[9] == null) {
   p = 1;
 } else if (((Double) i[9]).doubleValue() <= 0.4602728042892097) {
 p = WekaClassifier.N12132d6f58(i);
 } else if (((Double) i[9]).doubleValue() > 0.4602728042892097) {
   p = 1;
 } 
 return p;
}
static double N12132d6f58(Object []i) {
 double p = Double.NaN;
 if (i[3] == null) {
   p = 1;
 } else if (((Double) i[3]).doubleValue() <= 0.592471580160005) {
   p = 1;
 } else if (((Double) i[3]).doubleValue() > 0.592471580160005) {
   p = 0;
 } 
 return p;
}
static double N290bdbae59(Object []i) {
 double p = Double.NaN;
 if (i[1] == null) {
   p = 1;
 } else if (((Double) i[1]).doubleValue() <= 2.709240116924047) {
   p = 1;
 } else if (((Double) i[1]).doubleValue() > 2.709240116924047) {
   p = 0;
 } 
 return p;
}
}