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
	
	private final String IP_ADDRESS = "172.20.10.10";
	private int PORT = 4001;
	
	public MessageAPI() {
		//startServer();
	}
	
	private boolean isAvailablePort(int port) {
		boolean available = false;
		
		try {
			Socket s = new Socket("localhost", port);
		    s.close();
		    available = true;
		    System.out.println("Port = " + port);
		} catch (IOException e) {
			System.err.print(port + " ");
		}
		
		return available;
	}
	
	public void startServer() {
		//Integer port = PORT;
		//while(!isAvailablePort(port)) port++;
		
        try {
        	serverSocket = new ServerSocket(PORT);
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
        
//        int port = PORT;
//		while(!isAvailablePort(port)) port++;
		
		try {
			s = new Socket("localhost", PORT);
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
