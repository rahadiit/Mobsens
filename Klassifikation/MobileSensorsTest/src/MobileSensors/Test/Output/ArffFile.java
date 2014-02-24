package MobileSensors.Test.Output;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;



public class ArffFile {

	private String fileName;
	private String relName;
	private ArrayList<ArffAttribute> attributes;
	private String[] labels;
	private ArrayList<String> featureVectors;
	
	public ArffFile (String fileName, String relName, String[] labels) {
		
		this.fileName   = fileName;
		this.relName    = relName;
		this.labels     = labels;
		this.attributes = new ArrayList<ArffAttribute>();
		this.featureVectors = new ArrayList<String>();
		
	}
	
	public void addAttribute (String name, String type) {
		
		this.attributes.add(new ArffAttribute(name, type));
		
	}
	
	public void addFeatureVector (String featureVector) {
		
		this.featureVectors.add(featureVector);
		
		
	}
	
	public void write () throws IOException {
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(this.fileName));
		
		bw.write("@RELATION " + this.relName);
		bw.newLine();
		
		for (ArffAttribute attribute : this.attributes) {
			
			bw.write(attribute.toString());
			bw.newLine();
		}
		
		bw.write("@ATTRIBUTE label {" + StringUtils.join(this.labels, ",") + "}");
		bw.newLine();
		
		bw.write("@DATA");
		bw.newLine();
		
		for (String featureVector : this.featureVectors) {
			
			bw.write(featureVector);
			bw.newLine();
			
		}
		
		bw.flush();
		bw.close();
		
	}
	
}
