package top.zsmile.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(description = "基础类")
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    private Date createTime;
    /**
     * 创建人
     */
    @ApiModelProperty(hidden = true)
    private String createBy;
    /**
     * 更新时间
     */
    @ApiModelProperty(hidden = true)
    private Date updateTime;
    /**
     * 更新人
     */
    @ApiModelProperty(hidden = true)
    private String updateBy;
    /**
     * 是否删除，1是，0否
     */
    @ApiModelProperty(hidden = true)
    private Integer delFlag;

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

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
