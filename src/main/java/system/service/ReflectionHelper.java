package system.service;

import org.apache.log4j.Logger;

import java.lang.reflect.Field;

public class ReflectionHelper {

    private static final Logger logger = Logger.getLogger(ReflectionHelper.class);

    public static Object createInstance(String className) {
        try {
            return Class.forName("system.model."+className).newInstance();
        } catch (Exception ignored) {}
        return null;
    }

    public static void setFieldValue(Object object, String fieldName, String value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            if (field.getType().equals(String.class)) {
                field.set(object, value);
            } else if (field.getType().equals(int.class)) {
                field.set(object, Integer.decode(value));
            }
            field.setAccessible(false);
        } catch (Exception ignored) {}
    }
}