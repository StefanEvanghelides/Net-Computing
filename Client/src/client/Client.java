package client;

import java.io.IOException;
import java.util.ArrayList;

import client.json.parser.ParseException;

public class Client {
	
	private Controller controller;
	
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
	

	
	public void testArrays() {		
		ArrayList<Complaint> complaints = new ArrayList<>();
		
		try {
			complaints = this.getController().receiveComplaintList("https://6e145bfb-a718-460e-af0f-7fa9c390aad2.mock.pstmn.io/mockTest/Array");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("My Array:\n");
		for(int i=0; i<complaints.size(); i++) {
			System.out.println("Complaint #" + (i+1) + "\n " + complaints.get(i).toString());
		}
	}
	
	public void testComplaint() {
		
		try {
			Complaint c = this.getController().receiveComplaint("https://6e145bfb-a718-460e-af0f-7fa9c390aad2.mock.pstmn.io/mockTest/Complaint");
			System.out.println(c.toString());
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	/* Main function. */
	public static void main(String[] args) {
		Client A = new Client();
		
		A.testComplaint();
		
	}

}