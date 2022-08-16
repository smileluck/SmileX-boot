package top.zsmile.meta;

import java.util.LinkedHashMap;
import java.util.Map;

public class QueryWrapper extends AbstractWrapper {
    private String[] selectColumn = null;

    private String fromTable = null;

    public AbstractWrapper select(String... column) {
        selectColumn = column;
        return this;
    }

    public AbstractWrapper from(String fromTable) {
        fromTable = fromTable;
        return this;
    }

    public AbstractWrapper orderBy(String... condition) {
        maps.put(SqlKeyword.ORDER_BY, condition);
        return this;
    }

    public AbstractWrapper join(String... condition) {
        maps.put(SqlKeyword.JOIN, condition);
        return this;
    }

    public AbstractWrapper leftJoin(String... condition) {
        maps.put(SqlKeyword.LEFT_JOIN, condition);
        return this;
    }

    public AbstractWrapper rightJoin(String... condition) {
        maps.put(SqlKeyword.RIGHT_JOIN, condition);
        return this;
    }

}
