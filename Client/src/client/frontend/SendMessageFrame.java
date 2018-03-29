package client.frontend;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class SendMessageFrame extends JDialog implements ActionListener {
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
		messageBody = new JTextArea(15, 30);
		messageBody.setLineWrap(true);
		JScrollPane scroller = new JScrollPane(messageBody);
		messagePane.add(scroller);
		messagePane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		getContentPane().add(messagePane, BorderLayout.CENTER);

		JPanel buttonPane = new JPanel();
		sendButton = new JButton("Send");
		sendButton.addActionListener(this);
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPane.add(sendButton);
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		pack();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == sendButton) {
			try {
				this.parent.getController().sendMessage(this.messageBody.getText());
			} catch (IOException error) {
				JOptionPane.showMessageDialog(this, "User is offline", "Connection Error",
					JOptionPane.ERROR_MESSAGE);
			}
			this.dispose();
		}
	}
}
