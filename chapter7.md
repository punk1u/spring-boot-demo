# 集成Redis缓存
    Redis是一个基于内存的单线程高性能k-v型数据库。与Memcached相比，Redis支持丰富的数据类型，包括string（字符串）、list（链表）、set（集合）、zset（sorted set有序集合）和hash（哈希类型）。
    
## Redis基本的增删改查
### 字符串类型的增删改查：
    增加一个值key为name，value为punklu：
        set name 'punklu'
    OK
    查询name的值：
        get name
    "punklu"
    更新name的值为punk
        set name 'punk'
    删除name的值：
        del name
    (integer) 1
    查询是否存在name，0代表不存在：
        exists name
    (integer) 0
### List集合的增删改查:
    添加key为user_list,value为‘punklu’,'punk'的list集合
        lpush user_list 'punklu' 'punk'
    (integer) 2
    查询key为user_list的集合
        lrange user_list 0 -1
    1) "punklu"
    2) "punk"
    往list尾部添加love元素
        rpush user_list 'love'
    (integer) 3
    往list头部添加hope元素
        lpush user_list 'hope'
    (integer) 4
    查询key为user_list的集合
        lrange user_list 0 -1
    1) "hope"
    2) "punklu"
    3) "punk"
    4) "love"
    更新index为0的值
        lset user_list 0 ‘wish’
    OK
    查询key为user_list的集合
        lrange user_list 0 -1
    1) "wish"
    2) "punklu"
    3) "punk"
    4) "love"
    删除index为0的值
        lrem user_list 0 'wish'
    (integer) 1
    查询key为user_list的集合
        lrange user_list 0 -1
    1) "punklu"
    2) "punk"
    3) "love"

### Set集合的增删改查
    添加key为user_set,value为"punklu","punk","love"的集合
        sadd user_set "punklu" "punk" "love"
    (integer) 3
    查询key为user_set的集合
        smembers user_set
    1) "punklu"
    2) "punk"
    3) "love"
    删除value为love，返回1表示删除成功，0表示失败
        srem user_set 'love'
    (integer) 1
    添加love 元素，set集合是没有顺序的，所以无法判断添加到哪个位置
        sadd user_set 'love'
    (integer) 1
    再次添加love元素，set集合里已经存在，返回0代表添加不成功，但是不会报错
        sadd user_set 'love'
    (integer) 0

### Hash集合的增删改查
    清除数据库
        flushdb
    OK
    创建Hash，key为user_hset,字段为user1，值为punklu
        hset user_hset "user1" "punklu"
    (integer) 1
    往key为user_hset的哈希集合添加字段为user2，值为"punk"
        hset user_hset "user2" "punk"
    (integer) 1
    查询user_hset字段长度
        hlen user_hset
    (integer) 2
    查询user_hset所有字段
        hkeys user_hset
    1) "user1"
    2) "user2"
    查询user_hset所有值
        hvals user_hset
    1) "punklu"
    2) "punk"
    查询字段user1的值
        hget user_hset "user1"
    "punklu"
    获取key为user_hset的哈希集合的所有字段和值
        hgetall user_hset
    1) "user1"
    2) "punklu"
    3) "user2"
    4) "punk"
    删除字段user1和值
        hdel user_hset user1
    (integer) 1
    获取key为user_hset的哈希集合的所有字段和值
        hgetall user_hset
    1) "user2"
    2) "punk"
    
### SortedSet集合的增删改查
    清除数据库
        flushdb
    OK
    为SortedSet集合添加punklu元素，分数为1
        zadd user_zset 1 "punklu"
    (integer) 1
    为SortedSet集合添加punk元素，分数为2
        zadd user_zset 2 "punk"
    (integer) 1
    为SortedSet集合添加love元素，分数为3
        zadd user_zset 3 "love"
    (integer) 1
    按照分数由小到大查询user_zset集合的元素
        zrange user_zset 0 -1
    1) "punklu"
    2) "punk"
    3) "love"
    按照分数由大到小查询user_zset集合的元素
        zrevrange user_zset 0 -1
    1) "love"
    2) "punk"
    3) "punklu"
    查询元素punklu的分数值
        zscore user_zset "punklu"
    "1"
    查询元素love的分数值
        zscore user_zset "love"
    "3"
    
Redis默认有16个数据库，客户端与Redis建立连接后会自动选择0号数据库。