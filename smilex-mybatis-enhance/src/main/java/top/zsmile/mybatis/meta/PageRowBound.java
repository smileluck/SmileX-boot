package top.zsmile.mybatis.meta;

import top.zsmile.common.utils.StringPool;

public class PageRowBound {
    /**
     * 每页显示条数，默认10
     */
    private int size = 10;

    /**
     * 当前页，从1算起
     */
    private long current = 1;

    public PageRowBound(long current, int size) {
        this.current = current;
        this.size = size;
    }

    public String toString() {
        return "limit " + offset() + StringPool.COMMA + this.size;
    }

    private long offset() {
        return (this.current - 1) * this.size;
    }
}
