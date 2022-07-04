package top.zsmile.modules.blog.entity;

import java.io.Serializable;
import java.util.Date;
import top.zsmile.entity.BaseEntity;

public class BlogCommentEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    private Long id;
    /**
    * 租户ID
    */
    private Long tenantId;
    /**
    * 开放用户ID
    */
    private Long openUserId;
    /**
    * 评论内容
    */
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
