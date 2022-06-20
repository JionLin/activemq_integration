package activemq.advanced_features;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;

import javax.jms.*;
import java.io.IOException;

/**
 * @author johnny
 * @Classname DLQConsumer
 * @Description 死信队列
 * 默认间隔1s的时间,进行重新发送6次,如果发送失败,告诉mq这个是有毒消息, 就会进入死信队列,形成有毒消息,告诉broken 不用在发了。
 * 可以进行设置次数为3次,
 * @Date 2022/4/30 10:25
 */
public class DLQConsumer {

    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    public static final String QUEUE_NAME = "queue-async_sends";


    public static void main(String[] args) throws JMSException {
        // 创建连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        RedeliveryPolicy  redeliveryPolicy = new RedeliveryPolicy();
        redeliveryPolicy.setMaximumRedeliveries(3);
        //  当你第四次消费消息的时候就会将消息加入死信队列
        activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);

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
