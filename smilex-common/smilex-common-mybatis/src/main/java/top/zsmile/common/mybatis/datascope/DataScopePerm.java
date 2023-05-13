package top.zsmile.common.mybatis.datascope;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/02/24/15:16
 * @ClassName: DataScopePerm
 * @Description: DataScopePerm
 */
@Data
public class DataScopePerm {

    @ApiModelProperty("过滤字段")
    private String fieldName;
    @ApiModelProperty("是否过滤")
    private Boolean needFilter;
    @ApiModelProperty("过滤表")
    private String[] filterTable;
    @ApiModelProperty("是否过滤更新")
    private Boolean opUpdate;
    @ApiModelProperty("是否过滤删除")
    private Boolean opQuery;
    @ApiModelProperty("数据权限处理方式")
    private String handleKey;

}
