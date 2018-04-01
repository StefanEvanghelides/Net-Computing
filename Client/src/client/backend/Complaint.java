package client.backend;

import client.json.JSONObject;

public class Complaint {
	private String id;
	private String type;
	private String description;
	private String sender_ip;
	private String location;
	private String name;
	private String resolved;
	private String timestamp;
	
	public Complaint(String id, String type, String description, String timestamp, String sender_ip, String location, String name, String resolved) {
		this.id = id;
		this.type = type;
		this.description = description;
		this.timestamp = timestamp;
		this.sender_ip = sender_ip;
		this.location = location;
		this.name = name;
		this.resolved = resolved;
	}
	
	public Complaint(String type, String description, String sender_ip, String location, String name) {
		this(null, type,  description, null, sender_ip, location, name, "false");
	}
	
	public String serialize() {
		JSONObject obj = new JSONObject();
		
		obj.put("type", type);
		obj.put("description", description);
		obj.put("sender_ip", sender_ip);
		obj.put("location", location);
		obj.put("name", name);
		
		return obj.toJSONString();
	}
	
	@Override
	public String toString() {
		return "<html>" + type + "  <br/>" + location + "</html>"; 
	}
	
//	public String getFullDescription() {
//		return "My complaint:<br/>" +
//			    "  ID = " + id + "<br/>" + 
//				"  Type = " + type + "<br/>" +
//				"  Description = " + description + "<br/>" +
//				"  Sender IP = " + sender_ip + "<br/>" +
//				"  Location = " + location + "<br/>" +
//				"  Name = " + name + "<br/>";
//	}
	
	/* Getters */
	
	public String getId() {return this.id;}
	public String getType() {return this.type;}
	public String getName() {return this.name;}
	public String getLocation() {return this.location;}
	public String getDescription() {return this.description;}
	public String getResolved() {return this.resolved;}
	public String getTimestamp() {return this.timestamp;}
	public String getSenderIp() {return this.sender_ip;}
}
