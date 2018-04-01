package client.backend;

import client.json.JSONObject;

/**
 * Packet is the object that is sent over the socket connections between clients
 */

public class Packet {
	String name;
	String message;
	
	/**
	 * Construct the packet
	 * @param name
	 * @param message
	 */
	public Packet(String name, String message) {
		this.name = name;
		this.message = message;
	}
	
	/**
	 * Serialize the packet
	 * @return serialized packet
	 */
	public String serialize() {
		JSONObject obj = new JSONObject();
		obj.put("name", name);
		obj.put("message", message);
		
		return obj.toJSONString();
	}
	
	@Override
	public String toString() {return name + ": " + message + "\n";}
}
