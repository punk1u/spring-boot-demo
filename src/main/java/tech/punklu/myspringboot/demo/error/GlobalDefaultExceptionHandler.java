package tech.punklu.myspringboot.demo.error;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * 统一业务异常处理类
 *
 * @ControllerAdvice：定义统一的异常处理类，basePackages属性用于定义扫描哪些包，默认可不配置
 * @ExceptionHandler：用来定义函数针对的异常类型，可以传入多个需要捕获的异常类
 */
@ControllerAdvice(basePackages = {"tech.punklu.myspringboot.demo",})
public class GlobalDefaultExceptionHandler {


    @ExceptionHandler({BusinessException.class})
    // 如果返回的是json数据或其他对象，就添加该注解
    @ResponseBody
    public ErrorInfo defaultErrorHandler(HttpServletRequest request, Exception e) throws Exception {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setMessage(e.getMessage());
        errorInfo.setUrl(request.getRequestURI());
        errorInfo.setCode(ErrorInfo.SUCCESS);
        return errorInfo;
    }
}
