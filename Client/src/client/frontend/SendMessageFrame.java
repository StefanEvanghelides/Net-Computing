package client.frontend;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class SendMessageFrame extends JDialog implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	public JButton sendButton;
	public JTextArea messageBody;
	public Client parent; 
	public String receive_ip;

	public SendMessageFrame(Client parent, String receive_ip) {
		super(parent, "New Message", true);
		this.parent = parent;
		this.receive_ip = receive_ip;
		
		JPanel messagePane = new JPanel();
		this.messageBody = new JTextArea(15,30);
		this.messageBody.setLineWrap(true);
		JScrollPane scroller = new JScrollPane(messageBody);
		messagePane.add(scroller);
		messagePane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		this.getContentPane().add(messagePane, BorderLayout.CENTER);
		
		JPanel buttonPane = new JPanel();
		this.sendButton = new JButton("Send");
		this.sendButton.addActionListener(this);
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
		buttonPane.add(sendButton);
		this.getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		this.pack();
		this.setVisible(true);	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == sendButton) {
			this.parent.getController().sendMessage(this.messageBody.getText());
			this.dispose();
		}
	}
}
