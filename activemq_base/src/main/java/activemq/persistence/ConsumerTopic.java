package activemq.persistence;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author johnny
 * @Classname ConsumerTopic
 * @Description 消费者topic
 * @Date 2022/4/28 16:56
 */
public class ConsumerTopic {
    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    public static final String TOPIC_NAME = "topic-persistent";

    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.setClientID("z1");


        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);


        // 创建持久型订阅消息
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "z1");
        // 发布订阅
        connection.start();

        // 进行接收
        Message message = topicSubscriber.receive();
        while (message != null) {
            TextMessage textMessage= (TextMessage) message;
            System.out.println("收到持久化 topic： "+textMessage.getText());
            message=topicSubscriber.receive();
        }

        session.close();
        connection.close();

    }
}
