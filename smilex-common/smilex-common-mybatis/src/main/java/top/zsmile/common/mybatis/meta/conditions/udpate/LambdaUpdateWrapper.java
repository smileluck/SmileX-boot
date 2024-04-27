package top.zsmile.common.mybatis.meta.conditions.udpate;

import top.zsmile.common.mybatis.meta.SFunction;
import top.zsmile.common.mybatis.meta.StringPool;
import top.zsmile.common.mybatis.meta.conditions.AbstractUpdateWrapper;
import top.zsmile.common.mybatis.meta.conditions.AbstractWrapper;
import top.zsmile.common.mybatis.service.BaseService;
import top.zsmile.common.mybatis.utils.LambdaUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/04/25/17:07
 * @ClassName: UpdateWrapper
 * @Description: UpdateWrapper
 */
public class LambdaUpdateWrapper<E> extends AbstractUpdateWrapper<E, SFunction<E, ?>, LambdaUpdateWrapper<E>>
        implements LambdaUpdate<LambdaUpdateWrapper<E>, E, SFunction<E, ?>> {

    private BaseService service;

    public LambdaUpdateWrapper(BaseService<E> service) {
        super.init();
        this.service = service;
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
        return Arrays.asList(column).stream().map(LambdaUtils::getColumnName).collect(Collectors.joining(StringPool.COMMA));
    }

//    @Override
//    public int update() {
//        return service.update(this);
//    }
}
