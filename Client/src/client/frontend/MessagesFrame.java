package client.frontend;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MessagesFrame extends JDialog implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	private Client parent;
	private JTextArea messagesPane;
	private JButton clearMessagesButton;

	public MessagesFrame(Client parent) {
		super(parent, "Messages", true);
		this.parent = parent;
		setSize(350,330);
		
		this.setLayout(new BorderLayout());
		
		messagesPane = new JTextArea();
		messagesPane.setSize(350, 330);
		messagesPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		messagesPane.setEditable(false);
		try {
			messagesPane.setText(parent.getController().retrieveMessagesFromFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		messagesPane.setCaretPosition(0);
		messagesPane.setLineWrap(true);	
		
		clearMessagesButton = new JButton("Clear messages");
		clearMessagesButton.addActionListener(this);
		
		JScrollPane scroller = new JScrollPane(messagesPane);
		getContentPane().add(scroller,BorderLayout.CENTER);
		getContentPane().add(clearMessagesButton, BorderLayout.SOUTH);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == clearMessagesButton) {
			try {
				parent.getController().clearMessages();
				messagesPane.setText(parent.getController().retrieveMessagesFromFile());
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(parent, "Error clearing messages", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
