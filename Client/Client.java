import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;


public class Client {
	
	private Controller controller;
	private ServerSocket serverSocket;
	
	public Client() {
		controller = new Controller();
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

	public String GET(String urlToRead) throws Exception {
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
		return result.toString();
	}
	
	/* Getters and setters. */
	public Controller getController() {
		return this.controller;
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	/* Main function. */
	public static void main(String[] args) {
		Client A = new Client();
		
		//A.startServer(8000);
		
		A.sendMessage("something", "localhost", 8000);
		
		//A.stopServer();

	}

}
