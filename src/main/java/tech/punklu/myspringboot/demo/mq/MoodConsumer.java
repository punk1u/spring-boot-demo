package tech.punklu.myspringboot.demo.mq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import tech.punklu.myspringboot.demo.model.Mood;
import tech.punklu.myspringboot.demo.service.MoodService;

import javax.annotation.Resource;

/**
 * 消费者
 */
@Component
public class MoodConsumer {

    @Resource
    private MoodService moodService;


    /**
     * @param text
     * @JmsdListener：使用JmsListener配置消费者监听的队列queue，其中，text是接收到的消息, 每当向名为queue的队列中放入一条消息的时候，都将在此处将添加的内容打印出来
     */
    @JmsListener(destination = "queue")
    public void receiveQueue(String text) {
        System.out.println("用户发表说说【" + text + "】成功");
    }

    @JmsListener(destination = "queue.asyn.save")
    public void receiveQueue(Mood mood) {
        moodService.save(mood);
    }
}
