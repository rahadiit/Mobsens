package MobileSensors.Test.Output;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;

import MobileSensors.Deprecated.Accelerometer;
import MobileSensors.Storage.Sensors.*;

public class WekaFile {

	public static void writeFile(Collection<Accelerometer> axis, int length, String path) {

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {

			ArrayList<Accelerometer> axisL = (ArrayList<Accelerometer>) axis;

			
//			@RELATION house
//
//			@ATTRIBUTE houseSize NUMERIC
//			@ATTRIBUTE lotSize NUMERIC
//			@ATTRIBUTE bedrooms NUMERIC
//			@ATTRIBUTE granite NUMERIC
//			@ATTRIBUTE bathroom NUMERIC
//			@ATTRIBUTE sellingPrice NUMERIC
//
//			@DATA
			
			bw.write("@RELATION values");
			bw.newLine();
			for(int i=0;i<length;i++){
				bw.write("@ATTRIBUTE x"+i+" NUMERIC");
				bw.newLine();
				bw.write("@ATTRIBUTE y"+i+" NUMERIC");
				bw.newLine();
				bw.write("@ATTRIBUTE z"+i+" NUMERIC");
				bw.newLine();
			}
			
			bw.write("@DATA");
			bw.newLine();
			
			for (int i = 0; i < axisL.size() - length; i++) {
				
				if(i==1000)
					break;

				if(i%1000==0)
					System.out.println(i+ " / "+(axisL.size() - length));
				
				String zeile = "";
				for (int j = i; j < length + i; j++) {

					zeile += (String.valueOf(axisL.get(j).getX()).contains("E")?0:axisL.get(j).getX()) + "," 
							+ (String.valueOf(axisL.get(j).getY()).contains("E")?0:axisL.get(j).getY())+ "," 
							+ (String.valueOf(axisL.get(j).getZ()).contains("E")?0:axisL.get(j).getZ());
					if (j < (length - 1 + i))
						zeile += ",";
				}
				bw.write(zeile);
				bw.newLine();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
