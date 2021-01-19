package tech.punklu.myspringboot.demo.mq;

import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import tech.punklu.myspringboot.demo.model.Mood;

import javax.annotation.Resource;
import javax.jms.Destination;

/**
 * 生产者
 */
@Service
public class MoodProducer {

    /**
     * JmsMessagingTemplate：发消息的工具类，也可以注入JmsTemplate，JmsMessagingTemplate对JmsTemplate进行了封装。
     * 参数destination是发送到队列的，message是待发送的消息
     */
    @Resource
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void sendMessage(Destination destination, final String message) {
        jmsMessagingTemplate.convertAndSend(destination, message);
    }

    public void sendMessage(Destination destination, final Mood mood) {
        jmsMessagingTemplate.convertAndSend(destination, mood);
    }
}
