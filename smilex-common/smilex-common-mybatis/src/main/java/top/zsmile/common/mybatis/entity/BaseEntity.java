package top.zsmile.common.mybatis.entity;

//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

//@Schema(description = "基础类")
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     */
//    @Schema(description = "创建时间", hidden = true)
    private LocalDateTime createTime;
    /**
     * 创建人
     */
//    @Schema(description = "创建用户", hidden = true)
    private String createBy;
    /**
     * 更新时间
     */
//    @Schema(description = "更新时间", hidden = true)
    private LocalDateTime updateTime;
    /**
     * 更新人
     */
//    @Schema(description = "更新用户", hidden = true)
    private String updateBy;
    /**
     * 是否删除，1是，0否
     */
//    @Schema(description = "逻辑删除，1是，0否", hidden = true)
    private Integer delFlag;

    /**
     * 创建时间
     */
    public LocalDateTime getCreateTime() {
        return this.createTime;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(LocalDateTime createTime) {
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
    public LocalDateTime getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 更新时间
     */
    public void setUpdateTime(LocalDateTime updateTime) {
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
        return delFlag;
    }

    /**
     * 是否删除，1是，0否
     */
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
