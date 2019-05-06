package utils;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @author ztkj
 * @Date 2019/5/5 15:20
 * @Description
 */
public class ArrayUtil {


    /**
     * 判断数组是否不为空
     *
     * @param array
     * @return
     */
    public static boolean isNotEmpty(Object[] array) {
        return ArrayUtils.isNotEmpty(array);
    }


    /**
     * 判断数组是否为空
     *
     * @param array
     * @return
     */
    public static boolean isEmpty(Object[] array) {
        return ArrayUtils.isEmpty(array);
    }

}
