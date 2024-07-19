package top.zsmile.modules.sys.entity;

import java.io.Serializable;

import top.zsmile.common.mybatis.annotation.TableName;
import top.zsmile.common.mybatis.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 数据字典
 */
@TableName("sys_dict")
@Schema(description = "数据字典")
public class SysDictEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @Schema(description = "Id，更新时需要传")
    private Long id;
    /**
    * 字典编码
    */
    @Schema(description = "字典编码", hidden=false)
    private String dictCode;
    /**
    * 字典名称
    */
    @Schema(description = "字典名称", hidden=false)
    private String dictName;
    /**
    * 备注
    */
    @Schema(description = "备注", hidden=false)
    private String remark;

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
    * 字典编码
    */
    public String getDictCode(){
        return this.dictCode;
    }
    /**
    * 字典编码
    */
    public void setDictCode(String dictCode){
        this.dictCode = dictCode;
    }
    /**
    * 字典名称
    */
    public String getDictName(){
        return this.dictName;
    }
    /**
    * 字典名称
    */
    public void setDictName(String dictName){
        this.dictName = dictName;
    }
    /**
    * 备注
    */
    public String getRemark(){
        return this.remark;
    }
    /**
    * 备注
    */
    public void setRemark(String remark){
        this.remark = remark;
    }
}
