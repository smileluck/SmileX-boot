package top.zsmile.common.mybatis.meta.conditions;

import top.zsmile.common.mybatis.meta.StringPool;
import top.zsmile.common.mybatis.meta.conditions.query.Query;
import top.zsmile.common.mybatis.meta.conditions.udpate.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @param <E>        注入的Entity实体
 * @param <R>        使用的COLUMN字段类型，主要为String和SFunciton
 * @param <Children> 当前实体，this
 */
public abstract class AbstractUpdateWrapper<E, R, Children extends AbstractUpdateWrapper<E, R, Children>> extends AbstractWrapper<E, R, Children>
        implements Update<Children, E, R> {


    protected List<String> setList;

    @Override
    protected void init() {
        super.init();
        setList = new ArrayList<>();
    }

    @Override
    public String getSqlSet() {
        if (setList.isEmpty()) {
            return StringPool.EMPTY;
        }
        return setList.stream().collect(Collectors.joining(StringPool.COMMA, StringPool.SPACE, StringPool.SPACE));
    }
}
