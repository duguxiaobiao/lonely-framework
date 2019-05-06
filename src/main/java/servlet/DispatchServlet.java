package servlet;

import cn.hutool.json.JSONUtil;
import helpers.ConfigHelper;
import helpers.ControllerHelper;
import helpers.HelpLoader;
import org.apache.commons.lang3.StringUtils;
import pojo.*;
import utils.ArrayUtil;
import utils.CodecUtil;
import utils.ReflectionUtil;
import utils.StreamUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ztkj
 * @Date 2019/5/5 16:31
 * @Description
 */
public class DispatchServlet extends HttpServlet {


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        //初始化相关类
        HelpLoader.init();

        //获取ServletContext对象
        ServletContext servletContext = config.getServletContext();

        //注册处理jsp的Servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");

        //注册处理静态资源的默认Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");


    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        //获取请求方法和路径
        String reqMethod = req.getMethod().toLowerCase();
        String pathInfo = req.getPathInfo();

        //构建请求实体
        Request request = new Request(reqMethod, pathInfo);

        //获取处理对象
        Handler handler = ControllerHelper.getHandler(request);

        if (handler != null) {
            //构建请求参数对象
            Map<String, Object> paramsMap = new HashMap<>();

            //表单形式
            Enumeration<String> parameterNames = req.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String parameterName = parameterNames.nextElement();
                String parameterValue = req.getParameter(parameterName);
                paramsMap.put(parameterName, parameterValue);
            }

            //get请求方式
            String requestBody = CodecUtil.decodeUrl(StreamUtil.getString(req.getInputStream()));
            if (StringUtils.isNotEmpty(requestBody)) {
                String[] params = StringUtils.split(requestBody, "&");
                if (ArrayUtil.isNotEmpty(params)) {
                    for (String param : params) {
                        String[] array = StringUtils.split(param, "=");
                        if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
                            String paramName = array[0];
                            String paramValue = array[1];
                            paramsMap.put(paramName, paramValue);
                        }
                    }
                }
            }

            //入参
            Param param = new Param(paramsMap);

            //调用方法
            Class<?> handlerClass = handler.getControllerClass();
            Method handlerMethod = handler.getControllerMethod();
            Object result = ReflectionUtil.invokeMethod(handlerClass, handlerMethod, param);

            //处理响应对象
            if (result instanceof View) {
                View view = (View) result;
                String viewPath = view.getPath();
                if (viewPath.startsWith("/")) {
                    //重定向
                    resp.sendRedirect(req.getContextPath() + viewPath);
                } else {
                    //转发
                    Map<String, Object> viewModel = view.getModel();
                    viewModel.forEach((key, value) -> req.setAttribute(key, value));
                    req.getRequestDispatcher(ConfigHelper.getAppJspPath() + viewPath).forward(req, resp);
                }

            } else if (result instanceof Data) {

                //返回数据
                Data data = (Data) result;
                resp.setContentType("application/json");
                resp.setCharacterEncoding("utf-8");

                PrintWriter writer = resp.getWriter();
                writer.write(JSONUtil.toJsonStr(data));
                writer.flush();
                writer.close();
            }

        }


    }
}
