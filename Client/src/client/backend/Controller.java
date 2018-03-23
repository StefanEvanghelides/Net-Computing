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
	
//	public void sendComplaint() {
//		Complaint c = new Complaint();
//		complanintAPI.send
//	}
	
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
