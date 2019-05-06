package helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author ztkj
 * @Date 2019/5/5 14:54
 * @Description 容器类
 */
public class BeanHelper {

    private static final Logger logger = LoggerFactory.getLogger(BeanHelper.class);

    private static final Map<Class<?>, Object> BEANS_MAP;

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        BEANS_MAP = new HashMap<>();

        for (Class<?> beanClass : beanClassSet) {
            BEANS_MAP.put(beanClass, ReflectionUtil.newInstance(beanClass));
        }
    }

    /**
     * 获取容器beanMap
     *
     * @return
     */
    public static Map<Class<?>, Object> getBeansMap() {
        return BEANS_MAP;
    }

    /**
     * 获取指定类型的实体对象
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {

        if (!BEANS_MAP.containsKey(clazz)) {
            logger.debug("该类不存在：{}", clazz.getName());
            throw new RuntimeException("该类不存在：" + clazz);
        }

        return (T) BEANS_MAP.get(clazz);
    }
}
