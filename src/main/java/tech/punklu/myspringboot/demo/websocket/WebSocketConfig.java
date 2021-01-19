package tech.punklu.myspringboot.demo.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


/**
 * WebSocket配置类
 * Spring框架提供了基于WebSocket的STOMP支持，STOMP是一个简单的可互操作的协议，通常被用于通过中间服务器在客户端之间进行异步消息传递。
 * WebSocketConfig继承自WebSocketMessageBrokerConfigurer进行WebSokcet配置，然后通过@EnableWebSocketMessageBroker注解开启WebSokcet消息代理。
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 表示定义一个前缀为“/chat”的endPoint，并开启sockjs支持，sockjs可以解决浏览器对WebSocket的兼容性问题，客户端将通过这里配置的URL来建立WebSocket连接
        registry.addEndpoint("/chat").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // enableSimpleBroker表示设置消息代理的前缀，即如果消息的前缀是/topic，就会将消息转发给消息代理（broker），再由消息代理将消息广播给当前连接的客户端
        // 其中，queue是方便对群发消息和点对点消息进行管理
        registry.enableSimpleBroker("/topic", "/queue");
        // setApplicationDestinationPrefixes表示配置一个或多个前缀，通过这些前缀过滤出需要被注解方法处理的消息。例如，前缀为“/app”的destination可以通过
        // @MessageMapping注解的方法处理，而其他的destination则直接交给broker处理。
        registry.setApplicationDestinationPrefixes("/app");
    }
}
