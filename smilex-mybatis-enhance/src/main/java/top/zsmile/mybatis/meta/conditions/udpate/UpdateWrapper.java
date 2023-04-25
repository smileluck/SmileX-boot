package top.zsmile.mybatis.meta.conditions.udpate;

import top.zsmile.mybatis.meta.StringPool;
import top.zsmile.mybatis.meta.conditions.AbstractWrapper;
import top.zsmile.mybatis.meta.conditions.query.Query;
import top.zsmile.mybatis.meta.conditions.query.QueryWrapper;

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
public class UpdateWrapper<E> extends AbstractWrapper<E, String, UpdateWrapper<E>>
        implements Update<UpdateWrapper<E>, E, String> {

    List<String> setList;

    public UpdateWrapper() {
        setList = new ArrayList<>();
    }

    @Override
    public UpdateWrapper<E> set(boolean condition, String column, Object obj) {
        if (condition) {
            setList.add(column + StringPool.EQUALS + formatValue(StringPool.ZERO_INDEX, obj));
        }
        return _this;
    }

    @Override
    public String getSqlSet() {
        if (setList.isEmpty()) {
            return StringPool.EMPTY;
        }
        return setList.stream().collect(Collectors.joining(StringPool.COMMA, StringPool.SPACE, StringPool.SPACE));
    }
}
