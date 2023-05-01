package top.zsmile.mybatis.meta.conditions.query;

import top.zsmile.mybatis.meta.SFunction;
import top.zsmile.mybatis.meta.conditions.AbstractWrapper;
import top.zsmile.mybatis.meta.conditions.SharedString;
import top.zsmile.mybatis.utils.LambdaUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class LambdaQueryWrapper<E> extends AbstractWrapper<E, SFunction, LambdaQueryWrapper<E>>
        implements Query<LambdaQueryWrapper<E>, E, SFunction> {

    private SharedString sqlSelect = SharedString.of();

    public LambdaQueryWrapper() {
        super.init();
    }

    @Override
    public LambdaQueryWrapper<E> select(SFunction... columns) {
        sqlSelect.setValue(Arrays.stream(columns).map(LambdaUtils::getColumnName).collect(Collectors.joining(",")));
        return _this;
    }
}
