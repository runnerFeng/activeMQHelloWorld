package com.feng;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Desc:消息消费者
 * Created by jinx on 2017/9/21.
 */
public class JMSConsumer {
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;//默认连接用户名
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;//默认连接密码
    private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;//默认连接地址

    public static void main(String[] args) {
        ConnectionFactory connectionFactory;//连接工厂
        Connection connection;//连接
        Session session;//会话 接受或者发送消息的线程
        Destination destination;//消息的目的地
        MessageConsumer messageConsumer;//消息的消费者
        //实例化连接工厂
        connectionFactory = new ActiveMQConnectionFactory(JMSConsumer.USERNAME, JMSConsumer.PASSWORD, JMSConsumer.BROKEURL);
        //通过连接工厂获取连接
        try {
            connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建session
            //createSession(paramA,paramB)
            //paramA设置为false时：paramB的值可为Session.AUTO_ACKNOWLEDGE，Session.CLIENT_ACKNOWLEDGE，DUPS_OK_ACKNOWLEDGE其中一个。
            //paramA设置为true时：paramB的值忽略， acknowledgment mode被jms服务器设置为SESSION_TRANSACTED 。
            //此处第一个参数设置为true时，必须进行事物提交session.commit,如果为false则不需进行事物提交
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            //创建一个连接HelloWorld的消息队列
            destination = session.createQueue("HelloWorld");
            //创建消息消费者
            messageConsumer = session.createConsumer(destination);
            while (true) {
                TextMessage textMessage = (TextMessage) messageConsumer.receive(100000);
//                session.commit();
                if (null != textMessage) {
                    System.out.println("收到的消息:" + textMessage.getText());
                } else {
                    break;
                }
            }
//            session.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
