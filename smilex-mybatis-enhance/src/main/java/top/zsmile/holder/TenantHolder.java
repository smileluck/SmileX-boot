package top.zsmile.holder;

import org.springframework.core.NamedThreadLocal;

import java.util.concurrent.ThreadLocalRandom;

public class TenantHolder {
    private static final ThreadLocal<Boolean> TENANT_IGNORE_HOLDER = new NamedThreadLocal<Boolean>("smilex-tenant") {
        @Override
        protected Boolean initialValue() {
            return Boolean.FALSE;
        }
    };

    public static void setIgnore(Boolean ignore) {
        TENANT_IGNORE_HOLDER.set(ignore);
    }

    public static Boolean isIgnore() {
        return TENANT_IGNORE_HOLDER.get();
    }

    public static void clearIgnore() {
        TENANT_IGNORE_HOLDER.remove();
    }

}
