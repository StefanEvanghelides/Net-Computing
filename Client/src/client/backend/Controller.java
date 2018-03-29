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
	private final String IP_ADDRESS = "172.20.10.8";
	
	public Controller() {
		messageAPI = new MessageAPI();
		complaintAPI = new ComplaintAPI();
	}
	
	public void sendComplaint(String type, String description, String sender_ip, String coords, String name) throws IOException, TimeoutException {
		Complaint c = new Complaint(type, description, sender_ip, coords, name);
		complaintAPI.sendComplaint(IP_ADDRESS, EXCHANGE_NAME, c);
	}
	
	public void sendMessage(String message) throws IOException {	
		messageAPI.sendMessage(message);
	}
	
	public void setResolvedComplaint(String urlString, Complaint c) throws IOException {
		String id = c.getId();
		complaintAPI.setResolvedComplaint(urlString + "/" + id + "/resolved");
	}
	
	public ArrayList<Complaint> receiveComplaintList(String endpoint) throws IOException, ParseException {
		ArrayList<Complaint> c = complaintAPI.retrieveComplaintList(endpoint);
		return c;
	}
	
	public Complaint receiveComplaint(String endpoint) throws IOException, ParseException {
		Complaint c = complaintAPI.retrieveComplaint(endpoint);
		return c;
	}
	
	public String getLocalIPAddress() throws SocketException {
		NetworkInterface ni = NetworkInterface.getByName("wlo1");
        Enumeration<InetAddress> inetAddresses =  ni.getInetAddresses();

        String result = "";
        while(inetAddresses.hasMoreElements()) {
            InetAddress ia = inetAddresses.nextElement();
            if(!ia.isLinkLocalAddress()) {
                result = ia.getHostAddress();
                break;
            }
        }
        
        return result;
	}
}
