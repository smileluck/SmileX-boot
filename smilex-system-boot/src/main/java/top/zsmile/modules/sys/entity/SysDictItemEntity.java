package top.zsmile.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;

import top.zsmile.annotation.TableName;
import top.zsmile.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 数据字典信息
 */
@TableName("sys_dict_item")
@ApiModel(value="数据字典信息", description = "数据字典信息")
public class SysDictItemEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @ApiModelProperty(value = "Id，更新时需要传")
    private Long id;
    /**
    * 字典ID
    */
    @ApiModelProperty(value = "字典ID", hidden=false)
    private Long dictId;
    /**
    * 字典编码
    */
    @ApiModelProperty(value = "字典编码", hidden=false)
    private String dictCode;
    /**
    * 数据值
    */
    @ApiModelProperty(value = "数据值", hidden=false)
    private String dictValue;
    /**
    * 数据显示项
    */
    @ApiModelProperty(value = "数据显示项", hidden=false)
    private String dictLabel;
    /**
    * 备注
    */
    @ApiModelProperty(value = "备注", hidden=false)
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
    * 字典ID
    */
    public Long getDictId(){
        return this.dictId;
    }
    /**
    * 字典ID
    */
    public void setDictId(Long dictId){
        this.dictId = dictId;
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
    * 数据值
    */
    public String getDictValue(){
        return this.dictValue;
    }
    /**
    * 数据值
    */
    public void setDictValue(String dictValue){
        this.dictValue = dictValue;
    }
    /**
    * 数据显示项
    */
    public String getDictLabel(){
        return this.dictLabel;
    }
    /**
    * 数据显示项
    */
    public void setDictLabel(String dictLabel){
        this.dictLabel = dictLabel;
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
