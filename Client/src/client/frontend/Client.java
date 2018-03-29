package client.frontend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;

import client.backend.Complaint;
import client.backend.Controller;
import client.json.parser.ParseException;

public class Client extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	/* Main function. */
	public static void main(String[] args) {
		new Client();
	}

	private Controller controller;

	private ArrayList<Complaint> complaints;
	private SpringLayout layout;
	private JList<Complaint> complaintsList;
	private JScrollPane complaintsListPane;
	private JButton refreshComplaintsListButton;
	private JButton addComplaintButton;
	private JButton resolveComplaintButton;
	private JButton sendMessageButton;
	private JButton seeMessagesButton;
	private JTextPane complaintInfoPane;
	private JCheckBox showUnresolvedCheckBox;
	private JComboBox<String> showNumberOfComplaints;
	
	private int numComplaintsShown = 10;

	private JPanel contentPanel;
	private String larsAddress = "http://172.20.10.8:5000/complaints";
	
	public Client() {
		super("Complaint System");

		layout = new SpringLayout();
		contentPanel = (JPanel) getContentPane();
		controller = new Controller();

		setLayout(layout);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(640, 480);

		initialiseButtons();
		initialiseComplaintInfoPane();
		initialiseComplaintsList();
		initializeConstraints();

		setVisible(true);
		updateComplaintsList();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == refreshComplaintsListButton) {
			updateComplaintsList();
			return;
		}

		if (e.getSource() == addComplaintButton) {
			new AddComplaintFrame(this);
			return;
		}

		if (e.getSource() == sendMessageButton) {
			String IPAddress = complaintsList.getSelectedValue().getSenderIp();
			new SendMessageFrame(this, IPAddress);
			return;
		}

		if (e.getSource() == resolveComplaintButton) {
			Complaint c = complaintsList.getSelectedValue();
			try {
				controller.setResolvedComplaint(larsAddress, c);
			} catch (IOException error) {
				JOptionPane.showMessageDialog(this, "Error connecting to server", "Connection Error",
						JOptionPane.ERROR_MESSAGE);
			}
			updateComplaintsList();
			return;
		}
		
		if(e.getSource() == seeMessagesButton) {
			new MessagesFrame(this);
		}
		
		if(e.getSource() == showUnresolvedCheckBox) {
			updateComplaintsList();
			return;
		}
		
		if(e.getSource() == showNumberOfComplaints) {
			numComplaintsShown = Integer.parseInt((String)showNumberOfComplaints.getSelectedItem());
			updateComplaintsList();
			return;
		}
	}

	/* Getters and Setter */
	public Controller getController() {
		return controller;
	}

	private void initialiseButtons() {
		refreshComplaintsListButton = new JButton("Refresh");
		refreshComplaintsListButton.setFocusPainted(false);
		
		addComplaintButton = new JButton("Add");
		addComplaintButton.setFocusPainted(false);
		
		resolveComplaintButton = new JButton("Resolve");
		resolveComplaintButton.setVisible(false);
		resolveComplaintButton.setFocusPainted(false);
		
		sendMessageButton = new JButton("Send message");
		sendMessageButton.setVisible(false);
		sendMessageButton.setFocusPainted(false);
		
		seeMessagesButton = new JButton(new ImageIcon("images/mail.png"));
		seeMessagesButton.setFocusPainted(false);
		
		showUnresolvedCheckBox = new JCheckBox("Only show unresolved");
		showUnresolvedCheckBox.setFont(new java.awt.Font("Dialog", 1, 10));
		showUnresolvedCheckBox.setFocusPainted(false);
		
		String[] options = {"5","10","15","20"};
		showNumberOfComplaints = new JComboBox<String>(options);
		showNumberOfComplaints.setSelectedIndex(1);

		refreshComplaintsListButton.addActionListener(this);
		addComplaintButton.addActionListener(this);
		resolveComplaintButton.addActionListener(this);
		sendMessageButton.addActionListener(this);
		seeMessagesButton.addActionListener(this);;
		showUnresolvedCheckBox.addActionListener(this);
		showNumberOfComplaints.addActionListener(this);

		contentPanel.add(refreshComplaintsListButton);
		contentPanel.add(addComplaintButton);
		contentPanel.add(resolveComplaintButton);
		contentPanel.add(sendMessageButton);
		contentPanel.add(showUnresolvedCheckBox);
		contentPanel.add(showNumberOfComplaints);
		contentPanel.add(seeMessagesButton);
	}

	private void initialiseComplaintInfoPane() {
		complaintInfoPane = new JTextPane();
		complaintInfoPane.setEditable(false);
		complaintInfoPane.setContentType("text/html");
		contentPanel.add(complaintInfoPane);
	}

	/* Private helpers */
	private void initialiseComplaintsList() {
		complaintsList = new JList<Complaint>();
		complaintsList.setFixedCellHeight(40);
		complaintsList.setFixedCellWidth(100);
		complaintsListPane = new JScrollPane(complaintsList);

		complaintsList.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
					Client.this.updateComplaintInfoPane();
				}
			}

		});

		contentPanel.add(complaintsListPane);
	}

	private void initializeConstraints() {
		layout.putConstraint(SpringLayout.NORTH, complaintsListPane, 10, SpringLayout.NORTH, contentPanel);
		layout.putConstraint(SpringLayout.WEST, complaintsListPane, 10, SpringLayout.WEST, contentPanel);
		layout.putConstraint(SpringLayout.SOUTH, complaintsListPane, -10, SpringLayout.NORTH,
				showUnresolvedCheckBox);
		layout.putConstraint(SpringLayout.EAST, complaintsListPane, 200, SpringLayout.WEST, complaintsListPane);

		layout.putConstraint(SpringLayout.WEST, refreshComplaintsListButton, 10, SpringLayout.WEST, contentPanel);
		layout.putConstraint(SpringLayout.SOUTH, refreshComplaintsListButton, -10, SpringLayout.SOUTH, contentPanel);
		layout.putConstraint(SpringLayout.EAST, refreshComplaintsListButton, -10, SpringLayout.WEST,
				addComplaintButton);

		layout.putConstraint(SpringLayout.SOUTH, addComplaintButton, -10, SpringLayout.SOUTH, contentPanel);
		layout.putConstraint(SpringLayout.EAST, addComplaintButton, 0, SpringLayout.EAST, complaintsListPane);

		layout.putConstraint(SpringLayout.SOUTH, sendMessageButton, -10, SpringLayout.SOUTH, contentPanel);
		layout.putConstraint(SpringLayout.EAST, sendMessageButton, 0, SpringLayout.EAST, complaintInfoPane);

		layout.putConstraint(SpringLayout.NORTH, seeMessagesButton, 10, SpringLayout.SOUTH, complaintInfoPane);
		layout.putConstraint(SpringLayout.WEST, seeMessagesButton, 0, SpringLayout.WEST, complaintInfoPane);		
		
		layout.putConstraint(SpringLayout.SOUTH, resolveComplaintButton, -10, SpringLayout.SOUTH, contentPanel);
		layout.putConstraint(SpringLayout.EAST, resolveComplaintButton, -10, SpringLayout.WEST, sendMessageButton);
		
		layout.putConstraint(SpringLayout.SOUTH, showUnresolvedCheckBox, -10, SpringLayout.NORTH, refreshComplaintsListButton);
		layout.putConstraint(SpringLayout.EAST, showUnresolvedCheckBox, -10, SpringLayout.WEST, complaintInfoPane);		

		layout.putConstraint(SpringLayout.SOUTH, showNumberOfComplaints, -10, SpringLayout.NORTH, refreshComplaintsListButton);
		layout.putConstraint(SpringLayout.WEST, showNumberOfComplaints, 10, SpringLayout.WEST, contentPanel);			
		
		layout.putConstraint(SpringLayout.NORTH, complaintInfoPane, 10, SpringLayout.NORTH, contentPanel);
		layout.putConstraint(SpringLayout.WEST, complaintInfoPane, 10, SpringLayout.EAST, complaintsListPane);
		layout.putConstraint(SpringLayout.SOUTH, complaintInfoPane, -10, SpringLayout.NORTH, sendMessageButton);
		layout.putConstraint(SpringLayout.EAST, complaintInfoPane, -10, SpringLayout.EAST, contentPanel);
	}

	public void updateComplaintInfoPane() {
		Complaint c = null;

		if (!complaintsList.isSelectionEmpty()) {
			c = complaintsList.getSelectedValue();
		}

		sendMessageButton.setVisible(!complaintsList.isSelectionEmpty());
		resolveComplaintButton.setVisible(!complaintsList.isSelectionEmpty());
		
		complaintInfoPane.setText(c == null ? "" : 
			"<span style='font-size: 18px;font-weight: 700;'>" + c.getType() + "</span><br/>" + 
			"<span style='font-size: 14px;font-weight: 400;'>" + c.getLocation() + "</span><br/><br/>" + 
			"<span style='font-size: 12px;'>" + c.getDescription() + "</span><br/><br/>" + 
			"<span style='font-size: 12px;'>Posted by " + c.getName() + "</span><br/><br/>" +
			(c.getResolved().equals("true") ? "<span style='font-size: 12px;'>Complaint has been resolved</span>" : "")
		);
	}

	public void updateComplaintsList() {
		complaints = new ArrayList<Complaint>();
		
		String complaintsNum = "?count=" + numComplaintsShown;
		String showUnresolved = (showUnresolvedCheckBox.isSelected() ? "&resolved=false" : "");		
		
		try {
			complaints = controller.receiveComplaintList(larsAddress + complaintsNum + showUnresolved);
		} catch (IOException | ParseException e) {
			JOptionPane.showMessageDialog(this, "Error connecting to server", "Connection Error",
					JOptionPane.ERROR_MESSAGE);
		}

		DefaultListModel<Complaint> model = new DefaultListModel<Complaint>();

		try {
			model.addElement(new Complaint("Noise","Here",controller.getLocalIPAddress(),"Something","Matt"));
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Complaint c : complaints) {
			model.addElement(c);
		}

		complaintsList.setModel(model);

		updateComplaintInfoPane();
	}

}