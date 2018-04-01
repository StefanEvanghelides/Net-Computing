package client.backend;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.TimeoutException;
import client.json.parser.ParseException;

public class Controller {
	private MessageAPI messageAPI;
	private ComplaintAPI complaintAPI;
	
	private final String EXCHANGE_NAME = "Complaints";
	private final String SERVER_IP_ADDRESS = "192.168.178.22";
	private final String MESSAGES_PATH = "cache/messages.txt";
	private final String SERVER_PORT = "5000";
	private final String COMPLAINTS_ENDPOINT = "complaints";
	
	public Controller() {
		messageAPI = new MessageAPI();
		complaintAPI = new ComplaintAPI();
	}
	
	/* Complaint API part. */
	public void setResolvedComplaint(Complaint c) throws IOException {
		String id = c.getId();
		String mainPath = this.getMainPath();
		String resolvedPath = mainPath + "/" + id + "/resolved";
		complaintAPI.setResolvedComplaint(resolvedPath);
	}
	
	public ArrayList<Complaint> receiveComplaintList(String endpoint) throws IOException, ParseException {
		String mainPath = this.getMainPath();
		String receiveComplaintsPath = mainPath + endpoint;
		ArrayList<Complaint> c = complaintAPI.retrieveComplaintList(receiveComplaintsPath);
		return c;
	}
	
	public void sendComplaint(String type, String description, String sender_ip, String coords, String name) throws IOException, TimeoutException {
		Complaint c = new Complaint(type, description, sender_ip, coords, name);
		complaintAPI.sendComplaint(SERVER_IP_ADDRESS, EXCHANGE_NAME, c);
	}
	
	
	/* Message API part. */
	public void sendMessage(String IPAddress, String name, String message) throws IOException {	
		messageAPI.sendMessage(IPAddress, name, message);
	}
	
	public String readMessage() throws IOException {
		return messageAPI.readData(MESSAGES_PATH);
	}
	
	public void clearMessages() throws IOException {
		messageAPI.clearData();
	}
	
	/* Getting the local IPAddress on UNIX machines. */
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
