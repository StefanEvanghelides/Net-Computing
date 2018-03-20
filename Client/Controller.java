
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class Controller {
	private MessageAPI message;
	private ComplaintAPI complaint;
	
	public Controller() {
		message = new MessageAPI();
		complaint = new ComplaintAPI();
	}
	
	public void sendComplaint() {
		
	}
	
	public void sendMessage() {
		
	}

	public String receiveComplaint(String endpoint) {
		String result;
		try {
			result = complaint.GET(endpoint);
		} catch(Exception e) {
			e.printStackTrace();
			result = "failed";
		}

		return result;
	}
	
	
	/* Getters and setters. */
	
	public MessageAPI getMessage() {
		return this.message;
	}
	
	public void setMessage(MessageAPI message) {
		this.message = message;
	}
	
	public ComplaintAPI getComplaint() {
		return this.complaint;
	}
	
	public void setComplaint(ComplaintAPI complaint) {
		this.complaint = complaint;
	}
	
}
