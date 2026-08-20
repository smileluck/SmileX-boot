package top.zsmile.common.mybatis.meta.conditions.update;

import top.zsmile.common.mybatis.meta.SFunction;
import top.zsmile.common.mybatis.meta.StringPool;
import top.zsmile.common.mybatis.meta.conditions.AbstractUpdateWrapper;
import top.zsmile.common.mybatis.utils.LambdaUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 更新实体封装（Lambda 列引用）
 */
public class LambdaUpdateWrapper<E> extends AbstractUpdateWrapper<E, SFunction<E, ?>, LambdaUpdateWrapper<E>>
        implements LambdaUpdate<LambdaUpdateWrapper<E>, E, SFunction<E, ?>> {

    public LambdaUpdateWrapper() {
        super.init();
    }

    @Override
    public LambdaUpdateWrapper<E> set(boolean condition, SFunction<E, ?> column, Object obj) {
        if (condition) {
            setList.add(LambdaUtils.getColumnName(column) + StringPool.EQUALS + formatValue(StringPool.ZERO_INDEX, obj));
        }
        return _this;
    }

    @Override
    protected String columnToString(SFunction<E, ?> column) {
        return LambdaUtils.getColumnName(column);
    }

    @Override
    protected String columnToString(SFunction<E, ?>... column) {
        return Arrays.asList(column).stream().map(LambdaUtils::getColumnName)
                .collect(Collectors.joining(StringPool.COMMA));
    }

    @Override
    protected LambdaUpdateWrapper<E> newChildren() {
        return new LambdaUpdateWrapper<E>();
    }
}
