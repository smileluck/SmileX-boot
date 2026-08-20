package top.zsmile.common.mybatis.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import top.zsmile.common.mybatis.cache.TableInfoCache;
import top.zsmile.common.mybatis.meta.TableInfo;
import top.zsmile.common.mybatis.utils.FieldEncryptor;

import java.sql.Statement;
import java.util.List;

/**
 * 字段解密拦截器
 * <p>
 * 拦截结果集处理，对带 @FieldEncrypt 注解的实体字段自动解密。
 * Map 形式的查询结果（selectMapById 等）无法关联实体元数据，不做解密。
 */
@Slf4j
@Intercepts({
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class}),
})
public class FieldDecryptInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object result = invocation.proceed();
        if (result instanceof List) {
            for (Object record : (List<?>) result) {
                decryptIfPossible(record);
            }
        }
        return result;
    }

    private void decryptIfPossible(Object record) {
        if (record == null || record instanceof java.util.Map) {
            return;
        }
        try {
            TableInfo tableInfo = TableInfoCache.getTableInfo(record.getClass());
            if (tableInfo.hasEncryptField()) {
                FieldEncryptor.decrypt(record, tableInfo);
            }
        } catch (Exception e) {
            // 非 TableInfo 管理的普通对象，跳过
        }
    }

    @Override
    public Object plugin(Object target) {
        return target instanceof ResultSetHandler ? Plugin.wrap(target, this) : target;
    }
}
