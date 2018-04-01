package client.backend;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.TimeoutException;
import client.json.parser.ParseException;

/**
 * Controller contains the methods for all interactions with the client
 */

public class Controller {
	private MessageAPI messageAPI;
	private ComplaintAPI complaintAPI;
	
	private final String EXCHANGE_NAME = "Complaints";
	private final String SERVER_IP_ADDRESS = "192.168.178.22";
	private final String MESSAGES_PATH = "cache/messages.txt";
	private final String SERVER_PORT = "5000";
	private final String COMPLAINTS_ENDPOINT = "complaints";
	
	/**
	 * Construct the controller
	 */
	public Controller() {
		messageAPI = new MessageAPI();
		complaintAPI = new ComplaintAPI();
	}
	
	
	
	/* Complaint API part. */
	
	/**
	 * Set a complaint to be resolved
	 * @param c complaint to be resolved
	 * @throws IOException
	 */
	public void setResolvedComplaint(Complaint c) throws IOException {
		String id = c.getId();
		String mainPath = this.getMainPath();
		String resolvedPath = mainPath + "/" + id + "/resolved";
		complaintAPI.setResolvedComplaint(resolvedPath);
	}
	
	/**
	 * Get complaints list from the server 
	 * @param endpoint - address of the server
	 * @return complaints list
	 * @throws IOException
	 * @throws ParseException
	 */
	public ArrayList<Complaint> receiveComplaintList(String endpoint) throws IOException, ParseException {
		String mainPath = this.getMainPath();
		String receiveComplaintsPath = mainPath + endpoint;
		ArrayList<Complaint> c = complaintAPI.retrieveComplaintList(receiveComplaintsPath);
		return c;
	}
	
	/**
	 * Send complaint to the server
	 * @param type type of complaint
	 * @param description description of the complaint
	 * @param sender_ip ip of the sender
	 * @param location location of the complaint
	 * @param name name of the sender
	 * @throws IOException
	 * @throws TimeoutException
	 */
	public void sendComplaint(String type, String description, String sender_ip, String location, String name) throws IOException, TimeoutException {
		Complaint c = new Complaint(type, description, sender_ip, location, name);
		complaintAPI.sendComplaint(SERVER_IP_ADDRESS, EXCHANGE_NAME, c);
	}
	
	
	
	/* Message API part. */
	
	/**
	 * Send message to other client
	 * @param IPAddress ip of the other client
	 * @param name name of the sender
	 * @param message contents of the message
	 * @throws IOException
	 */
	public void sendMessage(String IPAddress, String name, String message) throws IOException {	
		messageAPI.sendMessage(IPAddress, name, message);
	}
	
	/**
	 * Read the contents of the messages file
	 * @return contents of the message file
	 * @throws IOException
	 */
	public String retrieveMessagesFromFile() throws IOException {
		return messageAPI.readData(MESSAGES_PATH);
	}
	
	/**
	 * Clear the contents of the message file
	 * @throws IOException
	 */
	public void clearMessages() throws IOException {
		messageAPI.clearData();
	}
	
	/**
	 * Retrieve the IP address of the current machine. Currently only working for Linux
	 * @return IP address of this machine
	 * @throws SocketException
	 */
	public String getLocalIPAddress() throws SocketException {
		NetworkInterface ni = NetworkInterface.getByName("wlo1");
        String result = "";
        
        if(ni == null) return result;
        Enumeration<InetAddress> inetAddresses =  ni.getInetAddresses();
        while(inetAddresses.hasMoreElements()) {
            InetAddress ia = inetAddresses.nextElement();
            if(!ia.isLinkLocalAddress()) {
                result = ia.getHostAddress();
                break;
            }
        }
        
        return result;
	}

	private String getMainPath() {
		return "http://" + SERVER_IP_ADDRESS + ":" + SERVER_PORT + "/" + COMPLAINTS_ENDPOINT;
	}
}
