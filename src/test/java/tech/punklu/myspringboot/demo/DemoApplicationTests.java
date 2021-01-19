package tech.punklu.myspringboot.demo;

import com.sun.tools.javac.util.Assert;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;
import tech.punklu.myspringboot.demo.model.Mood;
import tech.punklu.myspringboot.demo.model.User;
import tech.punklu.myspringboot.demo.mq.MoodProducer;
import tech.punklu.myspringboot.demo.service.MoodService;
import tech.punklu.myspringboot.demo.service.UserService;

import javax.annotation.Resource;
import javax.jms.Destination;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private UserService userService;

    @Resource
    private MoodService moodService;


    /**
     * RedisTemplate和StringRedisTemplate都是Spring Data Redis提供的模板类，用来操作数据。
     * 其中StringRedisTemplate只针对键值是字符串的数据进行操作。在启动应用的时候，Spring会初始化这两个模板类，通过@Resource
     * 注解注入即可使用。
     * <p>
     * RedisTemplate和StringTemplate除了提供opsForValue方法用来操作简单属性数据之外，还提供了以下数据访问方法：
     * 1）opsForList：操作含有list的数据
     * 2）opsForSet：操作含有Set的数据
     * 3）opsForZSet：操作含有ZSet（有序set）的数据
     * 4）opsForHash：操作含有hash的数据
     * <p>
     * 当数据存放到Redis的时候，key和value都是通过Spring提供的Serializer序列化到数据库的。RedisTemplate默认使用
     * JdkSerializationRedisSerializer，而StringRedisTemplate默认使用StringRedisSerializer
     */
    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private MoodProducer moodProducer;

    @Test
    public void mysqlTest() {
        String sql = "select id,name,password from user";
        List<User> users = (List<User>) jdbcTemplate.query(sql, new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setId(resultSet.getString("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        });

        System.out.println("查询成功:");
        for (User user : users) {
            System.out.println("[id]:" + user.getId() + ";[name]:" + user.getName());
        }
    }

    @Test
    public void testRepository() {
        // 查询所有数据
        List<User> userList = userService.findAll();
        System.out.println("findAll():" + userList.size());
        // 通过name查询数据
        List<User> userList2 = userService.findByName("用户1");
        System.out.println("findByName():" + userList2.size());
        // 通过name模糊查询数据
        List<String> ids = new ArrayList<String>();
        ids.add("1");
        ids.add("2");
        List<User> userList3 = userService.findByIdIn(ids);
        System.out.println("findByIdIn():" + userList3.size());
        // 分页查询数据
        PageRequest pageRequest = new PageRequest(0, 10);
        Page<User> userList4 = userService.findAll(pageRequest);
        System.out.println("page findAll():" + userList4.getTotalPages() + "/" + userList4.getSize());

        // 新增数据
        User user = new User();
        user.setId("3");
        user.setName("test");
        user.setPassword("123");
        userService.save(user);
        // 删除数据
        userService.delete("3");
    }

    /**
     * Redis测试
     */
    @Test
    public void testRedis() {
        // 增 key：name，value：punklu
        redisTemplate.opsForValue().set("name", "punklu");
        // 根据key获取
        String name = (String) redisTemplate.opsForValue().get("name");
        System.out.println(name);
        // 删除
        redisTemplate.delete("name");
        // 更新
        redisTemplate.opsForValue().set("name", "punk");
        // 查询
        name = stringRedisTemplate.opsForValue().get("name");
        System.out.println(name);

    }


    @Test
    public void testFindById() {
        Long redisUserSize = 0L;
        // 查询id=1的数据，该数据存在于Redis缓存中
        User user = userService.findById("1");
        redisUserSize = redisTemplate.opsForList().size("ALL_USER_LIST");
        System.out.println("目前缓存中的用户数量为：" + redisUserSize);
        System.out.println("---->>>id:" + user.getId() + "name:" + user.getName());
        // 查询id为2的数据，该数据存在于Redis缓存中
        User user1 = userService.findById("2");
        redisUserSize = redisTemplate.opsForList().size("ALL_USER_LIST");
        System.out.println("目前缓存中的用户数量为：" + redisUserSize);
        System.out.println("---->>>id:" + user1.getId() + "name:" + user1.getName());
        // 查询id=4的数据，不存在于Redis缓存中，存在于数据库中，所以会把在数据库中查询到的数据更新到缓存中
        User user2 = userService.findById("4");
        System.out.println("---->id: " + user2.getId() + "name:" + user2.getName());
        redisUserSize = redisTemplate.opsForList().size("ALL_USER_LIST");
        System.out.println("目前缓存中的用户数量为： " + redisUserSize);
    }

    @Test
    public void testMyBatis() {
        User user = userService.findByNameAndPassword("punk1", "123456");
        System.out.println(user.getName());
    }

    @Test
    public void testMood() {
        Mood mood = new Mood();
        mood.setId("1");
        mood.setUserId("1");
        mood.setPraiseNum(0);
        mood.setContent("这是第一条说说！");
        mood.setPublishTime(new Date());
        Mood result = moodService.save(mood);
    }

    /**
     * 测试activeMq
     */
    @Test
    public void testActiveMq() {
        Destination destination = new ActiveMQQueue("queue");
        moodProducer.sendMessage(destination, "hello,mq!!!");
    }

    @Test
    public void testActiveMQAsynSave() {
        Mood mood = new Mood();
        mood.setId("2");
        mood.setUserId("2");
        mood.setPraiseNum(0);
        mood.setContent("这是第一条说说！！！");
        mood.setPublishTime(new Date());
        String msg = moodService.asynSave(mood);
        System.out.println("异步发表说说:" + msg);
    }


}
