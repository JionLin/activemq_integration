package activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author johnny
 * @Classname JmsConsumerTopic
 * @Description 主题的消费者
 * 1 创建连接工厂
 * 2 创建连接 开启连接
 * 3 创建session
 * 4 创建目的地
 * 5 创建consumer消费者
 * 6 进行监听消息,进行消费
 * 7 关闭连接
 * @Date 2022/4/20 12:47
 */
public class JmsConsumerPersisentTopic {


    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    public static final String TOPIC_NAME = "johnny";

    public static void main(String[] args) throws Exception {
        System.out.println("z3");
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.setClientID("z3");


        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);


        // 创建持久型订阅消息
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "z3");
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
