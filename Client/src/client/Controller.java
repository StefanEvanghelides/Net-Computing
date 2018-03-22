package client;

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
	
	public void sendComplaint() {
		
	}
	
	public void sendMessage() {
		
	}

	public ArrayList<Complaint> receiveComplaint(String endpoint) throws IOException, ParseException {
		ArrayList<Complaint> c;
		
		c = complaintAPI.GET(endpoint);

		return c;
	}
	
	
	/* Getters and setters. */
	
	public MessageAPI getMessage() {
		return this.messageAPI;
	}
	
	public void setMessage(MessageAPI messageAPI) {
		this.messageAPI = messageAPI;
	}
	
	public ComplaintAPI getComplaint() {
		return this.complaintAPI;
	}
	
	public void setComplaint(ComplaintAPI complaintAPI) {
		this.complaintAPI = complaintAPI;
	}
	
}
