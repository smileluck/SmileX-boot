package top.zsmile.mybatis.meta;

import top.zsmile.common.constant.StringConstant;

public interface StringPool extends StringConstant {
    String ZERO_INDEX = "{0}";

    String WRAPPER = "ew";
    String WRAPPER_PARAM = "SXPARAMVAL";
    String WRAPPER_PARAM_FORMAT = "#{%s.paramPairs.%s}";
}
