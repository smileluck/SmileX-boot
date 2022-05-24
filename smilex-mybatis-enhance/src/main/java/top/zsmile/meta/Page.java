package top.zsmile.meta;

import java.util.Collections;
import java.util.List;

public class Page<T> implements IPage<T> {
    private static final long serialVersionUID = 1L;

    /**
     * 查询数据列表
     */
    protected List<T> records = Collections.EMPTY_LIST;

    /**
     * 总数
     */
    protected long total = 0;

    /**
     * 每页显示条数，默认10
     */
    protected long size = 10;

    /**
     * 当前页，从1算起
     */
    protected long current = 1;

    public Page() {
    }
    /**
     * 分页构造函数
     *
     * @param current 当前页
     * @param size    每页显示条数
     */
    public Page(long current, long size) {
        this(current, size, 0);
    }

    public Page(long current, long size, long total) {
        this(current, size, total, true);
    }

    public Page(long current, long size, boolean searchCount) {
        this(current, size, 0, searchCount);
    }

    public Page(long current, long size, long total, boolean searchCount) {
        if (current > 1) {
            this.current = current;
        }
        this.size = size;
        this.total = total;
    }

    public boolean hasPrev() {
        return this.current > 1;
    }

    public boolean hasNext() {
        return this.current < getPages();
    }

    @Override
    public List<T> getRecords() {
        return this.records;
    }

    @Override
    public IPage<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    @Override
    public long getTotal() {
        return this.total;
    }

    @Override
    public IPage<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public IPage<T> setSize(long size) {
        this.size = size;
        return this;
    }

    @Override
    public long getCurrent() {
        return this.current;
    }

    @Override
    public IPage<T> setCurrent(long current) {
        this.current = current;
        return this;
    }
}
