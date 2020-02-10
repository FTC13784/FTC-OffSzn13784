package org.firstinspires.ftc.teamcode.util;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
    private Properties properties;

    public PropertiesLoader(String fileName) {
       // File sdcard = Environment.DIR;
        File config = new File("system/Android", "data/ftc/autonomous/" + fileName + ".properties");
        properties = new Properties();
        try {
            properties.load(new FileInputStream(config));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean getBooleanProperty(String name) {
        try {
            return Boolean.parseBoolean(getStringProperty(name));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid boolean: " + getStringProperty(name) + " for property: " + name);
        }
    }

    public int getIntegerProperty(String name) {
        try {
            return Integer.parseInt(getStringProperty(name));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid integer: " + getStringProperty(name) + " for property: " + name);
        }
    }

    public long getLongProperty(String name) {
        try {
            return Long.parseLong(getStringProperty(name));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid long: " + getStringProperty(name) + " for property: " + name);
        }
    }

    public float getFloatProperty(String name) {
        try {
            return Float.parseFloat(getStringProperty(name));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid float: " + getStringProperty(name) + " for property: " + name);
        }
    }

    public double getDoubleProperty(String name) {
        try {
            return Double.parseDouble(getStringProperty(name));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid double: " + getStringProperty(name) + " for property: " + name);
        }
    }

    public String getStringProperty(String name) {
        try {
            return properties.getProperty(name);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Cannot find property with name " + name);
        }
    }
}
