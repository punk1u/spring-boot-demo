# Spring Boot中使用过滤器和监听器
### 过滤器Filter
    Filter也称为过滤器，是处于客户端与服务器资源文件之间的一道过滤网，开发时可以通过Filter技术管理Web服务器的所有资源，例如对jsp、Servlet、图片或HTML文件进行拦截，
    从而事先一些特殊的功能，如实现URL级别的权限访问控制、过滤敏感词汇等高级功能。
    
    Filter接口源码：
    public interface Filter{
        void init(FilterConfig var1) throws ServletException;
        void doFilter(ServletRequest var1,ServletResponse var2,FilterChain var3) throws IOException,ServletException;
        void destroy(); 
    }
    
    Filter的创建和销毁由Web服务器负责。Web服务器启动时，Web服务器将创建Filter的实例对象，并调用其init方法，读取web.xml配置，完成对象的初始化功能，从而为后续的用户请求做好拦截的准备工作（Filter
    对象只会创建一次，init方法也只会执行一次）。通过init方法的参数可获得代表当前filter配置信息的FilterConfig对象。
    
    当客户请求访问与过滤器关联的URL时，过滤器将先执行doFilter方法。FilterChain参数用于访问后续过滤器。Filter对象创建后会驻留在内存中，当web应用移除或服务器停止时才销毁。在Web容器卸载Filter对象之前，
    destroy被调用。该方法在Filter的生命周期中仅执行一次。Filte可以有很多个，一个个Filter组合起来就形成一个FilterChain，也就是过滤链。
    
### 监听器Listener
    监听器可以用于监听WEB应用中某些对象、信息的创建、销毁、增加、修改、删除等动作的发生，然后做出相应的响应处理。
    
    根据监听对象可以把监听器分为三类：ServletContext（对应application）、HttpSession（对应Session）、ServletRequest（对应Request）。Application在整个Web服务中只有一个，在web服务关闭时
    销毁。Session对应每个对话，在会话起始时创建，关闭会话时销毁。Request对象是客户发送请求时创建的（一同创建的还有Response），用于封装请求数据，在一次请求处理完毕后销毁。
    
    根据监听的事件，可以把监听器分为三类：
    1、监听对象创建和销毁，如ServletContextListener
    2、监听对象域中属性的增加和删除，如HttpSessionListener和ServletRequestListener
    3、监听绑定到Session上的某个对象的状态，如ServletContextAttributeListener、HttpSessionAttributeListener、ServletRequestAttributeListener等