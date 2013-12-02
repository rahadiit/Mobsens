package mobsens.classification.input;

import java.util.ArrayList;

import mobsens.classification.data.Recording;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.ClientFilter;

import mobsens.classification.data.Sensor;

public class RESTFul {

	public static String postServerResponse(Client client, String URL,
			String type, String message) {

		WebResource webResource = client.resource(URL);
		ClientResponse cResponse = null;
		if (!message.equals("")) {
			cResponse = webResource.type(type).post(ClientResponse.class,
					message);
		} else {
			cResponse = webResource.type(type).get(ClientResponse.class);
		}
		return cResponse.getEntity(String.class);
	}

	public static String getServerResponse(Client client, String URL,
			String type) {
		return postServerResponse(client, URL, type, "");
	}

	public static Client login(String URL_LOGIN, String username,
			String password) {

		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getClasses().add(JacksonJsonProvider.class);

		Client client = Client.create(clientConfig);

		// source:http://stackoverflow.com/questions/9676588/how-can-you-authenticate-using-the-jersey-client-against-a-jaas-enabled-web-serv
		// add a filter to set cookies received from the server and to check if
		// login has been triggered
		client.addFilter(new ClientFilter() {
			private ArrayList<Object> cookies;

			@Override
			public ClientResponse handle(ClientRequest request)
					throws ClientHandlerException {
				if (cookies != null) {
					request.getHeaders().put("Cookie", cookies);
				}
				ClientResponse response = getNext().handle(request);
				// copy cookies
				if (response.getCookies() != null) {
					if (cookies == null) {
						cookies = new ArrayList<Object>();
					}
					// A simple addAll just for illustration (should probably
					// check for duplicates and expired cookies)
					cookies.addAll(response.getCookies());
				}
				return response;
			}
		});

		String message = "{\"user\":{\"email\":\"" + username
				+ "\",\"password\":\"" + password + "\"}}";

		// System.out.println(message);

		System.out.println(postServerResponse(client, URL_LOGIN,
				"application/json", message));

		return client;

	}

	public static ArrayList<Recording> recordingOutput(Client client,
			String recordingsURL) {
		ArrayList<Recording> recordings = new ArrayList<>();

		String response = getServerResponse(client, recordingsURL,
				"application/json");

		try {
			JsonFactory f = new JsonFactory();
			JsonParser jp = f.createJsonParser(response);
			jp.nextToken(); // will return JsonToken.START_OBJECT (verify?)

			while (jp.nextToken() != JsonToken.END_ARRAY) {
				int user_id = -1;
				int device_id = -1;
				String title = null;
				String url = null;

				while (jp.nextToken() != JsonToken.END_OBJECT) {

					String fieldname = jp.getCurrentName();

					if ("user_id".equals(fieldname)) { // contains an object
						user_id = jp.nextIntValue(-1);
					} else if ("device_id".equals(fieldname)) {
						device_id = jp.nextIntValue(-1);

					} else if ("title".equals(fieldname)) {
						title = jp.nextTextValue();

					} else if ("url".equals(fieldname)) {
						url = jp.nextTextValue();

					} else {
						throw new IllegalStateException("Unrecognized field '"
								+ fieldname + "'!");
					}
				}
				recordings.add(new Recording(user_id, device_id, title, url));
			}
			// System.out.println(recordings.size());
			jp.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return recordings;

	}

	public static String getCSV(Client client, int recordingID, String url,
			Sensor sensor) {

		url += recordingID + "/" + sensor.toString() + ".csv";
		return getServerResponse(client, url, "application/csv");
	}

}
