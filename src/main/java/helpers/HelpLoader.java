package helpers;

import utils.ClassUtil;

/**
 * @author ztkj
 * @Date 2019/5/5 16:08
 * @Description 辅助启动类
 */
public class HelpLoader {

    public static void init(){

        Class<?>[] loadClass = {
                BeanHelper.class,
                ClassHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };

        for (Class<?> aClass : loadClass) {
            ClassUtil.loadClass(aClass.getName(),true);
        }

    }


}
