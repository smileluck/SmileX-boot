package top.zsmile.system.modules.open.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "博客文章列表", description = "博客文章列表")
public class BlogArticleVo {
    /**
     * ID
     */
    @ApiModelProperty(value = "Id，更新时需要传")
    private Long id;
    /**
     * 栏目ID
     */
    @ApiModelProperty(value = "栏目ID", hidden = false)
    private Long sectionId;
    /**
     * 栏目名称
     */
    @ApiModelProperty(value = "栏目名称", hidden = false)
    private String sectionName;
    /**
     * 标签id，以,分割
     */
    @ApiModelProperty(value = "标签id，以,分割", hidden = false)
    private String tagIds;
    /**
     * 标签名称，以,分割
     */
    @ApiModelProperty(value = "标签名称，以,分割", hidden = false)
    private String tagNames;
    /**
     * 文章封面
     */
    @ApiModelProperty(value = "文章封面", hidden = false)
    private String poster;
    /**
     * 文章标题
     */
    @ApiModelProperty(value = "文章标题", hidden = false)
    private String articleTitle;
    /**
     * 文章简介
     */
    @ApiModelProperty(value = "文章简介", hidden = false)
    private String articleDigest;
    /**
     * 文章内容
     */
    @ApiModelProperty(value = "文章内容", hidden = false)
    private String articleContent;
    /**
     * 访问类型,1通用类型，2统一密码，3独立密码
     */
    @ApiModelProperty(value = "访问类型,1通用类型，2统一密码，3独立密码", hidden = false)
    private Integer visitType;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", hidden = true)
    private Date updateTime;
}
