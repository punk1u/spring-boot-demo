package tech.punklu.myspringboot.demo.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import tech.punklu.myspringboot.demo.dao.UserDao;
import tech.punklu.myspringboot.demo.error.BusinessException;
import tech.punklu.myspringboot.demo.model.User;
import tech.punklu.myspringboot.demo.repository.UserRepository;
import tech.punklu.myspringboot.demo.service.UserService;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 用户服务层实现类
 */
@Service
public class UserServiceImpl implements UserService {

    Logger logger = LogManager.getLogger(this.getClass());

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserDao userDao;

    private static final String ALL_USER = "ALL_USER_LIST";

    @Override
    public User findById(String id) {
        // 1、查询Redis缓存中的所有数据
        List<User> userList = redisTemplate.opsForList().range(ALL_USER, 0, -1);
        if (userList != null && userList.size() > 0) {
            for (User user : userList) {
                if (user.getId().equals(id)) {
                    return user;
                }
            }
        }
        // 2、若从Redis中查不到对应的数据，则查询数据库中的数据
        User user = userRepository.getOne(id);
        if (user != null) {
            // 3、将数据插入Redis缓存中
            redisTemplate.opsForList().leftPush(ALL_USER, user);
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(String id) {
        userRepository.deleteById(id);
        // 日志记录
        //logger.info("userId:"  + id + "用户被删除!");
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }


    @Override
    public List<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public List<User> findByNameLike(String name) {
        return userRepository.findByNameLike(name);
    }

    @Override
    public List<User> findByIdIn(Collection<String> ids) {
        return userRepository.findByIdIn(ids);
    }

    @Override
    public User findByNameAndPassword(String name, String password) {
        return userDao.findByNameAndPassword(name, password);
    }


    /**
     * @param name
     * @param password
     * @return
     * @Retryable：value属性表示当出现哪些异常的时候触发重试，maxAttempts表示最大重试次数默认为3，delay表示重试的延迟时间，multiplier表示上一次延时时间是这一次的倍数
     */
    @Override
    @Retryable(value = {BusinessException.class}, maxAttempts = 5, backoff = @Backoff(delay = 5000, multiplier = 2))
    public User findByNameAndPasswordRetry(String name, String password) {

        System.out.println("[findByNameAndPasswordRetry] 方法失败重试了！");
        throw new BusinessException();
    }
}
