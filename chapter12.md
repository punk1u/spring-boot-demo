# 全局异常处理与Retry重试
### 全局异常
    Spring Boot提供了一个默认的处理错误的映射：/error，当处理中抛出异常之后，会转到该请求中处理，并且该请求中有一个全局的错误页面来展示异常内容。但是Spring Boot提供的默认错误映射并不友好，
    还需要自己实现异常提示。
    
    在项目中，会遇到各种各样的业务异常，业务异常是指正常的业务处理时，由于某些业务的特殊要求而导致处理不能继续而抛出异常。我们希望这些业务异常能够被统一处理，因此使用Spring Boot进行全局异常处理就变得
    很方便。具体见error包下的代码

### Retry重试介绍
    当调用一个接口时，可能由于网络等原因造成第一次失败，再去尝试就成功了，这就是重试机制，重试的解决方案很多，比如利用try-catch-redo简单重试模式，通过判断返回结果或监听异常来判断是否重试。示例代码：
    
    public void testRetry() throws Exception{
        boolean result = false;
        try{
            result = load();
            if(!result){
                // 一次重试
                load();
            }
        }catch (Exception e){
            // 一次重试
            load();
        }
    }
    
    try-catch-redo重试模式还有可能出现重试无效，解决这个问题的方法是尝试增加重试次数retrycount以及重试间隔周期interval，达到增加重试有效的可能性，因此可以利用try-catch-redo-retry strategy策略重试模式：
    
    public void testRetry2() throws Exception{
        boolean result = false;
        try{
            result = load();
            if(!result){
                // 延迟3秒，重试3次
                reLoad(3000L,3); // 延迟多次重试
            }
        }catch(Exception e){
            // 延迟3s，重试3次
            reload(3000L,3); // 延迟多次重试
        }
    }
    
    但是这两种策略有一个共同的问题就是：正常逻辑和重试逻辑强耦合。基于这些问题，对于Spring-Retry规范正常逻辑和重试逻辑，将正常逻辑和重试逻辑解耦。Spring-Retry是一个开源工具包，该工具把重试操作模板定制化，
    可以设置重试策略和回退策略。同时，重试执行实例保证线程安全。Spring-Retry重试可以用Java代码方式实现，也可以用注解@Retryable方式实现，提倡以注解的方式实现。