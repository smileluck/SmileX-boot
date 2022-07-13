package top.zsmile.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;
import top.zsmile.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 */
@ApiModel(value="", description = "")
public class SysFileEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @ApiModelProperty(value = "Id，更新时需要传")
    private Long id;
    /**
    * 模块，alioss,minio
    */
    @ApiModelProperty(value = "模块，alioss,minio", hidden=false)
    private String modules;
    /**
    * 网络地址
    */
    @ApiModelProperty(value = "网络地址", hidden=false)
    private String netPath;
    /**
    * 文件地址
    */
    @ApiModelProperty(value = "文件地址", hidden=false)
    private String filePath;
    /**
    * 文件大小
    */
    @ApiModelProperty(value = "文件大小", hidden=false)
    private Double fileSize;
    /**
    * 文件后缀
    */
    @ApiModelProperty(value = "文件后缀", hidden=false)
    private String fileSuffix;

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
    * 模块，alioss,minio
    */
    public String getModules(){
        return this.modules;
    }
    /**
    * 模块，alioss,minio
    */
    public void setModules(String modules){
        this.modules = modules;
    }
    /**
    * 网络地址
    */
    public String getNetPath(){
        return this.netPath;
    }
    /**
    * 网络地址
    */
    public void setNetPath(String netPath){
        this.netPath = netPath;
    }
    /**
    * 文件地址
    */
    public String getFilePath(){
        return this.filePath;
    }
    /**
    * 文件地址
    */
    public void setFilePath(String filePath){
        this.filePath = filePath;
    }
    /**
    * 文件大小
    */
    public Double getFileSize(){
        return this.fileSize;
    }
    /**
    * 文件大小
    */
    public void setFileSize(Double fileSize){
        this.fileSize = fileSize;
    }
    /**
    * 文件后缀
    */
    public String getFileSuffix(){
        return this.fileSuffix;
    }
    /**
    * 文件后缀
    */
    public void setFileSuffix(String fileSuffix){
        this.fileSuffix = fileSuffix;
    }
}
