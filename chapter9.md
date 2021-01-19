# Quartz定时器和发送Email
    Quartz是一个完全由java编写的开源任务调度框架，通过触发器设置作业定时运行规则、控制作业的运行时间。Quartz定时器作用很多，比如定时发送消息、定时发送报表等。
    
    Quartz框架主要核心组件包括调度器、触发器、作业。调度器作为作业的总指挥，触发器作为作业的操作者，作业为应用的功能模块。其关系如下：
    
    Job <-----------JobDetail <----------------Trigger
                        |                         | 
                        |                         |
                        |                         |      
                        |                         |
                         --------- Scheduler ------
                         
    Job是一个接口，该接口只有一个方法execute，被调度的作业（类）需实现该接口的execute()方法，JobExecutionContext类提供了调度上下文的各种信息。每次执行该Job均重新创建一个Job实例：
    Job源码：
    public interface Job(){
        void execute(JobExecutionContext var1) throws JobExecutionException;
    }
    
    Quartz在每次执行Job时，都重新创建一个Job实例，所以它不直接接收一个Job实例，相反它接收一个Job实现类，以便运行时通过newInstance()的反射机制实例化Job。因此需要一个类来描述Job的实现类
    及其他相关的静态信息，如Job名字、描述、关联监听器等信息，JobDetail承担了这一角色。JobDetail用来保存作业的详细信息。一个JobDetail可以有多个Trigger，但是一个Trigger只能对应一个JobDetail。
    
    Trigger触发器描述触发Job的执行规则，主要有SimpleTrigger和CronTrigger两个子类。当仅需触发一次或者以固定时间间隔周期执行时，SimpleTrigger是最适合的选择；而CronTrigger可以通过Cron表达式
    定义出各种复杂时间规则的调度方案，Cron表达式定义如下：
    格式：[秒] [分] [小时] [日] [月] [周] [年]
    
    0 0 12 * * ? 每天12点触发
    
    Scheduler负责管理Quartz的运行环境，Quartz是基于多线程架构的，启动的时候会初始化一套线程，这套线程用来执行一些预置的作业。Trigger和JobDetail可以注册到Scheduler中。Scheduler可以将Trigger
    绑定到某一JobDetail中，这样当Trigger触发时，对应的Job就会被执行。Scheduler拥有一个SchedulerContext，类似于ServletContext，保存着Scheduler上下文信息，Job和Trigger都可以访问SchedulerContext
    内的信息。
    
    创建定时器的方法有两种：
    1、使用XML配置文件的方式（resource目录下的spring-mvc.xml及spring-quartz.xml以及对应的TestTask类）
    2、使用注解的方式

# Spring Boot发送Email
    邮件发送与接收的过程如下：
    1）、发件人使用SMTP协议传输邮件到邮件服务器A
    2）、邮件服务器A根据邮件中指定的接收者投送邮件至相应的邮件服务器B
    3）、收件人使用POP3协议从邮件服务器B接收邮件
    
    SMTP（Simple Mail Transfer Protocol）是电子邮件传输的互联网标准，定义在RFC 5321，默认使用25端口。
    POP3（Post Office Protocol - version 3）主要用于支持使用客户端远程管理在服务器上的电子邮件，定义在RFC 1939,为POP协议的第三版（最新版）。
    
    这两个协议均属于TCP/IP协议族的应用层协议，运行在TCP层。
    
    SUN公司提供了一款邮件发送和接收的开源类库JavaMail，支持常用的邮件协议，如SMTP、POP3、IMAP等。使用它可以不再需要考虑底层的通信细节（如Socket）。
    
    Spring提供了非常好用的JavaMailSender接口实现邮件发送，在Spring Boot的starter模块中已为此提供了自动化配置。
    
    具体可见SendJunkMailServiceImpl以及SendMailQuartz定时发送邮件任务类.