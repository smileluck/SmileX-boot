package top.zsmile.modules.blog.entity;

import java.io.Serializable;
import java.util.Date;
import top.zsmile.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 博客时间线
 */
@ApiModel(value="博客时间线", description = "博客时间线")
public class BlogTimelineEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @ApiModelProperty(value = "Id，更新时需要传")
    private Long id;
    /**
    * 年份
    */
    @ApiModelProperty(value = "年份", hidden=false)
    private String year;
    /**
    * 标题
    */
    @ApiModelProperty(value = "标题", hidden=false)
    private String title;
    /**
    * 说明
    */
    @ApiModelProperty(value = "说明", hidden=false)
    private String description;

    /**
    * 获取：ID
    */
    public Long getId(){
        return this.id;
    }
    /**
    * 设置：ID
    */
    public void setId(Long id){
        this.id = id;
    }
    /**
    * 获取：年份
    */
    public String getYear(){
        return this.year;
    }
    /**
    * 设置： 年份
    */
    public void setYear(String year){
        this.year = year;
    }
    /**
    * 获取：标题
    */
    public String getTitle(){
        return this.title;
    }
    /**
    * 设置： 标题
    */
    public void setTitle(String title){
        this.title = title;
    }
    /**
    * 获取：说明
    */
    public String getDescription(){
        return this.description;
    }
    /**
    * 设置： 说明
    */
    public void setDescription(String description){
        this.description = description;
    }
}
