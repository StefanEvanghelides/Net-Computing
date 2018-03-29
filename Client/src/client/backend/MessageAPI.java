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


public class MessageAPI {
	private String message;
	private ServerSocket serverSocket;
	
	private final String IP_ADDRESS = "172.20.10.10";
	private int PORT = 4002;
	
	public MessageAPI() {
		startServer();
	}
	
	public void startServer() {		
	    final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);
		
		Runnable serverTask = new Runnable() {

			@Override
			public void run() {
		        try {
		        	serverSocket = new ServerSocket(PORT);
		            while (true) {		        		
						Socket clientSocket = serverSocket.accept();

		                BufferedReader rd = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		        		StringBuilder result = new StringBuilder();
		                String line;
		        		while ((line = rd.readLine()) != null) {
		        			result.append(line);
		        		}
		        		rd.close();
						
						clientProcessingPool.submit(new ClientTask(clientSocket)); 
		        		
		           
		        		System.out.println("Message = " + result);
		        		
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
	
	
    private class ClientTask implements Runnable {
        private Socket clientSocket;

        private ClientTask(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            System.out.println("Got a client !");
            
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
	public void sendMessage(String message) throws IOException {
        Socket s;
		
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
