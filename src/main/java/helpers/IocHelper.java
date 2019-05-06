package helpers;

import annotations.Autowire;
import cn.hutool.core.collection.CollectionUtil;
import utils.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author ztkj
 * @Date 2019/5/5 15:03
 * @Description
 */
public final class IocHelper {


    static {
        //获取所有的实体bean
        Map<Class<?>, Object> beansMap = BeanHelper.getBeansMap();
        if (CollectionUtil.isNotEmpty(beansMap)) {

            //循环遍历所有bean
            beansMap.forEach((clazz, obj) -> {
                //循环遍历每个bean中的所有Field
                Field[] declaredFields = clazz.getDeclaredFields();
                if (declaredFields.length > 0) {

                    for (Field declaredField : declaredFields) {
                        //判断是否存在注入注解
                        if (declaredField.isAnnotationPresent(Autowire.class)) {

                            //为改属性赋值
                            Object bean = BeanHelper.getBean(declaredField.getType());
                            if (bean != null) {
                                //通过反射赋值
                                ReflectionUtil.setFiled(obj, declaredField, bean);
                            }
                        }

                    }
                }
            });
        }
    }


}
