package client.backend;

import client.json.JSONObject;

public class Packet {
	String name;
	String message;
	
	public Packet(String name, String message) {
		this.name = name;
		this.message = message;
	}
	
	public String serialize() {
		JSONObject obj = new JSONObject();
		obj.put("name", name);
		obj.put("message", message);
		
		return obj.toJSONString();
	}
	
	@Override
	public String toString() {
		return name + ": " + message;
	}
}
