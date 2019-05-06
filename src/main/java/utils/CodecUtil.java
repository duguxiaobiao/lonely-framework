package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author ztkj
 * @Date 2019/5/5 17:21
 * @Description 用于编码的转换
 */
public class CodecUtil {

    private static final Logger logger = LoggerFactory.getLogger(CodecUtil.class);


    /**
     * 编码
     *
     * @param source
     * @return
     */
    public static String encodeUrl(String source) {
        String target;
        try {
            target = URLEncoder.encode(source, "utf-8");
        } catch (Exception e) {
            logger.error("encodeUrl error，异常原因：{}", ExceptionUtil.getStackTrace(e));
            throw new RuntimeException(e);
        }
        return target;
    }


    /**
     * 解码
     *
     * @param source
     * @return
     */
    public static String decodeUrl(String source) {
        String target;

        try {
            target = URLDecoder.decode(source, "utf-8");
        } catch (Exception e) {
            logger.error("decodeUrl error，异常原因：{}", ExceptionUtil.getStackTrace(e));
            throw new RuntimeException(e);
        }
        return target;
    }

}
