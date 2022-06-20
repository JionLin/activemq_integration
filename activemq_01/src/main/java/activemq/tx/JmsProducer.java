package activemq.tx;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author johnny
 * @Classname JmsProducer
 * @Description 事务 偏生产者  签收偏消费者
 * @Date 2022/4/21 17:27
 */
public class JmsProducer {
    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    public static final String QUEUE_NAME = "johnny";

    public static void main(String[] args) throws JMSException {
        // 创建连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 创建连接
        Connection connection = activeMQConnectionFactory.createConnection();
        // 启动
        connection.start();
        // 创建session 自动补齐快捷键 shift+command+enter

        // 第一个事务  第二个签收
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        // 创建目的地 队列
        Queue queue = session.createQueue(QUEUE_NAME);
        // 创建消息的生产者
        MessageProducer messageProducer = session.createProducer(queue);

        try {
            // 持久化消息和非持久化消息   队列默认为持久化消息
            for (int i = 1; i <= 3; i++) {
                TextMessage textMessage = session.createTextMessage("msg--" + i);
                messageProducer.send(textMessage);
            }
            session.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            session.rollback();
            messageProducer.close();
            session.close();
            connection.close();
            System.out.println("消息发送mq完成");
        }



    }
}
