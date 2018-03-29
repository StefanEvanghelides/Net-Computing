package client.frontend;

import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MessagesFrame extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private JTextArea messagesPane;

	public MessagesFrame(Client parent) {
		super(parent, "Messages", true);
		setSize(350,330);
		
		messagesPane = new JTextArea();
		messagesPane.setSize(350, 330);
		messagesPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		messagesPane.setEditable(false);
		try {
			messagesPane.setText(parent.getController().readMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		messagesPane.setCaretPosition(0);
		messagesPane.setLineWrap(true);		
		
		JScrollPane scroller = new JScrollPane(messagesPane);
		getContentPane().add(scroller);
		
		//pack();
		setVisible(true);
	}
}
