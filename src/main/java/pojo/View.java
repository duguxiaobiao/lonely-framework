package pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ztkj
 * @Date 2019/5/5 16:25
 * @Description
 */
public class View {


    private String path;

    private Map<String,Object> model;

    public View(String path) {
        this.path = path;
        this.model = new HashMap<>();
    }


    public View addModel(String key,Object value){
        this.model.put(key,value);
        return this;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }
}
