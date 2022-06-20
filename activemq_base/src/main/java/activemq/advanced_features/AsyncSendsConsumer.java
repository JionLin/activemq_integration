package activemq.advanced_features;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @author johnny
 * @Classname AsyncSends 高级特性之异步投递 使用异步投递的方式进行
 * 异步投递客户端消费消息
 * @Description
 * @Date 2022/4/29 11:10
 */

public class AsyncSendsConsumer {

    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    public static final String QUEUE_NAME = "queue-async_sends";


    public static void main(String[] args) throws JMSException {
        // 创建连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 创建连接
        Connection connection = activeMQConnectionFactory.createConnection();
        //开启连接
        connection.start();
        // 创建session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建目的地
        Queue queue = session.createQueue(QUEUE_NAME);
        // 创建consumer
        MessageConsumer messageConsumer = session.createConsumer(queue);
        // 另外一种方式,异步的方式进行 通过监听的方式进行 当有消息发送来的时候,进行自动监听消息
        messageConsumer.setMessageListener(
                (message) -> {
                    if (message != null && message instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) message;
                        try {
                            System.out.println("获取的消息为:" + textMessage.getText());
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                });
        try {
            // 保证控制台不灭,
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        messageConsumer.close();
        session.close();
        connection.close();
    }
}
