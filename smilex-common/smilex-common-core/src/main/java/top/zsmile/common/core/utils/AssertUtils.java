package top.zsmile.common.core.utils;

import top.zsmile.common.core.exception.SXException;

import java.util.Objects;

public class AssertUtils {
    public static void notNull(Object object, String errMsg) {
        if (Objects.isNull(object)) {
            throw new SXException(errMsg);
        }
    }
}
