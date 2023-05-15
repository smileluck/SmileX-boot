package top.zsmile.core.utils;

import top.zsmile.core.exception.SXException;

import java.util.Objects;

public class AssertUtils {
    public static void notNull(Object object, String errMsg) {
        if (Objects.isNull(object)) {
            throw new SXException(errMsg);
        }
    }
}
