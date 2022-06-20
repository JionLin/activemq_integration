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
public class JmsConsumerTopic {


    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    public static final String TOPIC_NAME = "johnny";

    public static void main(String[] args) throws Exception {
        System.out.println("消费者1号");
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);

        MessageConsumer messageConsumer = session.createConsumer(topic);
        messageConsumer.setMessageListener(
                message -> {
                    if (message != null && message instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) message;
                        try {
                            System.out.println("property  topic1\t" + textMessage.getStringProperty("topic1"));
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                        try {
                            System.out.println("获取的topic主题为: " + textMessage.getText());
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                    if (message != null && message instanceof MapMessage) {
                        MapMessage mapMessage = (MapMessage) message;
                        try {
                            System.out.println("获取的topic主题为: " + mapMessage.getString("k1"));
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );


    }
}
