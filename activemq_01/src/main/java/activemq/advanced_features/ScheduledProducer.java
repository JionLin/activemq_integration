package activemq.advanced_features;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ScheduledMessage;

import javax.jms.*;

/**
 * @author johnny
 * @Classname ScheduledProducer
 * @Description 延时和定时投递
 * @Date 2022/4/29 21:39
 */
public class ScheduledProducer {
    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    public static final String QUEUE_NAME = "queue-async_sends";

    public static void main(String[] args) throws JMSException {
        // 创建连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

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

        // 设置延时投递3s,重复投递的时间间隔 4s,重复投递的次数5次
        for (int i = 1; i <= 3; i++) {
            TextMessage textMessage = session.createTextMessage("msg--" + i);
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 3 * 1000);
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, 4 * 1000);
            textMessage.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, 5);
            // 使用回调消息进行
            messageProducer.send(textMessage);
        }
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("消息发送mq完成");


    }
}
