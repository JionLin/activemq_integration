package activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author johnny
 * @Classname JmsProduce
 * @Description  jms 生产者
 * @Date 2022/4/16 22:00
 */
public class JmsProduce {
    // 和jdbc 一样,
    // 1。先创建工厂,
    // 2。在创建工厂连接,
    // 3。 创建session 创建目的地,队列或者主题   队列是一对一的模式 主题是一对多的模式
    // 4。 根据session创建producer,producers 发送消息
    // 5。 关闭连接
    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    public static final String QUEUE_NAME="johnny";

    public static void main(String[] args) throws JMSException {
        // 创建连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 创建连接
        Connection connection = activeMQConnectionFactory.createConnection();
        // 启动
        connection.start();
        // 创建session 自动补齐快捷键 shift+command+enter

        // 第一个事务  第二个签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建目的地 队列
        Queue queue = session.createQueue(QUEUE_NAME);
        // 创建消息的生产者
        MessageProducer messageProducer = session.createProducer(queue);

        // 持久化消息和非持久化消息   队列默认为持久化消息
//        messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        for (int i = 1; i <=3 ; i++) {
            TextMessage textMessage = session.createTextMessage("msg--" + i);
            messageProducer.send(textMessage);
        }
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("消息发送mq完成");


    }
}
