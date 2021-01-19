package tech.punklu.myspringboot.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.punklu.myspringboot.demo.model.User;

import java.util.Collection;
import java.util.List;

/**
 * 用户Repository
 */
public interface UserRepository extends JpaRepository<User, String> {


    /**
     * 通过名字查询，参数为name
     *
     * @param name
     * @return
     */
    List<User> findByName(String name);


    /**
     * 通过名字like查询，参数为name
     *
     * @param name
     * @return
     */
    List<User> findByNameLike(String name);


    /**
     * 通过主键id集合查询，参数为id集合
     *
     * @param ids
     * @return
     */
    List<User> findByIdIn(Collection<String> ids);
}
