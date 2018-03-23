package client;

public class Complaint {
	private String type;
	private String description;
	private String sender_ip;
	private String coords;
	private String name;
	
	public Complaint(String type, String description, String sender_ip, String coords, String name) {
		this.type = type;
		this.description = description;
		this.sender_ip = sender_ip;
		this.coords = coords;
		this.name = name;
	}
	
	public String serialize() {
		return "something";
	}
	
	@Override
	public String toString() {
		/*return "My complaint:\n" +
				"  Type = " + type + "\n" +
				"  Description = " + description + "\n" +
				"  Sender IP = " + sender_ip + "\n" +
				"  Coordinates = " + coords + "\n" +
				"  Name = " + name + "\n";*/
		return type + ": " + description + "\n";
	}
	
}
