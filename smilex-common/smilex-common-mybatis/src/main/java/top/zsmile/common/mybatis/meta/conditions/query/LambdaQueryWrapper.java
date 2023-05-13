package top.zsmile.common.mybatis.meta.conditions.query;

import top.zsmile.common.mybatis.meta.SFunction;
import top.zsmile.common.mybatis.meta.StringPool;
import top.zsmile.common.mybatis.meta.conditions.AbstractWrapper;
import top.zsmile.common.mybatis.meta.conditions.SharedString;
import top.zsmile.common.mybatis.utils.LambdaUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class LambdaQueryWrapper<E> extends AbstractWrapper<E, SFunction<E, ?>, LambdaQueryWrapper<E>>
        implements Query<LambdaQueryWrapper<E>, E, SFunction<E, ?>> {

    private SharedString sqlSelect = SharedString.of();

    public LambdaQueryWrapper() {
        super.init();
    }

    @Override
    public LambdaQueryWrapper<E> select(SFunction<E, ?>... columns) {
        sqlSelect.setValue(Arrays.stream(columns).map(LambdaUtils::getColumnName).collect(Collectors.joining(StringPool.COMMA)));
        return _this;
    }

    @Override
    protected String columnToString(SFunction<E, ?> column) {
        return LambdaUtils.getColumnName(column);
    }

    @Override
    protected String columnToString(SFunction<E, ?>... column) {
        return Arrays.asList(column).stream().map(LambdaUtils::getColumnName).collect(Collectors.joining(StringPool.COMMA));
    }
}
