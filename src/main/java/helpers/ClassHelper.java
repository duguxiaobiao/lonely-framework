package helpers;

import annotations.Controller;
import annotations.Service;
import utils.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ztkj
 * @Date 2019/5/5 14:21
 * @Description 获取指定扫描包路径下的所有类，含有指定注解的类
 */
public class ClassHelper {

    private static Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }


    /**
     * 获取@Service注解的类集合
     *
     * @return
     */
    public static Set<Class<?>> getServiceClassSet() {
        return getClassSetByType(Service.class);
    }

    /**
     * 获取@Controller注解的类集合
     *
     * @return
     */
    public static Set<Class<?>> getControllerClassSet() {
        return getClassSetByType(Controller.class);
    }

    /**
     * 获取所有注册到容器的类
     *
     * @return
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> beans = new HashSet<>();
        beans.addAll(getServiceClassSet());
        beans.addAll(getControllerClassSet());
        return beans;
    }

    /**
     * 获取指定类上存在指定注解的类集合
     *
     * @param type 指定注解
     * @return
     */
    private static Set<Class<?>> getClassSetByType(Class<? extends Annotation> type) {
        Set<Class<?>> beans = new HashSet<>();

        for (Class<?> loadClass : CLASS_SET) {
            if (loadClass.isAnnotationPresent(type)) {
                beans.add(loadClass);
            }

        }
        return beans;
    }
}
