package client.frontend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

public class AddComplaintFrame extends JDialog implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	public JButton sendButton;
	public Client parent; 

	public AddComplaintFrame(Client parent) {
		super(parent, true);
		this.parent = parent;
		this.setSize(400, 400);	
		this.sendButton = new JButton("Send");
		this.sendButton.addActionListener(this);
		
		this.getContentPane().add(sendButton);
		
		this.pack();
		this.setVisible(true);	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == sendButton) {
			this.parent.getController().sendComplaint();
		}
	}
}
