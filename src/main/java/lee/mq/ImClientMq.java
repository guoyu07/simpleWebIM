package lee.mq;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class ImClientMq {
	public static void main(String[] args) throws JMSException {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.252.129:61616");
		Connection connection = factory.createConnection();
		connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination dest = new ActiveMQQueue("test");
        MessageConsumer consumer = session.createConsumer(dest);
        consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
	        	if(message instanceof TextMessage){
	        		TextMessage msg = (TextMessage)message;
	        		try {
						System.out.println(msg.getText());
					} catch (JMSException e) {
						e.printStackTrace();
					}
	        	}
			}
		});
	}
}
