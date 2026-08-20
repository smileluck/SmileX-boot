package top.zsmile.common.mybatis.construct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import top.zsmile.common.mybatis.cache.TableInfoCache;

import top.zsmile.common.mybatis.dao.BaseMapper;
import top.zsmile.common.web.utils.SpringFileUtils;

import java.util.Map;

/**
 * 启动时预热 TableInfoCache（扫描根包下全部 BaseMapper 子接口）
 * <p>
 * 扫描路径可通过 smilex.mybatis.base-package 配置（默认 top/zsmile）；
 * 预热仅是优化，运行期 TableInfoCache 仍会按需惰性构建。
 */
@Slf4j
@Component
public class MybatisEnhanceInit implements InitializingBean {

    @Value("${smilex.mybatis.base-package:top/zsmile}")
    private String packageStr;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("===========start mybatis-enhance init===============");
        Map<String, Class> classByAnnotation = SpringFileUtils.getClassBySuperClass(packageStr + "/**/*.class", BaseMapper.class);

        int size = classByAnnotation.size();
        log.debug("mybatis-enhance size ==> " + size);

        TableInfoCache.initTableInfo(classByAnnotation);

        log.debug("===========over mybatis-enhance init===============");
    }
}
