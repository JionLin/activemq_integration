package activemq.persistence;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @author johnny
 * @Classname Consumer 消费者
 * @Description
 * @Date 2022/4/28 16:19
 */
public class ConsumerQueue {

    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    public static final String QUEUE_NAME = "queue-persistence";

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
        // 进行接收 处于同步阻塞的情况
//        while (true) {
//            TextMessage textMessage = (TextMessage) messageConsumer.receive(4000L);
//            if (textMessage != null) {
//                System.out.println("消费者的消息为:\t" + textMessage.getText());
//            } else {
//                break;
//            }
//        }
        // 另外一种方式,异步的方式进行 通过监听的方式进行 当有消息发送来的时候,进行自动监听消息
        messageConsumer.setMessageListener(
                (message)->{
                    if (message!=null && message instanceof TextMessage){
                        TextMessage textMessage= (TextMessage) message;
                        try {
                            System.out.println("获取的消息为:"+textMessage.getText());
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
