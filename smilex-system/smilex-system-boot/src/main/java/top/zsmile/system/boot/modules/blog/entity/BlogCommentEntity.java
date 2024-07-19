package top.zsmile.system.boot.modules.blog.entity;

import java.io.Serializable;

import top.zsmile.common.mybatis.annotation.TableName;
import top.zsmile.common.mybatis.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 租户博客评论
 */
@TableName("blog_comment")
@Schema(description = "租户博客评论")
public class BlogCommentEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @Schema(description = "Id，更新时需要传")
    private Long id;
    /**
    * 租户ID
    */
    @Schema(description = "租户ID", hidden=false)
    private Long tenantId;
    /**
    * 开放用户ID
    */
    @Schema(description = "开放用户ID", hidden=false)
    private Long openUserId;
    /**
    * 评论内容
    */
    @Schema(description = "评论内容", hidden=false)
    private String content;

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
    * 租户ID
    */
    public Long getTenantId(){
        return this.tenantId;
    }
    /**
    * 租户ID
    */
    public void setTenantId(Long tenantId){
        this.tenantId = tenantId;
    }
    /**
    * 开放用户ID
    */
    public Long getOpenUserId(){
        return this.openUserId;
    }
    /**
    * 开放用户ID
    */
    public void setOpenUserId(Long openUserId){
        this.openUserId = openUserId;
    }
    /**
    * 评论内容
    */
    public String getContent(){
        return this.content;
    }
    /**
    * 评论内容
    */
    public void setContent(String content){
        this.content = content;
    }
}
