package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author ztkj
 * @Date 2019/5/5 17:04
 * @Description 流操作工具类
 */
public class StreamUtil {

    private static final Logger logger = LoggerFactory.getLogger(StreamUtil.class);

    /**
     * 从输入流中读取数据
     *
     * @param is
     * @return
     */
    public static String getString(InputStream is) {

        StringBuilder stringBuilder = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

        } catch (Exception e) {
            logger.error("从输入流中读取数据出现异常，异常原因：{}", ExceptionUtil.getStackTrace(e));
            throw new RuntimeException(e);
        }
        return stringBuilder.toString();

    }
}
