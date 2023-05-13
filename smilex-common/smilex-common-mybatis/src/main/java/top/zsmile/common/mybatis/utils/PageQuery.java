package top.zsmile.common.mybatis.utils;

import top.zsmile.common.mybatis.meta.IPage;
import top.zsmile.common.mybatis.meta.Page;

import java.util.Map;

public class PageQuery<T> {
    public IPage<T> getPage(Map<String, Object> params) {
        return getPage(params, false);
    }

    public IPage<T> getPage(Map<String, Object> params, boolean isAsc, String... orderColumn) {
        // 分页参数
        int current = 1;
        Integer size = 10;

        if (params.get(Constants.PAGE) != null) {
            current = Integer.parseInt((String) params.get(Constants.PAGE));
        }
        if (params.get(Constants.SIZE) != null) {
            size = Integer.parseInt((String) params.get(Constants.SIZE));
        }

        //分页对象
        Page<T> page = new Page<T>(current, size);
        if (null != orderColumn) {
            page.setOrderColumn(orderColumn);
        }

        return page;
    }

}
