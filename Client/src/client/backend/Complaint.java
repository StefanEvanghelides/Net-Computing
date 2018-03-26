package client.backend;

public class Complaint {
	private String id;
	private String type;
	private String description;
	private String sender_ip;
	private String location;
	private String name;
	
	public Complaint(String id, String type, String description, String sender_ip, String location, String name) {
		this.id = id;
		this.type = type;
		this.description = description;
		this.sender_ip = sender_ip;
		this.location = location;
		this.name = name;
	}
	
	public Complaint(String type, String description, String sender_ip, String location, String name) {
		this(null, type,  description, sender_ip, location, name);
	}
	
	public String serialize() {
		return "something";
	}
	
	@Override
	public String toString() {
		return "<html>" + type + "  <br/>" + location + "</html>"; 
	}
	
	public String getFullDescription() {
		return "My complaint:<br/>" +
			    "  ID = " + id + "<br/>" + 
				"  Type = " + type + "<br/>" +
				"  Description = " + description + "<br/>" +
				"  Sender IP = " + sender_ip + "<br/>" +
				"  Location = " + location + "<br/>" +
				"  Name = " + name + "<br/>";
	}
	
}
