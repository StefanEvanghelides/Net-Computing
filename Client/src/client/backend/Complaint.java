package client.backend;

import client.json.JSONObject;

/**
 * Complaint is the class that stores all the information about complaints 
 * retrieved from the server
 */

public class Complaint {
	/**
	 * ID of the complaint, set on the server
	 */
	private String id;
	
	/**
	 * Type of the complaint
	 */
	private String type;
	
	/**
	 * Description of the complaint
	 */
	private String description;
	
	/**
	 * IP address of the complainer
	 */
	private String sender_ip;
	
	/**
	 * Location of the complaint
	 */
	private String location;
	
	/**
	 * Name of the complainer
	 */
	private String name;
	
	/**
	 * Whether the complaint has been resolved
	 */
	private String resolved;
	
	/**
	 * Time when the complaint was sent, set on the server
	 */
	private String timestamp;
	
	
	
	/**
	 * Complaint constructor
	 * 
	 * @param id id of the complaint, only set when retrieved from the server
	 * @param type type of complaint
	 * @param description description of the complaint
	 * @param timestamp when the complaint was sent, set on the server
	 * @param sender_ip ip of the complainer
	 * @param location location of the complaint
	 * @param name name of user who sent the complaint
	 * @param resolved whether the complaint has been resolved yet, set to false when initially created
	 */
	
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
	
	/**
	 * Overloaded constructor
	 */
	
	public Complaint(String type, String description, String sender_ip, String location, String name) {
		this(null, type,  description, null, sender_ip, location, name, "false");
	}
	
	/**
	 * Serialize the complaint to JSON
	 * 
	 * @return serialized complaint
	 */
	
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
