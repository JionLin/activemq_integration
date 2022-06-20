package activemq.advanced_features;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @author johnny
 * @Classname ReptyConsumer
 * @Description 重试机制
 * 在什么情况下,会导致重发
 * 1。client 使用了translation,在session中调用了rollback 未实验出来 后面在看看事务和签收的问题
 * 2。client 使用了translation,在session未调用commit 或者在调用之前关闭了commit 实验出来 报错
 * 3。3：Client再CLIENT_ACKNOWLEDGE的传递模式下，session中调用了recover 实验出来了
 * 如何保证消息不被重复消费,
 * 可以考虑使用redis的情况 k-v,如果消费了,就存储唯一的id进去,下次消费的时候,去查询,如果存在,就代表已经消费了。
 * @Date 2022/4/30 09:01
 */
public class RetryConsumer {

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
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
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
//                            textMessage.acknowledge();
                            System.out.println("获取的消息为:" + textMessage.getText());

                            session.commit();
                            session.rollback();
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
