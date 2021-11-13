package top.zsmile.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


/**
 * 转换工具
 */
public class ConvertUtil {

    /**
     * 将对象转换成Map
     * @param t
     * @param <T>
     * @return
     */
    public <T> Map<String, String> convert2Map(T t) {
        Map<String, String> result = new HashMap<String, String>();
        Field[] fields = t.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                result.put(field.getName(), field.get(t).toString());
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return result;
    }
}
