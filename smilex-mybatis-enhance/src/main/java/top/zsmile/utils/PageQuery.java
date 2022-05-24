package top.zsmile.utils;

import top.zsmile.meta.IPage;
import top.zsmile.meta.Page;

import java.util.Map;

public class PageQuery<T> {
    public IPage<T> getPage(Map<String, Object> params) {
        // 分页参数
        long current = 1;
        long size = 10;

        if (params.get(Constants.PAGE) != null) {
            current = Long.parseLong((String) params.get(Constants.PAGE));
        }
        if (params.get(Constants.SIZE) != null) {
            size = Long.parseLong((String) params.get(Constants.SIZE));
        }

        //分页对象
        Page<T> page = new Page<>(current, size);

        return page;
    }

}
