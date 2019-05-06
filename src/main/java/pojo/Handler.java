package pojo;

import java.lang.reflect.Method;

/**
 * @author ztkj
 * @Date 2019/5/5 15:29
 * @Description
 */
public class Handler {


    /**
     * controlle类
     */
    private Class<?> controllerClass;

    /**
     * controller方法
     */
    private Method controllerMethod;


    public Handler(Class<?> controllerClass, Method controllerMethod) {
        this.controllerClass = controllerClass;
        this.controllerMethod = controllerMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getControllerMethod() {
        return controllerMethod;
    }
}
