package MobileSensors.Test.Data;


public enum URLS {
	LIST_RECORDINGS, CSV, LOGIN;

	public String getURL() {
		switch (this) {
		case LIST_RECORDINGS:
			return "http://mobilesensing.west.uni-koblenz.de/recordings.json";
		case CSV:
			return "http://mobilesensing.west.uni-koblenz.de/recording/";
		case LOGIN:
			return "http://mobilesensing.west.uni-koblenz.de/users/sign_in.json";

		default:
			return "";
		}
	}
}
