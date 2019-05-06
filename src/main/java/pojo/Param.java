package pojo;

import utils.CastUtil;

import java.util.Map;

/**
 * @author ztkj
 * @Date 2019/5/5 16:16
 * @Description
 */
public class Param {


    private Map<String, Object> paramsMap;


    public Param(Map<String, Object> paramsMap) {
        this.paramsMap = paramsMap;
    }

    public long getLong(String name) {
        return CastUtil.castLong(name);
    }

    public Map<String, Object> getParamsMap() {
        return paramsMap;
    }
}
