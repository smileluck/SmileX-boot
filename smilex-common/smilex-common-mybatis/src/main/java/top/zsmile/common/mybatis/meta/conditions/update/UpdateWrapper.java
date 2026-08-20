package top.zsmile.common.mybatis.meta.conditions.update;

import top.zsmile.common.mybatis.meta.StringPool;
import top.zsmile.common.mybatis.meta.conditions.AbstractUpdateWrapper;

/**
 * 更新实体封装（字符串列名）
 */
public class UpdateWrapper<E> extends AbstractUpdateWrapper<E, String, UpdateWrapper<E>>
        implements Update<UpdateWrapper<E>, E, String> {

    public UpdateWrapper() {
        super.init();
    }

    @Override
    public UpdateWrapper<E> set(boolean condition, String column, Object obj) {
        if (condition) {
            setList.add(columnToString(column) + StringPool.EQUALS + formatValue(StringPool.ZERO_INDEX, obj));
        }
        return _this;
    }

    @Override
    protected UpdateWrapper<E> newChildren() {
        return new UpdateWrapper<E>();
    }
}
