package MobileSensors.Test.Input;

import java.util.ArrayList;

public class FileInput {

	public static ArrayList<String[]> deleteDuplicates(ArrayList<String[]> content){
		for(int i=0;i<content.size()-1;i++){
			String[] temp = content.get(i);
			while(content.remove(temp));
			content.set(i, temp);
		}
		return content;
	}
}
