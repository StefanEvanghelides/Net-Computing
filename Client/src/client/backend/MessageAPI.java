package client.backend;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import client.json.JSONObject;
import client.json.parser.JSONParser;
import client.json.parser.ParseException;


public class MessageAPI {
	private String message;
	private ServerSocket serverSocket;
	
	private final String IP_ADDRESS = "172.20.10.10";
	private int PORT = 4002;
	
	public MessageAPI() {
		startServer();
	}
	
	public void startServer() {		
		Runnable serverTask = new Runnable() {

			@Override
			public void run() {
		        try {
		        	serverSocket = new ServerSocket(PORT);
		            while (true) {		        		
						Socket clientSocket = serverSocket.accept();

		                BufferedReader rd = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		        		StringBuilder payload = new StringBuilder();
		                String line;
		        		while ((line = rd.readLine()) != null) {
		        			payload.append(line);
		        		}
		        		rd.close();	
		           
		        		Packet p = MessageAPI.this.parsePacket(payload.toString());
		        		
		        		System.out.println("Packet = " + p);    		
		        		
		        		clientSocket.close();
		            }
		        } catch(Exception e) {
		        	e.printStackTrace();
		        }
		        
			}			
		};
        Thread serverThread = new Thread(serverTask);
        serverThread.start();

	}
	
	public void sendMessage(final String IPAddress, final String name, final String message) throws IOException {        
        final Packet p = new Packet(name, message);
		Runnable clientTask = new Runnable() {
			@Override
			public void run() {
		        Socket s;
				try {
					s = new Socket(IPAddress, PORT);
			        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(s.getOutputStream());
			        outputStreamWriter.write(p.serialize());
			        outputStreamWriter.flush();
			        outputStreamWriter.close();
			        s.close();
				} catch (UnknownHostException e) {
					e.printStackTrace();
					return;
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
			}
        	
        };
        Thread clientThread = new Thread(clientTask);
        clientThread.start();
       
	}
	
	public Packet parsePacket(String payload) throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(payload);
		
		String name = null, message = null;
		
		if(obj.containsKey("name")) name = obj.get("name").toString();
		if(obj.containsKey("message")) message = obj.get("message").toString();
		
		return new Packet(name, message);
	}
	
	/* Getters and setters. */
	public String getMessage() {
		return this. message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public ServerSocket getServerSocket() {
		return this.serverSocket;
	}
	
	public void setServerSocket(ServerSocket ss) {
		this.serverSocket = ss;
	}
}
