package utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author ztkj
 * @Date 2019/5/5 11:21
 * @Description 异常输出工具类
 */
public class ExceptionUtil {

    public static String getStackTrace(Exception e) {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer, true));
        return writer.toString();
    }
}
