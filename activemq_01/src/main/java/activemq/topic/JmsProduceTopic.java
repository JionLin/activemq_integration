package activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.UUID;

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
public class JmsProduceTopic {

    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    public static final String TOPIC_NAME = "johnny";

    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);

        MessageProducer producer = session.createProducer(topic);

        for (int i = 1; i <= 3; i++) {
            TextMessage textMessage = session.createTextMessage("topic: " + i);
            textMessage.setStringProperty("topic1", "value1");
            producer.send(textMessage);

            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setString("k1", "v" + i);
            producer.send(mapMessage);


        }
        producer.close();
        session.close();
        connection.close();

    }
}
