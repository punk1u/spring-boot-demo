package tech.punklu.myspringboot.demo.service.impl;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.stereotype.Service;
import tech.punklu.myspringboot.demo.model.Mood;
import tech.punklu.myspringboot.demo.mq.MoodProducer;
import tech.punklu.myspringboot.demo.repository.MoodRepository;
import tech.punklu.myspringboot.demo.service.MoodService;

import javax.annotation.Resource;
import javax.jms.Destination;

@Service
public class MoodServiceImpl implements MoodService {

    @Resource
    private MoodRepository moodRepository;

    @Resource
    private MoodProducer moodProducer;

    @Override
    public Mood save(Mood mood) {
        return moodRepository.save(mood);
    }

    // 队列
    private static Destination destination = new ActiveMQQueue("queue.asyn.save");

    @Override
    public String asynSave(Mood mood) {
        // 往队列queue.asyn.save推送消息，消息内容为说说实体
        moodProducer.sendMessage(destination, mood);
        return "success";
    }
}
