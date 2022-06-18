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
    protected int size = 10;

    /**
     * 当前页，从1算起
     */
    protected long current = 1;

    /**
     * 排序字段
     */
    protected String[] orderColumn = null;

    /**
     * 正序
     */
    protected boolean asc = false;

    public Page() {
    }

    /**
     * 分页构造函数
     *
     * @param current 当前页
     * @param size    每页显示条数
     */
    public Page(long current, int size) {
        this(current, size, 0);
    }

    public Page(long current, int size, long total) {
        this(current, size, total, true);
    }

    public Page(long current, int size, boolean searchCount) {
        this(current, size, 0, searchCount);
    }

    public Page(long current, int size, long total, boolean searchCount) {
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
    public int getSize() {
        return this.size;
    }

    @Override
    public IPage<T> setSize(int size) {
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

    @Override
    public String[] getOrderColumn() {
        return this.orderColumn;
    }

    @Override
    public IPage<T> setOrderColumn(String[] orderColumn) {
        this.orderColumn = orderColumn;
        return this;
    }

}