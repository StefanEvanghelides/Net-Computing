package client.frontend;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class AddComplaintFrame extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JButton sendButton;
	private JTextField type, location, name;
	private JTextArea description;
	private JLabel typeLabel, locationLabel, nameLabel, descriptionLabel;
	private JPanel inputsPane, buttonPane;
	private JScrollPane scroller;
	private Client parent;
	private SpringLayout inputsPaneLayout;

	public AddComplaintFrame(Client parent) {
		super(parent, "Add Complaint", true);
		this.parent = parent;
		this.setSize(350, 330);

		inputsPane = new JPanel();
		inputsPaneLayout = new SpringLayout();
		inputsPane.setLayout(inputsPaneLayout);
		type = new JTextField(20);
		typeLabel = new JLabel("Type");
		location = new JTextField(20);
		locationLabel = new JLabel("Location");
		name = new JTextField(20);
		nameLabel = new JLabel("Name");
		description = new JTextArea(10, 20);
		descriptionLabel = new JLabel("Description");
		description.setLineWrap(true);
		scroller = new JScrollPane(description);
		inputsPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		inputsPane.add(typeLabel);
		inputsPane.add(type);
		inputsPane.add(locationLabel);
		inputsPane.add(location);
		inputsPane.add(nameLabel);
		inputsPane.add(name);
		inputsPane.add(descriptionLabel);
		inputsPane.add(scroller);

		buttonPane = new JPanel();
		sendButton = new JButton("Send");
		sendButton.addActionListener(this);
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPane.add(sendButton);

		initialiseConstraints();

		getContentPane().add(inputsPane, BorderLayout.CENTER);
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == sendButton) {
			try {
				String IPAddress = parent.getController().getLocalIPAddress();
				parent.getController().sendComplaint(type.getText(), 
						description.getText(), IPAddress,
						location.getText(), name.getText());
			} catch (IOException | TimeoutException e) {
				JOptionPane.showMessageDialog(this, "Error connecting to server", "Connection Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			parent.updateComplaintsList();
			dispose();
		}
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
}
