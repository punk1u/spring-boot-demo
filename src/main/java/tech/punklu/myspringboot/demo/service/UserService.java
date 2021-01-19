package tech.punklu.myspringboot.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tech.punklu.myspringboot.demo.model.User;

import java.util.Collection;
import java.util.List;

/**
 * 用户服务层接口
 */
public interface UserService {

    User findById(String id);

    List<User> findAll();

    User save(User user);

    void delete(String id);

    // 分页
    // Pageable是一个分页接口，查询时只需要传入一个Pageable接口的实现类，指定pageNumber和pageSize即可
    Page<User> findAll(Pageable pageable);

    List<User> findByName(String name);

    List<User> findByNameLike(String name);

    List<User> findByIdIn(Collection<String> ids);

    /**
     * 根据用户名和密码查找
     *
     * @param name
     * @param password
     * @return
     */
    User findByNameAndPassword(String name, String password);

    User findByNameAndPasswordRetry(String name, String password);
}
