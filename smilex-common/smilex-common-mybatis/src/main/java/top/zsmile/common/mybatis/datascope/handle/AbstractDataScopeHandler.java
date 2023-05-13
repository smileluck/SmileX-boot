package top.zsmile.common.mybatis.datascope.handle;

import top.zsmile.common.mybatis.datascope.DataScopePerm;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;

import java.util.Map;

/**
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/03/06/10:46
 * @ClassName: AbstractHandle
 * @Description: AbstractHandle
 */
public interface AbstractDataScopeHandler {

    /**
     * 不进行操作
     */
    public static final String NIL = "nil";

    /**
     * 根据创建时间
     */
    public static final String CREATE_BY = "createBy";

    /**
     * 处理SQL
     *
     * @param ms
     * @param dataScopePerm
     * @param admin
     * @return
     */
    String handle(MappedStatement ms, BoundSql boundSql, DataScopePerm dataScopePerm, Map admin);

}
