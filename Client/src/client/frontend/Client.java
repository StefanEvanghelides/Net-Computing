package client.frontend;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;

import client.backend.Complaint;
import client.backend.Controller;
import client.json.parser.ParseException;

public class Client extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private Controller controller;
	
	private ArrayList<Complaint> complaints;
	
	private JList<Complaint> complaintsList;
	private JButton refreshComplaintsList;
	
	public Client() {
		super("Test");
		this.setLayout(new FlowLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		controller = new Controller();
		this.complaintsList = new JList<Complaint>();		
		this.complaintsList.setSize(200, 200);
		this.getContentPane().add(complaintsList);
		
		this.refreshComplaintsList = new JButton(new AbstractAction("Refresh") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Client.this.updateComplaintsList();
			}
		});
		this.getContentPane().add(refreshComplaintsList);
		
		updateComplaintsList();
		
		this.pack();
		this.setVisible(true);
	}
	
	/* Getters and setters. */
	public Controller getController() {
		return this.controller;
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	

	
	public void updateComplaintsList() {
		try {
			complaints = this.getController().receiveComplaintList("https://6e145bfb-a718-460e-af0f-7fa9c390aad2.mock.pstmn.io/mockTest/Array");
		} catch (IOException | ParseException e) { e.printStackTrace(); }
		
		DefaultListModel<Complaint> model = new DefaultListModel<Complaint>();
		
		for(Complaint c : complaints) {
				model.addElement(c);
		}
		
		this.complaintsList.setModel(model);
	}
	
	public void testArrays() {		
		ArrayList<Complaint> complaints = new ArrayList<>();
		
		try {
			complaints = this.getController().receiveComplaintList("https://6e145bfb-a718-460e-af0f-7fa9c390aad2.mock.pstmn.io/mockTest/Array");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
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
			e.printStackTrace();
		}
	}
	

	/* Main function. */
	public static void main(String[] args) {
		new Client();
	}

}
