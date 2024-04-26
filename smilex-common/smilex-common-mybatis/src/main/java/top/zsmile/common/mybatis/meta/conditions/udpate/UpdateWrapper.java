package top.zsmile.common.mybatis.meta.conditions.udpate;

import top.zsmile.common.mybatis.meta.StringPool;
import top.zsmile.common.mybatis.meta.conditions.AbstractUpdateWrapper;
import top.zsmile.common.mybatis.meta.conditions.AbstractWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/04/25/17:07
 * @ClassName: UpdateWrapper
 * @Description: UpdateWrapper
 */
public class UpdateWrapper<E> extends AbstractUpdateWrapper<E, String, UpdateWrapper<E>>
        implements Update<UpdateWrapper<E>, E, String> {

    public UpdateWrapper() {
        super.init();
    }

    @Override
    public UpdateWrapper<E> set(boolean condition, String column, Object obj) {
        if (condition) {
            setList.add(column + StringPool.EQUALS + formatValue(StringPool.ZERO_INDEX, obj));
        }
        return _this;
    }

}
