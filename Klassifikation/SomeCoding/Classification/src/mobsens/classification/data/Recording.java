package mobsens.classification.data;

public class Recording {
	
	int user_id;
	int device_id;
	String title;
	String url;
	
	public Recording(){
		
	}
	
	public Recording(int user_id, int device_id, String title, String url) {
		
		this.user_id = user_id;
		this.device_id = device_id;
		this.title = title;
		this.url = url;
	}
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getDevice_id() {
		return device_id;
	}
	public void setDevice_id(int device_id) {
		this.device_id = device_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void prettyPrint(){
		
		System.out.println("User_id "+user_id);
		System.out.println("Device_id "+device_id);
		System.out.println("Title "+title);
		System.out.println("url "+url);
		System.out.println("");
		
	}
	
}
