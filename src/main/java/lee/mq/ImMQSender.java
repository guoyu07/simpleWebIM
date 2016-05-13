package lee.mq;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class ImMQSender {
	
	private static ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.252.129:61616");
	
	public static void send(Long userId,String message){
		Connection connection = null;
		try {
			connection = factory.createConnection();
			connection.start();
			Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
			Destination dest = new ActiveMQQueue("test");
			MessageProducer producer = session.createProducer(dest);
			TextMessage textMessage = session.createTextMessage(message);
			textMessage.setLongProperty("userId",userId);
			producer.send(textMessage);
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			try {
				if(connection!=null){
					connection.close();
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
	
}
