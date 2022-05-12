package top.zsmile.modules.sys.entity;

import top.zsmile.annotation.TableField;
import top.zsmile.annotation.TableId;
import top.zsmile.annotation.TableLogic;
import top.zsmile.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

@TableName("sys_dict")
public class SysDictEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
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
     * 是否删除，1是，0否
     */
    @TableLogic
    private Integer delFlag;


    /**
     * ID
     */
    public Long getId() {
        return this.id;
    }

    /**
     * ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 字典编码
     */
    public String getDictCode() {
        return this.dictCode;
    }

    /**
     * 字典编码
     */
    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    /**
     * 字典名称
     */
    public String getDictName() {
        return this.dictName;
    }

    /**
     * 字典名称
     */
    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    /**
     * 备注
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 创建时间
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 创建人
     */
    public String getCreateBy() {
        return this.createBy;
    }

    /**
     * 创建人
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 更新时间
     */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 更新人
     */
    public String getUpdateBy() {
        return this.updateBy;
    }

    /**
     * 更新人
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 是否删除，1是，0否
     */
    public Integer getDelFlag() {
        return this.delFlag;
    }

    /**
     * 是否删除，1是，0否
     */
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
