package top.zsmile.common.mybatis.meta.conditions.query;

import org.jetbrains.annotations.NotNull;
import top.zsmile.common.core.utils.Asserts;
import top.zsmile.common.mybatis.meta.SFunction;
import top.zsmile.common.mybatis.meta.StringPool;
import top.zsmile.common.mybatis.meta.conditions.AbstractQueryWrapper;
import top.zsmile.common.mybatis.meta.conditions.AbstractWrapper;
import top.zsmile.common.mybatis.meta.conditions.SharedString;
import top.zsmile.common.mybatis.service.BaseService;
import top.zsmile.common.mybatis.utils.LambdaUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LambdaQueryWrapper<E> extends AbstractQueryWrapper<E, SFunction<E, ?>, LambdaQueryWrapper<E>>
        implements LambdaQuery<LambdaQueryWrapper<E>, E, SFunction<E, ?>> {

    private static final long serialVersionUID = 1L;
    private BaseService<E> service;

    public LambdaQueryWrapper(BaseService<E> service) {
        super.init();
        this.service = service;
    }

    @Override
    public LambdaQueryWrapper<E> select(@NotNull SFunction<E, ?>... columns) {
        sqlSelect.setValue(Arrays.stream(columns).map(LambdaUtils::getColumnName).collect(Collectors.joining(StringPool.COMMA)));
        return _this;
    }


    @Override
    protected String columnToString(SFunction<E, ?> column) {
        return LambdaUtils.getColumnName(column);
    }

    @Override
    protected String columnToString(@NotNull SFunction<E, ?>... column) {
        return Arrays.asList(column).stream().map(LambdaUtils::getColumnName).collect(Collectors.joining(StringPool.COMMA));
    }

//    @Override
//    public List<E> list() {
//        return service.list(this);
//    }
}
