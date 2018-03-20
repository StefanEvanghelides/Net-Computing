
public class Client {
	
	private Controller controller;
	
	public Client() {
		controller = new Controller();
	}
	

	
	/* Getters and setters. */
	public Controller getController() {
		return this.controller;
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	


	/* Main function. */
	public static void main(String[] args) {
		Client A = new Client();
		
		String result = A.getController().receiveComplaint("localhost");
		
		System.out.println("Result: " + result);
	}

}
