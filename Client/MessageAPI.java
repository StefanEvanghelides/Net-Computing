import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class MessageAPI {
	private String message;
	private ServerSocket serverSocket;
	
	public MessageAPI() {
		
	}
	
	public void send() {
		
	}
	
	public void parsePacket(Packet p) {
		
	}

	public void startServer(int port) {
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
	
	public void sendMessage(String message, String address, int port) {
        Socket s;
		try {
			s = new Socket(address, port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
        BufferedReader rd;
		try {
			rd = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			try {
				s.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return;
		}
        StringBuilder result = new StringBuilder();
        String line;
		try {
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		try {
			rd.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stopServer() {
		if(serverSocket != null) {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			serverSocket = null;
		}
	}
	
	/* Getters and setters. */
	public String getMessage() {
		return this. message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
