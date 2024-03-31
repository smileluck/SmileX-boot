package top.zsmile.tool.gen.constant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author: B.Smile
 * @Date: 2022/1/22 11:31
 * @Description: Default Constants
 */
public class DefaultConstants implements Serializable {
    public static final String ENCODEING = "UTF-8";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String MYSQL_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    public static final List<String> IGNORE_COLUMN = Collections.unmodifiableList(Arrays.asList("create_time", "update_time", "create_by", "update_by"));
    /**
     * 删除键名，1删除，0未删除
     */
    public static final String DEFAULT_DELETE_LOGIC_KEY = "del_flag";
}
