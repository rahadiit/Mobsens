package mobsens.classification.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class IO {
	public static void writeFile(String path, ArrayList<String> content) {
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
			for (String row : content) {
				bw.write(row);
				bw.newLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<String[]> deleteDuplicates(ArrayList<String[]> content){
		for(int i=0;i<content.size();i++){
			content.remove(content.get(i));
		}
		return content;
	}
}
