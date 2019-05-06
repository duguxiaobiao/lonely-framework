package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author ztkj
 * @Date 2019/5/5 14:38
 * @Description 反射工具类
 */
public class ReflectionUtil {


    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);


    /**
     * 实例化
     *
     * @param clazz
     * @return
     */
    public static Object newInstance(Class<?> clazz) {
        Object object;
        try {
            object = clazz.newInstance();
        } catch (Exception e) {
            logger.error("实例化出现异常,异常原因：{}", ExceptionUtil.getStackTrace(e));
            throw new RuntimeException(e);
        }
        return object;
    }

    /**
     * 指定mehod方法
     *
     * @param obj
     * @param invokeMethod
     * @param args
     * @return
     */
    public static Object invokeMethod(Object obj, Method invokeMethod, Object... args) {
        Object object;
        try {
            invokeMethod.setAccessible(true);
            object = invokeMethod.invoke(obj, args);
        } catch (Exception e) {
            logger.error("invokeMethod出现异常，异常原因：{}", ExceptionUtil.getStackTrace(e));
            throw new RuntimeException(e);
        }
        return object;
    }


    /**
     * 设置属性
     *
     * @param obj
     * @param field
     * @param args
     */
    public static void setFiled(Object obj, Field field, Object... args) {
        try {
            field.setAccessible(true);
            field.set(obj, args);
        } catch (Exception e) {
            logger.error("setField出现异常，异常原因：{}", ExceptionUtil.getStackTrace(e));
            throw new RuntimeException(e);
        }
    }

}
