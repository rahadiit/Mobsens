package MobileSensors.Test.Input;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import MobileSensors.Test.Data.URLS;

import com.sun.jersey.api.client.Client;

public class JsonInput {

	public static String getJsonFromId(Client client, int id) {

		// String link = URLS.RECORDINGS.getURL() + "" + id + ".json";
		String link = "http://mobilesensing.west.uni-koblenz.de/admin/sensor-upload/246/raw?utf8=%E2%9C%93";
		return RESTful.getServerResponse(client, link, "application/json");

	}

	public static void parseJson(String response) {

		try {

			JsonFactory f = new JsonFactory();
			JsonParser jp = f.createJsonParser(response);
			//JsonToken ta = jp.nextToken(); // will return JsonToken.START_OBJECT
											// (verify?)
			//System.out.println(ta);
			
			for(int i=0;i<30;i++){
				System.out.println(jp.nextToken()+"    :"+jp.getCurrentName());
			}

//			while (ta!= null) {
//				while ((ta = jp.nextToken()) != JsonToken.END_OBJECT && ta!=null) {
//					String fieldname = jp.getCurrentName();
//					System.out.println(fieldname);
//					
//				}
//			}

		} catch (Exception e) {

		}

	}

}
