# 集成Spring Data JPA
    JPA(Java Persistence API)是SUN官方提出的Java持久化规范。所谓规范，即只定义标准规则，不提供实现。
    
    Spring Data JPA是Spring Data的一个子项目，通过提供基于JPA的Repository极大地减少了JPA作为数据访问方案的代码量。
    
    Spring Data JPA最顶层的接口是Repository，该接口是Repository类的父类。具体代码：
    package org.springframework.data.repository;
    import java.io.Serializable;
    public interface Repository<T,ID extends Serializable>{
        
    }
    
    Repository类下没有任何接口，只是一个空类。Repository接口的子类有CrudRepository、PagingAndSortingRepository、JpaRepositury等。
    其中，CrudRepository类提供了基本的增删改查等接口，PagingAndSortingRepository类提供了基本的分页和排序等接口。而JpaRepositury是
    CrudRepository和PagingAndSortingRepository的子类，继承了它们的所有接口。所以在真实的项目中，都是通过实现JpaRepositury或者其子类进行
    基本的数据库操作。JpaRepositury的具体代码:
    @NoRepositoryBean
    public interface JpaRepositury extends PagingAndSortingRepository<T,ID>{
        List<T> findAll();
        List<T> findAll(Sort var1);
        List<T> findAll(Iterable<ID> var1);
        <S extends T> List<S> save(Iterable<S> var1);
        void flush();
        <S extends T> S saveAndFlush(S var1);
        void deleteInBatch(Iterable<T> var1);
        void deleteAllInBatch();
        T getOne(ID var1);
        <S extends T> List<S> findAll(Example<S> var1);
        <S extends T> List<S> findAll(Example<S> var1,Sort var2);
    }
    
    @NoRepositoryBean：使用该注解标明此接口不是一个Repository Bean
    
    
    @Service:Spring Boot会自动扫描到@Component注解的类，并把这些类纳入Spring容器中管理。也可以用@Component注解，只是@Service注解更能
        表明该类是服务类。
    @Component：泛指组件，当组件不好归类的时候，可以使用这个注解进行标注。
    @Repository：持久层组件，用于标注数据访问组件，即DAO组件
    @Resource：这个注解属于J2EE，默认按名称进行装配，名称可以通过name属性进行指定。如果没有指定name属性，当注解写在字段上时，就默认取字段名进行
        查找。如果注解写在setter方法上，就默认取属性名进行装配。当找不到与名称匹配的bean时，才按照类型进行装配。但需要注意的是，name属性一旦指定，
        就只会按照名称进行装配。
    @Autowired：这个注解属于Spring，默认按类型装配。默认情况下，要求依赖对象必须存在，如果要允许null值，那么可以设置required属性为false。
        如果想使用名称装配，可以结合@Qualifier注解使用。