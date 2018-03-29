package client.frontend;

import javax.swing.JDialog;

public class MessagesFrame extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private Client parent;

	public MessagesFrame(Client parent) {
		super(parent, "Messages", true);
		this.parent = parent;
		this.setSize(350, 330);
		
		setVisible(true);
	}
}
