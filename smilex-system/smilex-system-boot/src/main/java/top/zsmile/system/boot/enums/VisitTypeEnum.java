package top.zsmile.system.boot.enums;

/**
 * 访问类型
 * 1通用类型，2统一密码，3独立密码
 */
public enum VisitTypeEnum {
    COMMON(1),
    UNIFY(2),
    ISOLATE(3);

    private Integer value;


    private VisitTypeEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
