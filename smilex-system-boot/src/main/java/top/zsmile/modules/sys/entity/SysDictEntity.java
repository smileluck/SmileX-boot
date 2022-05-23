package top.zsmile.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;
import top.zsmile.entity.BaseEntity;

public class SysDictEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    private Long id;
    /**
    * 字典编码
    */
    private String dictCode;
    /**
    * 字典名称
    */
    private String dictName;
    /**
    * 备注
    */
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
