package helpers;

import annotations.RequestMapping;
import org.apache.commons.collections4.CollectionUtils;
import pojo.Handler;
import pojo.Request;
import utils.ArrayUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author ztkj
 * @Date 2019/5/5 15:42
 * @Description
 */
public class ControllerHelper {

    /**
     * 用于存储 请求与处理方式的映射关系
     */
    private static final Map<Request, Handler> REQUEST_HANDLER_MAP = new HashMap<>();

    static {

        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();

        if (CollectionUtils.isNotEmpty(controllerClassSet)) {

            //循环遍历所有Controller类
            for (Class<?> controllerBean : controllerClassSet) {
                Method[] declaredMethods = controllerBean.getDeclaredMethods();
                if (ArrayUtil.isNotEmpty(declaredMethods)) {

                    //循环遍历每个controller中的方法
                    for (Method declaredMethod : declaredMethods) {
                        if (declaredMethod.isAnnotationPresent(RequestMapping.class)) {
                            RequestMapping annotation = declaredMethod.getAnnotation(RequestMapping.class);
                            String requestValue = annotation.value();

                            //验证 规则
                            if (requestValue.matches("\\w:\\w*")) {
                                String[] split = requestValue.split(":");
                                if (ArrayUtil.isNotEmpty(split) && split.length == 2) {
                                    Request request = new Request(split[0], split[1]);
                                    Handler handler = new Handler(controllerBean, declaredMethod);
                                    REQUEST_HANDLER_MAP.put(request, handler);

                                }
                            }
                        }

                    }

                }
            }

        }


    }

    /**
     * 返回指定参数下的处理类
     *
     * @param requestMethod
     * @param requestPath
     * @return
     */
    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return REQUEST_HANDLER_MAP.get(request);
    }

    /**
     * 返回指定参数下的处理类
     *
     * @param request
     * @return
     */
    public static Handler getHandler(Request request) {
        return REQUEST_HANDLER_MAP.get(request);
    }


}
