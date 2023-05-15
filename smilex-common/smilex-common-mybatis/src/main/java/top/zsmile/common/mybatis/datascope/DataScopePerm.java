package top.zsmile.common.mybatis.datascope;

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

    /**
     * 过滤字段
     */
    private String fieldName;
    /**
     * 是否过滤
     */
    private Boolean needFilter;
    /**
     * 过滤表
     */
    private String[] filterTable;
    /**
     * 是否过滤更新
     */
    private Boolean opUpdate;
    /**
     * 是否过滤删除
     */
    private Boolean opQuery;
    /**
     * 数据权限处理方式
     */
    private String handleKey;

}
