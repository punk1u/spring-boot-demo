package tech.punklu.myspringboot.demo.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class GreetingController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 根据之前的配置，@MessageMapping("/hello")注解的方法将用来接收“/app/hello”路径发送来的消息，在注解方法中对消息进行处理后，
     * 再将消息转发到@SendTo定义的路径上，而@SendTo路径是一个前缀为“/topic”的路径，因此该消息将被交给消息代理broker，再由broker进行广播
     *
     * @param message
     * @return
     * @throws Exception
     */
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Message greeting(Message message) throws Exception {
        return message;
    }


    /**
     * 在上面的例子中使用了@SendTo注解，该注解j将方法处理过的消息转发到broker，再由broker进行消息广播。除了@SendTo注解外，
     * Spring还提供了SimpMessagingTemplate类来让开发者更加灵活地发送消息，使用SimpMessagingTemplate对之前的进行改造。
     * <p>
     * 在Spring Boot中，SimpMessagingTemplate已经配置好，开发者直接注入进来即可。使用SimpMessagingTemplate，开发者可以在任何地方发送消息到broker，
     * 也可以发送消息给某一个用户，这就是点对点的消息发送
     *
     * @param message
     * @throws Exception
     */
    @MessageMapping("/hello1")
    public void greeting2(Message message) throws Exception {
        simpMessagingTemplate.convertAndSend("/topic/greetings", message);
    }

    /**
     * 点对点消息发送
     *
     * @param principal
     * @param chat
     */
    // @MessageMapping("/char")表示来自"/app/chat"路径的消息将被chat方法处理。Chat方法的第一个参数Principal可以用来获取当前登录用户的信息，
    // 第二个参数则是客户端发送来的消息
    @MessageMapping("/chat")
    public void chat(Principal principal, Chat chat) {
        String from = principal.getName();
        chat.setFrom(from);
        // convertAndSendToUser方法内部的默认实现定义了最终发送路径的前缀是"/user"，也就是说这条消息的最终发送路径是“/user/用户名/queue/chat”
        simpMessagingTemplate.convertAndSendToUser(chat.getTo(), "/queue/chat", chat);
    }


}
