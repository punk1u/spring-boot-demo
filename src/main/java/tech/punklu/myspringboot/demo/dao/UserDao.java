package tech.punklu.myspringboot.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tech.punklu.myspringboot.demo.model.User;

/**
 * @Mapper注解：重要注解，MyBatis根据接口定义与Mapper文件中的SQL语句动态创建接口实现
 * @Param：注解参数
 */
@Mapper
public interface UserDao {

    /**
     * 通过用户名和密码查询用户
     *
     * @param name
     * @param password
     * @return
     */
    User findByNameAndPassword(@Param("name") String name, @Param("password") String password);
}
