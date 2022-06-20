package activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author johnny
 * @Classname JmsProduceTopic
 * @Description 消息的发布 1：N的情况
 * 1. 创建连接工厂
 * 2。 创建连接,启动连接
 * 3。创建sesseion
 * 4。创建目的地(主题或者队列)
 * 5。创建生产者
 * 6。发送消息
 * 7。关闭连接
 * @Date 2022/4/20 12:46
 */
public class JmsProducePersisentTopic {

    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    public static final String TOPIC_NAME = "johnny";

    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();


        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);

        MessageProducer producer = session.createProducer(topic);
        // 设置为非持久化情况
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        connection.start();

        for (int i = 1; i <= 3; i++) {
            TextMessage textMessage = session.createTextMessage("topic: " + i);
            producer.send(textMessage);
        }
        producer.close();
        session.close();
        connection.close();
        System.out.println("  **** TOPIC_NAME消息发送到MQ完成 ****");
    }
}
