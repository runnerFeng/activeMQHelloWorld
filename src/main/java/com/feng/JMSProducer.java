package com.feng;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Desc:消息生产者
 * Created by jinx on 2017/9/21.
 */
public class JMSProducer {
    //默认连接用户名
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    //默认连接密码
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    //默认连接地址
    private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
    //发送的消息数量
    private static final int SENDNUM = 10;

    public static void main(String[] args) {
        ConnectionFactory connectionFactory;
        Connection connection;
        Session session;
        Destination destination;
        MessageProducer messageProducer;
        connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEURL);
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("HelloWorld");
            messageProducer = session.createProducer(destination);
            sendMessage(session, messageProducer);
            session.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(Session session, MessageProducer messageProducer) throws JMSException {
        for (int i = 0; i < JMSProducer.SENDNUM; i++) {
            TextMessage textMessage = session.createTextMessage("ActiveMQ发送消息" + i);
            System.out.println("activeMq发信消息：" + i);
            messageProducer.send(textMessage);
        }
    }
}
