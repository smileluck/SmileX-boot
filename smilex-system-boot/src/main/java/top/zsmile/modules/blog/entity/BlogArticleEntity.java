package top.zsmile.modules.blog.entity;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import top.zsmile.entity.BaseEntity;

public class BlogArticleEntity extends BaseEntity implements Serializable {
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
    * 栏目ID
    */
    private Long sectionId;
    /**
    * 标签id，以,分割
    */
    private String tagIds;
    /**
    * 文章标题
    */
    private String articleTitle;
    /**
    * 文章内容
    */
    private String articleContent;
    /**
    * 语法类型，1markdown，2html
    */
    private Integer grammarType;
    /**
    * 访问类型,1通用类型，2统一密码，3独立密码
    */
    private Integer visitType;
    /**
    * 独立密码
    */
    @JSONField(serialize = false)
    private String password;
    /**
    * salt
    */
    @JSONField(serialize = false)
    private String salt;

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
    * 栏目ID
    */
    public Long getSectionId(){
    return this.sectionId;
    }
    /**
    * 栏目ID
    */
    public void setSectionId(Long sectionId){
    this.sectionId = sectionId;
    }
    /**
    * 标签id，以,分割
    */
    public String getTagIds(){
    return this.tagIds;
    }
    /**
    * 标签id，以,分割
    */
    public void setTagIds(String tagIds){
    this.tagIds = tagIds;
    }
    /**
    * 文章标题
    */
    public String getArticleTitle(){
    return this.articleTitle;
    }
    /**
    * 文章标题
    */
    public void setArticleTitle(String articleTitle){
    this.articleTitle = articleTitle;
    }
    /**
    * 文章内容
    */
    public String getArticleContent(){
    return this.articleContent;
    }
    /**
    * 文章内容
    */
    public void setArticleContent(String articleContent){
    this.articleContent = articleContent;
    }
    /**
    * 语法类型，1markdown，2html
    */
    public Integer getGrammarType(){
    return this.grammarType;
    }
    /**
    * 语法类型，1markdown，2html
    */
    public void setGrammarType(Integer grammarType){
    this.grammarType = grammarType;
    }
    /**
    * 访问类型,1通用类型，2统一密码，3独立密码
    */
    public Integer getVisitType(){
    return this.visitType;
    }
    /**
    * 访问类型,1通用类型，2统一密码，3独立密码
    */
    public void setVisitType(Integer visitType){
    this.visitType = visitType;
    }
    /**
    * 独立密码
    */
    public String getPassword(){
    return this.password;
    }
    /**
    * 独立密码
    */
    public void setPassword(String password){
    this.password = password;
    }
    /**
    * salt
    */
    public String getSalt(){
    return this.salt;
    }
    /**
    * salt
    */
    public void setSalt(String salt){
    this.salt = salt;
    }
}
