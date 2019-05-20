package com.epam.utility;

import com.epam.config.Constants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {

    private String filePath = Constants.FILE_OR;
    private Properties properties = null;

    public Properties getProperties() {
        if (null == properties){
            load();
        }
        return properties;
    }

    private Properties load() {
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(this.getClass().getClassLoader().getResource(filePath).getPath());
            properties = new Properties();
            properties.load(stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != stream) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }
}
