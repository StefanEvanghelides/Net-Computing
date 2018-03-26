package client.frontend;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class AddComplaintFrame extends JDialog implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	private JButton sendButton;
	private JTextField type, location, name;
	private JLabel typeLabel, locationLabel, nameLabel, descriptionLabel;
	private JTextArea description;
	private Client parent;
	private SpringLayout inputsPaneLayout;
	
	private JPanel inputsPane, buttonPane;
	private JScrollPane scroller;

	public AddComplaintFrame(Client parent) {
		super(parent, "Add Complaint", true);
		this.parent = parent;
		this.setSize(350, 330);
		
		inputsPane = new JPanel();
		inputsPaneLayout = new SpringLayout();
		inputsPane.setLayout(inputsPaneLayout);
		this.type = new JTextField(20);
		this.typeLabel = new JLabel("Type");		
		this.location = new JTextField(20);
		this.locationLabel = new JLabel("Location");
		this.name = new JTextField(20);
		this.nameLabel = new JLabel("Name");		
		this.description = new JTextArea(10,20);
		this.descriptionLabel = new JLabel("Description");
		this.description.setLineWrap(true);
		scroller = new JScrollPane(this.description);
		inputsPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		inputsPane.add(typeLabel);
		inputsPane.add(type);
		inputsPane.add(locationLabel);
		inputsPane.add(location);
		inputsPane.add(nameLabel);
		inputsPane.add(name);
		inputsPane.add(descriptionLabel);
		inputsPane.add(scroller);
		
		buttonPane = new JPanel();
		this.sendButton = new JButton("Send");
		this.sendButton.addActionListener(this);
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
		buttonPane.add(this.sendButton);
		
		initialiseConstraints();
		
		this.getContentPane().add(inputsPane, BorderLayout.CENTER);
		this.getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		this.setVisible(true);	
	}

	private void initialiseConstraints() {
		inputsPaneLayout.putConstraint(SpringLayout.NORTH, typeLabel, 10, SpringLayout.NORTH, inputsPane);
		inputsPaneLayout.putConstraint(SpringLayout.WEST, typeLabel, 10, SpringLayout.WEST, inputsPane);
		inputsPaneLayout.putConstraint(SpringLayout.NORTH, locationLabel, 10, SpringLayout.SOUTH, typeLabel);
		inputsPaneLayout.putConstraint(SpringLayout.WEST, locationLabel, 0, SpringLayout.WEST, typeLabel);
		inputsPaneLayout.putConstraint(SpringLayout.NORTH, nameLabel, 10, SpringLayout.SOUTH, locationLabel);
		inputsPaneLayout.putConstraint(SpringLayout.WEST, nameLabel, 0, SpringLayout.WEST, locationLabel);
		inputsPaneLayout.putConstraint(SpringLayout.NORTH, descriptionLabel, 10, SpringLayout.SOUTH, nameLabel);
		inputsPaneLayout.putConstraint(SpringLayout.WEST, descriptionLabel, 0, SpringLayout.WEST, nameLabel);
		
		inputsPaneLayout.putConstraint(SpringLayout.NORTH, type, 0, SpringLayout.NORTH, typeLabel);
		inputsPaneLayout.putConstraint(SpringLayout.WEST, type, 10, SpringLayout.EAST, descriptionLabel);
		inputsPaneLayout.putConstraint(SpringLayout.NORTH, location, 0, SpringLayout.NORTH, locationLabel);
		inputsPaneLayout.putConstraint(SpringLayout.WEST, location, 10, SpringLayout.EAST, descriptionLabel);
		inputsPaneLayout.putConstraint(SpringLayout.NORTH, name, 0, SpringLayout.NORTH, nameLabel);
		inputsPaneLayout.putConstraint(SpringLayout.WEST, name, 10, SpringLayout.EAST, descriptionLabel);
		inputsPaneLayout.putConstraint(SpringLayout.NORTH, scroller, 0, SpringLayout.NORTH, descriptionLabel);
		inputsPaneLayout.putConstraint(SpringLayout.WEST, scroller, 10, SpringLayout.EAST, descriptionLabel);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.sendButton) {
			this.parent.getController().sendComplaint();
		}
	}
}
