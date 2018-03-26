package client.frontend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.backend.Complaint;
import client.backend.Controller;
import client.json.parser.ParseException;

public class Client extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	private Controller controller;
	
	private ArrayList<Complaint> complaints;
	
	private SpringLayout layout;
	private JList<Complaint> complaintsList;
	private JScrollPane complaintsListPane;
	private JButton refreshComplaintsListButton;
	private JButton addComplaintButton;
	private JButton resolveComplaintButton;
	private JButton sendMessageButton;
	private JTextPane complaintInfoPane;
	private JPanel contentPanel;
	
	private String address = "https://0feeab81-419c-4af6-b890-b67085a56e68.mock.pstmn.io";
	
	public Client() {
		super("Complaint System");
		
		this.layout = new SpringLayout();
		this.setLayout(this.layout);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(640, 480);
		this.contentPanel = (JPanel) this.getContentPane();
		this.controller = new Controller();
		
		initialiseComplaintsList();
		initialiseComplaintInfoPane();
		initialiseButtons();
		initializeConstraints();
		
		this.setVisible(true);
	}



	public void updateComplaintsList() {
		try {
			this.complaints = this.controller.receiveComplaintList(this.address + "/mock/all");
		} catch (IOException | ParseException e) { e.printStackTrace(); }
		
		DefaultListModel<Complaint> model = new DefaultListModel<Complaint>();
		
		for(Complaint c : this.complaints) { model.addElement(c); }
		
		this.complaintsList.setModel(model);
	}
	
	public void updateComplaintInfoPane() {
		Complaint c = this.complaintsList.getSelectedValue();
		this.complaintInfoPane.setText(c != null ? c.getFullDescription() : "");
	}
	
	
	
	/* Private helpers */
	private void initialiseComplaintsList() {
		this.complaintsList = new JList<Complaint>();
		this.complaintsList.setFixedCellHeight(40);
		this.complaintsList.setFixedCellWidth(100);
		this.complaintsListPane = new JScrollPane(complaintsList);
		
		this.complaintsList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Client.this.sendMessageButton.setVisible(!Client.this.complaintsList.isSelectionEmpty());
				Client.this.resolveComplaintButton.setVisible(!Client.this.complaintsList.isSelectionEmpty());
				Client.this.updateComplaintInfoPane();
			}
		});
		
		updateComplaintsList();
		
		this.contentPanel.add(complaintsListPane);
	}
	
	private void initialiseButtons() {
		this.refreshComplaintsListButton = new JButton("Refresh");
		this.addComplaintButton = new JButton("Add");
		this.resolveComplaintButton = new JButton("Resolve");
		this.resolveComplaintButton.setVisible(false);
		this.sendMessageButton = new JButton("Send message");
		this.sendMessageButton.setVisible(false);
	
		this.refreshComplaintsListButton.addActionListener(this);
		this.addComplaintButton.addActionListener(this);
		this.resolveComplaintButton.addActionListener(this);
		this.sendMessageButton.addActionListener(this);
	
		this.contentPanel.add(refreshComplaintsListButton);
		this.contentPanel.add(addComplaintButton);
		this.contentPanel.add(resolveComplaintButton);
		this.contentPanel.add(sendMessageButton);
	}

	private void initialiseComplaintInfoPane() {
		this.complaintInfoPane = new JTextPane();
		this.complaintInfoPane.setEditable(false);
		this.complaintInfoPane.setContentType("text/html");
		this.contentPanel.add(this.complaintInfoPane);
	}
	
	private void initializeConstraints() {		
		layout.putConstraint(SpringLayout.NORTH, complaintsListPane, 10, SpringLayout.NORTH, contentPanel);
		layout.putConstraint(SpringLayout.WEST, complaintsListPane, 10, SpringLayout.WEST, contentPanel);
		layout.putConstraint(SpringLayout.SOUTH, complaintsListPane, -10, SpringLayout.NORTH, refreshComplaintsListButton);
		layout.putConstraint(SpringLayout.EAST, complaintsListPane, 200, SpringLayout.WEST, complaintsListPane);
		
		layout.putConstraint(SpringLayout.WEST, refreshComplaintsListButton, 10, SpringLayout.WEST, contentPanel);
		layout.putConstraint(SpringLayout.SOUTH, refreshComplaintsListButton, -10, SpringLayout.SOUTH, contentPanel);
		layout.putConstraint(SpringLayout.EAST, refreshComplaintsListButton, -10, SpringLayout.WEST, addComplaintButton);
		
		layout.putConstraint(SpringLayout.SOUTH, addComplaintButton, -10, SpringLayout.SOUTH, contentPanel);
		layout.putConstraint(SpringLayout.EAST, addComplaintButton, 0, SpringLayout.EAST, complaintsListPane);
		
		layout.putConstraint(SpringLayout.SOUTH, sendMessageButton, -10, SpringLayout.SOUTH, contentPanel);
		layout.putConstraint(SpringLayout.EAST, sendMessageButton, 0, SpringLayout.EAST, complaintInfoPane);
		
		layout.putConstraint(SpringLayout.SOUTH, resolveComplaintButton, -10, SpringLayout.SOUTH, contentPanel);
		layout.putConstraint(SpringLayout.EAST, resolveComplaintButton, -10, SpringLayout.WEST, sendMessageButton);
		
		layout.putConstraint(SpringLayout.NORTH, complaintInfoPane, 10, SpringLayout.NORTH, contentPanel);
		layout.putConstraint(SpringLayout.WEST, complaintInfoPane, 10, SpringLayout.EAST, complaintsListPane);
		layout.putConstraint(SpringLayout.SOUTH, complaintInfoPane, -10, SpringLayout.NORTH, sendMessageButton);
		layout.putConstraint(SpringLayout.EAST, complaintInfoPane, -10, SpringLayout.EAST, contentPanel);
	}
	
	
	
	/* Getters and Setter */
	public Controller getController() {
		return controller;
	}
	
	

	/* Main function. */
	public static void main(String[] args) {
		new Client();
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == refreshComplaintsListButton) {
			updateComplaintsList();
			return;
		}
		
		if(e.getSource() == addComplaintButton) {
			new AddComplaintFrame(this);
			return;
		}
		
		if(e.getSource() == sendMessageButton) {
			new SendMessageFrame(this);
			return;
		}
		
		if(e.getSource() == resolveComplaintButton) {
			this.complaintsList.getSelectedValue();
			return;
		}
	}

}
