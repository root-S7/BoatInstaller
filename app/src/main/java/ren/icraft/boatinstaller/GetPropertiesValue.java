package ren.icraft.boatinstaller;

import android.content.Context;
import java.io.*;
import java.util.Properties;

public class GetPropertiesValue{
    public String getPropertiesReader(Context context,String propertyValue){
        Properties properties=new Properties();//获取Properties实例
        try {
            InputStream inStream = context.getAssets().open("config.properties");//获取配置文件输入流
            properties.load(inStream);//载入输入流
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/" + properties.getProperty(propertyValue);
    }
    public String getPropertiesReaderNoSlash(Context context,String propertyValue){
        Properties properties=new Properties();//获取Properties实例
        try {
            InputStream inStream = context.getAssets().open("config.properties");//获取配置文件输入流
            properties.load(inStream);//载入输入流
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(propertyValue);
    }
}