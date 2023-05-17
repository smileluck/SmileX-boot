package top.zsmile.common.core.api;

import java.io.Serializable;

public interface IResultCode extends Serializable {
    int getCode();

    String getMessage();
}
