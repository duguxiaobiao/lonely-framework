package helpers;

import constants.ConfigConstant;
import utils.PropsUtil;

import java.util.Map;

/**
 * @author ztkj
 * @Date 2019/4/30 11:45
 * @Description 配置加载工具类
 */
public class ConfigHelper {


    private static final Map<String,String> PROPERTIES_MAP;

    static {
        PropsUtil propsUtil = new PropsUtil(ConfigConstant.CONFIG_FILE);
        PROPERTIES_MAP = propsUtil.loadPropsToMap();
    }

    public static String getJdbcDriver() {
        return PROPERTIES_MAP.get(ConfigConstant.JDBC_DRIVER);
    }

    public static String getJdbcUrl() {
        return PROPERTIES_MAP.get(ConfigConstant.JDBC_URL);
    }

    public static String getJdbcUsername() {
        return PROPERTIES_MAP.get(ConfigConstant.JDBC_USERNAME);
    }

    public static String getJdbcPassword() {
        return PROPERTIES_MAP.get(ConfigConstant.JDBC_PASSWORD);
    }

    public static String getAppBasePackage() {
        return PROPERTIES_MAP.get(ConfigConstant.APP_BASE_PACKAGE);
    }

    public static String getAppJspPath() {
        return PROPERTIES_MAP.getOrDefault(ConfigConstant.APP_JSP_PATH,"/WEB-INF/view/");
    }

    public static String getAppAssetPath() {
        return PROPERTIES_MAP.getOrDefault(ConfigConstant.APP_ASSET_PATH,"/asset/");
    }

}
