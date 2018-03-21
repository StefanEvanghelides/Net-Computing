package client;

public class Complaint {
	private String type;
	private String description;
	private String sender_ip;
	private Coordinates coords;
	private String name;
	
	public Complaint(String type, String description, String sender_ip, Coordinates coords, String name) {
		this.type = type;
		this.description = description;
		this.sender_ip = sender_ip;
		this.coords = coords;
		this.name = name;
	}
	
	
}
