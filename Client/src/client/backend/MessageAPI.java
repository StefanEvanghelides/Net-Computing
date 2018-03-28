package client.backend;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class MessageAPI {
	private String message;
	private ServerSocket serverSocket;
	
	private final String MY_IP_ADDRESS = "172.20.10.10";
	private final String IP_ADDRESS = "172.20.10.10";
	private final int PORT = 8872;
	
	public MessageAPI() {
		//startServer();
	}
	
	public void sendPacket(Packet p) throws IOException {
        Socket s;
		try {
			s = new Socket(IP_ADDRESS, PORT);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
        
		ObjectOutputStream outToServer = new ObjectOutputStream(s.getOutputStream());
        
		//outToServer.write(p);
		
	}
	
	public void parsePacket(Packet p) {
		
	}

	private boolean isAvailablePort(int port) {
		boolean available = false;
		
		try {
			ServerSocket ss = new ServerSocket(port);
		    ss.close();
		    available = true;;
		} catch (IOException e) {
			System.out.println("meh, error\n");
		}
		
		return available;
	}
	
	public void startServer() {
		Integer port = PORT;
		while(!isAvailablePort(port)) port++;
		
        try {
        	serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                try {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("Message received:");
                } finally {
                    socket.close();
                }
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }
	}
	
	public void sendMessage(String message) throws IOException {
        Socket s;
		try {
			s = new Socket(IP_ADDRESS, PORT);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		ObjectOutputStream outToServer = new ObjectOutputStream(s.getOutputStream());
        outToServer.write(message.getBytes());
       
	}
	
	/* Getters and setters. */
	public String getMessage() {
		return this. message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
