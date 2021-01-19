#  Spring事务

### 事务四大特性（ACID）：
    原子性（Atomicity）、一致性（Consistency）、隔离性（Isolation）和持久性（Durability）
    
### Spring注解事务行为
    当事务方法被另一个事务方法调用时，必须指定事务应该如何传播。例如，方法可能继续在现有事务中运行，也可能开启一个事务，并在自己的事务中运行。事务的传播行为可以在@Transactional的propagation属性中指定：

    PROPAGATION_REQUIRED:如果当前没有事务，就新建一个事务；如果已经存在一个事务，就加入这个事务中
    PROPAGATION_SUPPORTS:支持当前事务，如果当前没有事务，就以非事务方式执行
    PROPAGATION_MANDATORY:使用当前的事务，如果当前没有事务，就抛出异常
    PROPAGATION_REQUIRES_NEW:新建事务，如果当前存在事务，就把当前事务挂起
    PROPAGATION_NOT_SUPPORTED:以非事务方式执行操作，如果当前存在事务，就把当前事务挂起
    PROPAGATION_NEVER:以非事务方式执行，如果当前存在事务，就抛出异常
    PROPAGATION_NESTED:如果当前存在事务，就在嵌套事务内执行；如果当前没有事务，就执行与PROPAGATION_REQUIRED类似的操作
    
### 隔离级别
    并发事务所导致的问题可以分为以下三类:
    1、脏读（Dirty Read）
        脏读发生在一个事务读取了另一个事务改写但尚未提交的数据。如果改写在稍后被回滚了，那么第一个事务获取的数据就是无效的
    2、不可重复读（Nonrepeatable Read）
        不可重复读发生在一个事务执行相同的查询两次或两次以上，但是每次都得到不同的数据时。通常是因为另一个并发事务在两次查询期间更新了数据
    3、幻读（Phantom Read）
        幻读与不可重复读类似，发生在一个事务读取了几行数据，接着另一个并发事务插入了一些数据时。在随后的查询中，第一个事务就会发现多了一些原本不存在的记录
    
    针对这些问题，Spring提供了5种事务的隔离级别：
    1、ISOLATION_DEFAULT
        使用数据库默认的事务隔离级别，另外4个与JDBC的隔离级别相对应
    2、ISOLATION_READ_UNCOMMITTED
        事务最低的隔离级别，允许另一个事务看到这个事务未提交的数据。这种隔离级别会产生脏读、不可重复读和幻读
    3、ISOLATION_READ_COMMITTED
        使用当前的事务，如果当前没有事务，就抛出异常
    4、ISOLATION_REPEATABLE_READ
        新建事务，如果当前存在事务，就把当前事务挂起
    5、ISOLATION_SERIALIZABLE
        以非事务方式执行操作，如果当前存在事务，就把当前事务挂起
    
    @Transactional还可以通过isolation属性定义隔离级别，值为上面的五种。还可以通过timeout属性设置事务过期时间，通过readOnly指定当前事务是否是只读事务，通过RollbackFor(noRollbackFor)指定
    哪个或者哪些异常可以引起（或不引起）事务回滚。
    
    
    Spring Boot开启事务很简单，只需要一个注解@Transactional就可以了，因为在Spring Boot中已经对常用的ORM框架默认开启了事务。
    Spring Boot用于配置事务的类为TransactionalAutoConfiguration，此配置类依赖于JtaAutoConfiguration和DataSourceTransactionManagerAutoConfiguration，而DataSourceTransactionManagerAutoConfiguration
    开启了对声明式事务的支持，所以在Spring Boot中，无须显示开启使用@EnableTransactionManagement。
    
    如果类级别和方法级别同时使用了@Transactional注解，就使用方法级别注解覆盖类级别注解。
