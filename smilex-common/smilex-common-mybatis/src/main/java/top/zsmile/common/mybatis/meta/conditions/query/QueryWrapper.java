package top.zsmile.common.mybatis.meta.conditions.query;

import top.zsmile.common.mybatis.meta.StringPool;
import top.zsmile.common.mybatis.meta.conditions.AbstractQueryWrapper;
import top.zsmile.common.mybatis.utils.TableQueryUtils;

/**
 * 查询实体封装（字符串列名）
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
        for (String column : columns) {
            TableQueryUtils.checkSqlName(column);
        }
        sqlSelect.setValue(String.join(StringPool.COMMA, columns));
        return _this;
    }

    @Override
    protected QueryWrapper<E> newChildren() {
        return new QueryWrapper<E>();
    }
}
