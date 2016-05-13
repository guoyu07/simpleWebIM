package lee.mq;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import lee.context.WSContext;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class ImMQReciver {
	
	private static ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.252.129:61616");
	
	public static void recive(){
		Connection connection = null;
		try {  
			connection = factory.createConnection();
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
		        			Long userId = msg.getLongProperty("userId");
		        			String text = msg.getText();
		        			for (javax.websocket.Session session : WSContext.getAllSession()) {
								try {
									session.getBasicRemote().sendText("用户["+userId+"]:"+text);
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						} catch (JMSException e) {
							e.printStackTrace();
						}
		        	}
				}
			});
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}
}
