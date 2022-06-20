package activemq.persistence;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author johnny
 * @Classname ProducerTopic
 * @Description 生产者topic
 * @Date 2022/4/28 16:57
 */
public class ProducerTopic {
    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    public static final String TOPIC_NAME = "topic-persistent";

    public static void main(String[] args) throws Exception {

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();


        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);

        MessageProducer producer = session.createProducer(topic);
        // 设置为持久化情况
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
