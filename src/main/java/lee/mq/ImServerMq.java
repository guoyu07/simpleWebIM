package lee.mq;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class ImServerMq {
	public static void main(String[] args) throws JMSException {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.252.129:61616");
		Connection connection = factory.createConnection();
		connection.start();
		Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
		Destination dest = new ActiveMQQueue("test");
		MessageProducer producer = session.createProducer(dest);
		TextMessage message1 = session.createTextMessage("测试啊");
		message1.setIntProperty("id",1);
		producer.send(message1);
		TextMessage message2 = session.createTextMessage("测试啊啊");
		message2.setIntProperty("id",2);
		producer.send(message2);
		connection.close();
	}
}
