package top.zsmile.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;
import top.zsmile.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 系统配置
 */
@ApiModel(value="系统配置", description = "系统配置")
public class SysConfigEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @ApiModelProperty(value = "Id，更新时需要传")
    private Long id;
    /**
    * 配置名称
    */
    @ApiModelProperty(value = "配置名称", hidden=false)
    private String configName;
    /**
    * 配置key
    */
    @ApiModelProperty(value = "配置key", hidden=false)
    private String configKey;
    /**
    * 配置类型，1text,2json
    */
    @ApiModelProperty(value = "配置类型，1text,2json", hidden=false)
    private Integer configType;
    /**
    * 配置信息
    */
    @ApiModelProperty(value = "配置信息", hidden=false)
    private String configValue;
    /**
    * 是否启用，0启用1禁用
    */
    @ApiModelProperty(value = "是否启用，0启用1禁用", hidden=false)
    private Integer status;

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
    public Integer getStatus(){
        return this.status;
    }
    /**
    * 是否启用，0启用1禁用
    */
    public void setStatus(Integer status){
        this.status = status;
    }
}
