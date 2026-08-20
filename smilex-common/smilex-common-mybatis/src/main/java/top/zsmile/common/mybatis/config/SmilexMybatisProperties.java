package top.zsmile.common.mybatis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashSet;
import java.util.Set;

/**
 * smilex.mybatis 前缀配置
 */
@ConfigurationProperties(prefix = "smilex.mybatis")
public class SmilexMybatisProperties {

    /**
     * 实体/Mapper 扫描根包，用于启动时预热 TableInfoCache
     */
    private String basePackage = "top.zsmile";

    /**
     * 字段加密密钥（Base64 编码的 AES 密钥），留空表示不启用加密
     */
    private String encryptKey = "";

    /**
     * 分页单页最大条数，防止超大 LIMIT 拖垮数据库
     */
    private int maxPageSize = 500;

    /**
     * 租户拦截器忽略的表名（小写）
     */
    private Set<String> tenantIgnoreTables = new HashSet<>();

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getEncryptKey() {
        return encryptKey;
    }

    public void setEncryptKey(String encryptKey) {
        this.encryptKey = encryptKey;
    }

    public int getMaxPageSize() {
        return maxPageSize;
    }

    public void setMaxPageSize(int maxPageSize) {
        this.maxPageSize = maxPageSize;
    }

    public Set<String> getTenantIgnoreTables() {
        return tenantIgnoreTables;
    }

    public void setTenantIgnoreTables(Set<String> tenantIgnoreTables) {
        this.tenantIgnoreTables = tenantIgnoreTables;
    }
}
