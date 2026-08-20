package top.zsmile.tool.gen.constant;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 生成器默认常量
 */
public class DefaultConstants implements Serializable {
    public static final String ENCODING = "UTF-8";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";
    /**
     * 审计四字段由 BaseEntity 提供，生成实体时剔除
     */
    public static final List<String> IGNORE_COLUMN = Collections.unmodifiableList(Arrays.asList("create_time", "update_time", "create_by", "update_by"));
    /**
     * 删除键名，1删除，0未删除
     */
    public static final String DEFAULT_DELETE_LOGIC_KEY = "del_flag";
    /**
     * 租户键名（生成 @TenantId 注解）
     */
    public static final String DEFAULT_TENANT_KEY = "tenant_id";
    /**
     * 表名白名单（防注入/路径穿越）
     */
    public static final Pattern TABLE_NAME_PATTERN = Pattern.compile("^[A-Za-z0-9_]+$");
    /**
     * 模块名白名单（防路径穿越）
     */
    public static final Pattern MODULE_NAME_PATTERN = Pattern.compile("^[A-Za-z0-9_-]+$");
    /**
     * 数据库主机地址白名单（主机名/IP，杀 SSRF 面）
     */
    public static final Pattern DB_ADDRESS_PATTERN = Pattern.compile("^[A-Za-z0-9._-]+$");
    /**
     * 支持的数据库连接类型
     */
    public static final List<String> SUPPORTED_DB_TYPES = Collections.singletonList("mysql");
    /**
     * JDBC 连接参数黑名单（防恶意参数）
     */
    public static final List<String> DANGEROUS_JDBC_PARAMS = Collections.unmodifiableList(Arrays.asList(
            "autoDeserialize", "allowLoadLocalInfile", "allowUrlInLocalInfile", "allowLoadLocalInfileInPrefDir"));
    /**
     * 全部可用模板类型（预览接口/生成选择白名单）
     */
    public static final List<String> TEMPLATE_TYPES = Collections.unmodifiableList(Arrays.asList(
            "entity", "mapper", "service", "serviceimpl", "controller", "xml", "vue", "vuemodel", "sql"));
    /**
     * 默认生成的模板类型（全部）
     */
    public static final List<String> DEFAULT_TEMPLATE_TYPES = TEMPLATE_TYPES;
}
