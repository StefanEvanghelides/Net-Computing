package client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import client.json.JSONArray;
import client.json.JSONObject;
import client.json.parser.JSONParser;
import client.json.parser.ParseException;


public class ComplaintAPI {
	String address;

    public ArrayList<Complaint> GET(String urlToRead) throws IOException, ParseException {
		StringBuilder result = new StringBuilder();
		URL url = new URL(urlToRead);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();
		
		ArrayList<Complaint> c = deserialize(result.toString());
		return c;
		
		//return result.toString();
	}

	public ArrayList<Complaint> deserialize(String jsonString) throws ParseException {	
		ArrayList<Complaint> complaints = new ArrayList<>();
		
		JSONParser parser = new JSONParser();
		JSONArray array = (JSONArray) parser.parse(jsonString);

		for(int i=0; i<array.size(); i++) {
			JSONObject obj = (JSONObject) array.get(i);
			
			String type = null, description = null, sender_ip = null, coords = null, name = null;
			
			if(obj.containsKey("type")) type = obj.get("type").toString();
			if(obj.containsKey("description")) description = obj.get("description").toString();
			if(obj.containsKey("sender_ip")) sender_ip = obj.get("sender_ip").toString();
			if(obj.containsKey("coords")) coords = obj.get("coords").toString();
			if(obj.containsKey("name")) name = obj.get("name").toString();
			
			Complaint c = new Complaint(type, description, sender_ip, coords, name);
			complaints.add(c);
		}

		
		return complaints;
	}

	/* Getters and setters.*/
	public String getAddress() {
		return this.address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
}
