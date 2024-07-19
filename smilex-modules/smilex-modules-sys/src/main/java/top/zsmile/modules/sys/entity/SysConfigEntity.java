package top.zsmile.modules.sys.entity;

import java.io.Serializable;

import top.zsmile.common.mybatis.annotation.TableName;
import top.zsmile.common.mybatis.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 系统配置
 */
@TableName("sys_config")
@Schema(description = "系统配置")
public class SysConfigEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @Schema(description = "Id，更新时需要传")
    private Long id;
    /**
    * 配置名称
    */
    @Schema(description = "配置名称", hidden=false)
    private String configName;
    /**
    * 配置key
    */
    @Schema(description = "配置key", hidden=false)
    private String configKey;
    /**
    * 配置类型，1text,2json
    */
    @Schema(description = "配置类型，1text,2json", hidden=false)
    private Integer configType;
    /**
    * 配置信息
    */
    @Schema(description = "配置信息", hidden=false)
    private String configValue;
    /**
    * 是否启用，0启用1禁用
    */
    @Schema(description = "是否启用，0启用1禁用", hidden=false)
    private Integer enableFlag;

    /**
    * ID
    */
    public Long getId(){
        return this.id;
    }
    /**
    * ID
    */
    public void setId(Long id){
        this.id = id;
    }
    /**
    * 配置名称
    */
    public String getConfigName(){
        return this.configName;
    }
    /**
    * 配置名称
    */
    public void setConfigName(String configName){
        this.configName = configName;
    }
    /**
    * 配置key
    */
    public String getConfigKey(){
        return this.configKey;
    }
    /**
    * 配置key
    */
    public void setConfigKey(String configKey){
        this.configKey = configKey;
    }
    /**
    * 配置类型，1text,2json
    */
    public Integer getConfigType(){
        return this.configType;
    }
    /**
    * 配置类型，1text,2json
    */
    public void setConfigType(Integer configType){
        this.configType = configType;
    }
    /**
    * 配置信息
    */
    public String getConfigValue(){
        return this.configValue;
    }
    /**
    * 配置信息
    */
    public void setConfigValue(String configValue){
        this.configValue = configValue;
    }
    /**
    * 是否启用，0启用1禁用
    */
    public Integer getEnableFlag(){
        return this.enableFlag;
    }
    /**
    * 是否启用，0启用1禁用
    */
    public void setEnableFlag(Integer status){
        this.enableFlag = status;
    }
}
