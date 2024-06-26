package top.zsmile.common.mybatis.meta.conditions.query;

import top.zsmile.common.mybatis.meta.StringPool;
import top.zsmile.common.mybatis.meta.conditions.AbstractQueryWrapper;
import top.zsmile.common.mybatis.meta.conditions.AbstractWrapper;
import top.zsmile.common.mybatis.meta.conditions.SharedString;

/**
 * 查询实体封装
 *
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/04/24/14:50
 * @ClassName: QueryWrapper
 * @Description: QueryWrapper
 */
public class QueryWrapper<E> extends AbstractQueryWrapper<E, String, QueryWrapper<E>>
        implements Query<QueryWrapper<E>, E, String> {

    public QueryWrapper() {
        super.init();
    }

    public QueryWrapper(E entity) {
        super.setEntity(entity);
        super.init();
    }

    public QueryWrapper(E entity, String... columns) {
        this(entity);
        this.select(columns);
    }

    @Override
    public QueryWrapper<E> select(String... columns) {
        sqlSelect.setValue(String.join(StringPool.COMMA, columns));
        return _this;
    }
}
