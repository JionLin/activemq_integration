package activemq.advanced_features;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;

import javax.jms.*;
import java.util.UUID;

/**
 * @author johnny
 * @Classname AsyncSends 高级特性之异步投递 使用异步投递的方式进行
 * 1。异步投递是:activemq默认使用异步方式进行消息的发送消息,除非是指定同步方式或者未使用事务的情况下,发送持久化消息,
 * 异步投递使用的场景是 在数据量大的情况下,允许消息丢失的情况下进行参数设置,提升produce的性能,但是会对client端内存造成大的压力以及broken的消耗
 * @Description
 * @Date 2022/4/29 11:10
 */

public class AsyncSendsProducer {

    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    public static final String QUEUE_NAME = "queue-async_sends";

    public static void main(String[] args) throws JMSException {
        // 创建连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 开启异步投递 设置为true
        activeMQConnectionFactory.setUseAsyncSend(true);
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
//        MessageProducer messageProducer = session.createProducer(queue);
        ActiveMQMessageProducer messageProducer = (ActiveMQMessageProducer) session.createProducer(queue);

        for (int i = 1; i <= 3; i++) {
            TextMessage textMessage = session.createTextMessage("msg--" + i);
            textMessage.setJMSMessageID(UUID.randomUUID().toString());
            String messageID = textMessage.getJMSMessageID();

            // 使用回调消息进行
            messageProducer.send(textMessage, new AsyncCallback() {
                @Override
                public void onSuccess() {
                    System.out.println(messageID + "发送成功");
                }

                @Override
                public void onException(JMSException exception) {
                    System.out.println(messageID + "发送失败");
                }
            });
        }
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("消息发送mq完成");


    }
}
