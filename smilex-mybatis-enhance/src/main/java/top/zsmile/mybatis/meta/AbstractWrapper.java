package top.zsmile.mybatis.meta;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 抽象类
 * 基本方法
 */
public abstract class AbstractWrapper {

    protected Map<SqlKeyword, Object> maps = new LinkedHashMap<>();

    public AbstractWrapper where(String... condition) {
        maps.put(SqlKeyword.WHERE, condition);
        return this;
    }

    public AbstractWrapper and(String... condition) {
        maps.put(SqlKeyword.AND, condition);
        return this;
    }

    public AbstractWrapper or(String... condition) {
        maps.put(SqlKeyword.OR, condition);
        return this;
    }

    public AbstractWrapper groupBy(String[] condition) {
        maps.put(SqlKeyword.GROUP_BY, condition);
        return this;
    }

    public AbstractWrapper having(String[] condition) {
        maps.put(SqlKeyword.HAVING, condition);
        return this;
    }


}
