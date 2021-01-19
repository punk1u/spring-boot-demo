# 集成Log4j日志
        Log4j是Apache下的一个开源项目，通过Log4j可以将日志信息打印到控制台、文件等。也可以控制每一条日志的输出格式，通过定义每一条日志信息的级别能更加
    细致地控制日志的生成过程。
        Log4j中有三个主要的组件，分别是Loggers（记录器）、Appenders（输出源）和Layouts（布局），这三个组件可以简单地理解为日志类别、日志要输出的地方和
    日志以哪种形式输出。
    
        Log4j支持两种配置文件格式，一种是XML格式的文件，一种是Java特文件log42.properties(键=值)。前者能配置更多功能，后者简单易读。
    
        Spring Boot默认使用Logback日志框架来记录日志，并用INFO级别输出到控制台，所以在引入Log4j2之前，需要先排除该包的依赖，再引入Log4j2的依赖。
    具体做法就是找到pom.xml文件中的spring-boot-starter-web依赖，使用exclusion标签排除Logback，具体代码：
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <exclusions>
                <!-- 排查Spring Boot默认日志,去除Spring Boot默认使用的Logback日志框架，以便使用Log4j2 -->
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-logging</artifactId>
                </exclusion>
            </exclusions>
        </dependency>