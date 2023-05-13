package top.zsmile.common.mybatis.meta;

import java.io.Serializable;
import java.util.List;

public interface IPage<T> extends Serializable {

    List<T> getRecords();

    IPage<T> setRecords(List<T> records);

    /**
     * 当前分页总页数
     */
    default long getPages() {
        if (getSize() == 0) {
            return 0L;
        }
        long pages = getTotal() / getSize();
        if (getTotal() % getSize() != 0) {
            pages++;
        }
        return pages;
    }

    /**
     * 当前满足条件总行数
     *
     * @return 总条数
     */
    long getTotal();

    /**
     * 设置当前满足条件总行数
     */
    IPage<T> setTotal(long total);

    /**
     * 获取每页显示条数
     *
     * @return 每页显示条数
     */
    int getSize();

    /**
     * 设置每页显示条数
     */
    IPage<T> setSize(int size);

    /**
     * 当前页
     *
     * @return 当前页
     */
    int getCurrent();

    /**
     * 设置当前页
     */
    IPage<T> setCurrent(int current);

    /**
     * 获取排序字段
     *
     * @return 字段列表
     */
    String[] getOrderColumn();

    /**
     * 设置排序字段
     */
    IPage<T> setOrderColumn(String[] orderColumn);

    default long getOffset() {
        return (getCurrent() - 1) * getSize();
    }
}
