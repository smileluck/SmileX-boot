package top.zsmile.mybatis.meta.conditions;

import top.zsmile.mybatis.meta.StringPool;

import java.io.Serializable;

/**
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/04/24/15:06
 * @ClassName: SharedString
 * @Description: SharedString
 */
public class SharedString implements Serializable {
    private static final long serialVersionUID = -4683171963544844082L;

    private String value;

    private SharedString(String value) {
        this.value = value;
    }

    public static SharedString of() {
        return new SharedString(StringPool.EMPTY);
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
