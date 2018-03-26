package client.backend;

import java.io.IOException;
import java.util.ArrayList;

import client.json.parser.ParseException;


public class Controller {
	private MessageAPI messageAPI;
	private ComplaintAPI complaintAPI;
	
	public Controller() {
		messageAPI = new MessageAPI();
		complaintAPI = new ComplaintAPI();
	}
	
	public void sendComplaint(String urlString, String type, String description, String sender_ip, String coords, String name) throws IOException {
		Complaint c = new Complaint(type, description, sender_ip, coords, name);
		complaintAPI.sendComplaint(urlString, c);
	}
	
	public void sendComplaint() {}
	
	public void setResolvedComplaint(String urlString) throws IOException {
		complaintAPI.setResolvedComplaint(urlString);
	}
	
	public void sendMessage() {
		
	}

	
	public ArrayList<Complaint> receiveComplaintList(String endpoint) throws IOException, ParseException {
		ArrayList<Complaint> c = complaintAPI.retrieveComplaintList(endpoint);
		return c;
	}
	
	public Complaint receiveComplaint(String endpoint) throws IOException, ParseException {
		Complaint c = complaintAPI.retrieveComplaint(endpoint);
		return c;
	}
	
	
}
