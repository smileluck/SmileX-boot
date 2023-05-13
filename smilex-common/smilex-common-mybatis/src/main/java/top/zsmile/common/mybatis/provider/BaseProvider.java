package top.zsmile.common.mybatis.provider;

import org.apache.ibatis.builder.annotation.ProviderContext;
import top.zsmile.common.mybatis.cache.TableInfoCache;
import top.zsmile.common.core.utils.NameStyleUtils;
import top.zsmile.common.mybatis.meta.TableInfo;
import top.zsmile.common.mybatis.utils.ReflectUtils;

public class BaseProvider {


    /**
     * 获取表信息
     *
     * @param providerContext
     * @return
     */
    protected TableInfo getTableInfo(ProviderContext providerContext) {
        TableInfo tableInfo = TableInfoCache.getTableInfo(providerContext);
        return tableInfo;
    }

    protected void validNullId(Object obj, TableInfo tableInfo) {
        if (ReflectUtils.getFieldValue(obj, NameStyleUtils.lineToHump(tableInfo.getPrimaryColumn(), false)) == null) {
            throw new IllegalArgumentException("主键不能为空");
        }
    }
}
