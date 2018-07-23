package com.example.activemq.activemq_producer;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;


public class JMSTopicProducer {
     public static void main(String[] args) {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
		 
		Connection connection = null;
		try {
			
			 connection = connectionFactory.createConnection();
			 connection.start();
			 
			Session session= connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			//创建目的地
			Destination destination = session.createTopic("myTopic");
			//创建发送者
			MessageProducer messageProducer = session.createProducer(destination);
			messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
			 //创建需要发送的消息
			TextMessage message = session.createTextMessage("我上课时间是周三、周六、周日");
		    
			//Text Map Bytes Stream Object  消息体处理类型
//			message.setStringProperty(name, value);//消息属性
			
			messageProducer.send(message);
			
			session.commit();
			session.rollback();
			session.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}
} 
