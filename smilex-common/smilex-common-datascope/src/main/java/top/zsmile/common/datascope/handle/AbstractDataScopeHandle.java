package top.zsmile.common.datascope.handle;


/**
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/03/06/10:46
 * @ClassName: AbstractHandle
 * @Description: AbstractHandle
 */
public abstract class AbstractDataScopeHandle implements DataScopeHandle {

    /**
     * 不进行操作
     */
    public static final String NIL = "nil";

    /**
     * 根据创建时间
     */
    public static final String CREATE_BY = "createBy";

}
