package top.zsmile.common.mybatis.utils;

import lombok.extern.slf4j.Slf4j;
import top.zsmile.common.mybatis.meta.IPage;
import top.zsmile.common.mybatis.meta.Page;

import java.util.Map;

/**
 * 从请求参数 Map 中解析分页对象
 * <p>
 * 解析后会将 page/size 键从 Map 中移除，避免分页参数混入查询条件；
 * 非法数字回退默认值（page=1, size=10），size 上限由分页拦截器兜底。
 */
@Slf4j
public class PageQuery<T> {

    public IPage<T> getPage(Map<String, Object> params) {
        return getPage(params, false, (String[]) null);
    }

    @SuppressWarnings("unchecked")
    public IPage<T> getPage(Map<String, Object> params, boolean isAsc, String... orderColumn) {
        int current = 1;
        int size = 10;

        current = parseIntSafely(removeParam(params, Constants.PAGE), 1);
        size = parseIntSafely(removeParam(params, Constants.SIZE), 10);

        Page<T> page = new Page<T>(current, size);
        if (orderColumn != null && orderColumn.length > 0) {
            page.setOrderColumn(orderColumn);
        }
        return page;
    }

    private static Object removeParam(Map<String, Object> params, String key) {
        if (params == null) {
            return null;
        }
        Object value = params.remove(key);
        if (value == null && key.equals(Constants.PAGE)) {
            // 兼容 current 命名
            value = params.remove("current");
        }
        return value;
    }

    private static int parseIntSafely(Object value, int defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        try {
            if (value instanceof Number) {
                return ((Number) value).intValue();
            }
            return Integer.parseInt(value.toString().trim());
        } catch (NumberFormatException e) {
            log.warn("分页参数非法[{}]，回退默认值{}", value, defaultValue);
            return defaultValue;
        }
    }
}
