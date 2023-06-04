package top.zsmile.common.datascope.handle;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import top.zsmile.common.datascope.DataScopePerm;

/**
 * 数据域处理抽象行为
 */
public interface DataScopeHandle {

    /**
     * 处理SQL
     *
     * @param ms            映射执行类
     * @param boundSql      SQL信息
     * @param dataScopePerm 数据配置
     * @return 返回处理后了的Sql语句
     */
    String handle(MappedStatement ms, BoundSql boundSql, DataScopePerm dataScopePerm);
}
