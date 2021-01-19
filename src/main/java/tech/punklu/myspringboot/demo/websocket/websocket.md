# Spring Boot整合WebSocket
    在HTTP协议中，所有的请求都是由客户端发起的，由服务端进行响应，服务端无法向客户端推送消息，但是在一些需要即时通信的应用中，又不可避免地需要服务端向客户端推送消息，传统的解决方案有轮询和Applet、Flash等。
    但是都有其缺点，轮询太耗费资源，另外两种有安全问题。WebSocket应运而生。
    
    WebSocket是一种在单个TCP连接上进行全双工通信的协议，已被W3C定为标准。使用WebSocket可以使得客户端和服务器之间的数据交换变得更加简单，它允许服务端主动向客户端推送数据。在WebSocket协议中，浏览器
    和服务器只需要完成一次握手，两者之间就可以直接创建持久性的连接，并进行双向数据传输。
    
    WebSocket使用了HTTP/1.1的协议升级特性，一个WebSocket请求首先使用非正常的HTTP请求以特定的模式访问一个URL，这个URL有两种模式，分别是ws和wss，对应HTTP协议中的HTTP和HTTPS，在请求中有一个Connection:Upgrade字段，
    表示客户端想要对协议进行升级，另外一个是Upgrade:Websocket字段，表示客户端想要将请求协议升级为WebSocket协议。这两个字段共同告诉服务器要将连接升级为WebSocket这样一种全双工协议，如果服务端统一协议升级。那么在握手完成之后，
    文本消息或者其他二进制消息就可以同时在两个方向上发送，而不需要关闭和重建连接。此时的客户端和服务端关系是对等的，它们可以互相向对方主动发送消息。和传统的解决方案相比，WebSocket有以下特点：
    1、WebSocket使用时需要先创建连接，这使得WebSocket成为一种有状态的协议，在之后的通信过程中可以省略部分状态信息（例如身份认证等）。
    2、WebSocket连接在端口80（ws）或者443（wss）上创建，与HTTP使用的端口相同，这样，基本上，所有的防火墙都不会阻止WebSocket连接。
    3、WebSocket使用HTTP协议进行握手。因此它可以自然而然地集成到网络浏览器和HTTP服务器中，而不需要额外的成本。
    4、心跳消息（ping和pong）将被反复的发送，进而保持WebSocket连接一直处于活跃状态。
    5、使用该协议，当消息启动或者到达的时候，服务端和客户端都可以知道。
    6、WebSocket连接关闭时将发送一个特殊的关闭消息。
    7、WebSocket支持跨域，可以避免ajax的限制
    8、HTTP规范要求浏览器将并发连接数限制为每个主机名两个连接，但是当使用WebSocket的时候，当握手完成之后，该限制就不存在了，因为此时的连接已经不再是HTTP连接了
    9、WebSocket协议支持扩展，用户可以拓展协议，实现部分自定义的子协议
    10、更好的二进制支持以及更好的压缩效果。
    
    maven依赖：
    <!-- WebSocket -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-websocket</artifactId>
        </dependency>

        <dependency>
            <groupId>org.webjars</groupId>
            <artifactId>webjars-locator-core</artifactId>
        </dependency>

        <dependency>
            <groupId>org.webjars</groupId>
            <artifactId>sockjs-client</artifactId>
            <version>1.1.2</version>
        </dependency>

        <dependency>
            <groupId>org.webjars</groupId>
            <artifactId>stomp-websocket</artifactId>
            <version>2.3.3</version>
        </dependency>

        <dependency>
            <groupId>org.webjars</groupId>
            <artifactId>jquery</artifactId>
            <version>3.3.1</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    
    其中，spring-boot-starter-websocket依赖是WebSocket相关依赖，其他的都是前端库。
    
    Spring框架提供了基于WebSocket的STOMP支持，STOMP是一个简单的可互操作的协议，通常被用于通过中间服务器在客户端之间进行异步消息传递。WebSocket配置如下：
    

### 点对点消息发送
    既然是点对点发送，就应该有用户的概念，因此，首先在项目中加入Spring Security的依赖，代码如下：
    <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
