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
	private final String larsPhone = "172.20.10.7";
	private final String larsQueue = "incomming";
	
	private final String message = "Some message";
	
	public TestRabbit() throws IOException, TimeoutException {
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost(larsPhone);
	    //System.out.println("1");
	    Connection connection = factory.newConnection();
	    //System.out.println("2");
	    Channel channel = connection.createChannel();
	    //System.out.println("3");
	    channel.queueDeclare(larsQueue, false, false, false, null);
	    //System.out.println("4");
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
