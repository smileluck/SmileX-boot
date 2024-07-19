package top.zsmile.modules.sys.entity;

import java.io.Serializable;

import top.zsmile.common.mybatis.annotation.TableName;
import top.zsmile.common.mybatis.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 系统日志
 */
@TableName("sys_log")
@Schema(description = "系统日志")
public class SysLogEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @Schema(description = "Id，更新时需要传")
    private Long id;
    /**
    * 日志模块，sys,blog
    */
    @Schema(description = "日志模块，sys,blog", hidden=false)
    private String logModule;
    /**
    * 日志标题
    */
    @Schema(description = "日志标题", hidden=false)
    private String logTitle;
    /**
    * 日志内容
    */
    @Schema(description = "日志内容", hidden=false)
    private String logValue;
    /**
    * 日志类型1:登录日志;2:操作日志;3:定时任务;4:异常日志;
    */
    @Schema(description = "日志类型1:登录日志;2:操作日志;3:定时任务;4:异常日志;", hidden=false)
    private Integer logType;
    /**
    * 用户ID
    */
    @Schema(description = "用户ID", hidden=false)
    private Long userId;
    /**
    * 操作类型
    */
    @Schema(description = "操作类型", hidden=false)
    private Integer operateType;
    /**
    * IP地址
    */
    @Schema(description = "IP地址", hidden=false)
    private String ipAddress;
    /**
    * 请求方法
    */
    @Schema(description = "请求方法", hidden=false)
    private String method;
    /**
    * 请求url路径
    */
    @Schema(description = "请求url路径", hidden=false)
    private String requestUrl;
    /**
    * 请求类型
    */
    @Schema(description = "请求类型", hidden=false)
    private String requestType;
    /**
    * 请求参数
    */
    @Schema(description = "请求参数", hidden=false)
    private String requestParams;
    /**
    * 耗费时间
    */
    @Schema(description = "耗费时间", hidden=false)
    private Long costTime;
    /**
    * 异常信息
    */
    @Schema(description = "异常信息", hidden=false)
    private String errMsg;

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
    * 日志模块，sys,blog
    */
    public String getLogModule(){
        return this.logModule;
    }
    /**
    * 日志模块，sys,blog
    */
    public void setLogModule(String logModule){
        this.logModule = logModule;
    }
    /**
    * 日志标题
    */
    public String getLogTitle(){
        return this.logTitle;
    }
    /**
    * 日志标题
    */
    public void setLogTitle(String logTitle){
        this.logTitle = logTitle;
    }
    /**
    * 日志内容
    */
    public String getLogValue(){
        return this.logValue;
    }
    /**
    * 日志内容
    */
    public void setLogValue(String logValue){
        this.logValue = logValue;
    }
    /**
    * 日志类型1:登录日志;2:操作日志;3:定时任务;4:异常日志;
    */
    public Integer getLogType(){
        return this.logType;
    }
    /**
    * 日志类型1:登录日志;2:操作日志;3:定时任务;4:异常日志;
    */
    public void setLogType(Integer logType){
        this.logType = logType;
    }
    /**
    * 用户ID
    */
    public Long getUserId(){
        return this.userId;
    }
    /**
    * 用户ID
    */
    public void setUserId(Long userId){
        this.userId = userId;
    }
    /**
    * 操作类型
    */
    public Integer getOperateType(){
        return this.operateType;
    }
    /**
    * 操作类型
    */
    public void setOperateType(Integer operateType){
        this.operateType = operateType;
    }
    /**
    * IP地址
    */
    public String getIpAddress(){
        return this.ipAddress;
    }
    /**
    * IP地址
    */
    public void setIpAddress(String ipAddress){
        this.ipAddress = ipAddress;
    }
    /**
    * 请求方法
    */
    public String getMethod(){
        return this.method;
    }
    /**
    * 请求方法
    */
    public void setMethod(String method){
        this.method = method;
    }
    /**
    * 请求url路径
    */
    public String getRequestUrl(){
        return this.requestUrl;
    }
    /**
    * 请求url路径
    */
    public void setRequestUrl(String requestUrl){
        this.requestUrl = requestUrl;
    }
    /**
    * 请求类型
    */
    public String getRequestType(){
        return this.requestType;
    }
    /**
    * 请求类型
    */
    public void setRequestType(String requestType){
        this.requestType = requestType;
    }
    /**
    * 请求参数
    */
    public String getRequestParams(){
        return this.requestParams;
    }
    /**
    * 请求参数
    */
    public void setRequestParams(String requestParams){
        this.requestParams = requestParams;
    }
    /**
    * 耗费时间
    */
    public Long getCostTime(){
        return this.costTime;
    }
    /**
    * 耗费时间
    */
    public void setCostTime(Long costTime){
        this.costTime = costTime;
    }
    /**
    * 异常信息
    */
    public String getErrMsg(){
        return this.errMsg;
    }
    /**
    * 异常信息
    */
    public void setErrMsg(String errMsg){
        this.errMsg = errMsg;
    }
}
