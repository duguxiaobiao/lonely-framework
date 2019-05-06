package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author ztkj
 * @Date 2019/5/5 11:12
 * @Description 类处理工具类
 */
public class ClassUtil {


    private static final Logger logger = LoggerFactory.getLogger(ClassUtil.class);


    /**
     * 获取类加载器
     *
     * @return
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }


    /**
     * 加载类
     *
     * @param className
     * @param isInitialized 是否初始化，表示是否执行类的静态代码块
     * @return
     */
    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> clazz;

        try {
            clazz = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            logger.error(ExceptionUtil.getStackTrace(e));
            throw new RuntimeException(e);
        }

        return clazz;
    }


    /**
     * 获取指定的包下的所有类
     *
     * @param packageName
     * @return
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> classSet = new HashSet<>();

        try {
            Enumeration<URL> urlResources = getClassLoader().getResources(packageName.replace(".", "/"));

            while (urlResources.hasMoreElements()) {
                URL url = urlResources.nextElement();
                if (url != null) {
                    //判断是文件还是jar包
                    String protocol = url.getProtocol();

                    if ("file".equals(protocol)) {
                        String path = url.getPath().replaceAll("%20", "");
                        addClass(classSet, path, packageName);
                    } else if ("jar".equals(protocol)) {
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        if (jarURLConnection != null) {
                            JarFile jarFile = jarURLConnection.getJarFile();
                            Enumeration<JarEntry> entries = jarFile.entries();
                            while (entries.hasMoreElements()) {
                                JarEntry jarEntry = entries.nextElement();
                                if (jarEntry != null) {
                                    String jarEntryName = jarEntry.getName();
                                    if (jarEntryName.endsWith(".class")) {
                                        String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                                        doAddClass(classSet, className);
                                    }

                                }
                            }
                        }
                    }
                }
            }


        } catch (IOException e) {
            logger.error(ExceptionUtil.getStackTrace(e));
            throw new RuntimeException(e);
        }

        return classSet;
    }


    /**
     * 添加Class
     * @param classSet
     * @param packagePath
     * @param packageName
     */
    public static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {

        //加载指定 包路径下的 .class文件或者目录
        File[] listFiles = new File(packagePath).listFiles(file -> file.isFile() && file.getName().endsWith(".class") || file.isDirectory());

        //循环遍历，如果是.class文件，则直接访问加载，如果是目录，则递归再次加载
        for (File file : listFiles) {
            String fileName = file.getName();
            if (file.isFile()) {
                //.class文件
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                className = Optional.ofNullable(packageName).orElse("") + "." + className;
                doAddClass(classSet, className);
            } else {
                //目录
                //构建子包path
                String subPackagePath = Optional.ofNullable(packagePath).orElse("") + "/" + fileName;

                //构建子包名
                String subPackageName = Optional.ofNullable(packageName).orElse("") + "." + fileName;

                //递归加载
                addClass(classSet, subPackagePath, subPackageName);
            }


        }


    }


    /**
     * 执行添加类到Set集合中
     *
     * @param classSet
     * @param className
     */
    public static void doAddClass(Set<Class<?>> classSet, String className) {
        Class<?> loadClass = loadClass(className, false);
        classSet.add(loadClass);
    }
}
