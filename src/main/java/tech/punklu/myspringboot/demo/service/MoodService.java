package tech.punklu.myspringboot.demo.service;

import org.springframework.stereotype.Service;
import tech.punklu.myspringboot.demo.model.Mood;

/**
 * 说说服务层
 */
public interface MoodService {

    Mood save(Mood mood);

    /**
     * 异步保存接口
     *
     * @param mood
     * @return
     */
    String asynSave(Mood mood);

}
