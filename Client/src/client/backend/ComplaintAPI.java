package client.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import client.json.JSONArray;
import client.json.JSONObject;
import client.json.parser.JSONParser;
import client.json.parser.ParseException;

/**
 * ComplaintAPI contains the methods to contact the server to send and retrieve complaints
 */

public class ComplaintAPI {

	/**
	 * Retrieve the list of complaints from the server
	 * 
	 * @param urlString address of the server
	 * @return list of complaints
	 * @throws IOException
	 * @throws ParseException
	 */
	
	public ArrayList<Complaint> retrieveComplaintList(String urlString) throws IOException, ParseException {
		String payload = GET(urlString);
		ArrayList<Complaint> complaints = deserializeArray(payload);
		return complaints;
	}
	
	/**
	 * Send a complaint to the server
	 * 
	 * @param IPHost address of the server
	 * @param exchange name of the RabbitMQ exchange
	 * @param c complaint to be sent
	 * @throws IOException
	 * @throws TimeoutException
	 */
	
	public void sendComplaint(String IPHost, String exchange, Complaint c) throws IOException, TimeoutException {
		String payload = c.serialize();
		
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost(IPHost);
	    factory.setConnectionTimeout(2000);
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();
	    channel.exchangeDeclare(exchange, "fanout");
	    channel.basicPublish(exchange, "", null, payload.getBytes());
	    System.out.println(" [x] Sent '" + payload + "'");

	    channel.close();
	    connection.close();
	}
	
	/**
	 * Set a complaint to be resolved
	 * 
	 * @param urlString - address of the server
	 * @throws IOException
	 */
	
	public void setResolvedComplaint(String urlString) throws IOException {
		PUT(urlString, "true");
	}
	
	
	/* RESTful API. */
	
	/* GET request */
    private String GET(String urlString) throws IOException, ParseException {
		StringBuilder result = new StringBuilder();
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    	conn.setReadTimeout(2000);
        conn.setConnectTimeout(2000);
		conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();
		
		return result.toString();
	}
    
    /* PUT Request */
    
    private void PUT(String urlString, String payload) throws IOException {
    	URL url = new URL(urlString);
    	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    	conn.setDoOutput(true);
    	conn.setReadTimeout(2000);
        conn.setConnectTimeout(2000);
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "text/plain; charset=UTF-8");
        
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(conn.getOutputStream());
        outputStreamWriter.write(payload);
        outputStreamWriter.flush();
        outputStreamWriter.close();     
   
        Integer responseCode = conn.getResponseCode();        
        System.out.println(responseCode + "   ");
        
    }

    /* Deserializers */
    
    private Complaint deserializeComplaint(String jsonString) throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(jsonString);
			
		String id = null, type = null, description = null, sender_ip = null, 
				location = null, name = null, resolved = null, timestamp = null;
		
		JSONObject senderInfo = new JSONObject();
		
		if(obj.containsKey("_id")) id = obj.get("_id").toString();
		if(obj.containsKey("type")) type = obj.get("type").toString();
		if(obj.containsKey("description")) description = obj.get("description").toString();
		if(obj.containsKey("location")) location = obj.get("location").toString();
		if(obj.containsKey("resolved")) resolved = obj.get("resolved").toString();
		if(obj.containsKey("timestamp")) timestamp = obj.get("timestamp").toString();
		
		if(obj.containsKey("sender-info")) senderInfo = (JSONObject) parser.parse(obj.get("sender-info").toString());
		if(senderInfo.containsKey("name")) name = senderInfo.get("name").toString();	
		if(senderInfo.containsKey("sender-ip")) sender_ip = senderInfo.get("sender-ip").toString(); 
		
	
		return new Complaint(id, type, description, timestamp, sender_ip, location, name, resolved);
    }
    
	private ArrayList<Complaint> deserializeArray(String jsonString) throws ParseException {	
		ArrayList<Complaint> complaints = new ArrayList<>();
		
		JSONParser parser = new JSONParser();
		JSONArray array = (JSONArray) parser.parse(jsonString);

		for(int i=0; i<array.size(); i++) {
			JSONObject obj = (JSONObject) array.get(i);
			
			Complaint c = deserializeComplaint(obj.toJSONString());
			
			complaints.add(c);
		}
		
		return complaints;
	}	
}
