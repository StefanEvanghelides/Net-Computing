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


public class MessageAPI {
	private String message;
	private ServerSocket serverSocket;
	
	private final String SENDER_ADDRESS = "172.20.10.10";
	private final String RECEIVER_ADDRESS = "172.20.10.10";
	private final int SENDER_PORT = 880;
	private final int RECEIVER_PORT = 8800;
	
	public MessageAPI() {
		//startServer();
	}
	
	public void sendPacket(Packet p) throws IOException {
        Socket s;
		try {
			s = new Socket(RECEIVER_ADDRESS, RECEIVER_PORT);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
        
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(s.getOutputStream());
        outputStreamWriter.write(p.toString());
        outputStreamWriter.flush();
        outputStreamWriter.close();     		
	}
	
	public void parsePacket(Packet p) {
		
	}

	private boolean isAvailablePort(int port) {
		boolean available = false;
		
		try {
			Socket s = new Socket("localhost", port);
		    s.close();
		    available = true;;
		} catch (IOException e) {
			System.out.println("meh, error\n");
		}
		
		return available;
	}
	
	public void startServer() {
		Integer port = SENDER_PORT;
		while(!isAvailablePort(port)) port++;
		
        try {
        	serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                
                BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        		StringBuilder result = new StringBuilder();
                String line;
        		while ((line = rd.readLine()) != null) {
        			result.append(line);
        		}
        		rd.close();
           
        		socket.close();
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }
	}
	
	public void sendMessage(String message) throws IOException {
        Socket s;
        
        int port = RECEIVER_PORT;
		while(!isAvailablePort(port)) port++;
		
		try {
			s = new Socket("localhost", port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(s.getOutputStream());
        outputStreamWriter.write(message);
        outputStreamWriter.flush();
        outputStreamWriter.close();     
       
	}
	
	/* Getters and setters. */
	public String getMessage() {
		return this. message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
