package net.coding.api.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * TestConfig类定义了获取CONFIG_PATH内容的方法
 * 1、getProperty(key)
 * 2、getProperty(key,defaultValue)
 * 在TestConfig中封装了props的数据来源和数据获取过程
 * 调用TestConfig.getInstance()进行实例化，TestConfig对象来接收
 * 再用该对象调用getProperty()即可获取CONFIG_PATH定义的文件的内容
 *
 */


//创建TestConfig类
public class TestConfig {
//    1、定义静态属性和共用对象
//    获取config.path的属性
    private static final String CONFIG_PATH = System.getProperty("config.path","config.properties");
//    创建TestConfig对象
    private static TestConfig instance = new TestConfig();
//    创建Properties对象，import java.util.Properties，类似map字典对象
    private Properties props = new Properties();

    //对象实例化方法
    public static TestConfig getInstance(){
        return instance;
    }
//    2、定义TestConfig方法,读取CONFIG_PATH的数据，用上面创建的props接受
    private TestConfig(){
//        创建当前线程的类加载器对象
//        this.getClass.getClassLoader()； 得到的Classloader是静态的，表明类的载入者是谁
//        Thread.currentThread().getContextClassLoader()；得到的Classloader是动态的，谁执行（某个线程），就是那个执行者的 Classloader。
//        ClassLoader.getSystemClassLoader(); 得到系统ClassLoader，即系统的入口点所使用的ClassLoader。
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        //用当前ClassLoader类构造器的方法getResource读取CONFIG_PATH资源文件，return为URL
        URL appResourceURL = loader.getResource("CONFIG_PATH");

        try {
            //从appResourceURL的连接中获取输入流InputStream
            InputStream is = appResourceURL.openStream();
            //从is中读取数据，返回一个Properties数据
            props.load(is);
        } catch (IOException e) {
//            如果出现IOException异常，则返回读取失败
            System.err.println("Fail to load config: " + CONFIG_PATH);
        }

    }

    //  3、定义通过key获取values的方法
    public String getProperty(String key){
        return props.getProperty(key);
    }

    public String getProperty(String key,String defaultValue){
        return props.getProperty(key,defaultValue);
    }

}
