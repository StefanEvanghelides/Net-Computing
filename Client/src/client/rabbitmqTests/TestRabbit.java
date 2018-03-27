package client.rabbitmqTests;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class TestRabbit {
	
	private final String QUEUE_NAME = "testQueue";
	private final String EXCHANGE_NAME = "testExchange";
	private final String localhost = "localhost";
	private final String larsHost = "172.20.10.8";
	private final String myHost = "172.20.10.10";
	private final String larsQueue = "incomming";
	
	private final String message = "IT WORKS MOFO!!";
	
	public TestRabbit() throws IOException, TimeoutException {
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost(larsHost);
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();
	    channel.queueDeclare(larsQueue, false, false, false, null);
	    channel.basicPublish("", larsQueue, null, message.getBytes("UTF-8"));
	    System.out.println(" [x] Sent '" + message + "'");

	    channel.close();
	    connection.close();
	}

	/* Main function. */
	public static void main(String[] args) throws IOException, TimeoutException {
		TestRabbit A = new TestRabbit();
		
	}
}
