# Spring Boot工程目录作用

### /src/main/java
    放置所有的java文件
### /src/main/resources
    放置所有的资源文件，包括静态资源文件、配置文件、页面文件等
### /src/main/resources/static
    用于存放各类静态资源
### /src/main/resources/application.properties
    配置文件，Spring Boot默认支持两种配置文件类型，即.properties和.yml。Spring Boot会自动在/src/main/resource目录下找application.properties或application.yml配置文件。
    .properties文件的优先级高于.yml。如果在两个文件中配置了同一个属性，Spring Boot将使用.properties中的。
### /src/main/resources/templates
    用于存放模板文件，如Thymeleaf模板文件
### /src/test/java
    放置单元测试类Java代码
### /target
    放置编译后的.class文件、配置文件等
    
# 入口类注解
### @SpringBootApplication
    是一个组合注解，包含@EnableAutoConfiguration、@ComponentScan和@SpringBootConfiguration三个注解，是项目启动注解。
    入口类需要放置在包的最外层，以便能扫描到所有子包中的类。
    
# 测试类注解
### @RunWith(SpringRunner.class)
    参数化运行器。查源代码可知，SpringRunner类继承自SpringJunitClassRunner类，此处表明使用SpringJUnit4ClassRunner执行器，此执行器集合了Spring的一些功能。如果只是简单的JUnit单元测试，该注解可以去掉。
### @SpringBootTest
    因为Spring Boot程序的入口是SpringApplication，所以基本上所有配置都会通过入口类去加载，而该注解可以引用入口类的配置。    

# pom依赖
### spring-boot-starter-parent
    是一个特殊的starter，用来提供相关的maven默认依赖，使用它之后，常用的包可以省去version标签

### spring-boot-starter-web
    只要将其加入项目的maven依赖中，就得到了可执行的web应用。该依赖中包含许多常用的依赖包，比如spring-web、spring-webmvc等。不需要做任何Web配置，便能获得相关Web服务。
    
### spring-boot-starter-test
    和测试相关，只要引入它，就会把所有与测试相关的包全部引入。
    
### spring-boot-maven-plugin
    一个maven插件，能够以maven的方式为应用提供Spring Boot的支持，即为Spring Boot应用提供了执行Maven操作的可能。能够将Spring Boot应用打包为可执行的jar或war文件。
* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)

