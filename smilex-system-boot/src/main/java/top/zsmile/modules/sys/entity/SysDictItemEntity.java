package top.zsmile.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;

public class SysDictItemEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * 是否启用，1启用2金庸
    */
    private Long id;
    /**
    * 字典ID
    */
    private Long dictId;
    /**
    * 字典编码
    */
    private String dictCode;
    /**
    * 数据值
    */
    private String dictValue;
    /**
    * 数据显示项
    */
    private String dictLabel;
    /**
    * 备注
    */
    private String remark;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 创建人
    */
    private String createBy;
    /**
    * 更新时间
    */
    private Date updateTime;
    /**
    * 更新人
    */
    private String updateBy;

    /**
    * 是否启用，1启用2金庸
    */
    public Long getId(){
    return this.id;
    }
    /**
    * 是否启用，1启用2金庸
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
    /**
    * 创建时间
    */
    public Date getCreateTime(){
    return this.createTime;
    }
    /**
    * 创建时间
    */
    public void setCreateTime(Date createTime){
    this.createTime = createTime;
    }
    /**
    * 创建人
    */
    public String getCreateBy(){
    return this.createBy;
    }
    /**
    * 创建人
    */
    public void setCreateBy(String createBy){
    this.createBy = createBy;
    }
    /**
    * 更新时间
    */
    public Date getUpdateTime(){
    return this.updateTime;
    }
    /**
    * 更新时间
    */
    public void setUpdateTime(Date updateTime){
    this.updateTime = updateTime;
    }
    /**
    * 更新人
    */
    public String getUpdateBy(){
    return this.updateBy;
    }
    /**
    * 更新人
    */
    public void setUpdateBy(String updateBy){
    this.updateBy = updateBy;
    }
}