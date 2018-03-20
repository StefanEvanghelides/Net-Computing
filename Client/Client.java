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
		
		String result = A.getController().receiveComplaint();
		
		System.out.println("Result: " + result);
	}

}
